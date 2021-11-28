package edu.umn.cs.csci3081w.project.model;

public class LineIssueVehicleOpacityDecorator extends OpacityDecorator {

  private static final String OPACITY = "155";

  public LineIssueVehicleOpacityDecorator(Vehicle vehicle) {
    super(vehicle);
    alpha = OPACITY;
  }
}