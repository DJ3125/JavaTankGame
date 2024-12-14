package com.example.finalproject_4_5_24.controllerAndInput;

import com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer.GameElementOperator;
import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;
import com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes.LevelOne;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class GameController {
    private static GameController currentController;

    public static PlayerInputDecorator getGameInput(){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        if(currentController.currentLevel instanceof Level level){return level.getInput();}
        throw new RuntimeException("Cannot call the operator during a non level");
    }

    public static float canvasWidth(){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return (float) currentController.canvas.getWidth();
    }
    public static float canvasHeight(){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return (float) currentController.canvas.getHeight();
    }

    public static float convertXGameLocToScreenLoc(float xPos){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return currentController.currentLevel.convertXGameLocToScreenLoc(xPos);
    }
    public static float convertYGameLocToScreenLoc(float yPos){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return currentController.currentLevel.convertYGameLocToScreenLoc(yPos);
    }
    public static float convertXScreenLocToGameLoc(float xPos){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return currentController.currentLevel.convertXScreenLocToGameLoc(xPos);
    }
    public static float convertYScreenLocToGameLoc(float yPos){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        return currentController.currentLevel.convertYScreenLocToGameLoc(yPos);
    }
    public static Image getImage(String name){
        if(currentController == null){throw new RuntimeException("The Current Scene Hasn't Been Initialized");}
        Image result = pieceImages.get(name);
        if(result == null){throw new RuntimeException("The image name " + name + " does not exist in the pictures directory. Check path again");}
        return result;
    }
    public static Font getFont(String path, int size){
        Font font = null;
        try {font = Font.loadFont(new FileInputStream("src/main/resources/fonts/" + path + ".TTF"), size);} catch (FileNotFoundException error) {error.fillInStackTrace();}
        return font;
    }

    static void requestSceneSwitch(Scenes nextScene){
        currentController.sceneQueried = nextScene;
        nextScene.resetLastRan();
    }


    @FXML private Canvas canvas;
    private GraphicsContext ctx;

    private void initializeAll(){
        currentController = this;
        this.ctx = this.canvas.getGraphicsContext2D();
        this.currentLevel = new LevelOne();
        this.playerInput = new PlayerInput();
        this.canvas.requestFocus();
        this.canvas.setOnMouseClicked(mouseEvent ->{
            if(!this.mouseClickedQueried){
                this.mouseClickedQueried = true;
                this.beforeRun.add(() -> this.playerInput.mouseClicked = true);
                this.afterRun.add(() ->{
                    this.playerInput.mouseClicked = false;
                    this.mouseClickedQueried = false;
                });
            }
        });
        this.canvas.setOnKeyPressed(keyEvent -> this.beforeRun.add(()-> this.playerInput.setKeyCode(keyEvent.getCode(), true)));
        this.canvas.setOnKeyReleased(keyEvent -> this.afterRun.add(()-> this.playerInput.setKeyCode(keyEvent.getCode(), false)));
        this.canvas.setOnMouseMoved(mouseEvent -> {
            if(!this.mouseMovementEventQueried){
                this.mouseMovementEventQueried = true;
                this.beforeRun.add(() -> this.playerInput.setNewMouseLocation((int)(mouseEvent.getX()), (int)(mouseEvent.getY())));
                this.afterRun.add(() -> this.mouseMovementEventQueried = false);
            }
        });
        GameController controller = this;
        this.animationThread = new Thread("animationThread"){
            @Override public void run(){
                controller.currentLevel.resetLastRan();
                while(!this.isInterrupted()){
                    if(controller.sceneQueried != null){
                        controller.currentLevel = controller.sceneQueried;
                        controller.sceneQueried.resetLastRan();
                        controller.sceneQueried = null;
                    }
                    while(!this.isInterrupted() && !controller.currentLevel.readyToRun()){Thread.onSpinWait();}
                    Platform.runLater(controller::performUpdate);
                    controller.currentLevel.resetLastRan();
                }
            }
        };
    }

    public void initializeScene(){
        this.initializeAll();
        this.animationThread.start();
    }

    private void performUpdate(){
        for(UserInput i : this.beforeRun){i.setInput();}
        this.beforeRun.clear();
        this.currentLevel.updateCanvasAndGame(this.ctx, this.playerInput);
        for(UserInput i : this.afterRun){i.setInput();}
        this.afterRun.clear();
    }

    private static WritableImage trim(Image image){
        PixelReader reader = image.getPixelReader();
        Color base = reader.getColor(0, 0);
        int lowerBound, upperBound, rightBound, leftBound;
        {
            boolean found = false;
            int counter = 0;
            while(!found && counter < image.getHeight()){
                int count2 = 0;
                while(count2 < image.getWidth() && base.equals(reader.getColor(count2, counter))){count2++;}
                if(count2 < image.getWidth()){
                    found = true;
                }else{counter++;}
            }
            lowerBound = counter;
        }
        {
            boolean found = false;
            int counter = (int) (image.getHeight() -1);
            while(!found && counter > 0){
                int count2 = 0;
                while(count2 < image.getWidth() && base.equals(reader.getColor(count2, counter))){count2++;}
                if(count2 < image.getWidth()){
                    found = true;
                }else{counter--;}
            }
            upperBound = counter;
        }
        {
            boolean found = false;
            int counter = (int) (image.getWidth() -1);
            while(!found && counter > 0){
                int count2 = 0;
                while(count2 < image.getHeight() && base.equals(reader.getColor(counter, count2))){count2++;}
                if(count2 < image.getHeight()){found = true;}
                else{counter--;}
            }
            rightBound = counter;
        }
        {
            boolean found = false;
            int counter = 0;
            while(!found && counter < image.getWidth()){
                int count2 = 0;
                while(count2 < image.getHeight() && base.equals(reader.getColor(counter, count2))){count2++;}
                if(count2 < image.getHeight()){found = true;}
                else{counter++;}
            }
            leftBound = counter;
        }
        return new WritableImage(reader, lowerBound, leftBound, upperBound - lowerBound, rightBound - leftBound);
    }

    private interface UserInput{void setInput();}
    private Thread animationThread;
    private Scenes currentLevel, sceneQueried;
    private PlayerInput playerInput;
    private final ArrayList<UserInput> beforeRun = new ArrayList<>(), afterRun = new ArrayList<>();
    private static final HashMap<String, Image> pieceImages = new HashMap<>();
    public static final int interval = 20; // move per __ milliseconds
    public static final double fps = 1000.0/interval;
    private boolean mouseMovementEventQueried, mouseClickedQueried;


    private static final class PlayerInput implements PlayerInputDecorator{
        private PlayerInput(){
            this.validKeyCodes = new KeyCode[]{KeyCode.DOWN, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SHIFT, KeyCode.S, KeyCode.A, KeyCode.R};
            this.values = new boolean[this.validKeyCodes.length];
        }
        private void setKeyCode(KeyCode code, boolean value){
            if(!value || this.mostRecentPress != code){
                byte counter = 0;
                boolean set = false;
                while(!set && counter < this.validKeyCodes.length){
                    if(this.validKeyCodes[counter] == code){
                        set = true;
                        this.values[counter] = value;
                        if(value){this.mostRecentPress = code;}
                        else if(this.mostRecentPress == code){this.mostRecentPress = null;}
                    }
                    counter++;
                }
            }
        }

        @Override public boolean getValueOfKeyCode(KeyCode code){
            for(byte i = 0; i < this.validKeyCodes.length; i++){if(this.validKeyCodes[i] == code){return this.values[i];}}
            return false;
        }


        @Override public float getScreenMouseX() {return this.mouseX;}
        @Override public float getScreenMouseY() {return this.mouseY;}
        @Override public float getLastMouseX() {return this.lastMouseX;}
        @Override public float getLastMouseY() {return this.lastMouseY;}
        @Override public boolean mouseClicked() {return this.mouseClicked;}

        private void setNewMouseLocation(int x, int y){
            this.mouseX = x;
            this.mouseY = y;
            if(currentController.currentLevel.registerCodes()){
                this.lastMouseX = this.mouseX;
                this.lastMouseY = this.mouseY;
            }
        }

        private final KeyCode[] validKeyCodes;
        private final boolean[] values;
        private KeyCode mostRecentPress;
        private int mouseX, mouseY;
        private int lastMouseX, lastMouseY;
        private boolean mouseClicked;
    }

    static{
        File directory = new File("src/main/resources/pictures");
        if(!directory.exists() || !directory.isDirectory()){throw new RuntimeException("Why doesn't the path exist/is not a directory?");}
        else{
            for(File i : Objects.requireNonNull(directory.listFiles())){
                try {
                    pieceImages.put(i.getName().substring(0, i.getName().lastIndexOf(".")), (new Image(new FileInputStream(i))));
                }
                catch (FileNotFoundException error) {System.out.println(i.exists());}
            }
        }
    }
}