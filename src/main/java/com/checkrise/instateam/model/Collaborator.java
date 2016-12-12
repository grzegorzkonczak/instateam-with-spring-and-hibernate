package com.checkrise.instateam.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
  Represents a person who is candidate
  for working on any given project
 */
@Entity
public class Collaborator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Size(min = 3)
  private String name;

  // Many Collaborators can have one Role
  @NotNull
  @ManyToOne
  private Role role;

  public Collaborator() {}

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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

    @Override
    public String toString() {
        return name;
    }
}
