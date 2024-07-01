package com.example.investool.Logic;

import java.util.ArrayList;
import java.util.List;

public class Currency {
    private String name;
    private double intradayBid;
    private double intradayAsk;
    private double intradayChange;
    private double intradayPercentChange;

    public Currency(String name, double intradayBid, double intradayAsk) {
        this.name = name;
        this.intradayBid = intradayBid;
        this.intradayAsk = intradayAsk;
        this.intradayChange = intradayAsk-intradayBid;
        this.intradayPercentChange = (1-(intradayAsk/intradayBid))*-100;
    }

    public static List<Currency> getHardcodedCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("USD/EUR", 1.13, 1.15));
        currencies.add(new Currency("USD/GBP", 0.73, 0.75));
        currencies.add(new Currency("USD/JPY", 114.55, 114.60));
        currencies.add(new Currency("USD/AUD", 1.36, 1.35));
        return currencies;
    }

    public String getName() {
        return name;
    }

    public double getIntradayBid() {
        return intradayBid;
    }

    public double getIntradayAsk() {
        return intradayAsk;
    }

    public double getIntradayChange() {
        return intradayChange;
    }

    public double getIntradayPercentChange() {
        return intradayPercentChange;
    }

    public String getFormattedIntradayBid() {
        return String.format("%.3f", intradayBid);
    }

    public String getFormattedIntradayAsk() {
        return String.format("%.3f", intradayAsk);
    }

    public String getFormattedIntradayChange() {
        return String.format("%.3f", intradayChange);
    }

    public String getFormattedIntradayPercentChange() {
        return String.format("%.3f", intradayPercentChange);
    }
}
