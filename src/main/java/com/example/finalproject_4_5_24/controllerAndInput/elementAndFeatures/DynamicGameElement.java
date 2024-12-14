package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.*;
import com.example.finalproject_4_5_24.bullets.ExpirationProjectiles;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementOperator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.attack.Attack;
import com.example.finalproject_4_5_24.features.attack.AttackTypes;
import com.example.finalproject_4_5_24.features.collection.Collecting;
import com.example.finalproject_4_5_24.features.collection.CollectingTypes;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import com.example.finalproject_4_5_24.features.health.Health;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxTypes;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxable;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;
import com.example.finalproject_4_5_24.features.movement.*;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.ScalingAndPositioning;
import com.example.finalproject_4_5_24.features.spawning.Spawner;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.BiPredicate;

abstract sealed public class DynamicGameElement implements Located, Angled, HitBoxable, Velocities, HoldingFeature permits Enemies, EnemySpawnerEntity, Goal, Obstacle, Player, ExpirationProjectiles, Item {
    protected DynamicGameElement(GameLayer.EntityInitializationInfo entityInitializationInfo, MovementTypes movementType, float x, float y, float angle, float acceleration, PositioningTypes posType, float xDist, float yDist, float width, float height, float xScale, float yScale, HitBoxTypes hitBoxTypes){
        this.entityInitializationInfo = entityInitializationInfo;
        this.initializationInfo = new FeatureInitializationInfo(this.encapsulator, this.notifier, entityInitializationInfo);
        this.holder.initializeFeatureHolder(this.getInitializationInfo());
        this.holder.setFeatureEncapsulator(movementType.getEncapsulator(this.getInitializationInfo(), x, y, angle, acceleration));
        this.holder.setFeatureEncapsulator(posType.getEncapsulator(this.getInitializationInfo(), xDist, yDist, width, height, xScale, yScale));
        this.holder.setFeatureEncapsulator(hitBoxTypes.getEncapsulator(this.getInitializationInfo(), this::isPotentialForCollision));
    }
    protected DynamicGameElement(GameLayer.EntityInitializationInfo entityInitializationInfo, MovementTypes movementType, float x, float y, float angle, float acceleration, PositioningTypes posType, float width, float height, float xScale, float yScale, HitBoxTypes hitBoxTypes){this(entityInitializationInfo, movementType, x, y, angle, acceleration, posType, 0, 0, width, height, xScale, yScale, hitBoxTypes);}
    protected DynamicGameElement(GameLayer.EntityInitializationInfo entityInitializationInfo, MovementTypes movementType, float x, float y, PositioningTypes posType, float width, float height, HitBoxTypes hitBoxTypes){this(entityInitializationInfo, movementType, x, y, 0, 0, posType, 0, 0, width, height, 1, 1, hitBoxTypes);}
    public abstract int getMass();
    public abstract String getImage();
    public final void updateAll(){
        this.updateFeatures();
        this.updateOther();
    }
    final void itemResponse(InventoryItemOptions.ItemModification request){this.holder.loopThroughAllFeatures(featureEncapsulator -> featureEncapsulator.getFeature().respondToItem(request));}
    final void collisionResponse(HitBoxes.ModifiedCollisionNotification notification){
        this.respondToCollision(notification);
        this.holder.respondToCollision(notification.getChange(), notification.getMagnitudeEmbeddedX(), notification.getMagnitudeEmbeddedY());
    }
    protected abstract void respondToCollision(HitBoxes.ModifiedCollisionNotification notification);

    public final GameLayer.EntityInitializationInfo gameEnvironment() {return this.entityInitializationInfo;}

    @Override public final float[][] getThisVertices() {return this.getHitBoxEncapsulator().getThisVertices();}
    protected final FeatureInitializationInfo getInitializationInfo(){return this.initializationInfo;}
    abstract protected void updateOther();

    private void updateFeatures(){
        ((Features)this.getAttack()).updateOnTurn();
        ((Features)this.getHealth()).updateOnTurn();
        ((Features)this.getDimensions()).updateOnTurn();
        ((Features)this.getHitBoxes()).updateOnTurn();
        ((Features)this.getMovement()).updateOnTurn();
        this.getSpawner().updateOnTurn();
        this.getCollecting().updateOnTurn();
    }

