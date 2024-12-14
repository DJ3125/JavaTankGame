package com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;

import java.util.function.Function;
import java.util.function.Predicate;

public final class ObjectiveDisplay extends UIElement<GameLayer>{
    public ObjectiveDisplay(GameLayer layers, float x, float y, float width, Pair<Function<GameLayer.EntityInitializationInfo, String>, Predicate<GameLayer.EntityInitializationInfo>>[] objectives) {
        super(layers, x, y, width, 0);
        this.pairs = objectives;
        this.results = new boolean[this.pairs.length];
    }

    @Override public void drawSelf(GraphicsContext ctx) {
        ctx.save();
        int titleFontSize = 30;
        ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSIC", titleFontSize));
        ctx.setTextAlign(TextAlignment.LEFT);
        ctx.fillText("Objectives", this.getX(), this.getY(), this.getWidth());

        ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSIC", 20));
        Image check = GameController.getImage("checkedBox"), unCheck = GameController.getImage("uncheckedBox");
        for(int i = 0; i < this.results.length; i++){
            if(this.results[i]){ctx.drawImage(check, this.getX(), i * 43 + titleFontSize + this.getY(), 30, 30);}
            else{ctx.drawImage(unCheck, this.getX(), i * 43 + titleFontSize + this.getY(), 30, 30);}
            ctx.fillText(this.pairs[i].getKey().apply(this.getLayer().getInfo()), 40 + this.getX(), i * 43 + titleFontSize + this.getY() + 20);
        }
    }

    @Override public void updateSelf() {for(int i = 0; i < this.pairs.length; i ++){this.results[i] = this.pairs[i].getValue().test(this.getLayer().getInfo());}}

    private final boolean[] results;
    private final Pair<Function<GameLayer.EntityInitializationInfo, String>, Predicate<GameLayer.EntityInitializationInfo>>[] pairs;
}
