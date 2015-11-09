/**
 * 
 */
package com.crana.qcontroller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author SN2528
 *
 */
@JsonInclude(Include.NON_NULL)
public class GpsLocation {
	@JsonProperty("lat")
	private double latitude;
	@JsonProperty("long")
	private double longitude;
	@JsonIgnore
	private double distance;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
