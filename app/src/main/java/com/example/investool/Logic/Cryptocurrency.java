package com.example.investool.Logic;

import java.util.ArrayList;
import java.util.List;

public class Cryptocurrency {
    private String name;
    private double price;
    private double intradayChange;
    private double intrahourChange;
    private double intraweekChange;

    public Cryptocurrency(String name, double price, double intradayChange, double intrahourChange, double intraweekChange) {
        this.name = name;
        this.price = price;
        this.intradayChange = intradayChange;
        this.intrahourChange = intrahourChange;
        this.intraweekChange = intraweekChange;
    }

    public static List<Cryptocurrency> getHardcodedCryptocurrencies() {
        List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
        cryptocurrencies.add(new Cryptocurrency("Bitcoin BTC", 49813.59, 0.5, 0.3, 16.1));
        cryptocurrencies.add(new Cryptocurrency("Ethereum ETH", 2652.52, 0.2, -0.4, 0));
        cryptocurrencies.add(new Cryptocurrency("Tether USDT", 1.00, 0.0, 0.2, 0));
        cryptocurrencies.add(new Cryptocurrency("BNB BNB", 328.11, 0.4, 0.6, 0));
        cryptocurrencies.add(new Cryptocurrency("Solana SOL", 113.06, -0.5, -0.6, 0));
        cryptocurrencies.add(new Cryptocurrency("XRP XRP", 0.5246, 0.4, 0.8, 0));
        cryptocurrencies.add(new Cryptocurrency("USDC USDC", 1.00, 0.0, 0.2, 0));
        cryptocurrencies.add(new Cryptocurrency("Lido Staked Ether STETH", 2651.30, 0.2, 0.3, 0));
        return cryptocurrencies;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getIntradayChange() {
        return intradayChange;
    }

    public double getIntrahourChange() {
        return intrahourChange;
    }

    public double getIntraweekChange() {
        return intraweekChange;
    }
}
