package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.awt.Color;

public abstract class ColorVehicleDecorator extends Vehicle {

  private Vehicle vehicle;
  private Color color;

  public ColorVehicleDecorator(Vehicle vehicle) {
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
  public Color getColor() {
    return color;
  }
}
