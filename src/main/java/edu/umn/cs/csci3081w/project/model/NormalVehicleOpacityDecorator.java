package edu.umn.cs.csci3081w.project.model;

public class NormalVehicleOpacityDecorator extends OpacityDecorator {

  private static final String OPACITY = "255";

  public NormalVehicleOpacityDecorator(Vehicle vehicle) {
    super(vehicle);
    alpha = OPACITY;
  }
}
