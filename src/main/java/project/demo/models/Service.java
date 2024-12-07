package project.demo.models;

public class Service {

    private String name;
    private String description;
    private String price; // Can be a single price or a range (e.g., "300" or "300 - 700")
    private String imagePath;
    private double minPrice; // Parsed minimum price
    private double maxPrice; // Parsed maximum price    private Service service;



    public Service(String name, String description, String price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;

        parsePrice(); // Parse the price into minPrice and maxPrice
    }

    // Parse the price into minPrice and maxPrice
    private void parsePrice() {
        if (price.contains("-")) {
            String[] prices = price.split("-");
            this.minPrice = Double.parseDouble(prices[0].trim());
            this.maxPrice = Double.parseDouble(prices[1].trim());
        } else {
            this.minPrice = Double.parseDouble(price.trim());
            this.maxPrice = this.minPrice; // Single price case
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }


    public String getImagePath() {
        return imagePath;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    // Formatted price (single or range)
    public String getFormattedPrice() {
        if (price.contains("-")) {
            // Handle range by formatting each side
            String[] prices = price.split("-");
            String formattedStart = "₱" + prices[0].trim();
            String formattedEnd = "₱" + prices[1].trim();
            return formattedStart + " - " + formattedEnd;
        } else {
            // Handle single price
            return "₱" + price;
        }
    }

    // Get price based on job complexity
    public double getPriceForComplexity(String jobComplexity) {
        switch (jobComplexity.toLowerCase()) {
            case "high":
                return maxPrice; // Use max price for high complexity
            case "medium":
                return (minPrice + maxPrice) / 2; // Average price for medium complexity
            case "low":
                return minPrice; // Use min price for low complexity
            default:
                throw new IllegalArgumentException("Invalid job complexity: " + jobComplexity);
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
