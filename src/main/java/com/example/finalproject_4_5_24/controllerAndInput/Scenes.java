package com.example.finalproject_4_5_24.controllerAndInput;

import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes.LevelWonScene;
import com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes.TitleScreen;
import javafx.scene.canvas.GraphicsContext;

import static com.example.finalproject_4_5_24.controllerAndInput.GameController.interval;

abstract public sealed class Scenes implements SceneSwitching permits Level, LevelWonScene, TitleScreen {
    protected Scenes(){
        this.currentTick = 0;
//        this.introLength = 200;
//        this.outroLength = 200;
    }
    public boolean registerCodes(){return true;}
    final void updateCanvasAndGame(GraphicsContext ctx, PlayerInputDecorator inputEvent){
        this.currentTick++;
        ctx.clearRect(0, 0, GameController.canvasWidth(), GameController.canvasHeight());
        this.updateSelf(inputEvent);
        this.drawSelf(ctx);
//        LevelLayers<?>[] layers = this.getLayersInOrderFromFirstToLastUpdate();
//        boolean result = true;
//        PlayerInputDecorator decorator = inputEvent;
//        for (LevelLayers<?> i : layers) {
//            result = i.relayUpdateChain(result);
//            decorator = i.relayInputChain(decorator);
//        }
//        for(LevelLayers<?> i : layers){i.update();}
//        for(int i = layers.length - 1; i >= 0; i--){layers[i].draw(ctx);}
        this.lastRan = System.currentTimeMillis();
    }

    protected final void runChains(PlayerInputDecorator input, LevelLayers<?>... layers){
        boolean result = true;
        PlayerInputDecorator decorator = input;
        for (LevelLayers<?> i : layers) {
            result = i.relayUpdateChain(result);
            decorator = i.relayInputChain(decorator);
        }
    }

    protected final void updateLayers(LevelLayers<?>... layers){for(LevelLayers<?> i : layers){i.updateLayer();}}
    protected final void drawLayers(GraphicsContext ctx, LevelLayers<?>... layers){
        for(LevelLayers<?> i : layers){
            ctx.save();
            i.draw(ctx);
            ctx.restore();
        }
    }



    protected abstract void updateSelf(PlayerInputDecorator inputs);
    protected abstract void drawSelf(GraphicsContext ctx);

    protected final void requestSceneSwitch(Scenes scene){GameController.requestSceneSwitch(scene);}
    public float convertXGameLocToScreenLoc(float xPos){return xPos;}
    public float convertYGameLocToScreenLoc(float yPos){return yPos;}
    public float convertXScreenLocToGameLoc(float xPos){return xPos;}
    public float convertYScreenLocToGameLoc(float yPos){return yPos;}
    final void resetLastRan(){this.lastRan = System.currentTimeMillis();}
    final boolean readyToRun(){return System.currentTimeMillis() > this.lastRan + interval;}
    private long currentTick;
    private long lastRan = System.currentTimeMillis();
}
