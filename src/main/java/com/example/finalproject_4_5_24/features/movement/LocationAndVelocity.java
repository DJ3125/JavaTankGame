package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;

import java.util.ArrayList;
import java.util.Iterator;

final class LocationAndVelocity implements Located, Angled, Velocities {
    LocationAndVelocity(GameElementEncapsulator<?> encapsulator, float xPosition, float yPosition, float ang, float xVelocity, float yVelocity, float angVel, float xAcceleration, float yAcceleration, float angAccel){
        this.encapsulator = encapsulator;
        this.x = xPosition;
        this.xVel = xVelocity;
        this.xAccel = xAcceleration;
        this.y = yPosition;
        this.yVel = yVelocity;
        this.yAccel = yAcceleration;
        this.ang = ang;
        this.angVel = angVel;
//        this.angAccel = angAccel;

        this.forcesToApply = new ArrayList<>();
    }

    LocationAndVelocity(GameElementEncapsulator<?> encapsulator, float xPosition, float yPosition, float ang, float xVelocity, float yVelocity, float angVel){this(encapsulator, xPosition, yPosition, ang, xVelocity, yVelocity, angVel, 0, 0, 0);}
    LocationAndVelocity(GameElementEncapsulator<?> encapsulator, float xPosition, float yPosition, float ang){this(encapsulator, xPosition, yPosition, ang, 0, 0, 0);}


    void update(){
        this.yAccel = this.xAccel = 0;

        boolean[] priorities = new boolean[20];
        for(Forces i : this.forcesToApply){priorities[i.priority()] = true;}

        float xAdd = 0, yAdd = 0;
        for(int i = 0; i < priorities.length; i++){
            if(priorities[i]){
                Iterator<Forces> iterator = this.forcesToApply.iterator();
                while(iterator.hasNext()){
                    Forces current = iterator.next();
                    if(current.priority() == i){
                        this.xAccel += current.getAccelerationChangeForX();
                        this.yAccel += current.getAccelerationChangeForY();

//                        xAdd += current.getAccelerationChangeForX();
//                        yAdd += current.getAccelerationChangeForY();
                        iterator.remove();
                    }
                }
            }
        }
        this.xAccel /= this.encapsulator.getMass();
        this.yAccel /= this.encapsulator.getMass();

//                this.xAccel = xAdd;
//                this.yAccel = yAdd;

//        this.xAccel/= (float) GameController.fps;
//        this.yAccel/= (float) GameController.fps;

        this.forcesToApply.clear();

//        this.xVel /= (float) GameController.fps;
//        this.yVel /= (float) GameController.fps;

        this.xVel += (this.xAccel);
        this.yVel += (this.yAccel);

        this.y += this.yVel;
        this.x += this.xVel;

//        this.x += (float) (this.xVel / GameController.fps + 0.5 * this.xAccel / Math.pow(GameController.fps, 2));
//        this.y += (float) (this.yVel / GameController.fps + 0.5 * this.yAccel / Math.pow(GameController.fps, 2));


        this.ang += this.angVel;

        this.ang %= (float) Math.TAU;
        this.ang += (float) Math.TAU;
        this.ang %= (float) Math.TAU;

//        this.ang += this.angVel;
    }

    public void setAng(float ang) {
        this.ang = ang;
    }

    public void addForce(Forces newForce){this.forcesToApply.add(newForce);}

    void setAngVel(float angVel){this.angVel = angVel;}

    @Override public float getAngAccel() {return 0;}
    @Override public float getAngVel() {return this.angVel;}
    @Override public float getxAccel() {return this.xAccel;}
    @Override public float getyAccel() {return this.yAccel;}
    @Override public float getxVel() {return this.xVel;}
    @Override public float getyVel() {return this.yVel;}
    @Override public float getAngle() {return this.ang;}
    @Override public float getX() {return this.x;}
    @Override public float getY() {return this.y;}

    private float x, y, xVel, yVel, xAccel, yAccel, ang, angVel;
    private final ArrayList<Forces> forcesToApply;
    private final GameElementEncapsulator<?> encapsulator;
}
