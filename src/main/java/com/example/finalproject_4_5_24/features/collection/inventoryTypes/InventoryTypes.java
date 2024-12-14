package com.example.finalproject_4_5_24.features.collection.inventoryTypes;

import com.example.finalproject_4_5_24.features.collection.InventoryItem;

public enum InventoryTypes {
    NORMAL_BULLET_CARTRIDGE {
        @Override public final InventoryItem getInventoryItem(int initialCount) {
            if(initialCount < 0){throw new RuntimeException("Cannot have a negative count");}
            int max = 32;
            return new NormalBulletAmmo(Math.min(max, initialCount), max);
//            int remaining = initialCount;
//            NormalBulletAmmo[] ammo = new NormalBulletAmmo[initialCount/max + 1];
//            for(int i = 0; i < initialCount/max + 1; i++){
//                int took = Math.min(remaining, max);
//                ammo[i] = new NormalBulletAmmo(took, max);
//                remaining -= took;
//            }
//            return ammo;
        }
    },
    HEALTH_PACK{
        @Override public final InventoryItem getInventoryItem(int initialCount) {
            if(initialCount < 0){throw new RuntimeException("Cannot have a negative count");}
            int max = 3;
            return new HealthPack(Math.min(3, initialCount), max);
        }
    }


    ;
    public abstract InventoryItem getInventoryItem(int initialCount);
}
