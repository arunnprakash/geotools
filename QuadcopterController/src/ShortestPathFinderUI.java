import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.event.MapMouseEvent;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.domain.GpsLocation;

/**
 * 
 */

/**
 * @author SN2528
 *
 */
public class ShortestPathFinderUI {
	private JMapFrame frame;
	private MapContent mapContent;
	private final String shapeFile = "C:\\Users\\sn2528\\Downloads\\ne_110m_admin_0_countries\\ne_110m_admin_0_countries.shp";
	private DeviceLocationPlotter deviceLocationPlotter;
	private DeviceConfig baseStationConfig;
	private DeviceConfig movingDeviceConfig;
	private List<DeviceConfig> devices = new ArrayList<DeviceConfig>();
	private ShortestPathFinder shortestPathFinder;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ShortestPathFinderUI pathFinderUI = new ShortestPathFinderUI();
		pathFinderUI.init();
	}

	private void init() throws Exception {
		initDeviceLocation();
		mapContent = new MapContent();
		mapContent.setTitle("Shortest Path Finder");
		initShapeLayer();
		initDevicesLayer();
		final double minX = 66.37339792733152;
		final double maxX = 73.31514934151357;
		final double minY = 12.203484399037665;
		final double maxY = 7.575650122916295;
		
		frame = new JMapFrame(mapContent);
		ReferencedEnvelope initialArea = new ReferencedEnvelope(minX, maxX,
				minY, maxY, frame.getMapPane().getDisplayArea().getCoordinateReferenceSystem());
		frame.getMapPane().setDisplayArea(initialArea);
		frame.getMapPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MapMouseEvent mme = frame.getMapPane().getMouseEventDispatcher().convertEvent(e);
				double longitude = mme.getWorldPos().getX();
				double latitude = mme.getWorldPos().getY();
				System.out.println("latitude:: " + latitude);
				System.out.println("longitute:: " + longitude);
				movingDeviceConfig.getGpsLocation().setLatitude(latitude);
				movingDeviceConfig.getGpsLocation().setLongitude(longitude);
				deviceLocationPlotter.preDispose();
				deviceLocationPlotter.dispose();
				mapContent.removeLayer(deviceLocationPlotter);
				deviceLocationPlotter = new DeviceLocationPlotter(baseStationConfig, movingDeviceConfig, devices);
				mapContent.addLayer(deviceLocationPlotter);
				frame.getMapPane().updateUI();
			}
		});
		frame.enableStatusBar(true);
		frame.enableToolBar(true);
		frame.initComponents();
		frame.setSize(1200, 900);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void initDevicesLayer() {
		deviceLocationPlotter = new DeviceLocationPlotter(baseStationConfig, movingDeviceConfig, devices);
		mapContent.addLayer(deviceLocationPlotter);
	}

	private void initDeviceLocation() {
		baseStationConfig = createBaseStation(10d, 70d, "MB", DeviceLocomotionType.STATIC, true);
		devices.add(createBaseStation(9.5d, 70d, "P1", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(9d, 71d, "P2", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(10d, 70.5d, "P3", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(10.5d, 71d, "P4", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(11d, 70d, "P5", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(10.5d, 69.8d, "P6", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(10d, 69d, "P7", DeviceLocomotionType.STATIC, false));
		devices.add(createBaseStation(9.8d, 69.5d, "P8", DeviceLocomotionType.STATIC, false));
		movingDeviceConfig = createBaseStation(11d, 69d, "RP", DeviceLocomotionType.DYNAMIC, false);
		shortestPathFinder = new ShortestPathFinder(baseStationConfig, devices, movingDeviceConfig);
	}

	private SimpleFeatureSource initShapeLayer() throws IOException {
		FileDataStore store = FileDataStoreFinder.getDataStore(new File(shapeFile));
		SimpleFeatureSource featureSource = store.getFeatureSource();
		Style style = SLD.createPolygonStyle(Color.YELLOW, null, 0.0f);
		Layer shapeLayer = new FeatureLayer(featureSource, style);
		mapContent.addLayer(shapeLayer);
		return featureSource;
	}

	private static DeviceConfig createBaseStation(final double latitude, final double longitude, String deviceName, DeviceLocomotionType locomotionType, boolean isBaseStation) {
		DeviceConfig deviceConfig = new DeviceConfig();
		deviceConfig.setDeviceName(deviceName);
		deviceConfig.setDeviceId(UUID.randomUUID().toString());
		deviceConfig.setBaseStation(isBaseStation);
		deviceConfig.setLocomotionType(locomotionType);
		deviceConfig.setGpsLocation(new GpsLocation() {{
			setLatitude(latitude);
			setLongitude(longitude);
		}});
		return deviceConfig;
	}
}
