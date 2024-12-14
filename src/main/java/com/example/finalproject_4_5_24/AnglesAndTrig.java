package com.example.finalproject_4_5_24;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.features.movement.Located;

public final class AnglesAndTrig {
    private AnglesAndTrig(){}
    public static double getAngle(double x1, double y1, double x2, double y2){
        double xDist = x2 - x1, yDist = y2 - y1;
        if(xDist == 0 && yDist == 0){return 0;}
        else{
            if(xDist == 0){
                if(yDist > 0){return Math.PI * 1.5;}
                else{return Math.PI/2;}
            }else{
                double angle = Math.atan(yDist/xDist);
                if(xDist > 0){angle += Math.PI;}
                angle  = (angle + Math.TAU) % Math.TAU;
                return angle;
            }
        }
    }

    public static double getAngle(double x, double y){return getAngle(x, y, 0, 0);}

    public static double getAngle(double x1, double y1, Located located){return getAngle(x1, y1, located.getX(), located.getY());}

    public static double getAdjustedAngle(double x1, double y1, GameElementEncapsulator<?> located){return getAngle(x1, y1, located.getX() - located.getDimensionsEncapsulator().getXDistanceFromCenter(), located.getY() - located.getDimensionsEncapsulator().getYDistanceFromCenter());}

    public static double findDist(double x1, double y1, double x2, double y2){
        double xDist = x2 - x1, yDist = y2 - y1;
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
    }

    public static double findDist(double x1, double y1, Located located){return findDist(x1, y1, located.getX(), located.getY());}
    public static double findDist(double x, double y){return findDist(x, y, 0, 0);}
    public static double findAdjustedDist(double x1, double y1, GameElementEncapsulator<?> element){return findDist(x1, y1, element.getX() - element.getDimensionsEncapsulator().getXDistanceFromCenter(), element.getY() - element.getDimensionsEncapsulator().getYDistanceFromCenter());}
}
