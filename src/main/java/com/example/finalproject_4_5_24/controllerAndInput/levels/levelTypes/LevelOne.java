package com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes;

import com.example.finalproject_4_5_24.*;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.TertiaryLayerUIElements;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.Transitions;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameLayer;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes.CircularTransition;
import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes.FadeTransition;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.InventoryViewing;
import com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer.NullTertiaryUIElement;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.BulletAndAmmoDisplay;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.Button;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.HealthBar;
import com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements.ObjectiveDisplay;
import com.example.finalproject_4_5_24.features.movement.Located;
import com.example.finalproject_4_5_24.shapes.Polygon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public final class LevelOne extends Level {
    public LevelOne(){
        super(200, 0, new Polygon(new Located() {
            @Override public float getX() {return -3000;}
            @Override public float getY() {return -3000;}
        }, new Located() {
            @Override public float getX() {return -3000;}
            @Override public float getY() {return 10000;}
        }, new Located() {
            @Override public float getX() {return 10000;}
            @Override public float getY() {return 10000;}
        }, new Located() {
            @Override public float getX() {return 10000;}
            @Override public float getY() {return -3000;}
        }));

        this.getGameLayer().addUIElement(new HealthBar(this.getGameLayer(), 300, 20, this.getProtagonist()));
        this.getGameLayer().addUIElement(new BulletAndAmmoDisplay(this.getGameLayer(), this.getProtagonist(), GameController.canvasWidth() - 70, 50, 50, 100));
//        this.getOperator().addElement(this.goal);


        this.getGameLayer().addUIElement(new Button("View Inventory", false, this.getGameLayer(), GameController.canvasWidth()/2, 40, 80, 30, button -> {
            this.tertiaryLayerUIElements = new InventoryViewing(this, tertiaryLayerUIElements1 -> {
                this.tertiaryLayerUIElements = tertiaryLayerUIElements1;
            }, this.getProtagonist());
        }));
        this.transitions = new FadeTransition(this, true, Color.BLACK, 200);
        this.tertiaryLayerUIElements = new NullTertiaryUIElement(this, tertiaryLayerUIElements1 -> {this.tertiaryLayerUIElements = tertiaryLayerUIElements1;});
        this.goal = new Goal(this.getGameLayer().getInfo(), 800, 800, 0);
        this.getOperator().addElement(this.goal);

        this.getGameLayer().addUIElement(new ObjectiveDisplay(this.getGameLayer(), 10, 30, 100, new Pair[]{
                new Pair<>((Function<GameLayer.EntityInitializationInfo, String>) entityInitializationInfo -> {return "Reach Home";}, (Predicate<GameLayer.EntityInitializationInfo>) o -> this.goal.hasReachedTarget()),
                new Pair<>((Function<GameLayer.EntityInitializationInfo, String>) entityInitializationInfo -> {return "Kill All The Nests " + entityInitializationInfo.operator().getAllEntitiesThatSatisfyCondition(StaticEnemySpawner.class, gameElementEncapsulator -> true).size();}, (Predicate<GameLayer.EntityInitializationInfo>) o -> !this.getOperator().hasElementWithCondition(StaticEnemySpawner.class, gameElementEncapsulator -> true))
        }));


//        this.getOperator().addElement(new Obstacle(this.getGameLayer().getInfo(), 800, 1000, 0, 500, 500));
        this.getOperator().addElement(new StaticEnemySpawner(this.getGameLayer().getInfo(), -1000, -1000, 0));
        this.getOperator().addElement(new StaticEnemySpawner(this.getGameLayer().getInfo(), -1000, 9000, 0));
        this.getOperator().addElement(new StaticEnemySpawner(this.getGameLayer().getInfo(), 9000, 9000, 0));
        this.getOperator().addElement(new StaticEnemySpawner(this.getGameLayer().getInfo(), 9000, -1000, 0));



//        this.getOperator().addElement(new NormalBulletAmmoItem(this.getGameLayer().getInfo(), 500, 500, 30, 30));


        this.getOperator().addElement(new HealthPackItem(this.getGameLayer().getInfo(), 500, 500, 100, 100));

        this.getOperator().close();
//        this.registerAllAdded();
    }

    @Override protected void drawSelf(GraphicsContext ctx) {
        this.drawLayers(ctx, this.getGameLayer(), this.transitions, this.tertiaryLayerUIElements);
    }

    @Override protected void updateSelf(PlayerInputDecorator inputs) {
        for(SwitchRequests i : this.requests){i.switchLayer();}
        this.requests.clear();
        this.checkForFail();
        this.checkForSuccess();
        if(this.starting && this.transitions.isFinished()){this.starting = false;}
        else if(!this.ending && (this.hasFailed() || this.hasSucceeded())){
            this.requests.add(() ->{this.transitions = new CircularTransition(this, false, Color.BLACK, 200, this.getProtagonist());});
            this.ending = true;
        }else if(this.ending && this.transitions.isFinished()){this.switchScenes();}
        LevelLayers<?>[] layers = new LevelLayers[]{
                this.transitions,
                this.tertiaryLayerUIElements,
                this.getGameLayer(),
        };
        this.runChains(inputs, layers);
        this.updateLayers(layers);
    }



    @Override public void switchScenes() {this.requestSceneSwitch(new LevelWonScene());}
    @Override protected void checkForFail() {if(this.getProtagonist().getHealthEncapsulator().isDead() && !this.hasSucceeded()){this.setFailed();}}
    @Override public void checkForSuccess() {if(this.goal.hasReachedTarget() && !this.getOperator().hasElementWithCondition(StaticEnemySpawner.class, gameElementEncapsulator -> true) && !this.hasFailed()){this.setSuccess();}}

    private interface SwitchRequests{void switchLayer();}

    private Transitions transitions;
    private TertiaryLayerUIElements tertiaryLayerUIElements;
    private final ArrayList<SwitchRequests> requests = new ArrayList<>();
    private boolean starting = true, ending = false;
    private final Goal goal;
}
