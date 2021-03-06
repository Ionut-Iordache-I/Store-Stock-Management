import java.time.LocalDateTime;

/**
 * Clasa cu atributele specifice unui discount.
 */
public class Discount {
    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;

    /**
     * Constructorul cu parametrii pentru clasa discount.
     * @param name Reprezinta numele discountului.
     * @param discountType Reprezinta tipul de discount
     *                     pe care il poate avea un produs.
     * @param value Reprezinta valoarea efectiva a discountului.
     */
    public Discount(DiscountType discountType, String name, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.lastDateApplied = LocalDateTime.now();
    }

    /**
     * Getter pentru atributul lastDateApplied.
     * @return Intoarce data si ora aplicarii discountului.
     */
    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    /**
     * Getter pentru tipul de discount pe care il avem.
     * @return Intoarce tipul de discount al clasei Discount.
     */
    public DiscountType getDiscountType() {
        return discountType;
    }

    /**
     * Getter pentru numele discountului.
     * @return Intoarce stringul reprezentand numele discountului.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pentru valoarea discountului.
     * @return Intoarce un double reprezentand valoarea discountului.
     */
    public double getValue() {
        return value;
    }

    /**
     * Metoda care actualizeaza atributul lastDateApplied
     * la data si ora curenta.
     */
    public void setAsAppliedNow() {
        this.lastDateApplied = LocalDateTime.now();
    }

    /**
     * Metoda ce modifica pretul in functie de tipul de discount
     * fie acesta fixed sau percentage.
     * @param price Pretul unui produs care va fi modificat.
     * @return Intoarce pretul dupa aplicarea discountului.
     */
    public double getNewPrice(double price) {
        if (this.discountType.equals(DiscountType.PERCENTAGE_DISCOUNT)) {
            return price * (100 - this.value) / 100;
        }
        else {
            return price - this.value;
        }
    }

    /**
     * Suprascrierea metodei toString pentru a afisa discountul
     * in formatul specificat.
     * @return Intoarce un string conform cu formatul cerut.
     */
    @Override
    public String toString() {
        return discountType + " " + value + " " + name + " " + lastDateApplied;
    }
}
