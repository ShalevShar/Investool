package com.example.investool.Network;

import java.util.Map;

public interface StockCallback {
    void onStocksRetrieved(Map<String, Map<String, Object>> stockData);

}
