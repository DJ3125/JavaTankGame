package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.features.movement.Located;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class CircularTransition extends NonNullTransition {
    public CircularTransition(Scenes scenes, boolean isFadeIn, Color color, int duration, float x, float y){
        super(scenes, isFadeIn, color, duration);
        this.location = new Located() {
            @Override public float getX() {return x;}
            @Override public float getY() {return y;}
        };
    }

    public CircularTransition(Scenes scenes, boolean isFadeIn, Color color, int duration, Located location){
        super(scenes, isFadeIn, color, duration);
        this.location = location;
    }

    @Override protected void performDraw(GraphicsContext ctx) {
        float x = GameController.convertXGameLocToScreenLoc(this.location.getX()), y = GameController.convertYGameLocToScreenLoc(this.location.getY());
        double radius = AnglesAndTrig.findDist(GameController.canvasHeight(), GameController.canvasWidth())/2.0 * (1 - Math.min(1, 1.0/this.getIterations() * this.getCount()));
        ctx.setFill(this.getFadeColor());
        ctx.fillRect(0, 0, GameController.canvasWidth(), Math.max(0, y - radius));
        ctx.fillRect(0, 0, Math.max(0, x - radius), GameController.canvasHeight());
        ctx.fillRect(0, Math.min(GameController.canvasHeight(), y + radius), GameController.canvasWidth(), GameController.canvasHeight() - Math.min(GameController.canvasHeight(), y + radius));
        ctx.fillRect(Math.min(GameController.canvasWidth(), x + radius), 0, GameController.canvasWidth() - Math.min(GameController.canvasWidth(), x + radius), GameController.canvasHeight());
        for(int i = (int) (x - radius); i < x + radius; i++){
            for(int j = (int)(y - radius); j < y + radius; j++){
                if(AnglesAndTrig.findDist(i, j, x, y) > radius){
                    ctx.getPixelWriter().setColor(i, j, this.getFadeColor());
                }
            }
        }
    }
    private final Located location;
}
