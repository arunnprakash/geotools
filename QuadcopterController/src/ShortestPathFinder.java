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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DeviceConfig baseStation0 = createBaseStation(10d, 70d, "MAIN_BASE_STATION", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation1 = createBaseStation(9d, 70d, "BASE_STATION_1", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation2 = createBaseStation(11d, 70d, "BASE_STATION_2", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation3 = createBaseStation(10d, 70d, "BASE_STATION_3", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation4 = createBaseStation(10d, 70d, "BASE_STATION_4", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation5 = createBaseStation(10d, 70d, "BASE_STATION_5", DeviceLocomotionType.STATIC);
		DeviceConfig baseStation6 = createBaseStation(10d, 70d, "MOVING_DEVICE", DeviceLocomotionType.DYNAMIC);
		double distance = getDistanceBetweenTwoDevices(baseStation0, baseStation1);
		System.out.println(distance);
		distance = getDistanceBetweenTwoDevices(baseStation0, baseStation2);
		System.out.println(distance);
	}

	private static double getDistanceBetweenTwoDevices(DeviceConfig baseStation0, DeviceConfig baseStation1) {
		double distance = DistanceCalculator.distance(baseStation0.getGpsLocation().getLatitude(), baseStation0.getGpsLocation().getLongitude(), 
				baseStation1.getGpsLocation().getLatitude(), baseStation1.getGpsLocation().getLongitude(), 8, 8);
		return distance;
	}

	private static DeviceConfig createBaseStation(double latitude, double longitude, String deviceName, DeviceLocomotionType locomotionType) {
		DeviceConfig deviceConfig = new DeviceConfig();
		deviceConfig.setDeviceName("MAIN_BASE_STATION");
		deviceConfig.setDeviceId(UUID.randomUUID().toString());
		deviceConfig.setLocomotionType(locomotionType);
		deviceConfig.setGpsLocation(new GpsLocation() {{
			setLatitude(latitude);
			setLongitude(longitude);
		}});
		return deviceConfig;
	}

}
