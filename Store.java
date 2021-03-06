import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import exceptions.*;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clasa cu atributele si metodele necesare modificarii ulterioare a
 * elementelor prezente in cadrul magazinului.
 * Aceasta clasa aplica design-pattern-ul Singleton
 */
public class Store {
    private String name = "POO Store";
    private static Currency currency; // moneda de schimb a magazinului
    // lista in care salvez produsele adaugate pe store
    private ArrayList<Product> products;
    // folosesc HashSet pentru a stoca toti producatorii o singura data
    private HashSet<Manufacturer> manufacturers;

    // vector ce retine primele 5 campuri de pe prima linie din csv
    public static String[] HEADERS = new String[5];
    // lista in care salvez monedele adaugate pe store
    public static ArrayList<Currency> currenciesList = new ArrayList<>();
    // lista in care salvez discounturile adaugate pe store
    public static ArrayList<Discount> discountsList= new ArrayList<>();

    /**
     * constructorul privat specific implementarii
     * design pattern-ului singleton
     */
    private Store() {
        currency = new Currency("EUR", "€", 1.0);
        currenciesList.add(currency);
        products = new ArrayList<>();
        manufacturers =new HashSet<>();
    }
    public static Store singleton = null;

    /**
     * Metoda statica ce produce singura instanta a clasei.
     * @return Intoarce instanta clasei.
     */
     public static Store getInstance() {
        if (singleton == null)
            singleton = new Store();
        return singleton;
    }

    /**
     * Getter pentru arraylist-ul de produse.
     * @return Intoarce un ArrayList cu produsele din store
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Getter pentru HashSet-ul de producatori.
     * @return Intoarce un HashSet cu producatorii
     * ai caror produse apar in store.
     */
    public HashSet<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    /**
     * Getter pentru moneda de schimb a magazinului.
     * @return Intoarce moneda de schimb folosita de magazin.
     */
    public static Currency getCurrency() {
        return currency;
    }

