package com.example.finalproject_4_5_24.features.hitBoxes;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;
import com.example.finalproject_4_5_24.features.movement.Located;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;

import java.util.ArrayList;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

abstract sealed public class HitBoxes extends Features implements HitBoxable permits EllipticalHitBox, RectangleHitBox, NullHitBox{
    protected HitBoxes(DynamicGameElement.FeatureInitializationInfo info, Predicate<GameElementEncapsulator<?>> chooser){
        super(info);
        this.chooser = chooser;
    }

    protected abstract float[][] getVertices();

    @Override public float[][] getThisVertices() {return this.modifiedVertices.clone();}

    private float[][] getModifiedVertices(){
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();
        float[][] modified = new float[this.vertices.length][2];
        short counter = 0;
        for(float[] i : this.vertices){
            double dist, angle;
            {
                float newCenterX = dimensions.getXDistanceFromCenter(), newCenterY = dimensions.getYDistanceFromCenter();
                float vertX = (i[0] - newCenterX) * dimensions.getXScale() + newCenterX, vertY = (i[1] - newCenterY) * dimensions.getYScale() + newCenterY;
                dist = AnglesAndTrig.findDist(newCenterX, newCenterY, vertX, vertY);
                angle = AnglesAndTrig.getAngle(newCenterX, newCenterY, vertX, vertY) + this.getEncapsulator().getAngle();
            }
            modified[counter][0] = (float)Math.round(Math.cos(angle) * dist) + this.getEncapsulator().getX();
            modified[counter][1] = (float)Math.round(Math.sin(angle) * dist) + this.getEncapsulator().getY();
            counter++;
        }
        return modified;
    }

    abstract protected boolean isInside(float x, float y);

    @Override public final boolean contains(float x, float y) {return this.checkForPossibility(x, y) && this.isInside(x, y);}

    @Override protected final void updateOnTurn() {this.getEncapsulator().gameEnvironment().operator().loopThroughAll(this::notifyOtherForCollisions);}

    @Override public final boolean isPotentialForCollision(GameElementEncapsulator<?> encapsulator) {return this.chooser.test(encapsulator);}

    @Override protected void updateOnChange() {
        this.modifiedVertices = this.getModifiedVertices();
        this.boundingBox = this.updateBoundingBox();
        this.borderBoxes = this.updateBorderBoxes();
    }

    @Override protected void updateOnTick() {this.collided.clear();}

    protected final float[] getRelativePosition(float x, float y){
        PositioningTypes.DimensionsEncapsulator dimensions = this.getEncapsulator().getDimensionsEncapsulator();
        double distance, newAngle;
        {
            float[] loc = dimensions.getAdjustedCenter();
            distance = AnglesAndTrig.findDist(x, y, loc[0], loc[1]);
            newAngle = AnglesAndTrig.getAngle(x, y, loc[0], loc[1]) - this.getEncapsulator().getAngle();
        }
        return new float[]{(float)Math.round(distance * Math.cos(newAngle)/dimensions.getXScale()), (float)Math.round(distance * Math.sin(newAngle)/dimensions.getYScale())};
    }


    private BiPredicate<Float, Float> updateBoundingBox(){
        if(this.modifiedVertices.length == 0){return (x, y) -> false;}
        else{
            float rightMost = this.modifiedVertices[0][0], leftMost = this.modifiedVertices[0][0], upMost = this.modifiedVertices[0][1], downMost = this.modifiedVertices[0][1];
            for(float[] i : this.modifiedVertices){
                float x = i[0], y = i[1];
                rightMost = Math.max(x, rightMost);
                leftMost = Math.min(x, leftMost);
                upMost = Math.max(y, upMost);
                downMost = Math.min(y, downMost);
            }
            float finalR = rightMost, finalL = leftMost, finalU = upMost, finalD = downMost;
            return (x, y) -> x >= finalL && x <= finalR && y >= finalD && y <= finalU;
        }
    }


    private boolean checkForPossibility(float x, float y){
        return this.boundingBox.test(x, y);
    }

    @Override public final boolean checkIfPointsAreInsideOther(BiPredicate<Float, Float> contains){
        for(float[] i : this.modifiedVertices){if(contains.test(i[0], i[1])){return true;}}
        return false;
    }

