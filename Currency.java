/**
 * Clasa cu atributele specifice unei monede.
 */
public class Currency {
    private String name;
    private String symbol;
    private double parityToEur;

    /**
     * Constructorul cu atributele clasei Currency.
     * @param name Numele specific unei monede.
     * @param symbol Simbolul specific unei monede.
     * @param parityToEur Paritatea raportata la euro a unei monede.
     */
    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

    /**
     * Constructorul ce actualizeaza doar atributul symbol
     * al clasei Currency, folosit de metoda convertStringToPriceCurrency()
     * din clasa Helper.
     * @param symbol Simbolul specific unei monede.
     */
    public Currency(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter pentru atributul buildName al clasei.
     * @return Intoarce numele monedei.
     */
    public String getName() {
        return name;
    }

    /**
     * Actualizeaza numele monedei.
     * @param name Reprezinta numele la care este actualizata moneda.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pentru atributul symbol al clasei.
     * @return Intoarce simbolul monedei.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Actualizeaza simbolul monedei.
     * @param symbol Reprezinta simbolul la care este actualizata moneda.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter pentru atributul parityToEur al clasei.
     * @return Intoarce paritatea raportata la euro a monedei.
     */
    public double getParityToEur() {
        return parityToEur;
    }


    /**
     * Face update atributului ce defineste paritatea raportata la euro a monedei.
     * @param parityToEUR Valoarea ulterioara a atributului parityToEur
     */
    void updateParity(double parityToEUR) {
        this.parityToEur = parityToEUR;
    }

    /**
     * Suprascrie metoda toString.
     * @return Intoarce stringul format din numele si paritatea raportata la euro a monedei.
     */
    @Override
    public String toString() {
        return name + " " + parityToEur;
    }
}
