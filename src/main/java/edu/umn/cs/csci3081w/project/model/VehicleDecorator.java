package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class VehicleDecorator extends Vehicle {

  protected Vehicle vehicle;

  public VehicleDecorator(Vehicle vehicle) {
    // this creates a new vehicle that we don't need. does the garbage collector immediately
    // come and get rid of it since we are not maintaining a reference to it anywhere?
    // does this matter?
    super(vehicle.getId(), vehicle.getLine(), vehicle.getCapacity(), vehicle.getSpeed(),
          vehicle.getPassengerLoader(), vehicle.getPassengerUnloader());
    this.vehicle = vehicle;
  }

  public void report(PrintStream out) {
    vehicle.report(out);
  }

  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }

  @Override
  public Vehicle getBaseVehicle() {
    return vehicle.getBaseVehicle();
  }

  @Override
  public Vehicle getVehicle() {
    return vehicle;
  }

  @Override
  public int getRed() {
    return vehicle.getRed();
  }

  @Override
  public int getGreen() {
    return vehicle.getGreen();
  }

  @Override
  public int getBlue() {
    return vehicle.getBlue();
  }
}
