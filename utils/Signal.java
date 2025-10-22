package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Signal<T> {
  private final List<Consumer<T>> subscribers = new ArrayList<>();

  public void connect(Consumer<T> callback) {
    subscribers.add(callback);
  }

  public void emit(T data) {
    for (Consumer<T> callback : subscribers) {
      callback.accept(data);
    }
  }
}
