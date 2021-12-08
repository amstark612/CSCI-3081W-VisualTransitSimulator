package edu.umn.cs.csci3081w.project.model;

import com.google.gson.JsonObject;

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

  /**
   * Retrieves the current vehicle information sends the information to the visualization module.
   *
   * @return whether the trip was completed
   */
  @Override
  public boolean provideInfo() {
    boolean tripCompleted = false;
    if (!isTripComplete()) {
      JsonObject data = new JsonObject();
      data.addProperty("command", "observedVehicle");

      String type = "";
      if (getBaseVehicle() instanceof SmallBus) {
        type = SmallBus.SMALL_BUS_VEHICLE;
      } else if (getBaseVehicle() instanceof LargeBus) {
        type = LargeBus.LARGE_BUS_VEHICLE;
      } else if (getBaseVehicle() instanceof ElectricTrain) {
        type = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      } else if (getBaseVehicle() instanceof DieselTrain) {
        type = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }

      StringBuilder carbonEmissionHistoryString = new StringBuilder();
      int length = Math.min(5, getCarbonEmissionHistory().size());
      if (length > 0) {
        carbonEmissionHistoryString.append(getCarbonEmissionHistory().get(0));
        for (int i = 1; i < length; i++) {
          carbonEmissionHistoryString.append(", ");
          carbonEmissionHistoryString.append(getCarbonEmissionHistory().get(i));
        }
      }

      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(String.format("%d", getId()) + System.lineSeparator());
      stringBuilder.append("-----------------------------" + System.lineSeparator());
      stringBuilder.append(String.format("* Type: %s", type) + System.lineSeparator());
      stringBuilder.append(String.format("* Position: (%f,%f)", getPosition().getLongitude(),
          getPosition().getLatitude()) + System.lineSeparator());
      stringBuilder.append(String.format("* Passengers: %d", getPassengers().size())
          + System.lineSeparator());
      stringBuilder.append(String.format("* CO2: %s", carbonEmissionHistoryString.toString())
          + System.lineSeparator());

      data.addProperty("text", stringBuilder.toString());
      getVehicleSubject().getSession().sendJson(data);
      tripCompleted = false;
      return tripCompleted;
    } else {
      JsonObject data = new JsonObject();
      data.addProperty("command", "observedVehicle");
      data.addProperty("text", "");
      getVehicleSubject().getSession().sendJson(data);
      tripCompleted = true;
      return tripCompleted;
    }
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
