package com.example.tacobellmenuscraper;

import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, Double> menuMap = new HashMap<>();

    public void createMenu() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Document menuDocument = Jsoup.connect("https://www.tacobell.com/food/tacos").get();
        String title = menuDocument.title();
        System.out.println(title);
        Elements elements = menuDocument.select("div.product-details");
        for (Element element : elements) {
            String[] item = element.text().split("\\$");
            menuMap.put(item[0].trim(), Double.valueOf(item[1].split(" ")[0].trim()));
        }
        for (Map.Entry<String, Double> entry: menuMap.entrySet()) {
            System.out.println(entry.getKey() + " is $" + entry.getValue());
        }
    }

    public Map<String, Double> getMenuMap() {
        return menuMap;
    }
}
