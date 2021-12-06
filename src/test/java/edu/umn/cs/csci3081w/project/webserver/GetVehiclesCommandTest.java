package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerLoader;
import edu.umn.cs.csci3081w.project.model.PassengerUnloader;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Stop;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.VehicleTestImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class GetVehiclesCommandTest {

  Position positionStub;
  Stop stopStub;
  Route routeStub;
  Line lineStub;
  List<Vehicle> vehicles;

  /**
   * Setup operations before each test run.
   */
  @BeforeEach
  public void setup() {
    // set up everything to make a vehicle fake
    positionStub = mock(Position.class);
    when(positionStub.getLongitude()).thenReturn(1.0);
    when(positionStub.getLatitude()).thenReturn(1.0);

    stopStub = mock(Stop.class);
    when(stopStub.getPosition()).thenReturn(positionStub);

    routeStub = mock(Route.class);
    when(routeStub.getNextStop()).thenReturn(stopStub);
    when(routeStub.getName()).thenReturn("routeStub");

    lineStub = mock(Line.class);
    when(lineStub.getOutboundRoute()).thenReturn(routeStub);
    when(lineStub.getInboundRoute()).thenReturn(routeStub);

    vehicles = new ArrayList<>();
  }

  /**
   * Test normal constructor.
   */
  @Test
  public void testConstructorNormal() {
    GetVehiclesCommand command = new GetVehiclesCommand(mock(VisualTransitSimulator.class));
    assertNotNull(command.getSimulator());
  }

  /**
   * Tests vehicle reporting functionality with vehicle fake.
   */
  @Test
  public void testExecuteFake() {
    vehicles.add(new VehicleTestImpl(1, lineStub, 1, 1, mock(PassengerLoader.class),
        mock(PassengerUnloader.class)));

    // set up webserversession spy and vts stub
    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));

    VisualTransitSimulator vtsStub = mock(VisualTransitSimulator.class);
    when(vtsStub.getActiveVehicles()).thenReturn(vehicles);

    // create command object
    GetVehiclesCommand command = new GetVehiclesCommand(vtsStub);
    command.execute(sessionSpy, new JsonObject());

    // set up expected value
    JsonObject position = new JsonObject();
    position.addProperty("longitude", 1.0);
    position.addProperty("latitude", 1.0);

    JsonObject color = new JsonObject();
    color.addProperty("r", 255);
    color.addProperty("g", 255);
    color.addProperty("b", 255);
    color.addProperty("alpha", 255);

    JsonObject expected = new JsonObject();
    expected.addProperty("id", 1);
    expected.addProperty("numPassengers", 0);
    expected.addProperty("capacity", 1);
    expected.addProperty("type", "");
    expected.addProperty("co2", 0);
    expected.add("position", position);
    expected.add("color", color);

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonArray list = messageCaptor.getValue().getAsJsonObject().get("vehicles").getAsJsonArray();
    String output = list.get(0).toString();

    assertEquals(expected.toString(), output);
  }

  /**
   * Tests vehicle reporting functionality with actual vehicle.
   */
  @Test
  public void testExecuteSmallBus() {
    vehicles.add(new SmallBus(1, lineStub, 1, 1));

    // set up webserversession spy and vts stub
    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));

    VisualTransitSimulator vtsStub = mock(VisualTransitSimulator.class);
    when(vtsStub.getActiveVehicles()).thenReturn(vehicles);

    // create command object
    GetVehiclesCommand command = new GetVehiclesCommand(vtsStub);
    command.execute(sessionSpy, new JsonObject());

    // set up expected value
    JsonObject position = new JsonObject();
    position.addProperty("longitude", 1.0);
    position.addProperty("latitude", 1.0);

    JsonObject color = new JsonObject();
    color.addProperty("r", 255);
    color.addProperty("g", 255);
    color.addProperty("b", 255);
    color.addProperty("alpha", 255);

    JsonObject expected = new JsonObject();
    expected.addProperty("id", 1);
    expected.addProperty("numPassengers", 0);
    expected.addProperty("capacity", 1);
    expected.addProperty("type", "SMALL_BUS_VEHICLE");
    expected.addProperty("co2", 1);
    expected.add("position", position);
    expected.add("color", color);

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonArray list = messageCaptor.getValue().getAsJsonObject().get("vehicles").getAsJsonArray();
    String output = list.get(0).toString();

    assertEquals(expected.toString(), output);
  }

  /**
   * Tests vehicle reporting functionality with large bus.
   */
  @Test
  public void testExecuteLargeBus() {
    vehicles.add(new LargeBus(1, lineStub, 1, 1));

    // set up webserversession spy and vts stub
    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));

    VisualTransitSimulator vtsStub = mock(VisualTransitSimulator.class);
    when(vtsStub.getActiveVehicles()).thenReturn(vehicles);

    // create command object
    GetVehiclesCommand command = new GetVehiclesCommand(vtsStub);
    command.execute(sessionSpy, new JsonObject());

    // set up expected value
    JsonObject position = new JsonObject();
    position.addProperty("longitude", 1.0);
    position.addProperty("latitude", 1.0);

    JsonObject color = new JsonObject();
    color.addProperty("r", 255);
    color.addProperty("g", 255);
    color.addProperty("b", 255);
    color.addProperty("alpha", 255);

    JsonObject expected = new JsonObject();
    expected.addProperty("id", 1);
    expected.addProperty("numPassengers", 0);
    expected.addProperty("capacity", 1);
    expected.addProperty("type", "LARGE_BUS_VEHICLE");
    expected.addProperty("co2", 3);
    expected.add("position", position);
    expected.add("color", color);

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonArray list = messageCaptor.getValue().getAsJsonObject().get("vehicles").getAsJsonArray();
    String output = list.get(0).toString();

    assertEquals(expected.toString(), output);
  }

  /**
   * Tests vehicle reporting functionality with electric train.
   */
  @Test
  public void testExecuteElectricTrain() {
    vehicles.add(new ElectricTrain(1, lineStub, 1, 1));

    // set up webserversession spy and vts stub
    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));

    VisualTransitSimulator vtsStub = mock(VisualTransitSimulator.class);
    when(vtsStub.getActiveVehicles()).thenReturn(vehicles);

    // create command object
    GetVehiclesCommand command = new GetVehiclesCommand(vtsStub);
    command.execute(sessionSpy, new JsonObject());

    // set up expected value
    JsonObject position = new JsonObject();
    position.addProperty("longitude", 1.0);
    position.addProperty("latitude", 1.0);

    JsonObject color = new JsonObject();
    color.addProperty("r", 255);
    color.addProperty("g", 255);
    color.addProperty("b", 255);
    color.addProperty("alpha", 255);

    JsonObject expected = new JsonObject();
    expected.addProperty("id", 1);
    expected.addProperty("numPassengers", 0);
    expected.addProperty("capacity", 1);
    expected.addProperty("type", "ELECTRIC_TRAIN_VEHICLE");
    expected.addProperty("co2", 0);
    expected.add("position", position);
    expected.add("color", color);

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonArray list = messageCaptor.getValue().getAsJsonObject().get("vehicles").getAsJsonArray();
    String output = list.get(0).toString();

    assertEquals(expected.toString(), output);
  }

  /**
   * Tests vehicle reporting functionality with diesel train.
   */
  @Test
  public void testExecuteDieselTrain() {
    vehicles.add(new DieselTrain(1, lineStub, 1, 1));

    // set up webserversession spy and vts stub
    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));

    VisualTransitSimulator vtsStub = mock(VisualTransitSimulator.class);
    when(vtsStub.getActiveVehicles()).thenReturn(vehicles);

    // create command object
    GetVehiclesCommand command = new GetVehiclesCommand(vtsStub);
    command.execute(sessionSpy, new JsonObject());

    // set up expected value
    JsonObject position = new JsonObject();
    position.addProperty("longitude", 1.0);
    position.addProperty("latitude", 1.0);

    JsonObject color = new JsonObject();
    color.addProperty("r", 255);
    color.addProperty("g", 255);
    color.addProperty("b", 255);
    color.addProperty("alpha", 255);

    JsonObject expected = new JsonObject();
    expected.addProperty("id", 1);
    expected.addProperty("numPassengers", 0);
    expected.addProperty("capacity", 1);
    expected.addProperty("type", "DIESEL_TRAIN_VEHICLE");
    expected.addProperty("co2", 6);
    expected.add("position", position);
    expected.add("color", color);

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonArray list = messageCaptor.getValue().getAsJsonObject().get("vehicles").getAsJsonArray();
    String output = list.get(0).toString();

    assertEquals(expected.toString(), output);
  }
}
