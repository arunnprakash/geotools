import java.awt.event.ActionEvent;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

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
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.JMapPane;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.swing.dialog.JFileImageChooser;
import org.opengis.filter.FilterFactory2;
import org.opengis.style.ContrastMethod;

public class SaveMapAsImage {

	private JMapFrame frame;
	private MapContent mapContent;

	public static void main(String[] args) throws IOException {
		File file = null;
		File raster = null;
		if (args.length == 0) {
			raster = JFileImageChooser.showOpenFile(null);
			// display a data store file chooser dialog for shapefiles
			file = JFileDataStoreChooser.showOpenFile("shp", null);
			if (file == null) {
				return;
			}
		} else {
			file = new File(args[0]);
			if (!file.exists()) {
				System.err.println(file + " doesn't exist");
				return;
			}
			raster = new File(args[1]);
			if (!raster.exists()) {
				System.err.println(raster + " doesn't exist");
				return;
			}
		}
		new SaveMapAsImage(file, raster);
	}

	public SaveMapAsImage(File file, File raster) throws IOException {

		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();

		// Create a map content and add our shapefile to it
		mapContent = new MapContent();
		mapContent.setTitle("GeoTools Mapping");
		AbstractGridFormat format = GridFormatFinder.findFormat(raster);
		AbstractGridCoverage2DReader reader = format.getReader(raster);
		GridCoverage2D cov;
		try {
			cov = reader.read(null);
		} catch (IOException giveUp) {
			throw new RuntimeException(giveUp);
		}
		Style rasterStyle = createRGBStyle(cov);
		Layer rasterLayer = new GridReaderLayer(reader, rasterStyle);
		mapContent.addLayer(rasterLayer);

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);
		mapContent.addLayer(layer);
		mapContent.getViewport().setCoordinateReferenceSystem(
				DefaultGeographicCRS.WGS84);
		frame = new JMapFrame(mapContent);
		frame.enableStatusBar(true);
		frame.enableToolBar(true);
		JToolBar toolBar = frame.getToolBar();
		toolBar.addSeparator();
		SaveAction save = new SaveAction("Save");
		toolBar.add(save);
		frame.initComponents();
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}

	public void drawMapToImage(File outputFile, String outputType) {
		JMapPane mapPane = frame.getMapPane();
		ImageOutputStream outputImageFile = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(outputFile);
			outputImageFile = ImageIO.createImageOutputStream(fileOutputStream);
			RenderedImage bufferedImage = mapPane.getBaseImage();
			ImageIO.write(bufferedImage, outputType, outputImageFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (outputImageFile != null) {
					outputImageFile.flush();
					outputImageFile.close();
					fileOutputStream.flush();
					fileOutputStream.close();
				}
			} catch (IOException e) {// don't care now
			}
		}
	}

	private class SaveAction extends AbstractAction {
		/**
		 * Private SaveAction
		 */
		private static final long serialVersionUID = 3071568727121984649L;

		public SaveAction(String text) {
			super(text);
		}

		public void actionPerformed(ActionEvent arg0) {
			String[] writers = ImageIO.getWriterFormatNames();

			String format = (String) JOptionPane.showInputDialog(frame,
					"Choose output format:", "Customized Dialog",
					JOptionPane.PLAIN_MESSAGE, null, writers, "png");
			drawMapToImage(new File("ian." + format), format);

		}

	}

	private StyleFactory sf = CommonFactoryFinder.getStyleFactory();
	private FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

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

}