package model;

public class StudentPriceTicket extends PriceTicket {
    public StudentPriceTicket() {
        // Constructor implicit
    }

    public StudentPriceTicket(double price) {
        super(price);
    }

    public double getDiscount() {
        return price * 0.1; // 10% discount
    }
}
