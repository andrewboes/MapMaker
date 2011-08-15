package com.andrewsoft;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class CurrentTrack extends Overlay {

	private ArrayList<GeoPoint> points = new ArrayList<GeoPoint>(1024);

	/******* Public Methods *******/
	public void addPoint(Location location) {
		this.points.add(getPointFromLocation(location));
	}

	public void addPoints(List<Location> locations) {
		for (Location location : locations) {
			this.addPoint(location);
		}
	}
	
	/****** Private Methods *******/
	private GeoPoint getPointFromLocation(Location location) {
		int x = (int) (location.getLatitude() * 1E6);
		int y = (int) (location.getLongitude() * 1E6);
		return new GeoPoint(x, y);
	}
	
	/******* Overlay Overrides *******/
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		final Projection projection = mapView.getProjection();

		int numPoints = points.size();

		if (numPoints > 1) {
			for (int i = 0; i < points.size() - 1; i++) {
				GeoPoint gp1 = points.get(i);
				GeoPoint gp2 = points.get(i+1);
				Paint paint = new Paint();
				paint.setDither(true);
				paint.setColor(Color.RED);
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				paint.setStrokeJoin(Paint.Join.ROUND);
				paint.setStrokeCap(Paint.Cap.ROUND);
				paint.setStrokeWidth(4);
				paint.setAlpha(80);

				Point p1 = new Point();
				Point p2 = new Point();

				Path path = new Path();

				projection.toPixels(gp1, p1);
				projection.toPixels(gp2, p2);

				path.moveTo(p2.x, p2.y);
				path.lineTo(p1.x, p1.y);

				canvas.drawPath(path, paint);
			}
		}
	}
}
