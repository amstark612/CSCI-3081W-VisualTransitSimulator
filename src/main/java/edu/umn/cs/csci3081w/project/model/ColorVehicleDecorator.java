package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class ColorVehicleDecorator extends Vehicle {

  protected Vehicle vehicle;
  protected String red;
  protected String green;
  protected String blue;

  public ColorVehicleDecorator(Vehicle vehicle) {
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

  public String getRed() {
    return red;
  }

  public String getGreen() {
    return green;
  }

  public String getBlue() {
    return blue;
  }
}
