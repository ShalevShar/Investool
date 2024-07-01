package com.example.investool.Logic;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String name;
    private double open;
    private double close;
    private double change;
    private double changePercent;

    // Constructor
    public Stock(String name, double open, double close) {
        this.name = name;
        this.open = open;
        this.close = close;
        this.change = close-open;
        this.changePercent = ((1-(close/open))*-100);
    }

    // Hardcoded JSON data for stocks
    public static List<Stock> getHardcodedStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Dow", 38797.38, 38923.07));
        stocks.add(new Stock("S&P 500", 5021.84, 5017.17));
        stocks.add(new Stock("Nasdaq", 15942.55, 15894.43));
        stocks.add(new Stock("VIX", 14.12, 14.31));
        stocks.add(new Stock("Gold", 2042.60, 2052.2));
        stocks.add(new Stock("Oil", 77.70, 78.48));
        return stocks;
    }

    public String getName() {
        return name;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getChange() {
        return change;
    }
    public double getChangePercent() {
        return changePercent;
    }
    public String getFormattedOpen() {
        return String.format("%.3f", open);
    }

    public String getFormattedClose() {
        return String.format("%.3f", close);
    }

    public String getFormattedChange() {
        return String.format("%.3f", change);
    }

    public String getFormattedChangePercent() {
        return String.format("%.3f", changePercent);
    }
}
