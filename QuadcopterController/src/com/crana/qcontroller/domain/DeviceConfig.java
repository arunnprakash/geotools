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
	@JsonProperty("lT")
	private DeviceLocomotionType locomotionType;
	@JsonIgnore
	private DeviceConfig myNeigbour;
	@JsonIgnore
	private DeviceConfig edgeTerminalDevice;
	@JsonIgnore
	private Map<String, DeviceConfig> devices = new LinkedHashMap<String, DeviceConfig>();
	@JsonProperty("lat")
	private double latitute;
	@JsonProperty("long")
	private double longitute;
	@JsonIgnore
	private double distance;
	
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
	public double getLatitute() {
		return latitute;
	}
	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}
	public double getLongitute() {
		return longitute;
	}
	public void setLongitute(double longitute) {
		this.longitute = longitute;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return deviceName + " " + locomotionType + " is at " + distance + "Meter" ;
	}
}
