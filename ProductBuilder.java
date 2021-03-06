/**
 * Clasa destinata design pattern-ului Builder
 */
public class ProductBuilder {
    private String uniqueId;
    private String name;
    private Manufacturer manufacturer;
    private double price;
    private int quantity;

    /**
     * Constructorul clasei ProductBuilder.
     * @param uniqueId Noul id pentru atributul uniqueId.
     */
    public ProductBuilder(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Setter-ul pentru nume.
     * @param name Reprezinta numele la care se face actualizarea.
     * @return intoarce productBuilder
     */
    public ProductBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Setter-ul pentru manufacturer.
     * @param manufacturer Producatorul la care se face actualizarea.
     * @return Intoarce productBuilder
     */
    public ProductBuilder buildManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    /**
     * Setter-ul pentru pret.
     * @param price Pretul la care se face actualizarea
     * @return intoarce productBuilder
     */
    public ProductBuilder buildPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * Setter-ul pentru cantitate.
     * @param quantity Reprezintaa cantitatea la care se face actualizarea.
     * @return intoarce productBuilder
     */
    public ProductBuilder buildQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Getter pentru id.
     * @return Intoarce id-ul.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Getter pentru nume.
     * @return Intoarce numele.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pentru manufacturer.
     * @return Intoarce manufacturer-ul
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * getter penru price.
     * @return Intoarce pretul.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter pentru cantitate.
     * @return Intoarce cantitatea.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metoda build specifica care construieste obiectul.
     * @return Intoarce un obiect de tipul Product.
     */
    Product build() {
        return new Product(this);
    }
}
