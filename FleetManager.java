import java.util.ArrayList;
import java.util.List; 
import java.util.Collections;
public class FleetManager {
  private ArrayList<Vehicle> fleet = new ArrayList<Vehicle>();

  public void addVehicle(Vehicle v) throws InvalidOperationException{
    if (v == null) {
      throw new InvalidOperationException("Vehicle cannot be null");
    }
    String tempid = v.getId();
    for (Vehicle vehicleinlist : fleet){
      if (vehicleinlist.getId().equals(tempid)) throw new InvalidOperationException("vehicle-" + vehicleinlist.getId() + " already exists");      
    }
    fleet.add(v);
  }
  public void removeVehicle(String id) throws InvalidOperationException{
     if (id == null || id.isBlank()) {
      throw new InvalidOperationException("ID cannot be empty");
    }
    for (int i = 0; i < fleet.size(); i++){
      if (fleet.get(i).getId().equals(id)){
        fleet.remove(i);
        return;
      }
    }
      throw new InvalidOperationException("vehicle with id-" + id + " doesnt exist in fleet.");
    }
  public void startAllJourneys(double distance) throws InvalidOperationException{
    if (distance <= 0){
      throw new InvalidOperationException("Invalid distance input");
    }
    for (Vehicle vehicleinlist : fleet){
      try {vehicleinlist.move(distance);
      }
      catch (InvalidOperationException e) {
      System.out.println("Failed to move vehicle " + vehicleinlist.getId() + " - " + e.getMessage());
    }
  }
  }
  public double getTotalFuelConsumption(double distance) throws InvalidOperationException{
    if (distance <= 0){
      throw new InvalidOperationException("Invalid distance input");
    }
    double fuelsum = 0.0;
    for (Vehicle vehicleinlist : fleet){

      FuelConsumable tempvar = (FuelConsumable) vehicleinlist;
      try{
        double fuelused = tempvar.consumeFuel(distance); // this changes the vehicle fuel though 
        fuelsum+= fuelused;
      }
      catch (InsufficientFuelException e) {
        // for cases where fuel is insufficient and this error is produced
        System.out.println("error insufficient fuel");
      }
      
    }
    return fuelsum;
  }
  public void maintainAll(){ 
    for (Vehicle vehicleinlist : fleet){
      Maintainable tempvar = (Maintainable) vehicleinlist;
      if (tempvar.needsMaintenance()){tempvar.performMaintenance();}
    }
  }
  public List<Vehicle> searchByType(Class<?> type){
    List<Vehicle> result = new ArrayList<>();
    for (Vehicle vehicleinlist : fleet){
      if (type.isInstance(vehicleinlist)){
        result.add(vehicleinlist);
      }
    }
    return result;
  }
  public void sortFleetByEfficiency(){
    Collections.sort(fleet);
  }
  public String generateReport() throws InvalidOperationException{//total vehicles, count by type, average efficiency, total mileage, maintenance status)
  int totalvehiclecounter = fleet.size();
  List<Integer> counterlist = new ArrayList<>();
  ArrayList<String> maintain = new ArrayList<String>();
  int tempefficiencynum = fleet.size();
  double totmilege = 0.0;
  double totefficiency = 0.0;
  for (int i = 0; i < 5; i++) {
    counterlist.add(0); // counterlist = (0,0,0,0,0) index=> 0 - Car, 1 - Truck, 2 - bus , 3 - airplane, 4 - cargoship
    }
  for (Vehicle currentvehicle : fleet) {
    if (currentvehicle instanceof Car) {
      int currentCount = counterlist.get(0);
      counterlist.set(0, currentCount + 1);}
    else if (currentvehicle instanceof Truck) { 
      int currentCount = counterlist.get(1);
      counterlist.set(1, currentCount + 1);} 
    else if (currentvehicle instanceof Bus) {
      int currentCount = counterlist.get(2);
      counterlist.set(2, currentCount + 1);}
    else if (currentvehicle instanceof Airplane) {
      int currentCount = counterlist.get(3);
      counterlist.set(3, currentCount + 1);} 
    else if (currentvehicle instanceof CargoShip) {
      int currentCount = counterlist.get(4);
      counterlist.set(4, currentCount + 1);}
    
    if (currentvehicle instanceof CargoShip && ((CargoShip) currentvehicle).hasSail()){
      tempefficiencynum -= 1;
    }
    else{
      double tempeff = currentvehicle.calculateFuelEfficiency();
      totefficiency = totefficiency + tempeff;
    }
  totmilege = totmilege + currentvehicle.getCurrentMileage();
  if (((Maintainable) currentvehicle).needsMaintenance()){
    maintain.add(currentvehicle.getId());
  }
  }
  double aveff = totefficiency / tempefficiencynum;
  String outputreport = ("--------FLEET REPORT--------\n Total vehicle => " + totalvehiclecounter + "\n Num of cars => " + counterlist.get(0) + ", Num of Trucks => " + counterlist.get(1) + ", Num of buses => " + counterlist.get(2) + ", Num of airplanes => " + counterlist.get(3) + ", Num of Cargoships => " + counterlist.get(4) + "\n Average efficiecy (doesnt include Sailships) => " + aveff + "\n Total Mileage => " + totmilege + "\n Vehicles that need maintaince => " + String.join(", ", maintain));
  return outputreport;
  }
  public List<Vehicle> getVehiclesNeedingMaintenance(){
    List<Vehicle> templist = new ArrayList<>();
    for (Vehicle vehicleinlist : fleet){
      if (((Maintainable) vehicleinlist).needsMaintenance()){
        templist.add(vehicleinlist);
      }
    }
    return templist;
  }
}