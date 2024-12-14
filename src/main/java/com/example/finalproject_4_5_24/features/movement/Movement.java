package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;

public sealed abstract class Movement extends Features implements Located, Angled, Velocities permits InteractMovement, LinearMovement, NullMovement, PlayerHomingMovement, PlayerMovement {
    protected Movement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition){this(info, xPosition, yPosition, 0 );}


    protected Movement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle){
        super(info);
        this.info = new LocationAndVelocity(info.encapsulator(), xPosition, yPosition, angle);
    }

    @Override protected final void updateOnTurn() {}
    @Override protected void updateOnChange() {}
    @Override protected void updateOnTick() {}

    protected final LocationAndVelocity getLocationAndVelocity(){return this.info;}
    public void addForce(Forces forces){this.info.addForce(forces);}

    public abstract void move();
    @Override public final float getY() {return this.info.getY();}
    @Override public final float getX() {return this.info.getX();}
    @Override public final float getAngle(){return this.info.getAngle();}

    @Override public float getAngAccel() {return this.info.getAngAccel();}
    @Override public float getxAccel() {return this.info.getxAccel();}
    @Override public final float getxVel() {return this.info.getxVel();}
    @Override public final float getAngVel() {return this.info.getAngVel();}
    @Override public final float getyAccel() {return this.info.getyAccel();}
    @Override public final float getyVel() {return this.info.getyVel();}

    private final LocationAndVelocity info;
}
