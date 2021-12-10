package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class VehicleTest {

  private Vehicle testVehicle;
  private Route testRouteIn;
  private Route testRouteOut;
  private Line testLine;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testLine = new Line(10000, "testLine", "VEHICLE_LINE", testRouteOut, testRouteIn, new Issue());

    testVehicle = new VehicleTestImpl(1, testLine, 3, 1.0, new PassengerLoader(),
        new PassengerUnloader());
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testVehicle.getId());
    assertEquals("testRouteOut1", testVehicle.getName());
    assertEquals(3, testVehicle.getCapacity());
    assertEquals(1, testVehicle.getSpeed());
    assertEquals(testRouteOut, testVehicle.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testVehicle.getLine().getInboundRoute());
    assertEquals(255, testVehicle.getRed());
    assertEquals(255, testVehicle.getGreen());
    assertEquals(255, testVehicle.getBlue());
    assertEquals(255, testVehicle.getAlpha());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertEquals(false, testVehicle.isTripComplete());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(true, testVehicle.isTripComplete());

  }

  /**
   * Tests if getVehicleSubject and getVehicle functions work properly.
   */
  @Test
  public void testVehicleGetters() {
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject concreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(concreteSubject);
    assertTrue(testVehicle.getVehicleSubject() instanceof VehicleConcreteSubject);
    assertTrue(testVehicle.getVehicle() instanceof VehicleTestImpl);

  }

  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testVehicle.loadPassenger(testPassenger1));
    assertEquals(1, testVehicle.loadPassenger(testPassenger2));
    assertEquals(1, testVehicle.loadPassenger(testPassenger3));
    assertEquals(0, testVehicle.loadPassenger(testPassenger4));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Test to see if observer got attached.
   */
  @Test
  public void testProvideInfo() {
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if info is correctly provided if called before update.
   */
  @Test
  public void testProvideInfoNoUpdate() {
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: " + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if info is correctly provided if called after 3 updates.
   */
  @Test
  public void testProvideInfoThreeUpdates() {
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    for (int i = 0; i < 3; i++) {
      testVehicle.update();
    }
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.243774,44.972392)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0, 0, 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if info is correctly provided if called after the trip is complete.
   */
  @Test
  public void testProvideInfoTripComplete() {
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    for (int i = 0; i < 10; i++) {
      testVehicle.update();
    }
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    assertEquals("", message.get("text").getAsString());

  }

  /**
   * Test to see if information is correctly provided for a SmallBus.
   */
  @Test
  public void testProvideInfoSmallBus() {
    SmallBus testSmallBus = new SmallBus(1, testLine, 30, 1.0);
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testSmallBus.setVehicleSubject(vehicleConcreteSubject);
    testSmallBus.update();
    testSmallBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + SmallBus.SMALL_BUS_VEHICLE + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 1" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if information is correctly provided for a LargeBus.
   */
  @Test
  public void testProvideInfoLargeBus() {
    LargeBus testLargeBus = new LargeBus(1, testLine, 30, 1.0);
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testLargeBus.setVehicleSubject(vehicleConcreteSubject);
    testLargeBus.update();
    testLargeBus.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + LargeBus.LARGE_BUS_VEHICLE + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 3" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if information is correctly provided for a DieselTrain.
   */
  @Test
  public void testProvideInfoDieselTrain() {
    DieselTrain testDieselTrain = new DieselTrain(1, testLine, 30, 1.0);
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testDieselTrain.setVehicleSubject(vehicleConcreteSubject);
    testDieselTrain.update();
    testDieselTrain.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + DieselTrain.DIESEL_TRAIN_VEHICLE + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 6" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if information is correctly provided for an ElectricTrain.
   */
  @Test
  public void testProvideInfoElectricTrain() {
    ElectricTrain testElectricTrain = new ElectricTrain(1, testLine, 30, 1.0);
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testElectricTrain.setVehicleSubject(vehicleConcreteSubject);
    testElectricTrain.update();
    testElectricTrain.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + ElectricTrain.ELECTRIC_TRAIN_VEHICLE + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Test to see if vehicle does not move if speed is negative.
   */
  @Test
  public void testUpdateNegativeSpeed() {
    testVehicle = new VehicleTestImpl(1, testLine, 10, -1.0, new PassengerLoader(),
        new PassengerUnloader());
    WebServerSession sessionDummy = mock(WebServerSession.class);
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionDummy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testVehicle = null;
  }

}
