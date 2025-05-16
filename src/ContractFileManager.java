import java.io.*;

public class ContractFileManager {

    public void saveContract(Contract contract){
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("contracts.csv", true)); // append mode!
            Vehicle vehicle = contract.getVehicle();

            String base = String.format("%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f",
                    contract.getDate(),
                    contract.getCustomerName(),
                    contract.getCustomerEmail(),
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice()
            );

            if(contract instanceof SalesContract){
                SalesContract sc = (SalesContract) contract;
                writer.printf("%s|SALE|%.2f|%.2f|%.2f|%b|%.2f|%.2f\n",
                        base,
                        sc.getSalesTaxAmount(),
                        sc.getRecordingFee(),
                        sc.getProcessingFee(),
                        sc.isFinanced(),
                        sc.getTotalPrice(),
                        sc.getMonthlyPayment()
                );
            } else if(contract instanceof LeaseContract){
                LeaseContract lc = (LeaseContract) contract;
                writer.printf("%s|LEASE|%.2f|%.2f|%.2f|%.2f\n",
                        base,
                        lc.getEndingValue(),
                        lc.getLeaseFee(),
                        lc.getTotalPrice(),
                        lc.getMonthlyPayment()
                );
            }
            writer.close();
            System.out.println("Contract saved to contracts.csv");
        } catch (IOException e) {
            System.out.println("Error saving contract: "+ e.getMessage());
        }
    }
}

