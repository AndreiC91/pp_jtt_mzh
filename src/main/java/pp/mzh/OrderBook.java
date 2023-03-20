package pp.mzh;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class OrderBook {

  private static final Map<Long, Order> storage = new HashMap<>();

  public static Map<Long, Order> getStorage() {
    return storage;
  }
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
    for (Order order : storage.values()) {
      if (side == order.getSide()) {
        prices.add(order.getPrice());
      }
    }
    return prices.toArray(new Double[storage.size()])[level-1];
  }

  public static long getTotalSizeForLevel(char side, int level) {
    Map<Double, Long> priceSizeMap = new TreeMap<>();
    for (Order order : storage.values()) {
      if (side == order.getSide()) {
        priceSizeMap.merge(order.getPrice(), order.getSize(), Long::sum);
      }
    }
    return priceSizeMap.get(priceSizeMap.keySet().toArray(new Double[storage.size()])[level-1]);
  }

  public static List<Order> getOrdersBy(char side) {
    return storage.values().stream()
        .filter(order -> side == order.getSide())
        .sorted(Comparator.comparing(Order::getPrice))
        .collect(Collectors.toList());
  }
}
