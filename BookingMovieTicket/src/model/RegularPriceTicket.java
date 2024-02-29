package model;

public class RegularPriceTicket extends PriceTicket {
    public RegularPriceTicket() {
        // Constructor implicit
    }

    public RegularPriceTicket(double price) {
        super(price);
    }

    public double getDiscount() {
        return 0; // 0% discount
    }
}
