import java.util.*;

class Stock {
    String symbol, name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol; this.name = name; this.price = price;
    }
}

class Transaction {
    String symbol; int qty; double price; boolean isBuy;
    Transaction(String symbol, int qty, double price, boolean isBuy) {
        this.symbol = symbol; this.qty = qty; this.price = price; this.isBuy = isBuy;
    }
}

class User {
    String name; double balance = 100000;
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> history = new ArrayList<>();

    User(String name) { this.name = name; }

    void buy(Stock s, int q) {
        double cost = s.price * q;
        if (cost > balance) System.out.println("❌ Insufficient balance");
        else {
            balance -= cost;
            holdings.put(s.symbol, holdings.getOrDefault(s.symbol, 0) + q);
            history.add(new Transaction(s.symbol, q, s.price, true));
            System.out.println("✅ Bought " + q + " of " + s.symbol);
        }
    }

    void sell(Stock s, int q) {
        int owned = holdings.getOrDefault(s.symbol, 0);
        if (q > owned) System.out.println("❌ Not enough shares");
        else {
            balance += s.price * q;
            holdings.put(s.symbol, owned - q);
            history.add(new Transaction(s.symbol, q, s.price, false));
            System.out.println("✅ Sold " + q + " of " + s.symbol);
        }
    }

    void showPortfolio(Map<String, Stock> market) {
        double total = balance;
        System.out.println("\n📊 Portfolio:");
        for (String sym : holdings.keySet()) {
            int qty = holdings.get(sym);
            double price = market.get(sym).price;
            double val = qty * price;
            total += val;
            System.out.printf("%s: %d x ₹%.2f = ₹%.2f\n", sym, qty, price, val);
        }
        System.out.printf("💵 Balance: ₹%.2f | 📈 Total Value: ₹%.2f\n", balance, total);
    }

    void showHistory() {
        System.out.println("\n📄 History:");
        for (Transaction t : history) {
            String type = t.isBuy ? "BUY" : "SELL";
            System.out.printf("%s %d of %s @ ₹%.2f\n", type, t.qty, t.symbol, t.price);
        }
    }
}

public class StockTradingPlatform {
    static Scanner sc = new Scanner(System.in);
    static Map<String, Stock> market = new HashMap<>();

    public static void main(String[] args) {
        setupMarket();
        System.out.print("Enter your name: ");
        User user = new User(sc.nextLine());

        while (true) {
            System.out.print("""
                
                📌 Menu:
                1. View Market
                2. Buy Stock
                3. Sell Stock
                4. View Portfolio
                5. View History
                6. Exit
                Choose: """);
            int ch = sc.nextInt(); sc.nextLine();

            switch (ch) {
                case 1 -> viewMarket();
                case 2 -> trade(user, true);
                case 3 -> trade(user, false);
                case 4 -> user.showPortfolio(market);
                case 5 -> user.showHistory();
                case 6 -> { System.out.println("👋 Exiting..."); return; }
                default -> System.out.println("❗ Invalid");
            }
            updatePrices();
        }
    }

    static void setupMarket() {
        market.put("TCS", new Stock("TCS", "Tata", 3400));
        market.put("INFY", new Stock("INFY", "Infosys", 1500));
        market.put("RELI", new Stock("RELI", "Reliance", 2800));
        market.put("HDFC", new Stock("HDFC", "HDFC Bank", 1650));
    }

    static void viewMarket() {
        System.out.println("\n📈 Market:");
        for (Stock s : market.values())
            System.out.printf("%s (%s): ₹%.2f\n", s.name, s.symbol, s.price);
    }

    static void trade(User u, boolean buy) {
        viewMarket();
        System.out.print("Enter symbol: ");
        String sym = sc.nextLine().toUpperCase();
        if (!market.containsKey(sym)) {
            System.out.println("❌ Invalid stock");
            return;
        }
        System.out.print("Quantity: ");
        int qty = sc.nextInt(); sc.nextLine();
        if (buy) u.buy(market.get(sym), qty);
        else u.sell(market.get(sym), qty);
    }

    static void updatePrices() {
        Random r = new Random();
        for (Stock s : market.values()) {
            double change = (r.nextDouble() * 4 - 2); // -2% to +2%
            s.price = Math.round(s.price * (1 + change / 100) * 100.0) / 100.0;
        }
    }
}