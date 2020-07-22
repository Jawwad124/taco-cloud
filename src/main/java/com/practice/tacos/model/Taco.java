package com.practice.tacos.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Taco
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date createdAt;

  @NotNull
  @Size(min = 5, message = "Name should be atleast 5 characters")
  private String name;

  @Size(min = 1, message = "Choose min 1 ingredient")
  @ManyToMany(targetEntity = Ingredient.class)
  private List<Ingredient> ingredients;

  @PrePersist
  void createdAt()
  {
    createdAt = new Date();
  }
}
