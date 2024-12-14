package com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;

import com.example.finalproject_4_5_24.bullets.BranchingFlameExpirationBullet;
import com.example.finalproject_4_5_24.bullets.ExpirationProjectiles;
import com.example.finalproject_4_5_24.bullets.NormalExpirationProjectiles;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.spawning.SpawningTypes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public final class BulletAndAmmoDisplay extends UIElement<GameLayer>{
    public BulletAndAmmoDisplay(GameLayer layer, GameElementEncapsulator<?> focus, float x, float y, float width, float height) {
        super(layer, x, y, width, height);
        this.focus = focus;
    }

    @Override public void drawSelf(GraphicsContext ctx) {
        ctx.save();
        SpawningTypes.SpawnerEncapsulator s = this.focus.getSpawningEncapsulator();
        if(s.getCurrentCapacity() <= 0.2 * s.getMaxCapacity()){ctx.setFill(Color.RED);}
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.fillText("Current  " + s.getCurrentCapacity() + "\n Max  " + s.getMaxCapacity(), this.getX(), this.getY());
        if(s.getSpawningType() == BranchingFlameExpirationBullet.class){
            ctx.drawImage(GameController.getImage("fire"), this.getX() - this.getWidth()/2, this.getY() + 30, 50, 50);
        }else if(s.getSpawningType() == NormalExpirationProjectiles.class){
            ctx.drawImage(GameController.getImage("bullet"), this.getX() - this.getWidth()/2, this.getY() + 30, 50, 50);
        }
        ctx.restore();
    }

    @Override public void updateSelf() {

    }


    private final GameElementEncapsulator<?> focus;
}
