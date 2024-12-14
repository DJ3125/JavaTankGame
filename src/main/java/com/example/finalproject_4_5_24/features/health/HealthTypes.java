package com.example.finalproject_4_5_24.features.health;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;
import com.example.finalproject_4_5_24.features.attack.AttackPacket;

public enum HealthTypes {
    NULL_HEALTH{@Override public final HealthEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int healthAmount) {return this.encapsulate(new NullHealth(info));}},
    NORMAL_HEALTH{@Override public final HealthEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int healthAmount) {return this.encapsulate(new NormalHealth(info, healthAmount));}};
    public abstract HealthEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int healthAmount);
    protected final HealthEncapsulator encapsulate(Health encapsulator){return new HealthEncapsulator(encapsulator);}

    public final static class HealthEncapsulator extends FeatureEncapsulator<Health> implements HealthProperties{
        private HealthEncapsulator(Health health) {super(Health.class, health);}
        @Override public boolean isDead() {return this.getFeature().isDead();}
        @Override public int getHealthRemaining() {return this.getFeature().getHealthRemaining();}
        @Override public int changeHealth(AttackPacket attackPacket) {return this.getFeature().changeHealth(attackPacket);}
        @Override public int getMaxHealth() {return this.getFeature().getMaxHealth();}
    }
}
