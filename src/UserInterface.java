import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
                          R) Return Leased Vehicle
                          S) Sell/Lease
                          T) type (e.g. sedan)
                          V) View Contracts
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
                case "s" -> processGetBySellOrLeaseRequest();
                case "t" -> processGetByTypeRequest();
                case "r" -> processReturnLeasedVehicleRequest();
                case "v" -> processViewByContractsRequest();
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
    public void processGetBySellOrLeaseRequest(){
        try {
            int vin = getInt("Enter VIN: ");
            Vehicle found = null;
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                if (vehicle.getVin() == vin) {
                    found = vehicle;
                    break;
                }
            }
            if (found == null) {
                System.out.println("Vehcle not found in inventory.");
                return;
            }
            //customer info
            String customerName = getString("Enter customer name: ");
            String customerEmail = getString("Enter customer email: ");

            String type = "";
            while (!type.equalsIgnoreCase("s") && !type.equalsIgnoreCase("l")) {
                type = getString("Sale or Lease? (enter s or l): ");
            }
            //create contract type
            Contract contract;
            String today = java.time.LocalDate.now().toString();

            if (type.equalsIgnoreCase("l")) {
                int currentYear = java.time.LocalDate.now().getYear();
                if (currentYear - found.getYear() > 3) {
                    System.out.println("You can't lease a vehicle over 3 years old.");
                    return;
                }
                contract = new LeaseContract(today, customerName, customerEmail, found);
            } else {
                String finance = "";
                while (!finance.equalsIgnoreCase("y") && !finance.equalsIgnoreCase("n")) {
                    finance = getString("Will the customer finance? (y/n):");
                }
                boolean isFinanced = finance.equalsIgnoreCase("y");
                contract = new SalesContract(today, customerName, customerEmail, found, isFinanced);
            }

            // DISPLAY DETAILS
            System.out.println("\n----- Contract Details -----");
            System.out.printf("Year: %d\n", found.getYear());
            System.out.printf("Make: %s\n", found.getMake());
            System.out.printf("Model: %s\n", found.getModel());
            System.out.printf("Type: %s\n", found.getVehicleType());
            System.out.printf("Color: %s\n", found.getColor());
            System.out.printf("Odometer: %d\n", found.getOdometer());
            System.out.printf("Base Price: $%.2f\n", found.getPrice());

            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;
                System.out.printf("Sales Tax: $%.2f\n", sc.getSalesTaxAmount());
                System.out.printf("Recording Fee: $%.2f\n", sc.getRecordingFee());
                System.out.printf("Processing Fee: $%.2f\n", sc.getProcessingFee());
                System.out.printf("Total Price: $%.2f\n", sc.getTotalPrice());
                if (sc.isFinanced()) {
                    System.out.printf("Monthly Payment: $%.2f\n", sc.getMonthlyPayment());
                }
            } else if (contract instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) contract;
                System.out.printf("Lease Fee: $%.2f\n", lc.getLeaseFee());
                System.out.printf("Ending Value: $%.2f\n", lc.getEndingValue());
                System.out.printf("Total Price: $%.2f\n", lc.getTotalPrice());
                System.out.printf("Monthly Payment: $%.2f\n", lc.getMonthlyPayment());
            }


            // Confirm and Save
            String confirm = getString("Do you want to proceed with this sale/lease? (y/n): ");
            if (confirm.equalsIgnoreCase("y")) {
                new ContractFileManager().saveContract(contract);
                dealership.removeVehicle(found);
                new DealershipFileManager().saveDealership(dealership);
                System.out.println("Contract created and vehicle removed from inventory!");
            } else {
                System.out.println("Sale/lease cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error during sale/lease: " + e.getMessage());
        }
    }

private void processReturnLeasedVehicleRequest() {
    int vin = getInt("Enter VIN of returned vehicle: ");

    // Remove the lease contract for this VIN
    List<String> contracts = new ArrayList<>();
    String returnedContractLine = null;
    try (BufferedReader reader = new BufferedReader(new FileReader("contracts.csv"))) {
        String header = reader.readLine();
        contracts.add(header); // Keep the header
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split("\\|");
            // Find the contract with this VIN and type LEASE
            if (fields.length > 11 && fields[3].equals(String.valueOf(vin)) && fields[11].equalsIgnoreCase("LEASE") && returnedContractLine == null) {
                returnedContractLine = line; // Save this contract for extracting info
                continue;
            }
            contracts.add(line);
        }
    } catch (IOException e) {
        System.out.println("Error reading contracts file.");
        return;
    }

    // If contract not found
    if (returnedContractLine == null) {
        System.out.println("No matching lease contract found for this VIN.");
        return;
    }

    String[] fields = returnedContractLine.split("\\|");
    int year = Integer.parseInt(fields[4]);
    String make = fields[5];
    String model = fields[6];
    String type = fields[7];
    String color = fields[8];
    int odometer = Integer.parseInt(fields[9]);
    double price = Double.parseDouble(fields[10]);

    Vehicle returnedVehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
    dealership.addVehicle(returnedVehicle);
    new DealershipFileManager().saveDealership(dealership);

    // Save the contracts.csv without the returned lease
    try (PrintWriter writer = new PrintWriter(new FileWriter("contracts.csv"))) {
        for (String contractLine : contracts) {
            writer.println(contractLine);
        }
    } catch (IOException e) {
        System.out.println("Error saving updated contracts file.");
        return;
    }

    System.out.println("Lease contract deleted and vehicle returned to inventory!");
}

    private void processViewByContractsRequest() {
        System.out.println("\n=== ALL CONTRACTS ===");
        try (BufferedReader reader = new BufferedReader(new FileReader("contracts.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No contracts found or error reading file.");
        }

    }


}
