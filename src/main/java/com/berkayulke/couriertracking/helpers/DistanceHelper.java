package com.berkayulke.couriertracking.helpers;

public class DistanceHelper {

    private static final int R = 6371; // Radius of the earth
    
    
    public static double getDistance(double aLatitude, double aLongitude, double bLatitude, double bLongitude) {

        double latDistance = Math.toRadians(bLatitude - aLatitude);
        double lonDistance = Math.toRadians(bLongitude - aLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(aLatitude)) * Math.cos(Math.toRadians(bLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.abs(R * c * 1000); // convert to meters
    }
}
