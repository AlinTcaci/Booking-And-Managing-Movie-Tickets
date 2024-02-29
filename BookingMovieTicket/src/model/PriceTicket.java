package model;

public abstract class PriceTicket {
    protected double price;

    public PriceTicket() {
        // Constructor implicit
    }

    public PriceTicket(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract double getDiscount();
}

