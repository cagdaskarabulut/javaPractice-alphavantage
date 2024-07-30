
package com.stockanalysis;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) {
        try {
            String symbol = "MSFT";  // Change as needed
            JSONObject stockData = StockDataFetcher.fetchStockData(symbol);
            double latestPrice = StockDataFetcher.fetchLatestPrice(symbol);

            String analysis = StockAnalyzer.analyzeStock(stockData);
            StockAnalyzer.isStockGood(stockData);

            System.out.println("Analysis for " + symbol + ":");
            System.out.println(analysis);
            System.out.println("Latest price: " + latestPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
