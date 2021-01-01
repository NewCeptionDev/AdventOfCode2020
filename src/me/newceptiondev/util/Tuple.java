package me.newceptiondev.util;

import java.util.Objects;

public class Tuple<T, F> {

  private final T x;
  private final F y;

  public Tuple(T xValue, F yValue) {
    x = xValue;
    y = yValue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(final Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
    return Objects.equals(x, tuple.getX()) && Objects.equals(y, tuple.getY());
  }

  @Override
  public String toString() {
    return "Tuple{" + "x=" + x + ", y=" + y + '}';
  }

  public T getX() {
    return x;
  }

  public F getY() {
    return y;
  }
}
