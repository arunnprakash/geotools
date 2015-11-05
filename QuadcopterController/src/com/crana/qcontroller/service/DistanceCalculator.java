package com.crana.qcontroller.service;
/**
 * 
 */

/**
 * @author ArunPrakash
 *
 */
public class DistanceCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double lat1 = 12.9814853d;
		double lon1 = 80.2434073d;
		double el1 = 8;
		double lat2 = 12.9814644;
        double lon2 = 80.2397165;
        double el2 = 8;
		System.out.println(distance(lat1, lon1, lat2, lon2, el1, el2) + " Meter\n");
	    System.out.println(distance(lat1, lon1, lat2, lon2, 'M') + " Meters\n");
	    System.out.println(distance(lat1, lon1, lat2, lon2, 'K') + " Kilometers\n");
	    System.out.println(distance(lat1, lon1, lat2, lon2, 'N') + " Nautical Miles\n");
	}
	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double lat1, double lon1, double lat2, 
	        double lon2, double el1, double el2) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(lat2 - lat1);
	    Double lonDistance = Math.toRadians(lon2 - lon1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = el1 - el2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'M') {
        	dist = dist * 1609.344;
        }
        else if (unit == 'K') {
          dist = dist * 1.609344;
        } 
        else if (unit == 'N') {
          dist = dist * 0.8684;
        }
        return (dist);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }

}
