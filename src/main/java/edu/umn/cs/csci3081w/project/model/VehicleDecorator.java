package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class VehicleDecorator extends Vehicle {

  protected Vehicle vehicle;

  /**
   * Decorates an existing vehicle.
   *
   * @param vehicle the vehicle to be decorated
   */
  public VehicleDecorator(Vehicle vehicle) {
    super(vehicle.getId(), vehicle.getLine(), vehicle.getCapacity(), vehicle.getSpeed(),
          vehicle.getPassengerLoader(), vehicle.getPassengerUnloader());
    this.vehicle = vehicle;

    // set this vehicle to current vehicle's state so it doesn't behave like a new vehicle
    setPassengers(vehicle.getPassengers());
    setDistanceRemaining(vehicle.getDistanceRemaining());
    setNextStop(vehicle.getNextStop());
    setPosition(vehicle.getPosition());
    setCarbonEmissionHistory(vehicle.getCarbonEmissionHistory());
    this.vehicle = vehicle;
  }

  public void report(PrintStream out) {
    vehicle.report(out);
  }

  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }

  @Override
  public Vehicle getBaseVehicle() {
    return vehicle.getBaseVehicle();
  }

  @Override
  public Vehicle getVehicle() {
    return vehicle;
  }

  @Override
  public int getRed() {
    return vehicle.getRed();
  }

  @Override
  public int getGreen() {
    return vehicle.getGreen();
  }

  @Override
  public int getBlue() {
    return vehicle.getBlue();
  }
}
