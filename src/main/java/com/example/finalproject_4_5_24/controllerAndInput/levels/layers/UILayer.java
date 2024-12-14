package com.example.finalproject_4_5_24.controllerAndInput.levels.layers;

import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.Scenes;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public final class UILayer extends LevelLayers<Scenes>{
    public UILayer(Scenes currentScene) {
        super(currentScene);
    }
    @Override protected void draw(GraphicsContext ctx) {for(UIElement i : this.uiElements){i.drawSelf(ctx);}}
    @Override protected void update() {for(UIElement i : this.uiElements){i.updateSelf();}}
    @Override public void addUIElement(UIElement element) {this.uiElements.add(element);}
    private final ArrayList<UIElement> uiElements = new ArrayList<>();
}
