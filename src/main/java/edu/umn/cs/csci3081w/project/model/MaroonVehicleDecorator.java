package edu.umn.cs.csci3081w.project.model;

import java.awt.Color;

public class MaroonVehicleDecorator extends VehicleDecorator {

  private Color color;

  public MaroonVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    color = new Color(122, 0, 25);
  }

  @Override
  public int getRed() {
    return color.getRed();
  }

  @Override
  public int getGreen() {
    return color.getGreen();
  }

  @Override
  public int getBlue() {
    return color.getBlue();
  }
}
