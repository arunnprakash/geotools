package com.crana.qcontroller.domain;
import java.util.ArrayList;
import java.util.List;

import com.crana.qcontroller.service.DistanceCalculator;

/**
 * 
 */

/**
 * @author SN2528
 *
 */
public class Path {
	private List<DeviceConfig> devices = new ArrayList<DeviceConfig>();

	public Path(Path path) {
		super();
		if (path != null) {
			this.append(path.getDevices());
		}
	}

	public Path() {
	}

	public Path(DeviceConfig device) {
		this.append(device);
	}

	public List<DeviceConfig> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceConfig> devices) {
		this.devices = devices;
	}

	public void append(List<DeviceConfig> devices) {
		if (devices != null && !devices.isEmpty()) {
			this.devices.addAll(devices);
		}
	}
	
	public void append(DeviceConfig device) {
		if (device != null) {
			this.devices.add(device);
		}
	}

	public void append(Path path) {
		if (path != null) {
			this.append(path.getDevices());
		}
	}
	
	public double getDistance() {
		double distance = 0;
		if (!devices.isEmpty()) {
			DeviceConfig prevBaseStation = null;
			for (int i = 0; i < devices.size(); i++) {
				DeviceConfig currentBaseStation = devices.get(i);
				if (prevBaseStation != null) {
					distance = distance + DistanceCalculator.distance(prevBaseStation.getGpsLocation().getLatitude(), prevBaseStation.getGpsLocation().getLongitude(), 
							currentBaseStation.getGpsLocation().getLatitude(), currentBaseStation.getGpsLocation().getLongitude(), 8, 8);
				}
				prevBaseStation = currentBaseStation;
			}
		}
		return distance;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{path =");
		for (DeviceConfig device : devices) {
			sb.append(" "+device.getDeviceName());
		}
		sb.append("; distance="+getDistance()+"}");
		return sb.toString();
	}
}
