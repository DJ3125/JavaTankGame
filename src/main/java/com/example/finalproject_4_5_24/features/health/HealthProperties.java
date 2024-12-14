package com.example.finalproject_4_5_24.features.health;

import com.example.finalproject_4_5_24.features.attack.AttackPacket;

sealed interface HealthProperties permits Health, HealthTypes.HealthEncapsulator {
    int getHealthRemaining();
    int getMaxHealth();
    int changeHealth(AttackPacket packet);
    boolean isDead();
}
