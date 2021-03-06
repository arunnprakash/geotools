package com.crana.qcontroller.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.DirectLayer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.domain.Path;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * @author SN2528
 *
 */
public class DeviceLocationPlotter extends DirectLayer {
	private GeometryFactory geometryFactory= JTSFactoryFinder.getGeometryFactory(null);
	boolean graphicsInitialized = false;
	private double diameter = 24;
	private DeviceConfig baseStationConfig;
	private DeviceConfig movingDeviceConfig;
	private List<DeviceConfig> devices;
	private Path shortestPath;
	
	private Graphics2D graphics;
	private MapContent map;
	private MapViewport mapViewPort;

	public DeviceLocationPlotter(DeviceConfig baseStationConfig,
			DeviceConfig movingDeviceConfig, List<DeviceConfig> devices, Path shortestPath) {
		this.baseStationConfig = baseStationConfig;
		this.movingDeviceConfig = movingDeviceConfig;
		this.devices = devices;
		this.shortestPath = shortestPath;
	}

	private Point createCoOridinationPoint(DeviceConfig deviceConfig) {
		return geometryFactory.createPoint(new Coordinate(deviceConfig.getGpsLocation().getLongitude(), deviceConfig.getGpsLocation().getLatitude()));
	}

	/* (non-Javadoc)
	 * @see org.geotools.map.DirectLayer#draw(java.awt.Graphics2D, org.geotools.map.MapContent, org.geotools.map.MapViewport)
	 */
	@Override
	public void draw(Graphics2D graphics, MapContent map, MapViewport mapViewPort) {
		this.graphics = graphics;
		this.map = map;
		this.mapViewPort = mapViewPort;
		AffineTransform worldToScreen = mapViewPort.getWorldToScreen();
		createCircle(graphics, worldToScreen, baseStationConfig);
		createCircle(graphics, worldToScreen, movingDeviceConfig);
		for (DeviceConfig deviceConfig : devices) {
			createCircle(graphics, worldToScreen, deviceConfig);
		}
		createLineForMyDevices(graphics, worldToScreen, baseStationConfig);
		createLineForMyDevices(graphics, worldToScreen, movingDeviceConfig);
		for (DeviceConfig deviceConfig : devices) {
			createLineForMyDevices(graphics, worldToScreen, deviceConfig);
		}
		createLineForShortestPath(graphics, worldToScreen);
		graphicsInitialized = true;
	}

	private void createLineForShortestPath(Graphics2D graphics,
			AffineTransform worldToScreen) {
		graphics.setColor(Color.GREEN);
		if (shortestPath != null && !shortestPath.getDevices().isEmpty()) {
			DeviceConfig prevBaseStation = null;
			for (int i = 0; i < shortestPath.getDevices().size(); i++) {
				DeviceConfig currentBaseStation = shortestPath.getDevices().get(i);
				if (prevBaseStation != null) {
					Point device = createCoOridinationPoint(prevBaseStation);
					Point2D worldPoint = new Point2D.Double(device.getX(), device.getY());
					Point2D screenPoint = worldToScreen.transform(worldPoint, null);
					Point nearByDevice = createCoOridinationPoint(currentBaseStation);
					Point2D nearByDeviceWorldPoint = new Point2D.Double(nearByDevice.getX(), nearByDevice.getY());
					Point2D nearByDeviceScreenPoint = worldToScreen.transform(nearByDeviceWorldPoint, null);
					graphics.drawLine((int)screenPoint.getX(), (int)screenPoint.getY(), (int)nearByDeviceScreenPoint.getX(), (int)nearByDeviceScreenPoint.getY());
				}
				prevBaseStation = currentBaseStation;
			}
		}
	}

	private void createLineForMyDevices(Graphics2D graphics2, AffineTransform worldToScreen,
			DeviceConfig deviceConfig) {
		Point device = createCoOridinationPoint(deviceConfig);
		Point2D worldPoint = new Point2D.Double(device.getX(), device.getY());
		Point2D screenPoint = worldToScreen.transform(worldPoint, null);
		graphics.setColor(Color.GRAY);
		for (DeviceConfig nearByDeviceConfig : deviceConfig.getDevices().values()) {
			Point nearByDevice = createCoOridinationPoint(nearByDeviceConfig);
			Point2D nearByDeviceWorldPoint = new Point2D.Double(nearByDevice.getX(), nearByDevice.getY());
			Point2D nearByDeviceScreenPoint = worldToScreen.transform(nearByDeviceWorldPoint, null);
			graphics.drawLine((int)screenPoint.getX(), (int)screenPoint.getY(), (int)nearByDeviceScreenPoint.getX(), (int)nearByDeviceScreenPoint.getY());
		}
	}

	private void createCircle(Graphics2D graphics, AffineTransform worldToScreen, DeviceConfig deviceConfig) {
		Point device = createCoOridinationPoint(deviceConfig);
		Point2D worldPoint = new Point2D.Double(device.getX(), device.getY());
		Point2D screenPoint = worldToScreen.transform(worldPoint, null);
		if (deviceConfig.getLocomotionType().equals(DeviceLocomotionType.STATIC)){
			if (deviceConfig.isBaseStation()) {
				graphics.setColor(Color.GREEN);
			} else {
				graphics.setColor(Color.BLUE);
			}
		} else {
			graphics.setColor(Color.RED);
		}
		Shape theCircle = new Ellipse2D.Double(screenPoint.getX() - diameter/2, screenPoint.getY() - diameter/2, diameter, diameter);
		graphics.draw(theCircle);
		//graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) .5));
		graphics.fill(theCircle);
		graphics.setColor(Color.WHITE);
		graphics.drawString(deviceConfig.getDeviceName(), (float)(screenPoint.getX() + 5 - diameter/2), (float)(screenPoint.getY() + 5));
	}

	/* (non-Javadoc)
	 * @see org.geotools.map.Layer#getBounds()
	 */
	@Override
	public ReferencedEnvelope getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateUI() {
		if (graphicsInitialized) {
			graphics.setColor(Color.WHITE);
			graphics.clearRect((int)graphics.getClipBounds().getMinX(), (int)graphics.getClipBounds().getMinY(), (int)graphics.getClipBounds().getMaxX(), (int)graphics.getClipBounds().getMaxY());
			this.draw(graphics, map, mapViewPort);
		}
	}

}
