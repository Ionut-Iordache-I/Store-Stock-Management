import org.apache.commons.lang3.tuple.Pair;

/**
 * Clasa destinata implementarii metodelor ajutatoare.
 */
public class Helper {
    /**
     * Constructorul fara parametrii al clasei Helper.
     */
    private Helper() {}

    /**
     * Metoda ce converteste stringul format din pret si currency
     * in perechea formata din aceste atribute de tipul double si Currency.
     * @param priceWithCurrency Reprezinta stringul ce contine pretul si moneda.
     * @return Intoarce o pereche formata din pret si currency.
     */
    static Pair<Double, Currency> convertStringToPriceCurrency (String priceWithCurrency) {
        Currency currency = new Currency(priceWithCurrency.substring(0,1));
        String auxString = priceWithCurrency.trim().substring(1).replaceFirst(",", "");
        double price = Double.parseDouble(auxString);
        return Pair.of(price, currency);
    }
}
