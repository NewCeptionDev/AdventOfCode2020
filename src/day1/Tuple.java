package day1;

public class Tuple<T, F> {

    private final T x;
    private final F y;

    public Tuple(T x, F y){
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public F getY() {
        return y;
    }
}
