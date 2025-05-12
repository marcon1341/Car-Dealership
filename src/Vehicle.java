public class Vehicle {
    private int vin;
    private int year;
    private String make;
    private String model;
    private String vehicleType;
    private String color;
    private int odometer;
    private double price;

        //constructor
        public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price){
            this.vin = vin;
            this.year = year;
            this.make = make;
            this.model = model;
            this.vehicleType = vehicleType;
            this.color = color;
            this.odometer = odometer;
            this.price = price;
        }
        //getters
//
         int getVin() {return vin;}
         int getYear() {return year;}
         String getMake() {return make;}
         String getModel() {return model;}
         String getVehicleType() {return vehicleType;}
         String getColor() {return color;}
         int getOdometer() {return odometer;}
         double getPrice() {return price;}

        @Override
        public String toString(){
            return String.format("VIN: %d | YEAR: %d | MAKE: %s | MODEL: %s | VEHICLE TYPE: %s | COLOR: %s | ODOMETER: %,d miles | PRICE: $%,.2f" , vin,year,make,model,vehicleType,color,odometer,price);
        }
}

