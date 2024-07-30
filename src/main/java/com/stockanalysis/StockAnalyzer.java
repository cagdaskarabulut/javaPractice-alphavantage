package com.stockanalysis;

import org.json.JSONObject;

public class StockAnalyzer {

    public static String analyzeStockInfo(JSONObject data) {
        StringBuilder analysis = new StringBuilder();

        analysis.append("For Stock Fundamentally Calculation").append("\n");
        analysis.append("------------------------------------").append("\n");
        analysis.append("P/E Ratio: ").append(data.getString("PERatio")).append("\n");
        analysis.append("Forward P/E Ratio: ").append(data.getString("ForwardPE")).append("\n");
        analysis.append("PEG Ratio: ").append(data.getString("PEGRatio")).append("\n");
        analysis.append("Price/Sales Ratio: ").append(data.getString("PriceToSalesRatioTTM")).append("\n");
        analysis.append("Price/Book Ratio: ").append(data.getString("PriceToBookRatio")).append("\n");
        analysis.append("Dividend Yield: ").append(data.getString("DividendYield")).append("\n");
        analysis.append("EV/EBITDA: ").append(data.optString("EVToEBITDA", "N/A")).append("\n");
        analysis.append("Profit Margin: ").append(data.getString("ProfitMargin")).append("\n");
        analysis.append("Return on Equity (ROE): ").append(data.getString("ReturnOnEquityTTM")).append("\n");
        analysis.append("Revenue Growth: ").append(data.getString("QuarterlyRevenueGrowthYOY")).append("\n");
        analysis.append("Earnings Growth: ").append(data.getString("QuarterlyEarningsGrowthYOY")).append("\n");
        analysis.append("\n").append("\n");
        analysis.append("For Stock Worth Buying Calculation").append("\n");
        analysis.append("------------------------------------").append("\n");
        analysis.append("P/E Ratio: ").append(data.getString("PERatio")).append("\n");
        analysis.append("Forward P/E Ratio: ").append(data.getString("ForwardPE")).append("\n");
        analysis.append("PEG Ratio: ").append(data.getString("PEGRatio")).append("\n");
        analysis.append("PriceToSalesRatioTTM: ").append(data.getString("PriceToSalesRatioTTM")).append("\n");
        analysis.append("PriceToBookRatio: ").append(data.getString("PriceToBookRatio")).append("\n");
        analysis.append("DividendYield: ").append(data.getString("DividendYield")).append("\n");

        return analysis.toString();
    }

    public static int isStockGood(JSONObject data) {
        int totalParameter = 11;
        int score = 0;
        if (Double.parseDouble(data.getString("PERatio")) < 20) score++;
        if (Double.parseDouble(data.getString("ForwardPE")) < Double.parseDouble(data.getString("PERatio"))) score++;
        if (Double.parseDouble(data.getString("PEGRatio")) < 1.5) score++;
        if (Double.parseDouble(data.getString("PriceToSalesRatioTTM")) < 4) score++;
        if (Double.parseDouble(data.getString("PriceToBookRatio")) < 3) score++;
        if (Double.parseDouble(data.getString("DividendYield")) > 0.02) score++;
        if (data.optString("EVToEBITDA", "N/A").equals("-")) score++;
        if (Double.parseDouble(data.getString("ProfitMargin")) > 0.2) score++;
        if (Double.parseDouble(data.getString("ReturnOnEquityTTM")) > 0.15) score++;
        if (Double.parseDouble(data.getString("QuarterlyRevenueGrowthYOY")) > 0.05) score++;
        if (Double.parseDouble(data.getString("QuarterlyEarningsGrowthYOY")) > 0.05) score++;

        return score*100/totalParameter; //Yüzde karşılığını bul
    }

    public static int isStockWorthBuyingNow(JSONObject data) {
        int totalParameter = 7;
        int score = 0;
        double currentPERatio = Double.parseDouble(data.getString("PERatio"));
        double forwardPERatio = Double.parseDouble(data.getString("ForwardPE"));
        double pegRatio = Double.parseDouble(data.getString("PEGRatio"));
        double evEbitda = Double.parseDouble(data.optString("EVToEBITDA", "N/A"));
        double priceToSalesRatio = Double.parseDouble(data.getString("PriceToSalesRatioTTM"));
        double priceToBookRatio = Double.parseDouble(data.getString("PriceToBookRatio"));
        double dividendYield = Double.parseDouble(data.getString("DividendYield"));

        // Assuming threshold values for determining if the stock is cheap or expensive
        if (currentPERatio < 15) score++;
        if (forwardPERatio < currentPERatio) score++;
        if (pegRatio < 1.5) score++;
        if (evEbitda < 12) score++;
        if (priceToSalesRatio < 3) score++;
        if (priceToBookRatio < 2) score++;
        if (dividendYield > 0.02) score++;

        return score*100/totalParameter; //Yüzde karşılığını bul
    }

    public static String analyzeStock(JSONObject stockData) {
        // Sample JSON data

        // Analyze stock
        String analysis = analyzeStockInfo(stockData);
        System.out.println(analysis);

        // Check if stock is good
        int isGoodValue = isStockGood(stockData);
        boolean isGood = isGoodValue>= 8; // Criteria for a good stock;
        String isGoodResult ="Is the stock fundamentally good?  ---  Value: "+isGoodValue+"% &  Result:" + isGood;

        // Check if stock is worth buying now
        int worthBuyingValue = isStockWorthBuyingNow(stockData);
        boolean worthBuying = worthBuyingValue >= 4; // Criteria for being worth buying now
        String worthBuyingResult = "Is the stock worth buying now?  ---  Value: "+ worthBuyingValue + "% &  Result:"+ worthBuying;

        System.out.println("**************************");
        System.out.println(isGoodResult);
        System.out.println(worthBuyingResult);
        System.out.println("**************************");
        return isGoodResult + "/" + worthBuyingResult;

    }
}