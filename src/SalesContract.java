import javax.swing.plaf.PanelUI;

public class SalesContract extends Contract{
    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicle, boolean isFinanced){
        super(date, customerName, customerEmail, vehicle);
        this.isFinanced=isFinanced;

        //fixed values
        this.salesTaxAmount= vehicle.getPrice() *  0.05;
        this.recordingFee= 100;
        //variable price
        if(vehicle.getPrice() < 10000){
            this.processingFee = 295;
        }else {
            this.processingFee = 495;
        }
    }
        @Override
        public double getTotalPrice(){
            double basePrice = getVehicle().getPrice();
            return basePrice + recordingFee + processingFee;
        }
        @Override
    public double getMonthlyPayment(){
        if(!isFinanced){
            return 0.0;
        }
        double total = getTotalPrice();
        double price = getVehicle().getPrice();

        int loanLength;
        double interestRate;

        if(price >= 10000){
            loanLength = 48;
            interestRate = 0.0425;
        }else {
            loanLength = 24;
            interestRate = 0.0525;
        }
        double monthlyInterest = interestRate/12;

        //pmt = total * r / (1-(1+r)^-n)
        return (total * monthlyInterest) /(1 - Math.pow(1+ monthlyInterest,-loanLength));
        }

        //getter


    public boolean isFinanced() {
        return isFinanced;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }
}
