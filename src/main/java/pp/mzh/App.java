package pp.mzh;

import java.util.List;
import java.util.stream.LongStream;

public class App {

  public static void main(String[] args) {
    //add orders
    LongStream.range(1, 31)
        .forEach(id -> {
          double price = (id % 10 + id / 10 % 10) / 10.0;
          char side = id % 2 == 1 ? 'A' : 'B';
          OrderBook.addOrder(new Order(id, price, side, id));
        });
    System.out.println("All 20 orders added");
    nicePrint();

    //remove orders
    LongStream.range(1, 4)
        .forEach(id -> {
          OrderBook.removeOrder(id * 10);
        });
    System.out.println("Removed 2 orders with id 10 and 20");
    nicePrint();

    //modify size
    OrderBook.updateSizeForOrderByOrderId(5, 10);
    System.out.println("Updated order with id 5 to size 10");
    nicePrint();

    //return best prices
    System.out.println("Best ask price: " + OrderBook.getPriceForLevel('A', 1));
    System.out.println("2nd best ask price: " + OrderBook.getPriceForLevel('A', 2));
    System.out.println("Best bid price: " + OrderBook.getPriceForLevel('B', 1));
    System.out.println("2nd best bid price: " + OrderBook.getPriceForLevel('B', 2));

    //get total size for level
    System.out.println("Total size for ask level 1: " + OrderBook.getTotalSizeForLevel('A', 1));
    System.out.println("Total size for ask level 2: " + OrderBook.getTotalSizeForLevel('A', 2));
    System.out.println("Total size for bid level 1: " + OrderBook.getTotalSizeForLevel('B', 1));
    System.out.println("Total size for bid level 2: " + OrderBook.getTotalSizeForLevel('B', 2));

    //get all order for side - already used by nicePrint()
    System.out.println("--------------------------------------------------");
    System.out.println("Ask orders ordered by level / price ascending and time / id (lower first, earlier created order):");
    OrderBook.getOrdersBy('A').forEach(System.out::println);
    System.out.println("--------------------------------------------------");
    System.out.println("Bid orders ordered by level / price descending and time / id (lower first, earlier created order):");
    OrderBook.getOrdersBy('B').forEach(System.out::println);
  }

  private static void nicePrint() {
    System.out.println("--------------------------------------------------");
    List<Order> askOrders = OrderBook.getOrdersBy('A');
    List<Order> bidOrders = OrderBook.getOrdersBy('B');
    for (int i = 0; i < Math.max(askOrders.size(), bidOrders.size()); i++) {
      final Order bidOrder = bidOrders.size() > i ? bidOrders.get(i) : null;
      final Order askOrder = askOrders.size() > i ? askOrders.get(i) : null;

      nicePrintFor(i, bidOrder, askOrder);
    }
    System.out.println("--------------------------------------------------");
  }

  private static void nicePrintFor(int i, Order bidOrder, Order askOrder) {
    System.out.printf("Level %-2s | %-15s | %s%n", i + 1, bidOrder, askOrder);
  }
}
