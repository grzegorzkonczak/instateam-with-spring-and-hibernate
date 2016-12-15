package com.checkrise.instateam.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/*
  Represents a project for which a
  project manager is seeking collaborators
 */
@Entity
public class Project {

    // primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // project name
    @NotNull
    private String name;

    // project description: for now it simply cannot be empty or null
    // can be changed later
    @NotNull(message = "Description cannot be empty")
    private String description;

    // project roles, are fetched lazily when `findById` method is called
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> rolesNeeded;

    // project collaborators, are fetched lazily when `findById` method is called
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collaborator> collaborators;

    // Project status is enum class, see definition, can be ACTIVE,
    // UNASSIGNED, or ARCHIVED. In table it comes as INTEGER
    @Enumerated
    private Status status;

    // Creation date of project
    private Timestamp creationDate;


    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
