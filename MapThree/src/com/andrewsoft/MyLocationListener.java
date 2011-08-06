package com.andrewsoft;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

	/**
	 * Class to implement LocationListener. This handles event related to getting new location information.
	 * @author boesan
	 */
	public class MyLocationListener implements LocationListener	{
	
		private Context myContext;
		
		public MyLocationListener(Context context){
			myContext = context;
		}
		
	@Override
    public void onLocationChanged(Location loc) {
		//import android.content.Context;
	    loc.getLatitude();
	    loc.getLongitude();
//	    String Text = "My current location is: Latitude = " + loc.getLatitude() + "Longitude = " + loc.getLongitude();
//	    Toast.makeText( myContext.getApplicationContext(), Text, Toast.LENGTH_SHORT).show();
    }

	@Override
    public void onProviderDisabled(String provider)    {
		Toast.makeText( myContext.getApplicationContext(), "Gps Disabled",Toast.LENGTH_SHORT ).show();
    }

	@Override
    public void onProviderEnabled(String provider){
		Toast.makeText( myContext.getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
    }

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
