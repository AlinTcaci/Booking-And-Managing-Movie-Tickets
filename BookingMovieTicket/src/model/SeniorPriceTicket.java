package model;

public class SeniorPriceTicket extends PriceTicket {
    public SeniorPriceTicket() {
        // Constructor implicit
    }

    public SeniorPriceTicket(double price) {
        super(price);
    }

    public double getDiscount() {
        return price * 0.2; // 20% discount
    }
}
