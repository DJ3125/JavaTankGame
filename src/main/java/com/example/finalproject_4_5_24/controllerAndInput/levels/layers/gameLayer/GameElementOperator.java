package com.example.finalproject_4_5_24.controllerAndInput.levels.layers.gameLayer;

import com.example.finalproject_4_5_24.controllerAndInput.elementAndFeatures.DynamicGameElement;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class GameElementOperator {
    GameElementOperator(GameLayer gameLayer){
        Class<? extends DynamicGameElement>[] classes = DynamicGameElement.getAllSubclasses(DynamicGameElement.class);
        this.containers = new DynamicGameElementContainer[classes.length];
        short counter = 0;
        for (Class<? extends DynamicGameElement> i : classes) {
            this.containers[counter] = new DynamicGameElementContainer<>(i);
            counter++;
        }
        this.layer = gameLayer;
    }

    public void close(){
        if(!this.close){
            this.close = true;
            this.registerAll();
        }
    }

    private void loopThroughAllContainers(Predicate<DynamicGameElementContainer<?>> stopCondition){
        short counter = 0;
        while(counter < this.containers.length && !stopCondition.test(this.containers[counter])){
            counter++;
        }
    }
    public boolean hasElementWithCondition(Predicate<GameElementEncapsulator<?>> element){
        for(DynamicGameElementContainer<?> i : this.containers){for(GameElementEncapsulator<?> j : i.getElements()){if(element.test(j)){return true;}}}
        return false;
    }
    @SuppressWarnings("unchecked") public <T extends DynamicGameElement> boolean hasElementWithCondition(Class<T> classObject, Predicate<GameElementEncapsulator<? extends T>> element){
        for(DynamicGameElementContainer<?> i : this.containers){if(this.isSubclass(classObject, i.classObject)){for(GameElementEncapsulator<? extends T> j : (GameElementEncapsulator<? extends T>[]) i.getElements()){if(element.test(j)){return true;}}}}
        return false;
    }
    private boolean isSubclass(Class<?> superClass, Class<?> subClass){
        Class<?> classObject = subClass;
        while(classObject != Object.class && classObject != superClass){classObject = classObject.getSuperclass();}
        return classObject == superClass;
    }
    public ArrayList<GameElementEncapsulator<?>> getAllEntitiesThatSatisfyCondition(Predicate<GameElementEncapsulator<?>> operation){
        ArrayList<GameElementEncapsulator<?>> list = new ArrayList<>();
        this.loopThroughAll((encapsulator) ->{if(operation.test(encapsulator)){list.add(encapsulator);}});
        return list;
    }

    @SuppressWarnings("unchecked") public <T extends DynamicGameElement> ArrayList<GameElementEncapsulator<? extends T>> getAllEntitiesThatSatisfyCondition(Class<T> container, Predicate<GameElementEncapsulator<? extends T>> operation){
        ArrayList<GameElementEncapsulator<? extends T>> list = new ArrayList<>();
        for(DynamicGameElementContainer<?> i : this.containers){if(this.isSubclass(container, i.classObject)){for(GameElementEncapsulator<? extends T> j : (GameElementEncapsulator<? extends T>[]) i.getElements()){if(operation.test(j)){list.add(j);}}}}
        return list;
    }

    public void addElement(DynamicGameElement element){this.loopThroughAllContainers(container -> container.addElement(element));}
    public void addElement(GameElementEncapsulator<?> element){this.loopThroughAllContainers(container -> container.addElement(element.getElement()));}
    public void loopThroughAll(Consumer<GameElementEncapsulator<?>> operation){this.loopThroughAllContainers(container ->{for(GameElementEncapsulator<? extends DynamicGameElement> i : container.getElements()){operation.accept(i);}return false;});}
    @SuppressWarnings("unchecked") public <T extends DynamicGameElement> void loopThroughAll(Class<T> container, Consumer<GameElementEncapsulator<? extends T>> operation){for(DynamicGameElementContainer<?> i : this.containers){if(this.isSubclass(container, i.classObject)){for(GameElementEncapsulator<? extends T> j : (GameElementEncapsulator<? extends T>[]) i.getElements()){operation.accept(j);}}}}
    void updateAll() {
        if(!this.close){throw new RuntimeException("Need to close first");}
        this.loopThroughAll(encapsulator -> encapsulator.getElement().tickUpdate());
        this.loopThroughAllContainers(container -> {
            container.update();
            return false;
        });
    }

    private void registerAll() {
        this.loopThroughAllContainers(dynamicGameElementContainer -> {
            dynamicGameElementContainer.register();
            return false;
        });
    }

    private final DynamicGameElementContainer<? extends DynamicGameElement>[] containers;
    private boolean close = false;
    private final GameLayer layer;
    private static final class DynamicGameElementContainer<T extends DynamicGameElement>{
        private DynamicGameElementContainer(Class<T> classObject){
            if(!Modifier.isFinal(classObject.getModifiers())){throw new RuntimeException(classObject.getName() + " is Not Final.");}
            this.classObject = classObject;
            this.gameElement = new ArrayList<>();
            this.additionQueue = new ArrayList<>();
        }
        @SuppressWarnings("unchecked") private boolean addElement(DynamicGameElement element){
            if(this.classObject == element.getClass()){this.additionQueue.add((T)element);}
            return this.classObject == element.getClass();
        }
        private boolean addElement(GameElementEncapsulator<?> element){return this.addElement(element.getElement());}

        @SuppressWarnings("unchecked") private GameElementEncapsulator<T>[] getElements(){
            GameElementEncapsulator<T>[] encapsulation = new GameElementEncapsulator[this.gameElement.size()];
            int counter = 0;
            for(T i : this.gameElement){
                encapsulation[counter] = (GameElementEncapsulator<T>) i.getEncapsulator();
                counter++;
            }
            return encapsulation;
        }
        private void update(){
            Iterator<T> iterator = this.gameElement.iterator();
            while(iterator.hasNext()){
                T i = iterator.next();
                if(i.readyForRemoval()){iterator.remove();}
                else{i.updateAll();}
            }
            this.register();
        }

        private void register(){
            this.gameElement.addAll(this.additionQueue);
            this.additionQueue.clear();
        }

        private final ArrayList<T> gameElement, additionQueue;
        private final Class<T> classObject;
    }
}
