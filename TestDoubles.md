1. VTS testAddObserver (dummy)
2. VTS testConstructorNormalWithStorageFacility (dummy)
3. VTS BeforeEach (dummy)
4. GetVehiclesCommand constructor (dummy)

*they might consider the following 5 tests 'one' test that uses test doubles since they're so similar, i'm not sure*
5. GetVehiclesCommand testExecuteFake (fake, dummy, stub, spy)
6. GetVehiclesCommand testExecuteSmallBus (dummy, stub, spy)
7. GetVehiclesCommand testExecuteLargeBus (dummy, stub, spy)
8. GetVehiclesCommand testExecuteElectricTrain (dummy, stub, spy)
9. GetVehiclesCommand testExecuteDieselTrain (dummy, stub, spy)