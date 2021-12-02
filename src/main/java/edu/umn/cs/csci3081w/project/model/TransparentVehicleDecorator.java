package edu.umn.cs.csci3081w.project.model;

public class TransparentVehicleDecorator extends VehicleDecorator {

  private static final int OPACITY = 155;

  public TransparentVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
  }

  @Override
  public Vehicle getVehicle() {
    return vehicle;
  }

  @Override
  public int getAlpha() {
    return OPACITY;
  }
}