package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerLoader;
import edu.umn.cs.csci3081w.project.model.PassengerUnloader;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.Stop;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.VehicleConcreteSubject;
import edu.umn.cs.csci3081w.project.model.VehicleObserver;
import edu.umn.cs.csci3081w.project.model.VehicleSubject;
import edu.umn.cs.csci3081w.project.model.VehicleTestImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RegisterVehicleCommandTest {

  public class VehicleSubjectFake extends VehicleConcreteSubject {

    public List<VehicleObserver> observers;

    public VehicleSubjectFake() {
      super(mock(WebServerSession.class));
      observers = new ArrayList<>();
    }

    /**
     * Attaches an observer to the vehicle concrete subject fake.
     */
    public void attachObserver(VehicleObserver observer) {
      observers.clear();
      observer.setVehicleSubject(new VehicleConcreteSubject(mock(WebServerSession.class)));
      observers.add(observer);
    }

    public List<VehicleObserver> getObservers() {
      return observers;
    }
  }

  public class SimulatorFake extends VisualTransitSimulator {

    public VehicleSubjectFake vehicleConcreteSubject;
    private List<Vehicle> vehicles;

    /**
     * Creates a simulator fake.
     *
     * @param configFile the path to a config file
     * @param sessionDummy a session dummy
     */
    public SimulatorFake(String configFile, WebServerSession sessionDummy) {
      super(configFile, sessionDummy);
      vehicleConcreteSubject = new VehicleSubjectFake();

      // set up everything to make a vehicle fake
      Position positionStub = mock(Position.class);
      when(positionStub.getLongitude()).thenReturn(1.0);
      when(positionStub.getLatitude()).thenReturn(1.0);

      Stop stopStub = mock(Stop.class);
      when(stopStub.getPosition()).thenReturn(positionStub);

      Route routeStub = mock(Route.class);
      when(routeStub.getNextStop()).thenReturn(stopStub);
      when(routeStub.getName()).thenReturn("routeStub");

      Line lineStub = mock(Line.class);
      when(lineStub.getOutboundRoute()).thenReturn(routeStub);
      when(lineStub.getInboundRoute()).thenReturn(routeStub);

      vehicles = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        vehicles.add(new VehicleTestImpl(i, lineStub, 1, 1.0, new PassengerLoader(),
            new PassengerUnloader()));
      }
    }

    public void addObserver(Vehicle vehicle) {
      vehicleConcreteSubject.attachObserver(vehicle);
    }

    public List<Vehicle> getActiveVehicles() {
      return vehicles;
    }
  }

  /**
   * Tests whether command actually registers vehicle.
   */
  @Test
  public void testExecute() {

    String configFile = getClass().getClassLoader().getResource("config.txt").getFile();
    WebServerSession sessionDummy = mock(WebServerSession.class);
    SimulatorFake simulatorFake = new SimulatorFake(configFile, sessionDummy);

    JsonObject command = new JsonObject();
    command.addProperty("id", 3);

    List<VehicleObserver> observers = simulatorFake.vehicleConcreteSubject.getObservers();
    assertEquals(0, observers.size());

    RegisterVehicleCommand registerVehicleCommand = new RegisterVehicleCommand(simulatorFake);
    registerVehicleCommand.execute(sessionDummy, command);

    assertEquals(1, observers.size());
  }
}
