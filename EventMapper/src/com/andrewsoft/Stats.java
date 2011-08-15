package com.andrewsoft;

import java.util.Date;

import android.R.integer;
import android.location.Location;


public class Stats {
	private double distanceTraveled;
	private float speed;
	private long startTimeMillis;
	private long endTimeMillis;
	private double elevationGain;
	private double currentElevation;
	private Location lastLocation;
	
	/****** Public Methods *****/
	public void update(Location location){
		if(this.startTimeMillis == 0)
			this.startTimeMillis = location.getTime();
		this.endTimeMillis = location.getTime();
		this.speed = location.getSpeed();
		this.currentElevation = location.getAltitude();
		if(this.lastLocation != null)
		{
			double elevationChange = location.getAltitude() - this.lastLocation.getAltitude();
			this.elevationGain += elevationChange > 0 ? elevationChange : 0;
			this.distanceTraveled += location.distanceTo(lastLocation);
		}
		
		this.lastLocation = location;
	}

	public double getDistanceTraveled() {
		return this.distanceTraveled;
	}

	public float getSpeed() {
		return this.speed;
	}

	public Date getStartTime() {
		return new Date(this.startTimeMillis);
	}

	public Date getEndTime() {
		return new Date(this.endTimeMillis);
	}

	public double getElevationGain() {
		return this.elevationGain;
	}
	
	public double getCurrentElevation(){
		return this.currentElevation;
	}
	
	public double getAverageSpeed(){
		long movingTime = Math.abs(endTimeMillis-startTimeMillis); 
		return distanceTraveled / ((double) movingTime / 1000);
	}
}
