public class OrderedPair implements Comparable<OrderedPair> {
    private double x;
    private double y;

    // Constructor
    public OrderedPair(double x_val, double y_val) {
        this.x = x_val;
        this.y = y_val;
    }

    // Getters for x and y
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Calculate the distance of the point from (0,0)
    private double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    // Compare based on the distance from (0, 0)
    @Override
    public int compareTo(OrderedPair other) {
        double thisDistance = this.distanceFromOrigin();
        double otherDistance = other.distanceFromOrigin();

        return Double.compare(thisDistance, otherDistance);
    }

    // ToString method to print the pair
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
