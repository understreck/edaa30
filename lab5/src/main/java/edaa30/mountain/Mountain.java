package main.java.edaa30.mountain;

import main.java.edaa30.fractal.Fractal;
import main.java.edaa30.fractal.TurtleGraphics;

import java.util.HashMap;
import java.util.Map;

public class Mountain extends Fractal {
    private class Side {
        public final Point from, to;

        public Side(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return from.hashCode() ^ to.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if(o == null) return false;
            if(o instanceof Side) {
                var s = (Side)o;
                if(s.from.equals(from)) return s.to.equals(to);
                if(s.from.equals(to)) return s.to.equals(from);
            }

            return false;
        }


    }

    private final Point m_a, m_b, m_c;
    private final double m_deviation;

    private Map<Side, Point> m_halves = new HashMap<>();

    /** Creates an object that handles Mountain's fractal.
     * @param a is a triangle point
     * @param b is a triangle point
     * @param c is a triangle point
     */
    public Mountain(Point a, Point b, Point c, double deviation) {
        super();
        m_a = a;
        m_b = b;
        m_c = c;
        m_deviation = deviation;
    }

    /**
     * Returns the title.
     * @return the title
     */
    @Override
    public String getTitle() {
        return "Mountain fractals";
    }

    /** Draws the fractal.  
     * @param turtle the turtle graphic object
     */
    @Override
    public void draw(TurtleGraphics turtle) {
        triangle(turtle, order, m_a, m_b, m_c, m_deviation);
    }

    private Point half(Point from, Point to, double deviation) {
        var side = new Side(from, to);
        if(m_halves.containsKey(side)) {
            var p = m_halves.get(side);
            m_halves.remove(side);
            return p;
        }

        int x = from.getX() + (to.getX() - from.getX()) / 2;
        int y = from.getY() + (to.getY() - from.getY()) / 2 + (int)Math.round(randFunc(deviation));
        var point = new Point(x, y);
        m_halves.put(side, point);
        return point;
    }

    public static double randFunc(double dev) {
        double t = dev * Math.sqrt(-2 * Math.log(Math.random()));
        if (Math.random() < 0.5) {
            t = -t;
        }
        return t;
    }

    /*
     * Recursive method: Draws a recursive line of the triangle.
     */
    private void triangle(TurtleGraphics turtle, int order, Point a, Point b, Point c, double deviation) {
        if(order == 0) {
            turtle.moveTo(a.getX(), a.getY());

            turtle.penDown();
            turtle.forwardTo(b.getX(), b.getY());
            turtle.forwardTo(c.getX(), c.getY());
            turtle.forwardTo(a.getX(), a.getY());
            return;
        }

        var aTob = half(a, b, deviation);
        var bToc = half(b, c, deviation);
        var cToa = half(c, a, deviation);

        triangle(turtle, order - 1, a, aTob, cToa, deviation / 2);
        triangle(turtle, order - 1, aTob, b, bToc, deviation / 2);
        triangle(turtle, order - 1, aTob, bToc, cToa, deviation / 2);
        triangle(turtle, order - 1, cToa, bToc, c, deviation / 2);
    }

}
