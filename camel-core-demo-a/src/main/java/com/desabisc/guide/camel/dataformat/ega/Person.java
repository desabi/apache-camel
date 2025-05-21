package com.desabisc.guide.camel.dataformat.ega;

import com.fasterxml.jackson.annotation.JsonProperty;

// Simple POJO for marshaling/unmarshaling
public class Person {

  private String firstName;
  private String lastName;
  private int age;

  // Default constructor needed for Jackson
  public Person() {
  }

  public Person(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  @JsonProperty
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @JsonProperty
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @JsonProperty
  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
  }
}