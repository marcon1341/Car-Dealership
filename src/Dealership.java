import java.util.ArrayList;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    //constructor
    public Dealership(String name,String address,String phone){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle){this.inventory.add(vehicle);}
    public ArrayList<Vehicle> getAllVehicles(){return this.inventory;}

    public ArrayList<Vehicle> getVehiclesByPrice(double min, double max){return null;}
    public ArrayList<Vehicle> getVehiclesByMakeModel(String make, String model) {return null;}
    public ArrayList<Vehicle> getVehiclesByYear(int minYear, int maxYear){return null;}
    public ArrayList<Vehicle> getVehiclesByColor(String color){return null;}
    public ArrayList<Vehicle> getVehiclesByMileage(int minMileage, int maxMileage){ return null;}
    public ArrayList<Vehicle> getVehiclesByVehiclesType(String vehiclesType){ return null;}
    public void removeVehicle(Vehicle vehicle){

    }
}