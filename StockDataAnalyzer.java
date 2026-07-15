import java.util.ArrayList;
import java.util.Arrays;

/**
 * StockDataAnalyzer.java
 * A data analysis program that processes 10 days of stock prices using arrays
 * and ArrayLists to perform statistical calculations.
 */
public class StockDataAnalyzer {

    public static void main(String[] args) {
        // 1. Initialize 10 days of float data (using an array)
        float[] stockPricesArray = {
                150.50f, 152.00f, 149.75f, 152.00f, 155.30f,
                158.10f, 157.00f, 152.00f, 160.25f, 161.50f
        };

        // Initialize the equivalent data in an ArrayList using a loop for clean setup
        ArrayList<Float> stockPricesList = new ArrayList<>();
        for (float price : stockPricesArray) {
            stockPricesList.add(price);
        }

        System.out.println("=========================================");
        System.out.println("       STOCK DATA ANALYSIS REPORT        ");
        System.out.println("=========================================");

        System.out.println("10-Day Stock Prices: " + Arrays.toString(stockPricesArray));
        System.out.println("-----------------------------------------");

        // Execute calculations
        float averagePrice = calculateAveragePrice(stockPricesArray);
        float maxPrice = findMaximumPrice(stockPricesArray);

        float target = 152.00f;
        int occurrenceCount = countOccurrences(stockPricesArray, target);

        ArrayList<Float> cumulativeSums = computeCumulativeSum(stockPricesList);

        // Display results
        System.out.printf("1. Average Stock Price: $%.2f%n", averagePrice);
        System.out.printf("2. Maximum Stock Price: $%.2f%n", maxPrice);
        System.out.println("3. Occurrences of $" + target + ": " + occurrenceCount + " times");
        System.out.println("4. Cumulative Sums: " + cumulativeSums);
        System.out.println("=========================================");
    }

    /**
     * Calculates and returns the average price of the stocks in the array.
     */
    public static float calculateAveragePrice(float[] prices) {
        float sum = 0;
        for (float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    /**
     * Finds and returns the maximum stock price from the array.
     */
    public static float findMaximumPrice(float[] prices) {
        float max = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > max) {
                max = prices[i];
            }
        }
        return max;
    }

    /**
     * Returns the number of times a target price occurs in the array.
     */
    public static int countOccurrences(float[] prices, float targetPrice) {
        int count = 0;
        for (float price : prices) {
            if (price == targetPrice) {
                count++;
            }
        }
        return count;
    }

    /**
     * Computes the cumulative sum of prices at each position and returns a new
     * ArrayList.
     */
    public static ArrayList<Float> computeCumulativeSum(ArrayList<Float> prices) {
        ArrayList<Float> cumulativeSumList = new ArrayList<>();
        float runningTotal = 0;

        for (float price : prices) {
            runningTotal += price;
            cumulativeSumList.add(runningTotal);
        }

        return cumulativeSumList;
    }
}