import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.DeviceLocomotionType;
import com.crana.qcontroller.domain.GpsLocation;
import com.crana.qcontroller.service.DistanceCalculator;


/**
 * 
 */

/**
 * @author ArunPrakash
 *
 */
public class ShortestPathFinder {
	private DeviceConfig baseStationConfig;
	private List<DeviceConfig> devices = new ArrayList<DeviceConfig>();
	private DeviceConfig movingDeviceConfig;
	public ShortestPathFinder(DeviceConfig baseStationConfig,
			List<DeviceConfig> devices, DeviceConfig movingDeviceConfig) {
		super();
		this.baseStationConfig = baseStationConfig;
		this.devices = devices;
		this.movingDeviceConfig = movingDeviceConfig;
	}
	
	public ShortestPathFinder() {
		initDeviceLocation();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ShortestPathFinder pathFinder = new ShortestPathFinder();
		pathFinder.findPath();
	}

	private void findPath() {
		// TODO Auto-generated method stub
		
	}
	private static double getDistanceBetweenTwoDevices(DeviceConfig baseStation0, DeviceConfig baseStation1) {
		double distance = DistanceCalculator.distance(baseStation0.getGpsLocation().getLatitude(), baseStation0.getGpsLocation().getLongitude(), 
				baseStation1.getGpsLocation().getLatitude(), baseStation1.getGpsLocation().getLongitude(), 8, 8);
		return distance;
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
