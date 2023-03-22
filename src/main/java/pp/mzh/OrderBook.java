package pp.mzh;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderBook {

  private static final Map<Long, Order> storage = new HashMap<>();

  public static void addOrder(Order order) {
    storage.put(order.getId(), order);
  }

  public static void removeOrder(long orderId) {
    storage.remove(orderId);
  }

  public static void updateSizeForOrderByOrderId(long orderId, long size) {
    Order existingOrder = storage.get(orderId);
    Order updatedOrder = new Order(orderId, existingOrder.getPrice(), existingOrder.getSide(), size);
    storage.put(orderId, updatedOrder);
  }

  public static double getPriceForLevel(char side, int level) {
    Set<Double> prices = new TreeSet<>();

    storage.values().forEach(
        order -> {
          if (side == order.getSide()) {
            prices.add(order.getPrice());
          }
        }
    );

    if (side == 'A') {
      return prices.toArray(new Double[storage.size()])[level - 1];
    } else {
      return prices.toArray(new Double[storage.size()])[prices.size() - level];
    }
  }

  public static long getTotalSizeForLevel(char side, int level) {
    Map<Double, Long> priceSizeMap = new TreeMap<>();

    storage.values().forEach(
        order -> {
          if (side == order.getSide()) {
            priceSizeMap.merge(order.getPrice(), order.getSize(), Long::sum);
          }
        }
    );

    if (side == 'A') {
      return priceSizeMap.get(priceSizeMap.keySet().toArray(new Double[storage.size()])[level - 1]);
    } else {
      return priceSizeMap.get(priceSizeMap.keySet().toArray(new Double[storage.size()])[priceSizeMap.size() - level]);
    }
  }

  public static List<Order> getOrdersBy(char side) {
    return storage.values().stream()
        .filter(by(side))
        .sorted(accordingTo(side))
        .collect(Collectors.toList());
  }

  private static Predicate<Order> by(final char side) {
    return order -> side == order.getSide();
  }

  private static Comparator<? super Order> accordingTo(final char side) {
    if (side == 'A') {
      return Comparator.comparing(Order::getPrice);
    } else {
      return Comparator.comparing(Order::getPrice, Comparator.reverseOrder());
    }
  }
}
