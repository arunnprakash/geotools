/**
 * 
 */
package com.crana.qcontroller.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ArunPrakash
 *
 */
@JsonInclude(Include.NON_NULL)
public class DeviceConfig implements Serializable {
	@JsonProperty("dN")
	private String deviceName;
	@JsonProperty("dI")
	private String deviceId;
	@JsonProperty("bs")
	private boolean baseStation;
	@JsonProperty("lT")
	private DeviceLocomotionType locomotionType;
	@JsonIgnore
	private DeviceConfig myNeigbour;
	@JsonIgnore
	private DeviceConfig edgeTerminalDevice;
	@JsonIgnore
	private Map<String, DeviceConfig> devices = new LinkedHashMap<String, DeviceConfig>();
	@JsonProperty("gpsLoc")
	private GpsLocation gpsLocation;
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public boolean isBaseStation() {
		return baseStation;
	}
	public void setBaseStation(boolean baseStation) {
		this.baseStation = baseStation;
	}
	public DeviceLocomotionType getLocomotionType() {
		return locomotionType;
	}
	public void setLocomotionType(DeviceLocomotionType locomotionType) {
		this.locomotionType = locomotionType;
	}
	public DeviceConfig getMyNeigbour() {
		return myNeigbour;
	}
	public void setMyNeigbour(DeviceConfig myNeigbour) {
		this.myNeigbour = myNeigbour;
	}
	public DeviceConfig getEdgeTerminalDevice() {
		return edgeTerminalDevice;
	}
	public void setEdgeTerminalDevice(DeviceConfig edgeTerminalDevice) {
		this.edgeTerminalDevice = edgeTerminalDevice;
	}
	public Map<String, DeviceConfig> getDevices() {
		return devices;
	}
	public void setDevices(Map<String, DeviceConfig> devices) {
		this.devices = devices;
	}
	public GpsLocation getGpsLocation() {
		return gpsLocation;
	}
	public void setGpsLocation(GpsLocation gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
	@Override
	public String toString() {
		return deviceName + " " + locomotionType + " is at " + (gpsLocation != null?gpsLocation.getDistance():"") + "Meter" ;
	}
}
