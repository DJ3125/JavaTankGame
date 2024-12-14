package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class FadeTransition extends NonNullTransition {
    public FadeTransition(Scenes scenes, boolean isFadeIn, Color color, int duration){super(scenes, isFadeIn, color, duration);}
    @Override protected void performDraw(GraphicsContext ctx) {
        ctx.setGlobalAlpha(Math.min(1, 1.0/this.getIterations() * this.getCount()));
        ctx.setFill(this.getFadeColor());
        ctx.fillRect(0, 0, GameController.canvasWidth(), GameController.canvasHeight());
    }
}
