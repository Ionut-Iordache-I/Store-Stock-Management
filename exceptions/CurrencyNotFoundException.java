package exceptions;

public class CurrencyNotFoundException extends Exception {
    public CurrencyNotFoundException() {
        super("Moneda nu se afla in lista de monede!");
    }
}
