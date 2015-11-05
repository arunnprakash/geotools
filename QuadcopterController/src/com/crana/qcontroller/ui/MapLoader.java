/**
 * 
 */
package com.crana.qcontroller.ui;

import java.awt.Color;
import java.io.File;

import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.GridReaderLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.FilterFactory2;
import org.opengis.style.ContrastMethod;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

/**
 * @author ArunPrakash
 *
 */
public class MapLoader extends Thread {
	private MapContent mapContent;
	private StyleFactory sf = CommonFactoryFinder.getStyleFactory();
	private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
	public MapLoader(MapContent mapContent) {
		this.mapContent = mapContent;
	}

	@Override
	public void run() {
		try {
			/*File raster = new File("E:\\NaturalEarthData\\data-v1_2\\data-v1_2\\earthlights.jpg");*/
			File raster = new File("E:\\NaturalEarthData\\data-v1_2\\data-v1_2\\earthlights.jpg");
			AbstractGridFormat format = GridFormatFinder.findFormat(raster);
			AbstractGridCoverage2DReader reader = format.getReader(raster);
			GridCoverage2D cov = reader.read(null);
			Style rasterStyle = createRGBStyle(cov);
			Layer rasterLayer = new GridReaderLayer(reader, rasterStyle);
			mapContent.addLayer(rasterLayer);
			
			FileDataStore store = FileDataStoreFinder.getDataStore(new File("E:\\NaturalEarthData\\data-v1_2\\data-v1_2\\countries.shp"));
	        SimpleFeatureSource featureSource = store.getFeatureSource();
	        Style style = SLD.createPolygonStyle(Color.YELLOW, null, 0.0f);//SLD.createSimpleStyle(featureSource.getSchema());
	        Layer shapeLayer = new FeatureLayer(featureSource, style);
	        mapContent.addLayer(shapeLayer);
	        Point circle = addCircleLayer(mapContent, featureSource);
	        moveCircle(circle);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
        
	}
	private void moveCircle(Point circle) {
	}

	private Point addCircleLayer(MapContent map, SimpleFeatureSource fs) throws Exception {
	     //-------------------------- BUILDS THE CIRCLE ON THE MAP ------------------------------------------//

        StyleFactory sf = CommonFactoryFinder.getStyleFactory(null);
        FilterFactory ff = CommonFactoryFinder.getFilterFactory2();

        SimpleFeatureType pointtype = DataUtilities.createType("Location", "the_geom:Point," + "name:String");

        SimpleFeatureBuilder sfb = new SimpleFeatureBuilder(pointtype);

        double longitude = 78.05309734513273d;
        double latitude = 10.46776232616942d;

        GeometryFactory geometryFactory= JTSFactoryFinder.getGeometryFactory(null);
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        sfb.add(point);
        DefaultFeatureCollection col = new DefaultFeatureCollection();
        SimpleFeature feature1 = sfb.buildFeature(null);
        col.add(feature1);


        org.geotools.styling.Stroke stroke2 = sf.createStroke(
                ff.literal(new Color(0xC8, 0x46, 0x63)),
                //circle thickness
                ff.literal(1)
        );

        org.geotools.styling.Fill fill2 = sf.createFill(
                ff.literal(Color.RED));


        //Type of symbol
        Mark mark = sf.getCircleMark();

        mark.setFill(fill2);
        mark.setStroke(stroke2);

        Graphic graphic = sf.createDefaultGraphic();
        graphic.graphicalSymbols().clear();
        graphic.graphicalSymbols().add(mark);

        //circle dimension on the map
        graphic.setSize(ff.literal(5));

        GeometryDescriptor geomDesc = fs.getSchema().getGeometryDescriptor();
        String geometryAttributeName = geomDesc.getLocalName();
        PointSymbolizer sym2 = sf.createPointSymbolizer(graphic, geometryAttributeName);

        Rule rule2 = sf.createRule();
        rule2.symbolizers().add(sym2);
        Rule rules2[] = {rule2};
        FeatureTypeStyle fts2 = sf.createFeatureTypeStyle(rules2);
        Style style2 = sf.createStyle();
        style2.featureTypeStyles().add(fts2);
        map.addLayer(new FeatureLayer(col, style2));
        return point;	
	}

	/**
	 * This method examines the names of the sample dimensions in the provided
	 * coverage looking for "red...", "green..." and "blue..." (case insensitive
	 * match). If these names are not found it uses bands 1, 2, and 3 for the red,
	 * green and blue channels. It then sets up a raster symbolizer and returns
	 * this wrapped in a Style.
	 *
	 * @return a new Style object containing a raster symbolizer set up for RGB
	 *         image
	 */
	private Style createRGBStyle(GridCoverage2D cov) {
		// We need at least three bands to create an RGB style
		int numBands = cov.getNumSampleDimensions();
		if (numBands < 3) {
			return null;
		}
		// Get the names of the bands
		String[] sampleDimensionNames = new String[numBands];
		for (int i = 0; i < numBands; i++) {
			GridSampleDimension dim = cov.getSampleDimension(i);
			sampleDimensionNames[i] = dim.getDescription().toString();
		}
		final int RED = 0, GREEN = 1, BLUE = 2;
		int[] channelNum = { -1, -1, -1 };
		// We examine the band names looking for "red...", "green...", "blue...".
		// Note that the channel numbers we record are indexed from 1, not 0.
		for (int i = 0; i < numBands; i++) {
			String name = sampleDimensionNames[i].toLowerCase();
			if (name != null) {
				if (name.matches("red.*")) {
					channelNum[RED] = i + 1;
				} else if (name.matches("green.*")) {
					channelNum[GREEN] = i + 1;
				} else if (name.matches("blue.*")) {
					channelNum[BLUE] = i + 1;
				}
			}
		}
		// If we didn't find named bands "red...", "green...", "blue..."
		// we fall back to using the first three bands in order
		if (channelNum[RED] < 0 || channelNum[GREEN] < 0 || channelNum[BLUE] < 0) {
			channelNum[RED] = 1;
			channelNum[GREEN] = 2;
			channelNum[BLUE] = 3;
		}

		// Now we create a RasterSymbolizer using the selected channels
		SelectedChannelType[] sct = new SelectedChannelType[cov
		                                                    .getNumSampleDimensions()];
		ContrastEnhancement ce = sf.contrastEnhancement(ff.literal(1.0),
				ContrastMethod.NORMALIZE);
		for (int i = 0; i < 3; i++) {
			sct[i] = sf.createSelectedChannelType(String.valueOf(channelNum[i]), ce);
		}
		RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
		ChannelSelection sel = sf.channelSelection(sct[RED], sct[GREEN], sct[BLUE]);
		sym.setChannelSelection(sel);

		return SLD.wrapSymbolizers(sym);
	}
	public void loadMap() {
		this.start();
	}
}