    @Override public final boolean isCollided(GameElementEncapsulator<?> other){
        if(other.equals(this.getEncapsulator())){return false;}
        HitBoxTypes.HitBoxEncapsulator hit = other.getHitBoxEncapsulator();
        float[] myLoc = this.getEncapsulator().getDimensionsEncapsulator().getAdjustedCenter(), otherLoc = other.getDimensionsEncapsulator().getAdjustedCenter();
        return AnglesAndTrig.findDist(myLoc[0], myLoc[1], otherLoc[0], otherLoc[1]) <= this.getMaxDist(this.getEncapsulator()) + this.getMaxDist(other) && this.checkIfPointsAreInsideOther(hit::contains) || other.checkIfPointsAreInsideOther(this::contains);
    }

    private double getMaxDist(GameElementEncapsulator<?> encapsulator){
        PositioningTypes.DimensionsEncapsulator dimensions = encapsulator.getDimensionsEncapsulator();
        return AnglesAndTrig.findDist(dimensions.getHeight() + Math.abs(dimensions.getYDistanceFromCenter()), dimensions.getWidth() + Math.abs(dimensions.getXDistanceFromCenter()))/2.0;
    }

    private MiniHitBox[] updateBorderBoxes(){
        int l = this.vertices.length;
        MiniHitBox[] hitBoxes = new MiniHitBox[l];
        for(int i = 0; i < l; i++){hitBoxes[i] = new MiniHitBox(this.modifiedVertices[i][0], this.modifiedVertices[i][1], this.modifiedVertices[(i + 1) % l][0], this.modifiedVertices[(i + 1)% l][1], (float) (0.3 * Math.min(this.getEncapsulator().getDimensionsEncapsulator().getWidth(), this.getEncapsulator().getDimensionsEncapsulator().getHeight())));}
        return hitBoxes;
    }

    public void notifyOtherForCollisions(GameElementEncapsulator<?> other){
        if(!other.equals(this.getEncapsulator()) && this.chooser.test(other)){
            if(!this.collided.contains(other)){
                float x = 0, y = 0;
                short counter = 0;
                for (MiniHitBox outSideBorderBox : this.borderBoxes) {
                    boolean notified = false;
                    for (float[] j : other.getHitBoxEncapsulator().getThisVertices()) {
                        if (!notified) {
                            if (this.boundingBox.test(j[0], j[1]) && outSideBorderBox.contains(j[0], j[1])) {
                                notified = true;
                                Located magnitude = outSideBorderBox.getMagnitudes(j[0], j[1]);
                                x += magnitude.getX();
                                y += magnitude.getY();
                                counter++;
                            }
                        }
                    }
                }
                if(counter > 0){
                    x/=counter;
                    y/=counter;
                    float ang = (float) AnglesAndTrig.getAngle(x, y);
                    this.processCollision(other, ang, x, y);
                }
            }
        }
    }


    private void processCollision(GameElementEncapsulator<?> other, float angle, float embX, float embY){
        GameElementEncapsulator<?> encapsulator = this.getEncapsulator();
        float myInitVel, otherInitVel;
        {
            float myVelMag = (float)AnglesAndTrig.findDist(encapsulator.getxVel() + encapsulator.getxAccel(), encapsulator.getyVel() + encapsulator.getyAccel());
            float myVelAng = (float)AnglesAndTrig.getAngle(encapsulator.getxVel() + encapsulator.getxAccel(), encapsulator.getyVel() + encapsulator.getyAccel());
            myInitVel = (float) (Math.cos(myVelAng - angle) * myVelMag);
            float otherVelMag = (float)AnglesAndTrig.findDist(other.getxVel() + other.getxAccel(), other.getyVel() + other.getyAccel());
            float otherVelAng = (float)AnglesAndTrig.getAngle(other.getxVel() + other.getxAccel(), other.getyVel() + other.getyAccel());
            otherInitVel = (float)(Math.cos(otherVelAng - angle) * otherVelMag);
        }
        float c = encapsulator.getMass();
        float d = other.getMass();
        float e = myInitVel * c + otherInitVel * d;
        float[] solutions;
        {
            float aInEquation = d * d + c * d;
            float bInEquation = 2 * e * d;
            float f = (float) (c * Math.pow(myInitVel, 2) + d * Math.pow(otherInitVel, 2));
            float determinant = (float) (Math.sqrt(Math.pow(bInEquation, 2) + 4 * aInEquation * f * c)/(2 * aInEquation));
            solutions = new float[]{-bInEquation/(2 * aInEquation) + determinant, -bInEquation/(2 * aInEquation) - determinant};
        }
        float finalSolutionForOther;
        if(Math.abs(solutions[0] - otherInitVel) > Math.abs(solutions[1] - otherInitVel)){finalSolutionForOther = solutions[0];}
        else {finalSolutionForOther = solutions[1];}
        float otherSolutionForMe = (e - d * finalSolutionForOther)/c;

        float embAngle = (float) AnglesAndTrig.getAngle(embX, embY);
        this.notifyForCollision(new ModifiedCollisionNotification(other, angle, Math.abs(otherSolutionForMe - myInitVel) * c, embX, embY, embAngle));
        other.getHitBoxEncapsulator().notifyForCollision(new ModifiedCollisionNotification(this.getEncapsulator(), angle, Math.abs(finalSolutionForOther - otherInitVel) * d, embX, embY, (float) (embAngle + Math.PI)));
    }


