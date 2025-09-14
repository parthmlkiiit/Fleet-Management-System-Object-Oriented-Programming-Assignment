import java.util.ArrayList; 
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
      throw new InvalidOperationException("vehicle with id-" + id + "doesnt exist in fleet.");
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
        System.out.println("error insufficient fuel")
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
      if (type == null) {
    throw new IllegalArgumentException("Type cannot be null");
  }
    List<Vehicle> result = new ArrayList<>();
    for (Vehicle vehicleinlist : fleet){
      if (type.isInstance(vehicleinlist)){
        result.add(vehicleinlist);
      }
    }
    return result;
  }
}