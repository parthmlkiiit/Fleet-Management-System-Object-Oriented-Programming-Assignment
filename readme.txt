README.txt

Fleet Management System (Object-Oriented Programming Assignment)
===============================================================

1. Overview / OOP Concepts Demonstrated
----------------------------------------
- Abtract classes :  The `vehicles.Vehicle` class is defined as abstract. It has common fields (id, model, maxSpeed, currentMileage) and some concrete methods (e.g., `addMileage()`, getters). Subclasses (Car, Truck, Bus, Airplane, CargoShip) inherit from `Vehicle` and define abstract methods (e.g., `move()`, `calculateFuelEfficiency()`).

- Interfaces : Interfaces such as `Maintainable` and `FuelConsumable` specify behaviour (methods) which some Vehicles are required to offer. For instance, classes that adhere to `Maintainable` need to specify `needsMaintenance()`, `performMaintenance()` etc. This enables the code (for instance, in `FleetManager`) to function polymorphically with any vehicle which "can be maintained" or "can use fuel".

- Inheritance : Subclasses inherit shared behavior and attributes from `Vehicle`. For instance, Car, Truck etc use the shared logic (id checks, mileage increments etc) specified in `Vehicle`. There is also method overloading: each subclass overrides `move()`, `calculateFuelEfficiency()` in its own fashion, perhaps tweaking for type.

- Polymorphism : The `FleetManager` holds and operates with a list of `Vehicle` references. At run-time, each `Vehicle` reference could point to a Car, Truck, Airplane, etc. When you invoke `move()`, `calculateFuelEfficiency()`, or `needsMaintenance()`, the appropriate subclass implementation is executed. Moreover, you can search by type or sort by efficiency, etc., without being concerned about exact subclass in many places.

2. How to Compile, Run, and Test Persistence
---------------------------------------------
Compile

Open a terminal / command prompt in root folder and run:

javac -d bin vehicles/*.java exceptions/*.java fleet/*.java

This compiles all your `.java` files into `bin/`, preserving the package structure.

Run:

After successful compilation, run the main program using:

java -cp bin fleet.Main

Test Persistence (CSV save/load)-

- Use “Save Fleet” option in the CLI to export to a `.csv` file (you will be asked for filename).
- Then exit or reset, use “Load Fleet” option with the saved `.csv` file name to load back in.
- The vehicles and data should match those saved in the file (IDs, models, mileage, etc).

3. How to Use the CLI and Demo Features
----------------------------------------
Once you run `fleet.Main`, you will see a menu with numbered choices. Basic flow:

1. Add Vehicle – choose type (Car, Truck, Bus, Airplane, CargoShip), enter required info (ID, model, speed, mileage, plus type-specific data like number of wheels, max altitude, has sail, etc.).
2. Remove Vehicle – provide the ID to delete a vehicle.
3. Perform Action on Vehicle – pick a type, select a vehicle, then do actions specific to that vehicle (load/unload passengers or cargo depending on type).
4. Start Journey – simulate all vehicles moving a given distance (which updates mileage).
5. Refuel All – (if implemented) refuel fuel-consuming vehicles.
6. Perform Maintenance – perform maintenance on all vehicles that need it.
7. Generate Report – shows summary: total number of vehicles, counts by type, average efficiency (excluding some special cases like sail-ships if applicable), total mileage, which vehicles need maintenance.
8. Save Fleet – save current fleet data to CSV file.
9. Load Fleet – load fleet data from a CSV file.
10. Search by Type – list vehicles filtered by their class/type.
11. List Vehicles Needing Maintenance** – show IDs of vehicles with maintenance needed.
12. Total fuel consumed by fleet for a journey - uses the gettotalfuelconsumption to get the total consumption by the fleet for a dist
13. List all veh in fleet -  list all vehicles in fleet in format "type - id" eg "airplane - a001"
14. Exit – quit the program.

4. Notes / Expected Behavior
-----------------------------
- Default CLI given in ASSIGNMENT pdf LACKED many features like load/unload cargo and board/deboard passengers, along with we made features like gettotalfuelconsumption but isnt used so i added 3 new option 
   Perform Action on Vehicle , Total fuel consumed by fleet for a journey,  List all veh in fleet 
- When a vehicle is newly added it has no fuel so you have to refuel before starting
- Proper exception/errors are handled for everything.
- Total fuel consumption uses sum of ConsumeFuel as given in the pdf and also by the pdf Consume fuel actually reduces the fuel in all vehicles so while calling total fuel the fuel consumed is actually reduced.
- Maintenance is required when mileage crosses 10,000 km.
- Sorting by efficiency orders the fleet so that the most fuel-efficient show first (or according to your compareTo logic).
- Invalid inputs (e.g. negative distance, duplicate IDs) will be handled via exceptions; error messages will be printed (but program continues).
- CSV persistence should preserve all attributes needed to recreate Vehicles (type, ID, model, mileage, etc.).

5. Walkthrough / Demo Example & Expected Output
-----------------------------------------------
Here is a sample run you can try:

> java -cp bin fleet.Main

======== Fleet management system ========
1. Add Vehicle
2. Remove Vehicle
3. Perform Action on Vehicle
4. Start Journey
5. Refuel All
6. Perform Maintenance
7. Generate Report
8. Save Fleet
9. Load Fleet
10. Search by Type
11. List Vehicles Needing Maintenance
12. Exit
=========================================
Enter your choice :- 1

Select what type of vehicle to add:-
1. Car 2. Truck 3. Bus 4. Airplane 5. CargoShip
Choice => 1
Enter the following info -
ID => C1
Model => Toyota
Max speed => 120
Current mileage => 500
Number of wheels => 4
Vehicle added successfully!

Enter your choice :- 5
you entered 5
Currently the fleet consists of:
Land vehicles: 1
Air vehicles: 0
Water vehicles: 0
Enter fuel amount for land vehicles - 20
Car - C1 fueled with 20.0L

Enter your choice :- 4
Enter journey distance=> 200
The Car C1 moved 200.0 km and used 13.333333333333334 L
Journey started for all vehicles

Enter your choice :- 7
--------FLEET REPORT--------
 Total vehicle => 1
 Num of cars => 1, Num of Trucks => 0, Num of buses => 0, Num of airplanes => 0, Num of Cargoships => 0
 Average efficiecy (doesnt include Sailships) => 15.0
 Total Mileage => 700.0
 Vehicles that need maintaince => (empty as C1 doesnt need)

Enter your choice :- 8
Enter file name to save fleet- myfleet.csv
Sort by fuel efficiency before saving? (y/n): y
Fleet saved successfully to myfleet.csv

Enter your choice :- 12
Exiting program…

After saving, you can load:

> java -cp bin fleet.Main
Enter your choice :- 9
Enter file name to load fleet- myfleet.csv
Fleet loaded successfully from myfleet.csv

Enter your choice :- 7
--------FLEET REPORT--------
Total vehicle => 1
... (same as before) ...

