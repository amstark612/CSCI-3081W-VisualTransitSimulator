package edu.umn.cs.csci3081w.project.model;

import java.awt.Color;

public class ElectricTrainColorDecorator extends VehicleDecorator {

  private Color color;

  public ElectricTrainColorDecorator(Vehicle vehicle) {
    super(vehicle);
    color = new Color(60, 179, 113);
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