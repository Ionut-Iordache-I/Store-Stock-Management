package exceptions;

public class DiscountNotFoundException extends Exception {
    public DiscountNotFoundException() {
        super("Discountul nu se afla in lista de discount-uri!");
    }
}
