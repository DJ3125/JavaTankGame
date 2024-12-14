package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer;

import com.example.finalproject_4_5_24.AnglesAndTrig;
import com.example.finalproject_4_5_24.Obstacle;
import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import com.example.finalproject_4_5_24.controllerAndInput.PlayerInputDecorator;
import com.example.finalproject_4_5_24.controllerAndInput.UIElement;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.LevelLayers;
import com.example.finalproject_4_5_24.features.movement.Angled;
import com.example.finalproject_4_5_24.features.movement.Located;
import com.example.finalproject_4_5_24.features.scalingAndPositioning.PositioningTypes;
import com.example.finalproject_4_5_24.shapes.Line;
import com.example.finalproject_4_5_24.shapes.Polygon;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Objects;

public final class GameLayer extends LevelLayers<Level> {
    public GameLayer(Level currentScene, Polygon polygon, Line... lines) {
        super(currentScene);
        this.info = new EntityInitializationInfo(this);
        this.operator = new GameElementOperator(this);
        {
            float xMin = Float.MAX_VALUE, xMax = Float.MIN_VALUE, yMin = Float.MAX_VALUE, yMax = Float.MIN_VALUE;
            Polygon.PathIterator iterator = polygon.iterator();
            while(iterator.hasNext()){
                Located i = iterator.next();
                xMin = Math.min(xMin, i.getX());
                xMax = Math.max(xMax, i.getX());
                yMin = Math.min(yMin, i.getY());
                yMax = Math.max(yMax, i.getY());
                Located next = iterator.getPointAfter();
                this.operator.addElement(this.createObstacleFromLine(i.getX(), i.getY(), next.getX(), next.getY()));
            }
            this.camera = new DynamicCamera(new Located() {
                @Override public float getX() {return 0;}
                @Override public float getY() {return 0;}
            }, xMin, xMax, yMin, yMax);
        }
        for(Line i : lines){this.operator.addElement(this.createObstacleFromLine(i.start().getX(), i.start().getY(), i.finish().getX(), i.finish().getY()));}
//        this.gameEnvironment = new EntityInitializationInfo()
    }

    @Override protected void draw(GraphicsContext ctx) {
        ctx.save();
        ctx.translate(-this.camera.getX()/this.camera.scale() + GameController.canvasWidth()/2.0, -this.camera.getY()/this.camera.scale() +  GameController.canvasHeight()/2.0);
        ctx.scale(1.0/this.camera.scale(), 1.0/this.camera.scale());

        ctx.drawImage(GameController.getImage("grass"), -4000, -4000, 15000, 15000);

        ctx.fillRect(GameController.convertXScreenLocToGameLoc(this.getInputs().getScreenMouseX()), GameController.convertYScreenLocToGameLoc(this.getInputs().getScreenMouseY()),  10, 10);

        this.operator.loopThroughAll(encapsulator -> this.draw(encapsulator, ctx));
        ctx.restore();
        for(UIElement i : this.uiElements){i.drawSelf(ctx);}
    }

    @Override protected void update() {
        for(UIElement i : this.uiElements){i.updateSelf();}
        this.operator.updateAll();
    }
    public GameElementOperator getOperator() {return this.operator;}

    public void setCameraFocus(Located focus){this.camera.focus = focus;}

    private void draw(GameElementEncapsulator<?> entity, GraphicsContext ctx){
        PositioningTypes.DimensionsEncapsulator dimensionsEncapsulator = entity.getDimensionsEncapsulator();
        ctx.save();
        ctx.translate(entity.getX(), entity.getY());
        ctx.rotate(Math.toDegrees(entity.getAngle()));
        ctx.scale(dimensionsEncapsulator.getXScale(), dimensionsEncapsulator.getYScale());
        ctx.drawImage(GameController.getImage(entity.getImage()), -dimensionsEncapsulator.getWidth()/2.0- dimensionsEncapsulator.getXDistanceFromCenter(), -dimensionsEncapsulator.getHeight()/2.0-dimensionsEncapsulator.getYDistanceFromCenter(), dimensionsEncapsulator.getWidth(), dimensionsEncapsulator.getHeight());
        ctx.restore();
    }

    private Obstacle createObstacleFromLine(float x1, float y1, float x2, float y2){
        float angle = (float) AnglesAndTrig.getAngle(x1, y1, x2, y2);
        float dist = (float)AnglesAndTrig.findDist(x1, y1, x2, y2);
        return new Obstacle(this.info, (x1 + x2)/2, (y1 + y2)/2, angle, dist, 1060);
    }

    public float getCameraX(){return this.camera.getX();}
    public float getCameraY(){return this.camera.getY();}
    public float getCameraScale(){return this.camera.scale();}

    public EntityInitializationInfo getInfo() {return this.info;}

    @Override public void addUIElement(UIElement element) {this.uiElements.add(element);}

    private final GameElementOperator operator;
    private final DynamicCamera camera;
    private final EntityInitializationInfo info;
    private final ArrayList<UIElement> uiElements = new ArrayList<>();
//    private final EntityInitializationInfo gameEnvironment;

    private static final class DynamicCamera implements Located, Angled {
        private DynamicCamera(Located focus, float leftBound, float rightBound, float lowBound, float highBound){
            this.focus = focus;
            this.leftBound = leftBound;
            this.rightBound = rightBound;
            this.lowBound = lowBound;
            this.upBound = highBound;
        }

        @Override public float getAngle() {return 0;}
        @Override public float getX() {return Math.min(Math.max(this.focus.getX(), this.leftBound + GameController.canvasWidth()/2 * this.scale()), this.rightBound - GameController.canvasWidth()/2 * this.scale());}
        @Override public float getY() {return Math.min(Math.max(this.focus.getY(), this.lowBound + GameController.canvasHeight()/2 * this.scale()), this.upBound - GameController.canvasHeight()/2 * this.scale());}
        float scale(){return 4;}
        private Located focus;
        private final float leftBound, rightBound, lowBound, upBound;
    }

    public static final class EntityInitializationInfo{
        private EntityInitializationInfo(GameLayer layer) {this.layer = layer;}
        public GameElementOperator operator() {return this.layer.operator;}
        public PlayerInputDecorator inputs() {return this.layer.getInputs();}
        private final GameLayer layer;
    }
}