    void notifyForCollision(ModifiedCollisionNotification notification){
        this.collided.add(notification.getOtherCollider());
        this.getNotifier().notifyOfCollision(notification);
    }

    private final float[][] vertices = this.getVertices();
    private float[][] modifiedVertices = this.getModifiedVertices();
    private BiPredicate<Float, Float> boundingBox = this.updateBoundingBox();
    private MiniHitBox[] borderBoxes = this.updateBorderBoxes();
    private final ArrayList<GameElementEncapsulator<?>> collided = new ArrayList<>();
    private final Predicate<GameElementEncapsulator<?>> chooser;

    private static class MiniHitBox{
        private MiniHitBox(float x1, float y1, float x2, float y2, float height){
            this.width = (float) (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
            this.angle = (float) AnglesAndTrig.getAngle(x1, y1, x2, y2);
            this.height = height;

            this.directionOfNormal = (float) (this.angle +  Math.PI/2);
            this.x = (float) ((x1 + x2)/2 -1 * Math.cos(this.directionOfNormal) * this.height /2);
            this.y = (float) ((y1 + y2)/2 +1 * Math.sin(-this.directionOfNormal) * this.height /2);
        }
        private boolean contains(float x, float y){
            float[] rel = this.getRelativePosition(x, y);
            return (Math.abs(rel[1])<= this.height /2.0) && Math.abs(rel[0]) <= (this.width - 2 * this.height)/2 + (rel[1] + this.height/2);
        }

        private Located getMagnitudes(float x, float y){
            if(this.contains(x, y)){
                float vecX, vecY;
                {
                    double u = -Math.sin(this.angle), v = Math.cos(this.angle);
                    float mag, newX = x - this.x, newY = y - this.y;
                    newY *= -1;
                    mag = (float)(((newY * v - u * newX)));
                    vecX = (float) (-Math.sin(this.angle) * mag + this.height/2 * Math.cos(this.directionOfNormal));
                    vecY = (float) (Math.cos(this.angle) * mag + this.height/2 * Math.sin(this.directionOfNormal));
                }
                float fX = vecX, fY = vecY;
                return new Located() {
                    @Override public float getX() {return fX;}
                    @Override public float getY() {return fY;}
                };
            }else{return new Located() {
                @Override public float getX() {return 0;}
                @Override public float getY() {return 0;}
            };}
        }

        private float[] getRelativePosition(float x, float y){
            double distance = AnglesAndTrig.findDist(x, y, this.x, this.y), newAngle = AnglesAndTrig.getAngle(x, y, this.x, this.y) - this.angle;
            return new float[]{(float)Math.round(distance * Math.cos(newAngle)), (float)Math.round(distance * Math.sin(newAngle))};
        }
        private final float x, y, width, height, angle, directionOfNormal;
    }

    public static final class ModifiedCollisionNotification{
        private ModifiedCollisionNotification(GameElementEncapsulator<?> other, float angle, float change, float magnitudeEmbeddedX, float magnitudeEmbeddedY, float embAngle){
//            super(HitBoxes.class);
            this.angle = angle;
            this.change = change;
            this.other = other;
            this.magnitudeEmbeddedX = magnitudeEmbeddedX;
            this.magnitudeEmbeddedY = magnitudeEmbeddedY;
            this.embAngle = embAngle;
        }

        public GameElementEncapsulator<?> getOtherCollider(){return this.other;}

        public float getAngle() {return this.angle;}
        public float getChange() {return this.change;}
        public float getMagnitudeEmbeddedX() {return this.magnitudeEmbeddedX;}
        public float getEmbAngle() {return this.embAngle;}
        public float getMagnitudeEmbeddedY() {return this.magnitudeEmbeddedY;}
        private final GameElementEncapsulator<?> other;
        private final float angle, change, magnitudeEmbeddedX, magnitudeEmbeddedY, embAngle;
    }
}