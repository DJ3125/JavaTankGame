package com.example.finalproject_4_5_24.controllerAndInput;

import com.example.finalproject_4_5_24.controllerAndInput.levels.Level;

public sealed interface SceneSwitching permits Level, Scenes {
    void switchScenes();
}
