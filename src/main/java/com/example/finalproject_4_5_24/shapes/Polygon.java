package com.example.finalproject_4_5_24.shapes;

import com.example.finalproject_4_5_24.features.movement.Located;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class Polygon implements Iterable<Located>{
    public Polygon(@NotNull Located... points){
        if(points.length < 3){throw new IllegalArgumentException("Polygons must have at least 3 sides");}
        this.points = points;
    }


    @Override public @NotNull PathIterator iterator() {return new PathIterator(this.points);}

    public final static class PathIterator implements Iterator<Located>{
        private PathIterator(Located[] points){this.points = points;}
        @Override public boolean hasNext() {return this.current < this.points.length;}
        @Override public Located next() {
            if(!this.hasNext()){throw new RuntimeException("There is no next");}
            this.pointAfter = this.points[(this.current + 1) % this.points.length];
            return this.points[this.current++];
        }
        public Located getPointAfter(){
            if(this.pointAfter == null){throw new RuntimeException("cannot call the getPointAfter without calling next");}
            return this.pointAfter;
        }
        private int current = 0;
        private Located pointAfter;
        private final Located[] points;
    }


    private final Located[] points;
}
