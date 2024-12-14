package com.example.finalproject_4_5_24.controllerAndInput.levels;

import com.example.finalproject_4_5_24.*;
import com.example.finalproject_4_5_24.controllerAndInput.*;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.Transitions;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementEncapsulator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementOperator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes.LevelOne;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.InventoryViewing;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.NullTertiaryUIElement;
import com.example.finalproject_4_5_24.shapes.Line;
import com.example.finalproject_4_5_24.shapes.Polygon;

import java.util.function.Consumer;

abstract public sealed class Level extends Scenes implements SceneSwitching permits LevelOne {
    protected Level(float initialX, float initialY, Polygon polygon, Line... lines){
        super();
        this.gameLayer = new GameLayer(this, polygon, lines);
        this.protagonist = Player.getEncapsulator(this.getGameLayer().getInfo(), initialX, initialY);
        this.gameLayer.setCameraFocus(this.protagonist);
        this.gameLayer.getOperator().addElement(this.protagonist);
//        this.transitions = new CircularTransition(this, false, Color.BLACK, 200, this.protagonist);
//        this.intro = new FadeTransition(this, true, Color.BLACK, this.getIntroLength());
    }

    protected final GameElementEncapsulator<? extends Player> getProtagonist(){return this.protagonist;}
    public final GameElementOperator getOperator(){return this.gameLayer.getOperator();}

    @Override public final float convertXGameLocToScreenLoc(float xPos){return (xPos - this.gameLayer.getCameraX())/this.gameLayer.getCameraScale() + GameController.canvasWidth()/2;}
    @Override public final float convertYGameLocToScreenLoc(float yPos){return (yPos - this.gameLayer.getCameraY())/this.gameLayer.getCameraScale() + GameController.canvasHeight()/2;}
    @Override public final float convertXScreenLocToGameLoc(float xPos){return this.gameLayer.getCameraX() + this.gameLayer.getCameraScale() *(xPos- GameController.canvasWidth()/2);}
    @Override public final float convertYScreenLocToGameLoc(float yPos){return this.gameLayer.getCameraY() + this.gameLayer.getCameraScale() *(yPos- GameController.canvasHeight()/2);}

    public final PlayerInputDecorator getInput(){return this.gameLayer.getInputs();}

    protected final boolean hasFailed(){return this.failed;}
    protected final boolean hasSucceeded(){return this.success;}
    protected final void setFailed() {this.failed = true;}
    protected final void setSuccess() {this.success = true;}

    protected abstract void checkForFail();
    protected abstract void checkForSuccess();

    protected final GameLayer getGameLayer(){return this.gameLayer;}

    private final GameElementEncapsulator<? extends Player> protagonist;

    private boolean success, failed;
    private final GameLayer gameLayer;
}
