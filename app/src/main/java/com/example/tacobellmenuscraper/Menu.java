package com.example.tacobellmenuscraper;

import android.content.Context;
import android.content.res.Resources;
import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private double moneyAmount;

    private int dollarMenuNumber;

    private boolean drinks;

    private Map<String, Double> menuMap = new HashMap<>();

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

        for (Map.Entry<String, Double> entry: menuMap.entrySet()) {
            System.out.println(entry.getKey() + " is $" + entry.getValue());
        }
    }

    public Map<String, Double> getMenuMap() {
        return menuMap;
    }

    public Map<String, Double> getOrderMap(double money, int dollarMenu) {
        return menuMap; //ADD LOGIC and return a new Map
    }
}
