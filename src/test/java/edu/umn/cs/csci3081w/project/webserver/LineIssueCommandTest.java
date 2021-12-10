package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Issue;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LineIssueCommandTest {

  /**
   * Tests command's ability to create an issue on a given line.
   */
  @Test
  public void testExecute() {

    Route routeDummy = mock(Route.class);
    List<Line> lines = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      lines.add(new Line(i, "test line", SmallBus.SMALL_BUS_VEHICLE,
          routeDummy, routeDummy, new Issue()));
      assertFalse(lines.get(i).isIssueExist());
    }

    VisualTransitSimulator simulatorStub = mock(VisualTransitSimulator.class);
    when(simulatorStub.getLines()).thenReturn(lines);
    WebServerSession sessionDummy = mock(WebServerSession.class);

    JsonObject command = new JsonObject();
    command.addProperty("id", 1);

    LineIssueCommand lineIssueCommand = new LineIssueCommand(simulatorStub);
    lineIssueCommand.execute(sessionDummy, command);

    for (int i = 0; i < 5; i++) {
      if (i != 1) {
        assertFalse(lines.get(i).isIssueExist());
      } else {
        assertTrue(lines.get(i).isIssueExist());
      }
    }
  }
}
