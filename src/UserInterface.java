import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    BufferedReader in;

    public UserInterface(Dealership dealership) {
        this.dealership = dealership;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }
    public void display() throws IOException {
        System.out.println("=== Welcome to Marcus Auto Shop ===");
        String command = "";

        while (!command.equalsIgnoreCase("x")) {
            System.out.println("""          
                    MAIN MENU:
                      Search By:
                          A) all
                          P) price
                          M) make and model
                          Y) year
                          C) color
                          O) odometer
                          T) type (e.g. sedan)
                      +) add
                      -) remove
                      X) e(x)it
                    """);

            System.out.print("Enter command: ");
            command = in.readLine().toLowerCase().trim();

            switch (command) {
                case "a" -> processGetAllVehiclesRequest();
                case "p" -> processGetByPriceRequest();
                case "m" -> processGetByMakeModelRequest();
                case "y" -> processGetByYearRequest();
                case "c" -> processGetByColorRequest();
                case "o" -> processGetByMileageRequest();
                case "t" -> processGetByTypeRequest();
                case "+" -> processAddVehicleRequest();
                case "-" -> processRemoveVehicleRequest();
                case "x" -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void processGetAllVehiclesRequest(){
         var list  = this.dealership.getAllVehicles();
         if(list.size() == 0){
             System.out.println("None found.");
             return;
         }
         for(Vehicle vehicle : list){
             System.out.println(vehicle);
         }
    }
    int getInt(String name){
        System.out.printf("%s ", name);
        Integer value = null;
        while (value == null){
            try{
                String input = in.readLine().trim();
                value = Integer.parseInt(input);
            }catch (IOException e) {
                System.out.println("Try again");
            }
        }
        return value;
    }
    String getString(String name){
        System.out.printf("%s ", name);
         String value = null;
         while (value == null){
             try {
                 value = in.readLine().trim();
                 if(value.isEmpty()){
                     System.out.println("Input cannot be empty. Try again: ");
                     value = null;
                 }
             } catch (IOException e) {
                 System.out.println("Try Again:");
             }
         }
         return value;
    }

    public void processGetByPriceRequest(){
         var list = this.dealership.getVehiclesByPrice(getInt("Minimum"),getInt("Maximum"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle : list){
             System.out.println(vehicle);
         }
    }
    public void processGetByYearRequest(){
         var list = this.dealership.getVehiclesByYear(getInt("Minimum Year"), getInt("Maximum Year"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle : list){
             System.out.println(vehicle);
         }
    }
    public void processGetByMakeModelRequest(){
         var list = this.dealership.getVehiclesByMakeModel(getString("Make"),getString("Model"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle : list){
             System.out.println(vehicle);
         }
    }
    public void processGetByColorRequest(){
         var list = this.dealership.getVehiclesByColor(getString("Color"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle : list){
             System.out.println(vehicle);
         }
    }
    public void processGetByMileageRequest(){
         var list = this.dealership.getVehiclesByMileage(getInt("Minimum Mileage"),getInt("Maximum Mileage"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle: list){
             System.out.println(vehicle);
         }
    }
    public void processGetByTypeRequest(){
         var list = this.dealership.getVehiclesByVehiclesType(getString("Type"));
         if(list.size() == 0){
             System.out.println("None found");
         }
         for(Vehicle vehicle: list){
             System.out.println(vehicle);
         }
    }
private void processAddVehicleRequest() {
    System.out.println("\n=== ADD VEHICLE ===");

    int vin = getInt("VIN");

    int year = getInt("YEAR");

    String make = getString("MAKE");

    String model = getString("MODEL");

    String type = getString("TYPE");

    String color = getString("COLOR");

    int odometer = getInt("ODOMETER");

    double price = Double.parseDouble(getString("PRICE"));

    Vehicle newVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
    dealership.addVehicle(newVehicle);

    new DealershipFileManager().saveDealership(dealership);
    System.out.println("Vehicle added successfully.");
}

private void processRemoveVehicleRequest() {
    System.out.print("Enter VIN of vehicle to remove: ");
    int vin = getInt("VIN");

    Vehicle toRemove = null;
    for (Vehicle vehicle : dealership.getAllVehicles()) {
        if (vehicle.getVin() == vin) {
            toRemove = vehicle;
            break;
        }
    }

    if (toRemove != null) {
        dealership.removeVehicle(toRemove);

        new DealershipFileManager().saveDealership(dealership);
        System.out.println("Vehicle removed.");
    } else {
        System.out.println("Vehicle not found.");
            }
    }
}
