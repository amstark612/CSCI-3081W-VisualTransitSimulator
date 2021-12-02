package edu.umn.cs.csci3081w.project.model;

import java.awt.Color;

public class PinkVehicleDecorator extends VehicleDecorator {

  private Color color;

  public PinkVehicleDecorator(Vehicle vehicle) {
    super(vehicle);
    color = new Color(239, 130, 238);
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