package com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;

import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import com.example.finalproject_4_5_24.features.hitBoxes.HitBoxes;

public final class ElementNotifier {
    ElementNotifier(DynamicGameElement element){this.element = element;}
    public void notifyOfCollision(HitBoxes.ModifiedCollisionNotification notification){this.element.collisionResponse(notification);}
    public void notifyOfChange(){this.element.changeUpdate();}
    public void notifyOfItemUsage(InventoryItemOptions.ItemModification request){this.element.itemResponse(request);}
    private final DynamicGameElement element;
}
