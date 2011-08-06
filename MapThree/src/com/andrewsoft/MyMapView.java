package com.andrewsoft;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyMapView extends MapActivity {
	private MapView mapView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		GeoPoint start = DrawPath();
		mapView.getController().animateTo(start);
		mapView.getController().setZoom(15);
		this.createLocationManager();
		MyLocationOverlay myLocation = new MyLocationOverlay(this, mapView);
		myLocation.enableMyLocation();
		mapView.getOverlays().add(myLocation);
	}
	
	private void createLocationManager(){
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener(this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}

	private GeoPoint getPoint(double lat, double lon) {
		return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private GeoPoint DrawPath() {
		int color = Color.GREEN;
		Resources res = this.getResources();
		String[] waypoints = res.getStringArray(R.array.waypoints);
		Log.d("XXX", "Waypoint length: " + Integer.toString(waypoints.length));
		GeoPoint returnValue = null;
		try {
			JSONObject json1 = new JSONObject(waypoints[0]);
			double x1 = json1.getDouble("x");
			double y1 = json1.getDouble("y");
			GeoPoint start = this.getPoint(x1, y1);
			returnValue = start;
			GeoPoint end = this.getPoint(x1, y1);
			mapView.getOverlays().add(new MyOverlay(start, start, 1));
			for (int i = 1; i < waypoints.length; i++) {
				start = end;
				JSONObject json2 = new JSONObject(waypoints[i - 1]);
				double x2 = json2.getDouble("x");
				double y2 = json2.getDouble("y");
				end = this.getPoint(x2, y2);
				mapView.getOverlays().add(new MyOverlay(end, start, 2, color));
				Log.d("XXX", "Pair: " + json2.getString("x") +", " + json2.getString("y"));
			}
			mapView.getOverlays().add(new MyOverlay(end, end, 3));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}
}