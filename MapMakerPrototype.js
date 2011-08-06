<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false">
	</script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?libraries=geometry&sensor=false"></script>
	<script src="@Url.Content("~/Scripts/jquery-1.5.1.js")" type="text/javascript"></script>
	<script type="text/javascript">
		var markers = [];
		var line;
		var elevationService;
		var chart;
		var directionsService;
		var directionsLine;
		var map;
		var myChange;

		google.load("visualization", "1", { packages: ["columnchart"] });

		$(document).ready(function () {
			var latlng = new google.maps.LatLng(45, -123);
			var myOptions = {
				zoom: 8,
				center: latlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
			map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
			google.maps.event.addListener(map, 'click', function (event) {
				var marker = new google.maps.Marker({ position: event.latLng, map: map });
				markers.push(marker);
				if (markers.length > 1) {
					//getDirections();
					updateMap();
				}
			});
			elevationService = new google.maps.ElevationService();
			directionsService = new google.maps.DirectionsService();
			directionsLine = new google.maps.DirectionsRenderer();

		});

		function getDirections() {
			directionsLine.setMap(map);
			
			var start = markers[markers.length - 2].position;
			var end = markers[markers.length - 1].position;
			var request = {
				origin: start,
				destination: end,
				travelMode: google.maps.TravelMode.WALKING  
			};

			directionsService.route(request, function (result, status) {
				if (status == google.maps.DirectionsStatus.OK) {
					directionsLine.setDirections(result);
				}
			});
		}

		function updateMap() {
			if (null != line)
				line.setMap(null);
			var coordinates = [];
			$.each(markers, function (index, value) {
				coordinates.push(value.position);
			});
			updateLine(coordinates, map)
			updateDistanceTraveled(coordinates);
			getElevation(coordinates);
		}

		function updateLine(coordinates, map) {
			line = new google.maps.Polyline({
				path: coordinates,
				strokeColor: "#FF0000",
				strokeOpacity: .5,
				strokeWeight: 2,
				map: map
			});
		}

		function updateDistanceTraveled(coordinates) {
			var distanceMiles = google.maps.geometry.spherical.computeLength(coordinates, 3963.19);
			var distanceKilometers = google.maps.geometry.spherical.computeLength(coordinates) / 1000;
			if (distanceMiles > 0) {
				$("#distance").empty();
				$("#distance").html("Miles: " + parseFloat(distanceMiles).toFixed(2) + ", Km: " + parseFloat(distanceKilometers).toFixed(2));
			}
		}

		function getElevation(coordinates) {
			chart = new google.visualization.ColumnChart(document.getElementById('chart_canvas'));
			var pathRequest = { 'path': coordinates, 'samples': 256 };
			elevationService.getElevationAlongPath(pathRequest, plotElevation);

		}

		function plotElevation(results, status) {
			if (status == google.maps.ElevationStatus.OK) {
				elevations = results;
				var totalGain = 0;
				var first = elevations[0].elevation;

				// Extract the elevation samples from the returned results
				// and store them in an array of LatLngs.
				var elevationPath = [];
				for (var i = 0; i < results.length; i++) {
					elevationPath.push(elevations[i].location);
				}

				// Extract the data from which to populate the chart.
				// Because the samples are equidistant, the 'Sample'
				// column here does double duty as distance along the
				// X axis.
				var data = new google.visualization.DataTable();
				data.addColumn('string', 'Sample');
				data.addColumn('number', 'Elevation');
				for (var i = 0; i < results.length; i++) {
					data.addRow(['', elevations[i].elevation]);
					if (i != 0 && elevations[i].elevation > elevations[i - 1].elevation) {
						totalGain += elevations[i].elevation - elevations[i - 1].elevation;
					}
				}
				$("#distance").append(", Elevation Gain: " + totalGain * 3.2808399);

				// Draw the chart using the data within its DIV.
				document.getElementById('chart_canvas').style.display = 'block';
				chart.draw(data, {
					width: 640,
					height: 100,
					legend: 'none',
					titleY: 'Elevation (m)'
				});
			}
		}

		function clearMap() {
			for (i in markers) { markers[i].setMap(null); }
			markers.length = 0;
			line.setMap(null);
			$("#distance").html("Please click two more more points");
			$("#chart_canvas").empty();
		}

		function deleteLast() {
			markers[markers.length - 1].setMap(null);
			markers.pop();
			updateMap();
		}
	</script>