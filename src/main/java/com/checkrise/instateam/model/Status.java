package com.checkrise.instateam.model;


public enum Status {
    ACTIVE,
    ARCHIVED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
