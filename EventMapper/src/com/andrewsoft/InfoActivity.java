package com.andrewsoft;

import java.util.Date;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.widget.TextView;

public class InfoActivity extends Activity{
	private MyApp myApp;
	private RefreshHandler redrawHandler;
	private TextView distance;
	private TextView averageSpeed;
	private TextView elevationGain;
	private TextView currentElevation;
	private TextView speed;
	private TextView totalTime;
	
	private class RefreshHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			update();
		}
		
		public void sleep(long delayMillis){
			this.removeMessages(0);
			sendMessageDelayed(obtainMessage(0), delayMillis);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		myApp = (MyApp)this.getApplicationContext();		
		 
		distance = (TextView) findViewById(R.id.infoDistanceTraveledValue);
		averageSpeed = (TextView) findViewById(R.id.infoAverageSpeedValue);
		elevationGain = (TextView) findViewById(R.id.infoElevationGainValue);
		speed = (TextView) findViewById(R.id.infoSpeedValue);
		currentElevation = (TextView) findViewById(R.id.infoCurrentElevationValue);
		totalTime = (TextView) findViewById(R.id.infoTotalTimeValue);
		redrawHandler = new RefreshHandler();
		
		this.update();
	}
	
	private void update(){
		distance.setText(metersToMiles(myApp.getStats().getDistanceTraveled()));
		averageSpeed.setText(mpsToMph(myApp.getStats().getAverageSpeed()));
		elevationGain.setText(metersToFeet(myApp.getStats().getElevationGain()));
		speed.setText(mpsToMph(myApp.getStats().getSpeed()));
		currentElevation.setText(metersToFeet(myApp.getStats().getCurrentElevation()));
		
		long travelTime = myApp.getStats().getTotalTime();
		totalTime.setText(this.convertMillisToHoursMinutesString(travelTime));
		redrawHandler.sleep(200);
	}
	
	private String metersToMiles(double meters)	{
		return Double.toString(0.000621371192 * meters);
	}
	
	private String mpsToMph(double mps){
		return Double.toString(2.23693629 * mps);
	}
	
	private String metersToFeet(double meters){
		return Double.toString(3.2808399 * meters);
	}
	
	private String convertMillisToHoursMinutesString(long millis) {       
	    String format = String.format("%%0%dd", 2);  
	    millis = millis / 1000;  
	    String seconds = String.format(format, millis % 60);  
	    String minutes = String.format(format, (millis % 3600) / 60);  
	    String hours = String.format(format, millis / 3600);  
	    String time =  hours + ":" + minutes + ":" + seconds;  
	    return time;  
	}  
}
