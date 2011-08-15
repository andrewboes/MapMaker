package com.andrewsoft;

import android.app.Application;

import com.google.android.maps.MapView;

public class MyApp extends Application {
	private Stats myStats;
	private MapView mapView;

	public Stats getStats() {
		return myStats;
	}
	
	public MyApp(){
		this.myStats = new Stats();
	}
}
