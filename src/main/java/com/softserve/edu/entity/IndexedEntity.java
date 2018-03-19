package com.softserve.edu.entity;

public abstract class IndexedEntity<Index extends Number> {

    private Index id;

    public Index getId() {
        return id;
    }

    public void setId(Index id) {
        this.id = id;
    }

}
