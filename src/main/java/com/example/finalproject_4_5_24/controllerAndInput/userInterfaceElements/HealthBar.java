package com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.features.health.HealthTypes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public final class HealthBar extends UIElement<GameLayer>{
    public HealthBar(GameLayer layer, float width, float height, GameElementEncapsulator<?> focus) {
        super(layer, 0, 0, width, height);
        if(focus.getHealthEncapsulator().getMaxHealth() <= 0){throw new RuntimeException("Cannot assign a healthBar to an entity with no health");}
        this.focus = focus;
        this.lastRecordedHealth = this.lastRecordedChange = Math.max(focus.getHealthEncapsulator().getHealthRemaining(), 0);
    }

    @Override public void updateSelf() {
        int remaining = this.focus.getHealthEncapsulator().getHealthRemaining();
        if(this.lastRecordedHealth > remaining) {
            this.timeSinceLastChange++;
            if (this.timeSinceLastChange > GameController.fps) {this.lastRecordedHealth--;}
        }else if(this.lastRecordedHealth < remaining){
            this.lastRecordedHealth = remaining;
            this.timeSinceLastChange = 0;
        }
        if(remaining != this.lastRecordedChange){
            this.timeSinceLastChange = 0;
            this.lastRecordedChange = remaining;
        }
    }

    @Override public void drawSelf(GraphicsContext ctx) {
        Paint current = ctx.getFill();
        int borderSize = 3;
        ctx.fillRect(GameController.canvasWidth()/2 - this.getWidth()/2 - borderSize, GameController.canvasHeight() - this.getHeight()/2 - borderSize - 20, this.getWidth() + 2 * borderSize, this.getHeight() + 2 * borderSize);
        ctx.setFill(Color.BLACK);
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSIC", 20));
        ctx.fillText("Health", GameController.canvasWidth()/2, GameController.canvasHeight() - this.getHeight()/2 - 30);
        HealthTypes.HealthEncapsulator health = this.focus.getHealthEncapsulator();
        ctx.setFill(Color.YELLOW);
        ctx.fillRect(GameController.canvasWidth() /2 - this.getWidth()/2, GameController.canvasHeight() - this.getHeight()/2 - 20, this.getWidth() * this.lastRecordedHealth/health.getMaxHealth(), this.getHeight());
        ctx.setFill(Color.GREEN);
        ctx.fillRect(GameController.canvasWidth() /2 - this.getWidth()/2, GameController.canvasHeight() - this.getHeight()/2 - 20, this.getWidth() * health.getHealthRemaining()/health.getMaxHealth(), this.getHeight());
        ctx.setFill(current);
    }

    @Override public boolean clickedOn() {return false;}

    private int lastRecordedHealth, lastRecordedChange;
    private short timeSinceLastChange;
    private final GameElementEncapsulator<?> focus;
}