    public final void tickUpdate(){this.holder.loopThroughAllFeatures((featureEncapsulator) -> featureEncapsulator.getFeature().updateOnTick());}
    final void changeUpdate(){this.holder.loopThroughAllFeatures(featureEncapsulator -> featureEncapsulator.getFeature().updateOnChange());}
    public void addForce(Forces forceToAdd){this.getMovement().addForce(forceToAdd);}
    public abstract boolean readyForRemoval();
    @Override public final float getAngAccel() {return this.getMovementEncapsulator().getAngAccel();}
    @Override public final float getAngVel() {return this.getMovementEncapsulator().getAngVel();}
    @Override public final float getxAccel() {return this.getMovementEncapsulator().getxAccel();}
    @Override public final float getyAccel() {return this.getMovementEncapsulator().getyAccel();}
    @Override public final float getxVel() {return this.getMovementEncapsulator().getxVel();}
    @Override public final float getyVel() {return this.getMovementEncapsulator().getyVel();}
    @Override public final boolean contains(float x, float y) {return this.getHitBoxEncapsulator().contains(x, y);}
    @Override public final float getY() {return this.getMovementEncapsulator().getY();}
    @Override public final float getX() {return this.getMovementEncapsulator().getX();}
    @Override public final float getAngle() {return this.getMovementEncapsulator().getAngle();}
    @Override public final boolean checkIfPointsAreInsideOther(BiPredicate<Float, Float> contains) {return this.getHitBoxEncapsulator().checkIfPointsAreInsideOther(contains);}
    @Override public final boolean isCollided(GameElementEncapsulator<?> encapsulator) {return this.getHitBoxEncapsulator().isCollided(encapsulator);}
    protected final HitBoxes getHitBoxes() {return this.getHitBoxEncapsulator().getFeature();}
    protected final Movement getMovement() {return this.getMovementEncapsulator().getFeature();}
    protected final ScalingAndPositioning getDimensions() {return this.getDimensionsEncapsulator().getFeature();}
    protected final Health getHealth(){return this.getHealthEncapsulator().getFeature();}
    protected final Attack getAttack(){return this.getAttackEncapsulator().getFeature();}
    protected final Spawner getSpawner(){return this.getSpawningEncapsulator().getFeature();}
    protected final Collecting getCollecting(){return this.getCollectingEncapsulator().getFeature();}
    @Override public final MovementTypes.MovementEncapsulator getMovementEncapsulator(){return this.holder.getMovementEncapsulator();}
    @Override public final PositioningTypes.DimensionsEncapsulator getDimensionsEncapsulator(){return this.holder.getDimensionsEncapsulator();}
    @Override public final HitBoxTypes.HitBoxEncapsulator getHitBoxEncapsulator(){return this.holder.getHitBoxEncapsulator();}
    @Override public final HealthTypes.HealthEncapsulator getHealthEncapsulator() {return this.holder.getHealthEncapsulator();}
    @Override public final AttackTypes.AttackEncapsulator getAttackEncapsulator() {return this.holder.getAttackEncapsulator();}
    @Override public final SpawningTypes.SpawnerEncapsulator getSpawningEncapsulator() {return this.holder.getSpawningEncapsulator();}
    @Override public final CollectingTypes.CollectingEncapsulator getCollectingEncapsulator(){return this.holder.getCollectingEncapsulator();}
    protected final void setFeatureEncapsulator(FeatureEncapsulator<?> featureEncapsulator){this.holder.setFeatureEncapsulator(featureEncapsulator);}
    public final GameElementEncapsulator<? extends DynamicGameElement> getEncapsulator(){return this.encapsulator;}
    private final GameElementEncapsulator<? extends DynamicGameElement> encapsulator = new GameElementEncapsulator<>(this);
    private final ElementNotifier notifier = new ElementNotifier(this);
    private final FeatureInitializationInfo initializationInfo;
    private final FeatureHolder holder = new FeatureHolder();
    private final GameLayer.EntityInitializationInfo entityInitializationInfo;



    private static final Class<? extends DynamicGameElement>[] allSubclasses;

    @SuppressWarnings("unchecked") private static void addAllKnownFinalSubclasses(@NotNull Class<? extends DynamicGameElement> classObject, ArrayList<Class<? extends DynamicGameElement>> classes){
        if(classObject.isSealed()){for(Class<? extends DynamicGameElement> i : (Class<? extends DynamicGameElement>[]) classObject.getPermittedSubclasses()){addAllKnownFinalSubclasses(i, classes);}}
        else if(Modifier.isFinal(classObject.getModifiers())){classes.add(classObject);}
    }
    public static boolean isSubClass(Class<? extends DynamicGameElement> superClass, Class<? extends DynamicGameElement> subClass){
        Class<?> test = subClass;
        while(test != Object.class && test != superClass){test = test.getSuperclass();}
        return test == superClass;
    }
    public static <T extends DynamicGameElement> Class<? extends T> @NotNull [] getAllSubclasses(Class<T> classObject) {
        ArrayList<Class<? extends T>> list = new ArrayList<>();
        for(Class<? extends DynamicGameElement> i : allSubclasses){if(isSubClass(classObject, i)){list.add((Class<? extends T>) i);}}
        Class<? extends T>[] result = new Class[list.size()];
        int c = 0;
        for(Class<? extends T> i : list){
            result[c] = i;
            c++;
        }
        return result;
    }
    static{
        ArrayList<Class<? extends DynamicGameElement>> classes = new ArrayList<>();
        addAllKnownFinalSubclasses(DynamicGameElement.class, classes);
        allSubclasses = new Class[classes.size()];
        int c = 0;
        for(Class<? extends DynamicGameElement> i : classes){
            allSubclasses[c] = i;
            c++;
        }
    }

    public static final class FeatureInitializationInfo {
        private final GameElementEncapsulator<?> encapsulator;
        private final ElementNotifier notifier;
        private final GameLayer.EntityInitializationInfo info;
        private FeatureInitializationInfo(GameElementEncapsulator<?> encapsulator, ElementNotifier notifier, GameLayer.EntityInitializationInfo gameEnvironment) {
            this.encapsulator = encapsulator;
            this.notifier = notifier;
            this.info = gameEnvironment;
        }
        public GameElementEncapsulator<?> encapsulator() {return this.encapsulator;}
        public ElementNotifier notifier() {return this.notifier;}
        public PlayerInputDecorator input(){return this.info.inputs();}
        public GameElementOperator operator(){return this.info.operator();}
    }
}