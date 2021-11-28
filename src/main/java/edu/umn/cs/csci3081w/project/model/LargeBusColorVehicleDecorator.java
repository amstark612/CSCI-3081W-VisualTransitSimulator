package edu.umn.cs.csci3081w.project.model;

public class LargeBusColorVehicleDecorator extends ColorVehicleDecorator {

  private static final String RED = "230";
  private static final String BLUE = "130";
  private static final String GREEN = "238";

  public LargeBusColorVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    red = RED;
    blue = BLUE;
    green = GREEN;
  }

  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }
}