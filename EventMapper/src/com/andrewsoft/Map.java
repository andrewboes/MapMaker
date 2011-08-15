package com.andrewsoft;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class Map extends MapActivity {
	private MapView mapView;
	private LocationListener locationListener;
	private LocationManager locationManager;
	private ArrayList<Location> locations = new ArrayList<Location>();
	private CurrentTrack overlay;
	private MyApp myApp;
	
	/******** Overrides ********/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		if(mapView == null)
		{
			mapView = (MapView) findViewById(R.id.mapview);
			mapView.setBuiltInZoomControls(true);
			this.createLocationObjects();
			overlay = new CurrentTrack();
			mapView.getOverlays().add(overlay);
			mapView.getController().animateTo(new GeoPoint(45000000, -123000000));
			mapView.getController().setZoom(15);
		}
		myApp = (MyApp) this.getApplicationContext();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// locationManager.removeUpdates(locationListener);
	}

	/******** Private Methods ********/
	private void addLocation(Location location) {
		locations.add(location);
		myApp.getStats().update(location);
		Log.d("EVENTMAPPER",
				"Num locations:" + Integer.toString(locations.size()));
		Log.d("EVENTMAPPER",
				"Current Speed:" + Float.toString(location.getSpeed()));
		Log.d("EVENTMAPPER",
				"Distance Traveled:"
						+ Double.toString(myApp.getStats()
								.getDistanceTraveled()));
		overlay.addPoint(location);
	}

	private void createLocationObjects() {
		locationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				if (location.getAccuracy() < 200)
					addLocation(location);
				// if recording
			}
		};
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);

		MyLocationOverlay myLocation = new MyLocationOverlay(this, mapView);
		myLocation.enableMyLocation();
		mapView.getOverlays().add(myLocation);
	}
}