    /**
     * Setter pentru atributul currency.
     * @param currency Reprezinta noua moneda a clasei Store.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Setter pentru lista de produse products.
     * @param products Reprezinta o lista de obiecte de tipul Product.
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Metoda Care realizeaza citirea datelor din fisier.
     * @param filename Reprezinta numele fisierului de intrare.
     * @return Intoarce o lista de obiecte de tipul Product.
     */
    public ArrayList<Product> readCSV(String filename) {
        FileReader filereader;
        CSVReader csvReader;
        String[] nextRecord;
        try {
            filereader = new FileReader(filename);
            csvReader = new CSVReader(filereader);
            // pastrez in headers prima linie cu cele 5 campuri din CSV
            String[] firstLine = csvReader.readNext();
            for (int i = 0; i < 5; i++) {
                HEADERS[i] = firstLine[i];
            }
            Manufacturer notAvailable = new Manufacturer("Not Available");

            // citim linie cu linie din csv
            while ((nextRecord = csvReader.readNext()) != null) {
                // daca am mai putin de 5 elem. pe o linie trec la urmatoarea
                if (nextRecord.length  < 5) {
                    continue;
                }
                if (nextRecord[3] != null && !nextRecord[3].isEmpty()) {
                    if(!nextRecord[3].startsWith("£")) {
                        continue;
                    }
                    String uniq_id = nextRecord[0];
                    String product_name = nextRecord[1];
                    String manufacturer = nextRecord[2];
                    String price = nextRecord[3];
                    Pair<Double, Currency> currencyPricePair = Helper.convertStringToPriceCurrency(price.split("-")[0]);
                    Double finalPrice = currencyPricePair.getLeft();
                    // daca exista vreun produs cu pretul 0, nu il mai adaugam in lista de produse
                    if (finalPrice == 0.0) {
                        continue;
                    }
                    String number_available_in_stock = nextRecord[4];
                    int quantity;
                    if (number_available_in_stock == null || number_available_in_stock.isEmpty()) {
                        quantity = 0;
                    } else {
                        quantity = Integer.parseInt(number_available_in_stock.split("\\h")[0]);
                    }

                    Manufacturer manufacturer1 = null;
                    if ( manufacturer == null || manufacturer.isEmpty()) {
                        notAvailable.incrementCountProducts();
                        manufacturer1 = notAvailable;
                    } else {
                        try {
                            manufacturer1 = new Manufacturer(manufacturer);
                            this.addManufacturer(manufacturer1);
                        } catch (DuplicateManufacturerException ex) {
                            System.out.println(ex.getMessage());
                        } finally {
                            for (Manufacturer manufacturerObj : this.manufacturers) {
                                if (manufacturer.equals(manufacturerObj.getName())) {
                                    manufacturer1 = manufacturerObj;
                                    break;
                                }
                            }
                        }
                    }


                    try {
                        Product finalProduct = new ProductBuilder(uniq_id)
                                                    .buildName(product_name)
                                                    .buildPrice(finalPrice)
                                                    .buildQuantity(quantity)
                                                    .buildManufacturer(manufacturer1)
                                                    .build();
                        this.addProduct(finalProduct);
                    } catch (DuplicateProductException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return this.products;
    }

    /**
     * Scrie datele despre produse intr-un fișier CSV.
     * @param filename Reprezinta fisierul de iesire.
     */
    public void saveCSV(String filename) {
        File file = new File(filename);
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(HEADERS);

            for (Product product : products) {
                writer.writeNext(product.splitByAttributes());
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adaug un produs in array-ul de produse daca nu exista.
      * @param product Un produs ce urmeaza a fi adaugat in array.
     * @throws DuplicateProductException Pentru cazul in care am 2 produse identice.
     */
    public void addProduct(Product product) throws DuplicateProductException {
        for (Product prod : products) {
            if (prod.getUniqueId().equals(product.getUniqueId())) {
                throw new DuplicateProductException();
            }
        }
        // adaug produsul in lista de produse
        this.products.add(product);
        // verific daca in HashSet-ul de producatori am deja
        // producatorul pentru produsul meu si daca nu, il adaug
        Manufacturer manufacturer1 = null;
        for (Manufacturer manufacturerObj : manufacturers) {
            if (product.getManufacturer().getName().equals(manufacturerObj.getName())) {
                manufacturer1 = manufacturerObj;
            }
        }
        if (manufacturer1 == null) {
            manufacturer1 = product.getManufacturer();
            // adaug producatorul in HashSet
            manufacturers.add(manufacturer1);
        }
        // incrementez numarul de produse al producatorului respectiv
        manufacturer1.incrementCountProducts();
    }

    /**
     * Adaug un producator in array-ul de producatori.
     * @param manufacturer Producatorul ce urmeaza sa fie adaugat.
     * @throws DuplicateManufacturerException Pentru cazul in care
     * am 2 producatori identici.
     */
    public void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException {
        for (Manufacturer manufact : manufacturers) {
            if (manufact.getName().equals(manufacturer.getName())) {
                throw new DuplicateManufacturerException();
            }
        }
        this.manufacturers.add(manufacturer);
    }

    /**
     * Metoda care creeaza o moneda pentru actualizarea
     * ulterioara a preturilor produselor.
     * @param name Numele monedei.
     * @param symbol Simbolul aferent monedei.
     * @param parityToEur Paritatea raportata la euro a monedei.
     * @return Intoarce moneda nou creata.
     */
    public Currency createCurrency(String name, String symbol, double parityToEur) {
        Currency currency =  new Currency(name, symbol, parityToEur);
        // adaug moneda in lista de monede
        currenciesList.add(currency);
        return currency;
    }

    /**
     * Metoda schimba moneda magazinului si actualizeaza lista de preturi.
     * @param currency Reprezinta moneda la care se va face conversia.
     * @throws CurrencyNotFoundException Este aruncata daca nu se gaseste
     * moneda in lista de monede.
     */
    public void changeCurrency(Currency currency) throws CurrencyNotFoundException {
        boolean exist = false;
        for (Currency currency1 : currenciesList) {
            if (currency1.getName().equals(currency.getName())) {
                exist = true;
                break;
            }
        }
        // daca gasesc moneda in lista de monede
        // actualizez pretul fiecarui produs cu noua moneda
        if (exist == true) {
            for (Product product : this.products) {
                product.recalculatePrice(this.currency, currency);
            }
            this.currency = currency;
        }
        else {
            throw new CurrencyNotFoundException();
        }
    }

    /**
     * Creaza un discount ce poate fii aplicat asupra produselor.
     * @param discountType Tipul de discount pe care il are un produs.
     * @param name Numele discountului.
     * @param value Valoarea discountului.
     * @return Intoarce discountul nou creat cu discountType, nume si valoare.
     */
    public Discount createDiscount(DiscountType discountType, String name, double value) {
        Discount discount = new Discount(discountType, name, value);
        // adaug discountul la lista de discounturi creata anterior
        discountsList.add(discount);
        return discount;
    }

    /**
     * Metoda care aplica discountul tuturor produselor de pe store.
     * @param discount Reprezinta discountul aplicat produselor.
     * @throws DiscountNotFoundException Exceptie in cazul in care
     * discountul nu se gaseste in lista de discounturi.
     * @throws NegativePriceException Exceptie in cazul in care
     * pretul produsului este negativ.
     */
    public void applyDiscount(Discount discount) throws DiscountNotFoundException, NegativePriceException {
        boolean exists = false;
        // verific daca discount apartine listei de discounturi folosindu-ma
        // de valoare si tipul de discount
        for (Discount discount1 : discountsList) {
            if (discount1.getDiscountType().equals(discount.getDiscountType())
                    && discount1.getValue() == discount.getValue()) {
                exists = true;
                break;
            }
        }
        // daca discountul exista in lista continui verificarile si daca nu
        // arunc exceptia DiscountNotFoundException()
        if (exists) {
            for (Product product : this.products) {
                // verific daca dupa aplicarea discountului obtin
                // un produs cu pretul negativ si arunc exceptia, daca da
                if (discount.getNewPrice(product.getPrice()) < 0) {
                    throw new NegativePriceException();
                }
            }
            // daca trece verificarea anterioara aplic discountul produselor
            for (Product product : products) {
                product.setPrice(discount.getNewPrice(product.getPrice()));
                // setez discountul produsului atunci cand il aplic pe acesta
                product.setDiscount(discount);
            }
        } else {
            throw  new DiscountNotFoundException();
        }
    }

    /**
     * Metoda care intoarce lista produselor ce apartin producatorului specificat.
     * @param manufacturer Reprezinta producatorul ale carui produse
     *                     vor fi adaugate in lista.
     * @return Intoarce o lista cu produse ce apartin producatorului primit
     * ca parametru.
     */
    public ArrayList<Product> getProductsByManufacturer(Manufacturer manufacturer) {
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : products) {
            if (product.getManufacturer().getName().equals(manufacturer.getName())) {
                productsList.add(product);
            }
        }
        return productsList;
    }

    /**
     * Metoda determina un cost total pentru produsele din ArrayList-ul
     * primit ca parametru.
     * @param product O lista de produse.
     * @return Intoarce pretul produselor din lista, pe care il obtinem
     * adunand ficare produs in functie de cantitea in care acesta se gaseste.
     */
    public double calculateTotal(ArrayList<Product> product) {
        double totalPrice = 0.0;
        for (Product product1 : product) {
            totalPrice += product1.getQuantity() * product1.getPrice();
        }
        return totalPrice;
    }
}
