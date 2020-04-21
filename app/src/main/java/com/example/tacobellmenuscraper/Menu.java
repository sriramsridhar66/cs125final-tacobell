package com.example.tacobellmenuscraper;

import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Menu {

    private double moneyAmount;

    private int dollarMenuNumber;

    private boolean drinks;

    private HashMap<String, Double> menuMap = new HashMap<>();

    private String[] URLs = {"https://www.tacobell.com/food/tacos", "https://www.tacobell.com/food/burritos", "https://www.tacobell.com/food/quesadillas"};


    public Menu(final double setMoneyAmount, final int setDollarMenuNumber, final boolean setDrinks) {
        moneyAmount = setMoneyAmount;
        dollarMenuNumber = setDollarMenuNumber;
        drinks = setDrinks;
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
        for (HashMap.Entry<String, Double> entry : menuMap.entrySet()) {
            System.out.println(entry.getKey() + " is $" + entry.getValue());
        }
    }


    public HashMap<String, Double> getMenuMap() {
        return menuMap;
    }


    public HashMap<String, Double> getOrderMap() {
        menuMap = sortByAmount(menuMap);
        System.out.println("SORTED MAP:");
        for (HashMap.Entry<String, Double> entry : menuMap.entrySet()) {
            System.out.println(entry.getKey() + " is $" + entry.getValue());
        }
        return menuMap; //ADD LOGIC and return a new Map
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


    private HashMap<String, Double> getDollarMenu() {
        //if [dollarMenuNumber] is 0, return empty HashMap
        //randomly remove 6 - [dollarMenuNumber] dollar menu items from sortedMap
        return null;
    }

    private HashMap<String, Double> getComboDrinks() {
        //if drinks is false, return empty HashMap
        //if drinks is true, randomly return one combo
        return null;
    }

    private HashMap<String, Double> getRegularItems() {
        //do logic to choose regular items
        //return as a HashMap
        return null;
    }

    private HashMap<String, Double> getFreeItems() {
        //return free items as a Map
        //can use to test generated order activity layout since free items will always be in the generated order.
        return null;
    }
}
