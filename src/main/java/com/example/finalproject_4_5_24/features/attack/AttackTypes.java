package com.example.finalproject_4_5_24.features.attack;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.FeatureEncapsulator;

public enum AttackTypes {
    NORMAL_ATTACK{@Override public final AttackEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int attackAmount) {return this.encapsulator(new NormalAttack(info, attackAmount));}},
    NULL_ATTACK{@Override public final AttackEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int attackAmount) {return this.encapsulator(new NullAttack(info));}};
    public abstract AttackEncapsulator getEncapsulator(DynamicGameElement.FeatureInitializationInfo info, int attackAmount);
    protected final AttackEncapsulator encapsulator(Attack attack){return new AttackEncapsulator(attack);}

    public final static class AttackEncapsulator extends FeatureEncapsulator<Attack> implements AttackProperties {
        private AttackEncapsulator(Attack attack){super(Attack.class, attack);}
        @Override public int getAttack() {return this.getFeature().getAttack();}
        @Override public int getNormalAttack() {return this.getFeature().getNormalAttack();}
    }
}
