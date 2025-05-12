import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    //constructor
        Dealership(String name,String address,String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<Vehicle>();
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    //methods
    public void addVehicle(Vehicle vehicle){inventory.add(vehicle);}
    public ArrayList<Vehicle> getAllVehicles(){return inventory;}

    public ArrayList<Vehicle> getVehiclesByPrice(double min, double max){
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle: inventory) {
                if(vehicle.getPrice() >= min && vehicle.getPrice() <= max){
                    matches.add(vehicle);
                }
        }
            return matches;}
    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model) {
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle:inventory){
                if(vehicle.getModel().equalsIgnoreCase(model)&& vehicle.getMake().equalsIgnoreCase(make)){
                    matches.add(vehicle);
                }
            }
            return matches;}
    public ArrayList<Vehicle> getVehiclesByYear(int minYear, int maxYear){
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle:inventory){
                if(vehicle.getYear() >= minYear && vehicle.getYear()<= maxYear){
                    matches.add(vehicle);
                }
            }
            return matches;}
    public ArrayList<Vehicle> getVehiclesByColor(String color){
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle : inventory){
                if(vehicle.getColor().equalsIgnoreCase(color)){
                    matches.add(vehicle);
                }
            }
            return matches;}
    public ArrayList<Vehicle> getVehiclesByMileage(int minMileage, int maxMileage){
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle: inventory){
                if(vehicle.getOdometer() >= minMileage && vehicle.getOdometer() <= maxMileage){
                    matches.add(vehicle);
                }
            }
            return matches;}
    public ArrayList<Vehicle> getVehiclesByVehiclesType(String vehiclesType){
            ArrayList<Vehicle> matches = new ArrayList<>();
            for(Vehicle vehicle: inventory){
                if(vehicle.getVehicleType().equalsIgnoreCase(vehiclesType)){
                    matches.add(vehicle);
                }
            }
            return matches;}
    public void removeVehicle(Vehicle vehicle){this.inventory.remove(vehicle);}

    public String toString(){
        return String.format("DEALERSHIP NAME: %s #CARS: %d",this.name, this.inventory.size());
    }
}