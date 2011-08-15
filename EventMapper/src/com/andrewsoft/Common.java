package com.andrewsoft;


public class Common {
	
	/**** MayType ****/
	private static String mapType;
	
	public static void setMapType(String value){
		mapType = value;
	}
	
	public static String getMapType(){
		return mapType;
	}
	
	/**** Recording ****/
	private static boolean recording;
	
	public static void setRecording(boolean value){
		recording = value;
	}
	
	public static boolean recordingStarted(){
		return recording;
	}
}
