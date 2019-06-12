package com.tba.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArithmeticUtil {
    private static final Logger logger = LoggerFactory.getLogger(ArithmeticUtil.class);
    /**
     * This method calculates the current horizontal coordinate  of object using given parameters.
     * this is assumed that the surface where the object moves is flat.
     * @param initialX the initial horizontal coordinate of object in meters unit
     * @param speed in KH unit
     * @param direction:angle to horizon line in degree unit
     * @param timePeriod :time period in seconds
     * @return
     */
    public static double calculateXcoordinate(double initialX,double speed,
                                              double direction,int timePeriod){
        double slopeCoefficient = Math.tan(Math.toRadians(direction));
        logger.debug("slope coefficient="+slopeCoefficient);
        double x= calculateXdifference(speed, slopeCoefficient, timePeriod);
        int sign;
        if(direction>90 && direction<270){
            sign = -1;
        }else{
            sign = 1;
        }
        initialX=initialX+(sign)*x;
        return initialX;
    }

    /**
     * This method calculates the current vertical coordinate  of object using given parameters.
     * this is assumed that the surface where the object moves is flat.
     * @param initialY the initial vertical coordinate of object in meters unit
     * @param speed in KH unit
     * @param direction angle to horizon line in degree unit
     * @param timePeriod :in seconds
     * @return
     */
    public static double calculateYcoordinate(double initialY,double speed,double direction,int timePeriod){
        // calculate slope coefficient
        double slopeCoefficient = Math.tan(Math.toRadians(direction));
        logger.debug("slope coefficient="+slopeCoefficient);
        double x=calculateXdifference(speed, slopeCoefficient, timePeriod);
        double y=slopeCoefficient*x;
        int sign;
        if (direction>180 && direction<360){
            sign=-1;
        }else {
            sign=1;
        }
        initialY=initialY+(sign)*y;
        return initialY;
    }
    /**
     * This method calculates the current horizontal coordinate  of object using given parameters.
     * this is assumed that the surface where the object moves is flat.
     * @param speed in KH unit
     * @param slopeCoefficient: a ceofficient between X and Y
     * @param timePeriod :time period in seconds
     * @return
     */
    private static double calculateXdifference(double speed,double slopeCoefficient,int timePeriod){
        // the distance that the vehicle moved in meters
        double distanceMoved = (speed/3600)*timePeriod*1000;
        logger.debug("distance in meters="+distanceMoved);
        // movement on X axis
        double x=Math.sqrt(Math.pow(distanceMoved, 2)/(1+Math.pow(slopeCoefficient,2)));
        logger.debug("X change="+x);
        return x;
    }
}
