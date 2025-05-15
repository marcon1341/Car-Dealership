public class LeaseContract extends Contract{
    private double endingVale;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle){
        super(date, customerName, customerEmail, vehicle);
        //50 % of original price
        this.endingVale = vehicle.getPrice() * 0.5;
        //7%leasing fee
        this.leaseFee = vehicle.getPrice() * 0.07;
    }

    public double getEndingValue() {
        return endingVale;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return endingVale + leaseFee;
    }

    @Override
    public double getMonthlyPayment() {
        int leaseTerm = 36;
        double annualInterest = 0.04;
        double monthlyInterest = annualInterest / 12;
        double totalLeaseAmount = getTotalPrice();

    //(p * r)/(1-(1+r)^-n)
        return (totalLeaseAmount * monthlyInterest) / (1- Math.pow(1 + monthlyInterest,-leaseTerm));

    }

}
