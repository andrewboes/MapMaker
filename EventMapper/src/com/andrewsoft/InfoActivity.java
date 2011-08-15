package com.andrewsoft;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class InfoActivity extends Activity{
	private MyApp myApp;
	private RefreshHandler redrawHandler;
	private TextView distance;
	private TextView averageSpeed;
	private TextView elevationGain;
	
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
		this.redrawHandler = new RefreshHandler();
		
		this.update();
	}
	
	private void update(){
		distance.setText(Double.toString(myApp.getStats().getDistanceTraveled()));
		averageSpeed.setText(Double.toString(myApp.getStats().getAverageSpeed()));
		elevationGain.setText(Double.toString(myApp.getStats().getElevationGain()));
		redrawHandler.sleep(200);
	}
}
