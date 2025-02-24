module com.example.finalproject_4_5_24_ {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires org.jetbrains.annotations;

    exports com.example.finalproject_4_5_24;
    opens com.example.finalproject_4_5_24 to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.movement;
    opens com.example.finalproject_4_5_24.features.movement to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput;
    opens com.example.finalproject_4_5_24.controllerAndInput to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.hitBoxes;
    opens com.example.finalproject_4_5_24.features.hitBoxes to javafx.fxml;
    exports com.example.finalproject_4_5_24.sceneDirection;
    opens com.example.finalproject_4_5_24.sceneDirection to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.scalingAndPositioning;
    opens com.example.finalproject_4_5_24.features.scalingAndPositioning to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.health;
    opens com.example.finalproject_4_5_24.features.health to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures;
    opens com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures to javafx.fxml;
    exports com.example.finalproject_4_5_24.bullets;
    opens com.example.finalproject_4_5_24.bullets to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.attack;
    opens com.example.finalproject_4_5_24.features.attack to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.spawning;
    opens com.example.finalproject_4_5_24.features.spawning to javafx.fxml;
    exports com.example.finalproject_4_5_24.timers;
    opens com.example.finalproject_4_5_24.timers to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels.layers.transitionTypes to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements;
    opens com.example.finalproject_4_5_24.controllerAndInput.userInterfaceElements to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.movement.forces;
    opens com.example.finalproject_4_5_24.features.movement.forces to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels.levelTypes to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.collection;
    opens com.example.finalproject_4_5_24.features.collection to javafx.fxml;
    exports com.example.finalproject_4_5_24.features.collection.inventoryTypes;
    opens com.example.finalproject_4_5_24.features.collection.inventoryTypes to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels.tertiaryLayer to javafx.fxml;
    exports com.example.finalproject_4_5_24.shapes;
    opens com.example.finalproject_4_5_24.shapes to javafx.fxml;
//    exports com.example.finalproject_4_5_24.controllerAndInput.levels.layers;
//    opens com.example.finalproject_4_5_24.controllerAndInput.levels.layers to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer to javafx.fxml;
    exports com.example.finalproject_4_5_24.controllerAndInput.levels.layers;
    opens com.example.finalproject_4_5_24.controllerAndInput.levels.layers to javafx.fxml;
}