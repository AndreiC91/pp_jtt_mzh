package pp.mzh;

import java.util.stream.LongStream;

public class App {

  public static void main(String[] args) {
    //add orders
    LongStream.range(1, 21)
        .forEach(id -> {
          double price = (id % 10 + id / 10 % 10) / 10.0;
          char side = id % 2 == 1 ? 'A' : 'B';
          OrderBook.addOrder(new Order(id, price, side, id));
        });
    System.out.println(OrderBook.getStorage());

    //remove orders
    LongStream.range(1, 3)
        .forEach(id -> {
          OrderBook.removeOrder(id * 10);
        });
    System.out.println(OrderBook.getStorage());

    //modify size
    OrderBook.updateSizeForOrderByOrderId(5, 10);
    System.out.println(OrderBook.getStorage());

    //return best prices
    System.out.println(OrderBook.getPriceForLevel('A', 1));
    System.out.println(OrderBook.getPriceForLevel('A', 2));
    System.out.println(OrderBook.getPriceForLevel('B', 1));
    System.out.println(OrderBook.getPriceForLevel('B', 2));

    //get total size for level
    System.out.println(OrderBook.getTotalSizeForLevel('A', 1));
    System.out.println(OrderBook.getTotalSizeForLevel('A', 2));
    System.out.println(OrderBook.getTotalSizeForLevel('B', 1));
    System.out.println(OrderBook.getTotalSizeForLevel('B', 2));

    //get all order for side
    System.out.println(OrderBook.getOrdersBy('A'));
    System.out.println(OrderBook.getOrdersBy('B'));
  }

}
