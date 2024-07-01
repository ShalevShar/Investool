package com.example.investool.Network;

import java.util.Map;

public interface CurrencyCallback {
    void onCurrencyRetrieved(Map<String, Double> currencyData);
}
