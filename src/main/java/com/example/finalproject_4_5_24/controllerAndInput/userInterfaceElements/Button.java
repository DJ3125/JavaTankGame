package com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Button extends UIElement<LevelLayers<?>>{
    public Button(String name, boolean isImage, LevelLayers<?> layer, float x, float y, float width, float height, Consumer<Button> onclick) {
        super(layer, x, y, width, height);
        this.onClick = onclick;
        this.display = name;
        this.isImage = isImage;
    }
    @Override public void drawSelf(GraphicsContext ctx) {
        if(this.isImage){ctx.drawImage(GameController.getImage(this.display), this.getX() - this.getWidth()/2, this.getY() - this.getHeight()/2, this.getWidth(), this.getHeight());}
        else{
            ctx.save();
            ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSICFONT", 20));
            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.fillText(this.display, this.getX(), this.getY());
            ctx.restore();
        }
    }
    @Override public void updateSelf() {if(this.clickedOn()){this.onClick.accept(this);}}
    private final Consumer<Button> onClick;
    private String display;
    private boolean isImage;
}
