import java.io.*;

public class DealershipFileManager {
    public Dealership getDealership() {
        Dealership dealership = null;

        try {
            //read the file from .csv
            BufferedReader reader = new BufferedReader(new FileReader("inventory.csv"));
            String line;

            //get the dealership info
            line = reader.readLine();
            String[] split = line.split("\\|");
            String name = split[0];
            String address = split[1];
            String phone = split[2];

            //create Dealership obj from the info from the file
            dealership = new Dealership(name, address, phone);

            //load vehicle
            while ((line = reader.readLine()) != null) {
                String[] split2 = line.split("\\|");
                int vin = Integer.parseInt(split2[0]);
                int year = Integer.parseInt(split2[1]);
                String make = split2[2];
                String model = split2[3];
                String vehicleType = split2[4];
                String color = split2[5];
                int odometer = Integer.parseInt(split2[6]);
                double price = Double.parseDouble(split2[7]);

                //create new vehicle obj
                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(vehicle);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading inventory file " + e.getMessage());
        }
        return dealership;
    }

    public void saveDealership(Dealership dealership){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("inventory.csv"));
        //dealership info
                writer.printf("%s|%s|%s\n",dealership.getName(), dealership.getAddress(),dealership.getPhone());
            for(Vehicle vehicle : dealership.getAllVehicles()){
                writer.printf("%d|%d|%s|%s|%s|%s|%d|%.2f\n",vehicle.getVin(),vehicle.getYear(),vehicle.getMake(),vehicle.getModel(),vehicle.getVehicleType(),vehicle.getColor(),vehicle.getOdometer(),vehicle.getPrice());
            }
            writer.close();
            System.out.println("Dealership saved to inventory.csv");

        } catch (IOException e) {
            System.out.println("Error saving dealership!: "+ e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        DealershipFileManager fl = new DealershipFileManager();
//        Dealership dealership = fl.getDealership();
//        if (dealership != null) {
//            System.out.println("Vehicle's loaded: "+ dealership.getAllVehicles().size());
//            for (Vehicle vehicle : dealership.getAllVehicles()) {
//               System.out.println(vehicle);
//            }
//
//            System.out.println("Vehicle's from 5000 ~ 10,000");
//            for(Vehicle vehicle: dealership.getVehiclesByPrice(5000,12000)){
//                System.out.println( vehicle);
//            }
//
//            if (dealership != null) {
//                System.out.println("Dealership loaded:");
//                System.out.println(dealership);
//
//                System.out.println("\nINVENTORY LOADED FROM FILE");
//                for (Vehicle v : dealership.getAllVehicles()) {
//                    System.out.println(v);
//                }
//            } else {
//                System.out.println("Failed to load dealership from inventory.csv");
//            }
//
//        } else {
//            System.out.println("Failed to load");
//        }
//    }
}
