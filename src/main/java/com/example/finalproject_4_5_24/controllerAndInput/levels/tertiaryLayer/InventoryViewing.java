package com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.TertiaryLayerUIElements;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.Button;
import com.example.finalproject_4_5_24.features.collection.InventoryItem;
import com.example.finalproject_4_5_24.features.collection.InventoryItemOptions;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public final class InventoryViewing extends TertiaryLayerUIElements {
    public InventoryViewing(Level level, Consumer<TertiaryLayerUIElements> switching, GameElementEncapsulator<?> focus){
        super(level, switching);
        this.focus = focus;
        this.previousSize = 0;
    }
    
    @Override protected void draw(GraphicsContext ctx) {
        ctx.save();
        ctx.setFill(Color.BLACK);
        ctx.setGlobalAlpha(0.5);
        ctx.fillRect(0, 0, GameController.canvasWidth(), GameController.canvasHeight());
        ctx.restore();
        ctx.save();
        ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSIC", 40));
        ctx.setFill(Color.BLACK);
        ctx.fillRect(this.border, this.border, GameController.canvasWidth() - 2 * this.border, GameController.canvasHeight() - 2 * this.border);
        ctx.setFill(Color.WHITE);
        ctx.setTextAlign(TextAlignment.CENTER);
        double inventoryWidthSpace = (GameController.canvasWidth() - 2 * this.border) * this.proportion;
        ctx.fillText("Inventory", this.border + inventoryWidthSpace/2, this.border + ctx.getFont().getSize());
        ctx.setStroke(Color.WHITE);
        ctx.strokeLine(inventoryWidthSpace + this.border, this.border + 1, inventoryWidthSpace + this.border, GameController.canvasHeight() - this.border - 1);
        if(this.focus.getCollectingEncapsulator().isEmpty()){
            ctx.fillText("You Have", (GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion)/2 + inventoryWidthSpace + this.border, GameController.canvasHeight()/2, (GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion));
            ctx.fillText("No Items", (GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion)/2 + inventoryWidthSpace + this.border, GameController.canvasHeight()/2 + ctx.getFont().getSize() + 10, (GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion));
        }
        double spacing = 5, width = 40, height = 40;
        int size = this.focus.getCollectingEncapsulator().getInventorySize();
        double level = this.border + spacing + ctx.getFont().getSize();
//        Iterator<InventoryItem> iterator = this.focus.getCollectingEncapsulator().iterator();
        this.items.forEach(button -> {
            button.drawSelf(ctx);
        });
        while(size > 0){
            int amountToPlace = Math.min(size, (int)((inventoryWidthSpace - spacing)/(width + spacing)));
            double tabAmount = (inventoryWidthSpace - amountToPlace * (width + spacing))/2;
//            tabAmount/= 2;
            for(int i = 0; i < amountToPlace; i++){ctx.strokeRect(tabAmount + this.border + (spacing + width) * i, level, width, height);}
            level += spacing + height;
            size -= amountToPlace;
        }
        for(InventoryUsageOption i : this.options){i.drawSelf(ctx);}
        if(this.buttonClickedOn != null){
            ctx.save();
            ctx.fillText(this.buttonClickedOn.item.name(), (float) ((GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion)/2 + inventoryWidthSpace + this.border), this.border + 40, (float) (GameController.canvasWidth() - 2 * this.border - inventoryWidthSpace - 10));
            ctx.setFont(GameController.getFont("arcadeClassicFont/ARCADECLASSICFONT", 20));
            ctx.fillText(this.buttonClickedOn.item.getCurrentItemCount() + "x", (float) ((GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion)/2 + inventoryWidthSpace + this.border), this.border + 100, (float) (GameController.canvasWidth() - 2 * this.border - inventoryWidthSpace - 10));
            ctx.restore();
        }
        this.closeButton.drawSelf(ctx);
        ctx.restore();
    }

    @Override protected void update() {
//        if(this.closeButton.clickedOn()){this.returnToPlaying();}
        int size = this.newSize();
        if(size != this.previousSize){
            this.previousSize = size;
            this.buttonClickedOn = null;
            this.items.clear();
            this.options.clear();
            int inventorySize = this.focus.getCollectingEncapsulator().getInventorySize();
            double spacing = 5, width = 40, height = 40, level = this.border + spacing + 40 + height/2, inventoryWidthSpace = (GameController.canvasWidth() - 2 * this.border) * this.proportion;
            int margin = 3;
            Iterator<InventoryItem> item = this.focus.getCollectingEncapsulator().iterator();
            while(inventorySize > 0 && item.hasNext()){
                int amountToPlace = Math.min(inventorySize, (int)((inventoryWidthSpace - spacing)/(width + spacing)));
                double tabAmount = (inventoryWidthSpace - amountToPlace * (width + spacing))/2;

                int i = 0;
                while(i < amountToPlace && item.hasNext()){
                    this.items.add(new InventoryButton(this, (float) ((tabAmount + this.border + (spacing + width) * i) + width/2), (float) (level), (float) width - 2 * margin, (float) height - 2 * margin, item.next(), inventoryButton -> {
                        this.buttonClickedOn = inventoryButton;
                        this.options.clear();
                        int counter = 0;
                        for(InventoryItemOptions<?> k : inventoryButton.item){
                            this.options.add(new InventoryUsageOption(this, (float) ((GameController.canvasWidth() - 2 * this.border) * (1 - this.proportion)/2 + inventoryWidthSpace + this.border), GameController.canvasHeight()/2 + counter * 40, (float) (GameController.canvasWidth() - 2 * this.border - inventoryWidthSpace - 10), 30, k));
                            counter++;
                        }
                    }));
                    i++;
                }
                level += spacing + height;
                inventorySize -= amountToPlace;
            }
        }else{
            for(InventoryButton i : this.items){i.updateSelf();}
            for(InventoryUsageOption i : this.options){i.updateSelf();}
        }
        this.closeButton.updateSelf();
    }

    @Override protected PlayerInputDecorator modifyEvent(PlayerInputDecorator playerInputEvent) {return PlayerInputDecorator.getNullInstance(playerInputEvent);}


    private int newSize(){
        int counter = 0;
        for(InventoryItem i : this.focus.getCollectingEncapsulator()){if(i != null){counter++;}}
        return counter;
    }
    
    private final Button closeButton = new Button("x", true, this, GameController.canvasWidth() - 10, GameController.canvasHeight()- 10, 20, 20, (button -> {this.switchingBack.accept(new NullTertiaryUIElement(this.getCurrentScene(), tertiaryLayerUIElements -> {}));}));
    private final ArrayList<InventoryButton> items = new ArrayList<>();
    private final ArrayList<InventoryUsageOption> options = new ArrayList<>();
    private InventoryButton buttonClickedOn;
    private int previousSize;


    private final double proportion = 0.7;
    private final int border = 40;
    private final GameElementEncapsulator<?> focus;


    public static final class InventoryButton extends UIElement<InventoryViewing>{
        private InventoryButton(InventoryViewing layer, float x, float y, float width, float height, InventoryItem item, Consumer<InventoryButton> action) {
            super(layer, x, y, width, height);
            this.item = item;
            this.action = action;
        }

        @Override public void drawSelf(GraphicsContext ctx) {ctx.drawImage(GameController.getImage(this.item.image()), this.getX() - this.getWidth()/2, this.getY() - this.getHeight()/2, this.getWidth(), this.getHeight());}
        @Override public void updateSelf() {if(this.clickedOn()){this.action.accept(this);}}
        private final InventoryItem item;
        private final Consumer<InventoryButton> action;
    }

    public static final class InventoryUsageOption extends UIElement<InventoryViewing>{
        private InventoryUsageOption(InventoryViewing layer, float x, float y, float width, float height, InventoryItemOptions<?> option) {
            super(layer, x, y, width, height);
            this.option = option;
        }

        @Override public void drawSelf(GraphicsContext ctx) {
            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.fillText(this.option.getName(), this.getX(), this.getY(), this.getWidth());
        }

        @Override public void updateSelf() {if(this.clickedOn()){this.option.performAction((this.getLayer()).focus);}}

        private final InventoryItemOptions<?> option;
    }
}
