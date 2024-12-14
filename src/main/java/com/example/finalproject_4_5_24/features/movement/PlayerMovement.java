package com.example.finalproject_4_5_24.features.movement;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.features.movement.forces.DecelerationForce;
import com.example.finalproject_4_5_24.features.movement.forces.LocomotiveForce;
import javafx.scene.input.KeyCode;

final class PlayerMovement extends Movement {
    PlayerMovement(DynamicGameElement.FeatureInitializationInfo info, float xPosition, float yPosition, float acceleration, float turnVelocity){
        super(info, xPosition, yPosition);
        this.linearAcceleration = acceleration;
        this.turnVelocity = turnVelocity;
    }

    @Override public void move() {
//        if(!input.getValueOfKeyCode(KeyCode.SHIFT))
        PlayerInputDecorator input = this.getEncapsulator().gameEnvironment().inputs();
        {
            byte yMovement = 0, xMovement = 0;
            yMovement += (byte) (input.getValueOfKeyCode(KeyCode.DOWN) ? -1 : 0);
            yMovement += (byte) (input.getValueOfKeyCode(KeyCode.UP) ? 1 : 0);
            xMovement += (byte) (input.getValueOfKeyCode(KeyCode.LEFT) ? -1 : 0);
            xMovement += (byte) (input.getValueOfKeyCode(KeyCode.RIGHT) ? 1 : 0);
            this.addForce(new LocomotiveForce(this.getEncapsulator(), xMovement * this.linearAcceleration, -yMovement * this.linearAcceleration));
        }
        {
            double mouseAng = (AnglesAndTrig.getAngle(GameController.convertXScreenLocToGameLoc(input.getScreenMouseX()), GameController.convertYScreenLocToGameLoc(input.getScreenMouseY()), this));
            mouseAng = (float) (((mouseAng % Math.TAU) + Math.TAU)%Math.TAU);
            double difference = mouseAng - this.getAngle();
            boolean within = false;
            {
                byte counter = -1;
                while(!within && counter <= 1){if(Math.abs(difference + (counter++ * Math.TAU)) <= 1.001 * this.turnVelocity){within = true;}}
            }
            if(within){this.getLocationAndVelocity().setAngVel((float) (difference));}
            else{
                if((-difference + Math.TAU)%Math.TAU< (Math.TAU + difference) % Math.TAU){this.getLocationAndVelocity().setAngVel(-this.turnVelocity);}
                else{this.getLocationAndVelocity().setAngVel(this.turnVelocity);}
            }
        }



        this.addForce(new DecelerationForce(this.getEncapsulator()));
        this.getLocationAndVelocity().update();
        this.getNotifier().notifyOfChange();
    }

    private final float linearAcceleration, turnVelocity;
}
