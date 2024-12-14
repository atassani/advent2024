package day05;

public class OrderRule {
    private final int first;
    private final int second;

    public OrderRule(String first, String second) {
        this.first = Integer.parseInt(first);
        this.second = Integer.parseInt(second);
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return first + "|" + second;
    }
}
