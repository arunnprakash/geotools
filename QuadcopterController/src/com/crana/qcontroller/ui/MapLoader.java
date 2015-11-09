/**
 * 
 */
package com.crana.qcontroller.ui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.GridReaderLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.opengis.filter.FilterFactory2;
import org.opengis.style.ContrastMethod;

import com.crana.qcontroller.domain.DeviceConfig;

/**
 * @author ArunPrakash
 *
 */
public class MapLoader extends Thread {
	private DeviceConfig deviceConfig;
	private MapContent mapContent;
	private StyleFactory sf = CommonFactoryFinder.getStyleFactory();
	private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
	private final String shapeFile = "C:\\Users\\sn2528\\Downloads\\ne_110m_admin_0_countries\\ne_110m_admin_0_countries.shp";
	private final String rasterFile = "C:\\Users\\sn2528\\Downloads\\earthlights.tiff";
	private DeviceLocationPlotterLayer deviceLocationPlotterLayer;
	public MapLoader(MapContent mapContent) {
		this.mapContent = mapContent;
	}

	@Override
	public void run() {
		try {
			//initRasterLayer();
			initShapeLayer();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
public void setDeviceConfig(DeviceConfig deviceConfig) {
	this.deviceConfig = deviceConfig;
	initDevicesLayer();
}
	private void initDevicesLayer() {
		deviceLocationPlotterLayer = new DeviceLocationPlotterLayer(20, deviceConfig.getGpsLocation().getLatitude(), deviceConfig.getGpsLocation().getLongitude());
		mapContent.addLayer(deviceLocationPlotterLayer);
	}
	private SimpleFeatureSource initShapeLayer() throws IOException {
		FileDataStore store = FileDataStoreFinder.getDataStore(new File(shapeFile));
		SimpleFeatureSource featureSource = store.getFeatureSource();
		Style style = SLD.createPolygonStyle(Color.YELLOW, null, 0.0f);
		Layer shapeLayer = new FeatureLayer(featureSource, style);
		mapContent.addLayer(shapeLayer);
		return featureSource;
	}

	private void initRasterLayer() throws IOException {
		File raster = new File(rasterFile);
		AbstractGridFormat format = GridFormatFinder.findFormat(raster);
		AbstractGridCoverage2DReader reader = format.getReader(raster);
		GridCoverage2D cov = reader.read(null);
		Style rasterStyle = createRGBStyle(cov);
		Layer rasterLayer = new GridReaderLayer(reader, rasterStyle);
		mapContent.addLayer(rasterLayer);
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
