package com.example.tacobellmenuscraper;

import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Menu {

    private double moneyAmount;

    private int dollarMenuNumber;

    private boolean drinks;

    private boolean variety;

    private HashMap<String, Double> menuMap = new HashMap<>();

    private String[] URLs = {"https://www.tacobell.com/food/tacos", "https://www.tacobell.com/food/burritos", "https://www.tacobell.com/food/quesadillas"};


    public Menu(final double setMoneyAmount, final int setDollarMenuNumber, final boolean setDrinks, final boolean setVariety) {
        moneyAmount = setMoneyAmount;
        dollarMenuNumber = setDollarMenuNumber;
        drinks = setDrinks;
        variety = setVariety;
    }


    public void loadMenu() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        for (int i = 0; i < URLs.length; i++) {
            Document menuDocument = Jsoup.connect(URLs[i]).get();
            Elements elements = menuDocument.select("div.product-details");
            for (Element element : elements) {
                String[] item = element.text().split("\\$");
                menuMap.put(item[0].trim(), Double.valueOf(item[1].split(" ")[0].trim()));
            }
        }
    }


    public HashMap<String, Double> getMenuMap() {
        return menuMap;
    }


    public void sortMap() {
        menuMap = sortByAmount(menuMap);
        System.out.println("PRINTING MENU MAP AFTER SORT");
        printMap(menuMap);
    }


    private static HashMap<String, Double> sortByAmount(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public HashMap<String, Double> getDollarMenu() {
        HashMap<String, Double> temp = new HashMap<>();
        HashMap<String, Double> toReturn = new HashMap<>();

        if (dollarMenuNumber == 0 || moneyAmount < 1) {
            return toReturn;
        }

        for (Map.Entry<String, Double> entry : menuMap.entrySet()) {
            if (entry.getValue() == 1.00) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }

        ArrayList<Integer> chosen = new ArrayList<>(6);

        Random random = new Random();

        for (int i = 0; i < dollarMenuNumber; i++) {
            int randomEntry = random.nextInt(temp.size());
            if(chosen.contains(randomEntry)) {
                i--;
                continue;
            }
            chosen.add(randomEntry);
        }

        Object[] itemNames = temp.keySet().toArray();
        Object[] itemPrices = temp.values().toArray();

        for (int i = 0; i < dollarMenuNumber; i++) {
            if (moneyAmount > (Double) itemPrices[i]) {
                toReturn.put((String) itemNames[i], (Double) itemPrices[i]);
                moneyAmount -= (Double) itemPrices[i];
            } else {
                return toReturn;
            }
        }

        return toReturn;
    }


    public HashMap<String, Double> getComboDrinks() {
        HashMap<String, Double> temp = new HashMap<>();
        HashMap<String, Double> toReturn = new HashMap<>();

        if (!drinks || moneyAmount < 1.99) {
            return temp;
        }

        if (moneyAmount < 5.79) {
            temp.put("Fountain Drink", 1.99);
            return temp;
        }

        for (Map.Entry<String, Double> entry : menuMap.entrySet()) {
            if (entry.getKey().contains("Combo")) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }

        Random random = new Random();
        int randomEntry = random.nextInt(temp.size());

        Object[] itemNames = temp.keySet().toArray();
        Object[] itemPrices = temp.values().toArray();

        if ((Double) itemPrices[randomEntry] > moneyAmount) {
            return getComboDrinks();
        }

        toReturn.put((String) itemNames[randomEntry], (Double) itemPrices[randomEntry]);
        moneyAmount -= (Double) itemPrices[randomEntry];

        return toReturn;
    }


    public HashMap<String, Double> getRegularItems() {
        HashMap<String, Double> temp = new HashMap<>();
        HashMap<String, Double> toReturn = new HashMap<>();

        for (Map.Entry<String, Double> entry : menuMap.entrySet()) {
            if (!(entry.getValue() == 0) && !(entry.getKey().contains("Combo"))) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }

        if (variety) {
            Random random = new Random();

            for (int counter = 0; counter < 30; counter++) {
                int randomEntry = random.nextInt(temp.size());
                Object[] itemNames = temp.keySet().toArray();
                Object[] itemPrices = temp.values().toArray();
                if (moneyAmount > (Double) itemPrices[randomEntry]) {
                    toReturn.put((String) itemNames[randomEntry], (Double) itemPrices[randomEntry]);
                    moneyAmount -= (Double) itemPrices[randomEntry];
                }
                temp.remove(itemNames[randomEntry]);
            }
        } else {
            for (Map.Entry<String, Double> entry : temp.entrySet()) {
                if (moneyAmount > entry.getValue() && entry.getValue() != 1) {
                    toReturn.put(entry.getKey(), entry.getValue());
                    moneyAmount -= entry.getValue();
                }
            }
        }

        return toReturn;
    }


    public HashMap<String, Double> getFreeItems() {
        HashMap<String, Double> toReturn = new HashMap<>();

        for (HashMap.Entry<String, Double> entry : menuMap.entrySet()) {
            if (entry.getValue() == 0) {
                toReturn.put(entry.getKey(), entry.getValue());
            } else {
                return toReturn;
            }
        }
        return toReturn;
    }


    private void printMap(HashMap<String, Double> hashMap) {
        for (HashMap.Entry<String, Double> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " is $" + entry.getValue());
        }
    }
}
