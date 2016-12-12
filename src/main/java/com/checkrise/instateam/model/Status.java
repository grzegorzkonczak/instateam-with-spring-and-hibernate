package com.checkrise.instateam.model;


public enum Status {
    ACTIVE("Active"),
    ARCHIVED("Archived"),
    UNASSIGNED("Unassigned");

    public String getDescription() {
        return description;
    }

    private final String description;

    Status(String description) {
        this.description = description;
    }

}
