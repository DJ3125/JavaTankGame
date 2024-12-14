package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;

public enum MovementTypes {
    INTERACT_MOVEMENT{@Override public final MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {return this.encapsulate(new InteractMovement(info, xPosition, yPosition));}},
    PLAYER_HOMING_MOVEMENT{@Override public final MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {return this.encapsulate(new PlayerHomingMovement(info, xPosition, yPosition, angle, acceleration));}},
    PLAYER_MOVEMENT{@Override public final MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {return this.encapsulate(new PlayerMovement(info, xPosition, yPosition, acceleration, 0.2f));}},
    NULL_MOVEMENT{@Override public final MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {return this.encapsulate(new NullMovement(info, xPosition, yPosition, angle));}},
    LINEAR_MOVEMENT{@Override public final MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration) {return this.encapsulate(new LinearMovement(info, xPosition, yPosition, angle, acceleration));}};
    abstract public MovementEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float angle, float acceleration);
    protected final MovementEncapsulator encapsulate(Movement movement){return new MovementEncapsulator(movement);}


    public static final class MovementEncapsulator extends FeatureEncapsulator<Movement> implements Located, Angled, Velocities {
        private MovementEncapsulator(Movement movement){super(Movement.class, movement);}
        @Override public float getAngle() {return this.getFeature().getAngle();}
        @Override public float getX() {return this.getFeature().getX();}
        @Override public float getY() {return this.getFeature().getY();}
        @Override public float getAngAccel() {return this.getFeature().getAngAccel();}
        @Override public float getAngVel() {return this.getFeature().getAngVel();}
        @Override public float getxAccel() {return this.getFeature().getxAccel();}
        @Override public float getyAccel() {return this.getFeature().getyAccel();}
        @Override public float getxVel() {return this.getFeature().getxVel();}
        @Override public float getyVel() {return this.getFeature().getyVel();}
    }
}
