package com.crana.qcontroller.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crana.qcontroller.domain.DeviceConfig;
import com.crana.qcontroller.domain.Path;
import com.crana.qcontroller.service.DistanceCalculator;
import com.crana.qcontroller.service.ShortestPathFinder;


/**
 * @author ArunPrakash
 *
 */
public class ShortestPathFinderImpl implements ShortestPathFinder {
	private double maxTresholdDistance = 60000d;
	private DeviceConfig baseStationConfig;
	private List<DeviceConfig> devices = new ArrayList<DeviceConfig>();
	private DeviceConfig movingDeviceConfig;
	public ShortestPathFinderImpl(DeviceConfig baseStationConfig,
			List<DeviceConfig> devices, DeviceConfig movingDeviceConfig) {
		super();
		this.baseStationConfig = baseStationConfig;
		this.devices = devices;
		this.movingDeviceConfig = movingDeviceConfig;
	}
	
	/* (non-Javadoc)
	 * @see com.crana.qcontroller.service.ShortestPathFinder#findPath()
	 */
	@Override
	public Path findPath() {
		setMyDevices();
		List<Path> paths = findPath(baseStationConfig, movingDeviceConfig, new Path(baseStationConfig));
		paths = excludePathNotHave(paths, movingDeviceConfig);
		Path shortestPath = findShortestPath(paths);
		return shortestPath;
	}
	private Path findShortestPath(List<Path> paths) {
		Path shortestPath = null;
		for (Path path : paths) {
			if (shortestPath == null) {
				shortestPath = path;
			} else {
				if (shortestPath.getDistance() > path.getDistance()) {
					shortestPath = path;
				}
			}
		}
		return shortestPath;
	}

	private List<Path> excludePathNotHave(List<Path> paths, DeviceConfig deviceConfig) {
		List<Path> excludedPaths = new ArrayList<Path>();
		for (Path path : paths) {
			if (path.getDevices().contains(deviceConfig)) {
				excludedPaths.add(path);
			}
		}
		return excludedPaths;
	}

	private List<Path> findPath(DeviceConfig fromDevice,
			DeviceConfig toDevice, Path existingPath) {
		List<Path> paths = new ArrayList<Path>();
		if (isNotSameDevice(fromDevice, toDevice)) {
			for (DeviceConfig device : fromDevice.getDevices().values()) {
				if (!deviceAlreadyInPath(existingPath, device)) {
					Path path = new Path();
					path.append(existingPath);
					path.append(device);
					List<Path> newPaths = findPath(device, toDevice, path);
					paths.addAll(newPaths);
				}
			}
		}
		return paths.isEmpty()?Arrays.asList(existingPath):paths;
	}

	private boolean deviceAlreadyInPath(Path existingPath,
			DeviceConfig deviceConfig) {
		return existingPath != null && existingPath.getDevices().contains(deviceConfig);
	}

	private void setMyDevices() {
		List<DeviceConfig> allDevices = new ArrayList<DeviceConfig>(devices);
		allDevices.add(baseStationConfig);
		allDevices.add(movingDeviceConfig);
		for (DeviceConfig device1 : allDevices) {
			device1.getDevices().clear();
			for (DeviceConfig device2 : allDevices) {
				if (isNotSameDevice(device1, device2)) {
					double distance = getDistanceBetweenTwoDevices(device1, device2);
					if (distanceBelongsToThresholdLimit(distance)) {
						device1.getDevices().put(device2.getDeviceId(), device2);
					}
				}
			}
		}
	}

	private boolean distanceBelongsToThresholdLimit(double distance) {
		return distance <= maxTresholdDistance;
	}

	private boolean isNotSameDevice(DeviceConfig device1, DeviceConfig device2) {
		return !device1.getDeviceId().equalsIgnoreCase(device2.getDeviceId());
	}

	private double getDistanceBetweenTwoDevices(DeviceConfig baseStation0, DeviceConfig baseStation1) {
		double distance = DistanceCalculator.distance(baseStation0.getGpsLocation().getLatitude(), baseStation0.getGpsLocation().getLongitude(), 
				baseStation1.getGpsLocation().getLatitude(), baseStation1.getGpsLocation().getLongitude(), 8, 8);
		return distance;
	}
}
