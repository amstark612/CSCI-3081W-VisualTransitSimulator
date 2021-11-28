package edu.umn.cs.csci3081w.project.model;

public class ElectricTrainColorVehicleDecorator extends ColorVehicleDecorator {

  private static final String RED = "60";
  private static final String BLUE = "179";
  private static final String GREEN = "113";

  public ElectricTrainColorVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    red = RED;
    blue = BLUE;
    green = GREEN;
  }
}