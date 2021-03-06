import exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clasa in care am implementat lista de comenzi
 * pentru diferitele operatiuni ce se pot aplica asupra
 * produselor unui magazin.
 */
public class Main {
    public static void main(String[] args) {
        Store store = Store.getInstance();


        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String data = scan.nextLine();
            String[] dataSet = data.split(" ");
            try {
                switch (dataSet[0]) {
                    // listeaza currency-urile existente in store
                    case "listcurrencies":
                        for (Currency currency : Store.currenciesList) {
                            System.out.println(currency);
                        }
                        break;
                    // preia currency-ul curent de pe store
                    case "getstorecurrency":
                        System.out.println(store.getCurrency().getName());
                        break;
                    // adauga un currency pe store
                    case "addcurrency":
                        if (dataSet.length != 4) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String name = dataSet[1];
                        String symbol = dataSet[2];
                        double parityToEur = Double.parseDouble(dataSet[3]);
                        store.createCurrency(name, symbol, parityToEur);
                        break;
                    // incarca produsele dintr-un fisier CSV
                    case "loadcsv":
                        if (dataSet.length != 2) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String filename1 = dataSet[1];
                        store.readCSV(filename1);

                        break;
                    // salveaza produsele in fisier-ul dat ca parametru
                    case "savecsv":
                        if (dataSet.length != 2) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String filename = dataSet[1];
                        store.saveCSV(filename);
                        break;
                    // schimba currency-ul de pe store
                    case "setstorecurrency":
                        if (dataSet.length != 2) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String currencyName = dataSet[1];
                        try {
                            for (Currency currency : Store.currenciesList) {
                                if (currency.getName().equals(currencyName)) {
                                    store.changeCurrency(currency);
                                    break;
                                }
                            }
                        } catch (CurrencyNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    // actualizeaza paritatea unui currency fata de EUR
                    case "updateparity":
                        if (dataSet.length != 3) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String currencyName1 = dataSet[1];
                        double parityToEur1 = Double.parseDouble(dataSet[2]);
                        try {
                            boolean exist = false;
                            for (Currency currency : Store.currenciesList) {
                                if (currency.getName().equals(currencyName1)) {
                                    store.getCurrency().updateParity(parityToEur1);
                                    exist = true;
                                    break;
                                }
                            }
                            if (!exist) {
                                throw new CurrencyNotFoundException();
                            }
                        } catch (CurrencyNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    // listeaza produsele incarcate pe store
                    case "listproducts":
                        for (Product product : store.getProducts()) {
                            System.out.println(product.toString());
                        }
                        break;
                    // afiseaza detaliile unui produs dupa uniq_id
                    case "showproduct":
                        if (dataSet.length != 2) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String uniqueId = dataSet[1];
                        for (Product product1 : store.getProducts()) {
                            if (product1.getUniqueId().equals(uniqueId))
                                System.out.println(product1.toString());
                            break;
                        }
                        break;
                    // listeaza producatorii incarcati pe store
                    case "listmanufacturers":
                        for (Manufacturer manufacturer : store.getManufacturers()) {
                            System.out.println(manufacturer.getName());
                        }
                        break;
                    // listeaza produsele incarcate pe store care au manufacturer dat ca parametru
                    case "listproductsbymanufacturarer":
                        if (dataSet.length != 2) {
                            throw new WrongNumberParametersExcepion();
                        }
                        String manufacturerName = dataSet[1];
                        Manufacturer manufacturer = new Manufacturer(manufacturerName);
                        ArrayList<Product> productsList = store.getProductsByManufacturer(manufacturer);
                        for (Product product : productsList) {
                            System.out.println(product.toString());
                        }
                        break;
                    // listeaza discount-urile existente pe store
                    case "listdiscounts":
                        for (Discount discount : Store.discountsList) {
                            System.out.println(discount.toString());
                        }
                        break;
                    // adauga un discount pe store
                    case "addiscount":
                        try {
                            double value = Double.parseDouble(dataSet[2]);
                            String nameDiscount = "";
                            for (int i = 3; i < dataSet.length; i++) {
                                nameDiscount = nameDiscount.concat(dataSet[i]) + " ";
                            }
                            if (dataSet[1].equals("PERCENTAGE")) {
                                store.createDiscount(DiscountType.PERCENTAGE_DISCOUNT, nameDiscount, value);
                            } else if (dataSet[1].equals("FIXED")) {
                                store.createDiscount(DiscountType.FIXED_DISCOUNT, nameDiscount, value);
                            } else {
                                System.out.println("Discountul poate fi doar PERCENTAGE sau FIXED!");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    // aplica discount-ul specificat dupa tip si valoare
                    case "applydiscount":
                        if (dataSet.length != 3) {
                            throw new WrongNumberParametersExcepion();
                        }
                        double value2 = Double.parseDouble(dataSet[2]);
                        try {
                            if (dataSet[1].equals("PERCENTAGE")) {
                                store.applyDiscount(new Discount(DiscountType.PERCENTAGE_DISCOUNT, null, value2));
                            } else if (dataSet[1].equals("FIXED")) {
                                store.applyDiscount(new Discount(DiscountType.FIXED_DISCOUNT, null, value2));
                            } else {
                                System.out.println("Discountul poate fi doar PERCENTAGE sau FIXED!");
                            }
                        } catch (DiscountNotFoundException | NegativePriceException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    // calculeaza totalul produselor date ca parametru
                    case "calculatetotal":
                        ArrayList<Product> products = new ArrayList<>();
                        double price;
                        for (int i = 1; i < dataSet.length; i++) {
                            for (Product product : store.getProducts()) {
                                if (product.getUniqueId().equals(dataSet[i])) {
                                    products.add(product);
                                    break;
                                }
                            }
                        }
                        price = store.calculateTotal(products);
                        System.out.println(store.getCurrency().getSymbol() + "" + price);
                        break;
                    // opreste programul
                    case "exit":
                        exit = true;
                        break;
                    // opreste programul
                    case "quit":
                        exit = true;
                        break;
                    default:
                        break;
                }
            } catch (WrongNumberParametersExcepion | ArrayIndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
