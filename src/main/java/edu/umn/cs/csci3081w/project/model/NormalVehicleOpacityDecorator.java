package edu.umn.cs.csci3081w.project.model;

public class NormalVehicleOpacityDecorator extends VehicleDecorator {

  private static final int OPACITY = 255;

  public NormalVehicleOpacityDecorator(Vehicle vehicle) {
    super(vehicle);
  }

  @Override
  public int getAlpha() {
    return OPACITY;
  }
}
