package com.example.finalproject_4_5_24.features.health;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;
import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.Features;
import com.example.finalproject_4_5_24.features.attack.AttackPacket;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;

public sealed abstract class Health extends Features implements HealthProperties permits NullHealth, NormalHealth{
    protected Health(DynamicGameElement.FeatureInitializationInfo info, int healthAmount){
        super(info);
        this.maxHealth = this.currentHealth = healthAmount;
    }

    @Override protected final void updateOnTurn() {}
    @Override public final boolean isDead() {return this.currentHealth <= 0;}
    @Override public final int getHealthRemaining() {return this.currentHealth;}
    @Override public final int changeHealth(AttackPacket attackPacket) {return this.changeHealth(attackPacket.dmg());}
    @Override protected void respondToItem(InventoryItemOptions.ItemModification itemModification) {this.changeHealth(itemModification);}
    public final int changeHealth(InventoryItemOptions.ItemModification modification){return this.changeHealth(modification.getHealthChange());}
    private int changeHealth(int healthChange){
        if(healthChange < 0){return this.takeDamage(healthChange);}
        else if(healthChange > 0){return this.heal(healthChange);}
        return 0;
    }
    @Override protected void updateOnChange() {}
    @Override protected void updateOnTick() {}
    @Override public final int getMaxHealth() {return this.maxHealth;}


    abstract protected int takeDamage(int amount);
    abstract protected int heal(int amount);

    protected final int changeCurrentHealth(int change) {
        int former = this.currentHealth;
        this.currentHealth += change;
        if(this.currentHealth > this.maxHealth){this.currentHealth = this.maxHealth;}
        else if(this.currentHealth < 0){this.currentHealth = 0;}
        return this.currentHealth - former;
    }

    private final int maxHealth;
    private int currentHealth;
}