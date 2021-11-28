package edu.umn.cs.csci3081w.project.model;

public class SmallBusColorVehicleDecorator extends ColorVehicleDecorator {

  private static final String RED = "122";
  private static final String BLUE = "0";
  private static final String GREEN = "25";

  public SmallBusColorVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    red = RED;
    blue = BLUE;
    green = GREEN;
  }
}
