package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Passenger;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.Stop;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class GetRoutesCommandTest {

  /**
   * Tests command's ability to get routes from simulator and send to session.
   */
  @Test
  public void testExecute() {

    Position position = new Position(1.0, 1.0);

    Stop stopStub = mock(Stop.class);
    when(stopStub.getId()).thenReturn(1);
    when(stopStub.getPosition()).thenReturn(position);
    when(stopStub.getPassengers()).thenReturn(new ArrayList<Passenger>());

    List<Stop> stopStubs = new ArrayList<>();
    stopStubs.add(stopStub);

    Route routeStub = mock(Route.class);
    when(routeStub.getStops()).thenReturn(stopStubs);

    Line lineStub = mock(Line.class);
    when(lineStub.getOutboundRoute()).thenReturn(routeStub);
    when(lineStub.getInboundRoute()).thenReturn(routeStub);

    List<Line> lineStubs = new ArrayList<>();
    lineStubs.add(lineStub);

    VisualTransitSimulator simulatorStub = mock(VisualTransitSimulator.class);
    when(simulatorStub.getLines()).thenReturn(lineStubs);

    WebServerSession sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));
    JsonObject emptyCommand = new JsonObject();

    GetRoutesCommand getRoutesCommand = new GetRoutesCommand(simulatorStub);
    getRoutesCommand.execute(sessionSpy, emptyCommand);
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonObject data = messageCaptor.getValue();
    JsonArray routes = data.get("routes").getAsJsonArray();

    assertEquals("updateRoutes", data.get("command").getAsString());

    for (int i = 0; i < 2; i++) {
      JsonObject route = routes.get(i).getAsJsonObject();
      assertEquals(0, route.get("id").getAsInt());

      JsonObject stop = route.get("stops").getAsJsonArray().get(0).getAsJsonObject();
      assertEquals(1, stop.get("id").getAsInt());
      assertEquals(0, stop.get("numPeople").getAsInt());
      assertEquals(1.0, stop.get("position").getAsJsonObject().get("longitude").getAsDouble());
      assertEquals(1.0, stop.get("position").getAsJsonObject().get("latitude").getAsDouble());
    }
  }
}
