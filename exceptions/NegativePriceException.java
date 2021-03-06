package exceptions;

public class NegativePriceException extends Exception {
    public NegativePriceException() {
        super("Nu se poate aplica discount-ul, pretul devine negativ!");
    }
}
