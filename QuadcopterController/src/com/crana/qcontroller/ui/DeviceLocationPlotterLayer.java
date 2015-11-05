package com.crana.qcontroller.ui;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.DirectLayer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.map.event.MapLayerEvent;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * @author SN2528
 *
 */
public class DeviceLocationPlotterLayer extends DirectLayer {
	private GeometryFactory geometryFactory= JTSFactoryFinder.getGeometryFactory(null);
	boolean graphicsInitialized = false;
	private double diameter = 20;
	private Point circleCenterPoint;

	private Graphics2D graphics;
	private MapContent map;
	private MapViewport mapViewPort;
	public DeviceLocationPlotterLayer(double diameter, Point circleCenterPoint) {
		super();
		this.diameter = diameter;
		this.circleCenterPoint = circleCenterPoint;
	}

	public DeviceLocationPlotterLayer(double diameter, double latitude, double longitude) {
		super();
		this.diameter = diameter;
		this.circleCenterPoint = geometryFactory.createPoint(new Coordinate(longitude, latitude));
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
		Point2D worldPoint = new Point2D.Double(circleCenterPoint.getX(), circleCenterPoint.getY());
		Point2D screenPoint = worldToScreen.transform(worldPoint, null);
		Shape theCircle = new Ellipse2D.Double(screenPoint.getX() - diameter/2, screenPoint.getY() - diameter/2, diameter, diameter);
        graphics.draw(theCircle);
        graphics.setColor(Color.BLUE);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,(float) .5));
        graphics.fill(theCircle);
        graphicsInitialized = true;
	}

	/* (non-Javadoc)
	 * @see org.geotools.map.Layer#getBounds()
	 */
	@Override
	public ReferencedEnvelope getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setCenterPoint(double latitude, double longitude) {
		System.out.println("setCenterPoint");
		this.circleCenterPoint = geometryFactory.createPoint(new Coordinate(longitude, latitude));
		if (graphicsInitialized) {
			refresh();
		}
	}
	
	public void setCenterPoint(Point point) {
		System.out.println("setCenterPoint");
		this.circleCenterPoint = point;
		if (graphicsInitialized) {
			refresh();
		}
	}

	private void refresh() {
		System.out.println("graphicsInitialized");
		//this.draw(graphics, map, mapViewPort);
		this.fireMapLayerListenerLayerChanged(MapLayerEvent.DATA_CHANGED);
		this.fireMapLayerListenerLayerChanged(MapLayerEvent.VISIBILITY_CHANGED);
		this.fireMapLayerListenerLayerChanged(MapLayerEvent.PRE_DISPOSE);
		this.fireMapLayerListenerLayerChanged(MapLayerEvent.STYLE_CHANGED);
		this.fireMapLayerListenerLayerChanged(MapLayerEvent.VISIBILITY_CHANGED);
	}

}
