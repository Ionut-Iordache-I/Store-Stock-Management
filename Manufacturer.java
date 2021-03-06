/**
 * Clasa cu atributele specifice unui producator.
 */
public class Manufacturer {
    private String name; // numele producatorului
    private int countProducts = 0; // numarul de produse in gestiunea magazinului

    /**
     * Constructorul clasei Manufacturer.
     * @param name Numele producatorului.
     */
    public Manufacturer(String name) {
        this.name = name;
    }

    /**
     * Getter pentru numele producatorului.
     * @return Intoarce un string reprezentand numele producatorului.
     */
    public String getName() {
        return name;
    }

    /**
     * Preia numarul de produse existente in gestiunea magazinului.
     * @return Intoarce numarul de produse din magazin.
     */
    public int countProducts() {
        return countProducts;
    }

    /**
     * Metoda incrementeaza numarul de produse din gestiunea magazinului.
     */
    public void incrementCountProducts() {
        this.countProducts++;
    }
}
