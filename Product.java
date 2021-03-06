/**
 * Clasa ce retine atributele si metodele specifice unui produs.
 */
public class Product {
    private String uniqueId;
    private String name;
    private Manufacturer manufacturer;
    private double price;
    private int quantity;
    private Discount discount;

    /**
     * Constructorul ce primeste ca parametru un productBuilder
     * si actualizeaza atributele clasei product folosindu-se de
     * metodele regasite in productBuilder.
     * @param productBuilder Un obiect de tipul ProductBuilder.
     */
    public Product(ProductBuilder productBuilder) {
        this.uniqueId = productBuilder.getUniqueId();
        this.name = productBuilder.getName();
        this.manufacturer = productBuilder.getManufacturer();
        this.price = productBuilder.getPrice();
        this.quantity = productBuilder.getQuantity();
    }

    /**
     * Constructorul cu parametri al clasei Product.
     * @param uniqueId Id-ul unic al produsului.
     * @param name Numele produsului.
     * @param manufacturer Producatorul produsului.
     * @param price Pretul produsului.
     * @param quantity Cantitatea in care se gaseste produsul.
     */
    public Product(String uniqueId, String name, Manufacturer manufacturer, double price, int quantity) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Getter pentru id-ulprodusului.
     * @return Intoarce id-ul produsului.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * Getter pentru numele produsului.
     * @return Intoarce numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter pentru producatorul produsului.
     * @return Intoarce manufacturer-ul produsului.
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Getter pentru pretul produsului.
     * @return Intoarce pretul produsului.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Actualizeaza pretul produsului.
     * @param price Pretul la care va fi actualizat produsul.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter pentru cantitatea in care se gaseste un anumit produs.
     * @return Intoarce un int reprezentand cantitatea produsului.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Getter pentru discountul unui produs.
     * @return Intoarce discountul produsului.
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Actualizeaza discountul produsului.
     * @param discount Discountul ce urmeaza sa fie setat pentru produs.
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /**
     * Metoda care intoarce un vector cu elementele
     * unui produs pentru a fi puse in fisierul de output.
     * @return Un vector de stringuri.
     */
    public String[] splitByAttributes() {
        String word = " NEW";
        return new String[]{this.uniqueId, this.name, this.manufacturer.getName(),
                Store.getCurrency().getSymbol() + this.price, this.quantity + word.replace(" ", "\u00a0")};
    }

    /**
     * Metoda care face conversia pretului unui produs
     * la noua moneda dorita pentru conversie.
     * @param oldCurrency Vechea moneda pentru produs.
     * @param newCurrency Noua moneda pentru produs.
     */
    public void recalculatePrice(Currency oldCurrency, Currency newCurrency) {
        if (oldCurrency.getName().equals("EUR")) {
            this.price = this.price / newCurrency.getParityToEur();
        } else {
            this.price = (this.price * oldCurrency.getParityToEur())/ newCurrency.getParityToEur();
        }
    }

    /**
     * Suprascriu metoda toString() pentru a afisa produsul in forma ceruta.
     * @return Intoarce un string ce respecta formatul impus.
     */
    @Override
    public String toString() {
        return uniqueId + "," + name + "," + manufacturer.getName() + ","
                + Store.getInstance().getCurrency().getSymbol() + "" + price + "," + quantity;
    }
}
