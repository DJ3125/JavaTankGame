package com.example.finalproject_4_5_24.controllerAndInput.levels;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LevelSpecification {
    LevelTypes[] value();

    enum LevelTypes{
        LEVEL_ONE,
    }
}
