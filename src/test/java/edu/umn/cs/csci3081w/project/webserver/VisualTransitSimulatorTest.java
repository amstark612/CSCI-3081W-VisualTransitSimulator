package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VisualTransitSimulatorTest {

  private VisualTransitSimulator vts;
  private WebServerSession webServerSessionDummy;

  /**
   * Setup operations before each test run.
   */
  @BeforeEach
  public void setup() {
    String configFile = getClass().getClassLoader().getResource("config.txt").getFile();
    webServerSessionDummy = mock(WebServerSession.class);
    vts = new VisualTransitSimulator(configFile, webServerSessionDummy);

    List<Integer> vehicleStartTimings = new ArrayList<>();
    vehicleStartTimings.add(2);
    vehicleStartTimings.add(2);

    vts.start(vehicleStartTimings, 100);
  }

  /**
   * Tests normal constructor with storage facility.
   */
  @Test
  public void testConstructorNormalWithStorageFacility() {
    assertEquals(0, vts.getActiveVehicles().size());
    assertEquals(0, vts.getCompletedTripVehicles().size());
    assertEquals(2, vts.getTimeSinceLastVehicle().size());
    assertEquals(webServerSessionDummy, vts.getWebServerSession());
    assertEquals(4, vts.getStorageFacility().getSmallBusesNum());
    assertEquals(2, vts.getStorageFacility().getLargeBusesNum());
    assertEquals(1, vts.getStorageFacility().getElectricTrainsNum());
    assertEquals(5, vts.getStorageFacility().getDieselTrainsNum());
    assertEquals(2, vts.getLines().size());
  }

  /**
   * Tests command for starting simulation.
   */
  @Test
  public void testStart() {
    assertEquals(2, vts.getTimeSinceLastVehicle().size());
    assertEquals(0, vts.getSimulationTimeElapsed());
    assertEquals(100, vts.getNumTimeSteps());
  }

  /**
   * Tests VTS's responsibility of changing vehicle opacity based on line state.
   */
  @Test
  public void testUpdateVehicleOpacity() {
    VisualTransitSimulator.setLogging(true);
    vts.setVehicleFactories(9);
    for (int i = 0; i < 99; i++) {
      vts.update();
    }

    // check that all vehicles are opaque before line issue
    for (Vehicle vehicle : vts.getActiveVehicles()) {
      assertEquals(255, vehicle.getAlpha());
    }

    // create issue on all lines
    for (Line line : vts.getLines()) {
      line.createIssue();
    }

    // check that all vehicles are transparent
    vts.update();
    for (Vehicle vehicle: vts.getActiveVehicles()) {
      assertEquals(155, vehicle.getAlpha());
    }

    vts.update();
  }

  /**
   * Test VTS's responsibility to add observers to vehicle concrete subject.
   */
  @Test
  public void testAddObserver() {
    Vehicle vehicleDummy = mock(Vehicle.class);
    assertEquals(0, vts.getVehicleConcreteSubject().getObservers().size());
    vts.addObserver(vehicleDummy);
    assertEquals(1, vts.getVehicleConcreteSubject().getObservers().size());
  }
}
