package com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.UILayer;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.Button;
import javafx.scene.canvas.GraphicsContext;

public final class LevelWonScene extends Scenes {
    public LevelWonScene(){
        LevelWonScene thisScene = this;
        this.layer.addUIElement(new Button("Retry Level", false, this.layer, GameController.canvasWidth()/2, GameController.canvasHeight() - 30, 50, 50, button -> {
            thisScene.switchScenes();
        }));
    }

    @Override protected void updateSelf(PlayerInputDecorator inputs) {
        this.runChains(inputs, this.layer);
        this.updateLayers(this.layer);
    }

    @Override protected void drawSelf(GraphicsContext ctx) {
        this.drawLayers(ctx, this.layer);
    }


    @Override public void switchScenes() {
        this.requestSceneSwitch(new LevelOne());
    }

    private final UILayer layer = new UILayer(this);
}
