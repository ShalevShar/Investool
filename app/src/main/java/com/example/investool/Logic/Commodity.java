package com.example.investool.Logic;

import java.util.ArrayList;
import java.util.List;

public class Commodity {
    private String name;
    private String unit;
    private double price;
    private double change;
    private double percentChange;

    public Commodity(String name, String unit, double price, double change, double percentChange) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.change = change;
        this.percentChange = percentChange;
    }


    public static List<Commodity> getHardcodedCommodities() {
        List<Commodity> commodities = new ArrayList<>();

        commodities.add(new Commodity("Crude Oil", "USD/Bbl", 77.977, 1.057, 1.37));
        commodities.add(new Commodity("Brent", "USD/Bbl", 82.866, 0.866, 1.06));
        commodities.add(new Commodity("Natural gas", "USD/MMBtu", 1.6744, 0.0936, -5.29));
        commodities.add(new Commodity("Gasoline", "USD/Gal", 2.3997, 0.0324, 1.37));
        commodities.add(new Commodity("Heating Oil", "USD/Gal", 2.9203, 0.0007, 0.02));
        commodities.add(new Commodity("Coal", "USD/T", 120.00, 0.60, -0.50));
        commodities.add(new Commodity("TTF Gas", "EUR/MWh", 25.40, 0.33, -1.28));
        commodities.add(new Commodity("UK Gas", "GBp/thm", 62.0700, 0.58, -0.93));

        return commodities;
    }
    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public double getPercentChange() {
        return percentChange;
    }
}
