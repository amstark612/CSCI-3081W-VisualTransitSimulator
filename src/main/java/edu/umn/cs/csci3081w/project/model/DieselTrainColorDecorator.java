package edu.umn.cs.csci3081w.project.model;

import java.awt.Color;

public class DieselTrainColorDecorator extends VehicleDecorator {

  private Color color;

  public DieselTrainColorDecorator(Vehicle vehicle) {
    super(vehicle);
    color = new Color(255, 204, 51);
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