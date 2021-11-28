package edu.umn.cs.csci3081w.project.model;

public class DieselTrainColorVehicleDecorator extends ColorVehicleDecorator {

  private static final String RED = "255";
  private static final String BLUE = "204";
  private static final String GREEN = "51";

  public DieselTrainColorVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    red = RED;
    blue = BLUE;
    green = GREEN;
  }
}
