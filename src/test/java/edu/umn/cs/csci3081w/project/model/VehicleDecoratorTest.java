package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehicleDecoratorTest {

  private VehicleTestImpl testVehicle;
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
   * Tests if VehicleDecorator generates correct RGB values.
   */
  @Test
  public void testVehicleDecorator() {
    Vehicle genericVehicle = new VehicleDecoratorTestImpl(testVehicle);

    assertEquals(255, genericVehicle.getRed());
    assertEquals(255, genericVehicle.getGreen());
    assertEquals(255, genericVehicle.getBlue());
    assertTrue(genericVehicle.getBaseVehicle() instanceof VehicleTestImpl);
    assertTrue(genericVehicle.getVehicle() instanceof VehicleTestImpl);

  }

  /**
   * Tests if PinkVehicleDecorator generates correct RGB values.
   */
  @Test
  public void testPinkVehicleDecorator() {
    Vehicle pinkVehicle = new PinkVehicleDecorator(testVehicle);

    assertEquals(239, pinkVehicle.getRed());
    assertEquals(130, pinkVehicle.getGreen());
    assertEquals(238, pinkVehicle.getBlue());

  }

  /**
   * Tests if MaroonVehicleDecorator generates correct RGB values.
   */
  @Test
  public void testMaroonVehicleDecorator() {
    Vehicle maroonVehicle = new MaroonVehicleDecorator(testVehicle);

    assertEquals(122, maroonVehicle.getRed());
    assertEquals(0, maroonVehicle.getGreen());
    assertEquals(25, maroonVehicle.getBlue());

  }

  /**
   * Tests if YellowVehicleDecorator generates correct RGB values.
   */
  @Test
  public void testYellowVehicleDecorator() {
    Vehicle yellowVehicle = new YellowVehicleDecorator(testVehicle);

    assertEquals(255, yellowVehicle.getRed());
    assertEquals(204, yellowVehicle.getGreen());
    assertEquals(51, yellowVehicle.getBlue());

  }

  /**
   * Tests if GreenVehicleDecorator generates correct RGB values.
   */
  @Test
  public void testGreenVehicleDecorator() {
    Vehicle greenVehicle = new GreenVehicleDecorator(testVehicle);

    assertEquals(60, greenVehicle.getRed());
    assertEquals(179, greenVehicle.getGreen());
    assertEquals(113, greenVehicle.getBlue());

  }

  /**
   * Tests if TransparentVehicleDecorator generates correct a value.
   */
  @Test
  public void testTransparentVehicleDecorator() {
    Vehicle ghostVehicle = new TransparentVehicleDecorator(testVehicle);

    assertEquals(155, ghostVehicle.getAlpha());
    assertTrue(ghostVehicle.getVehicle() instanceof VehicleTestImpl);

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testVehicle = null;
  }

}
