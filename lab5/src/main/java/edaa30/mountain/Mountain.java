package main.java.edaa30.mountain;

import main.java.edaa30.fractal.Fractal;
import main.java.edaa30.fractal.TurtleGraphics;

public class Mountain extends Fractal {
    private final Point m_a, m_b, m_c;

    /** Creates an object that handles Mountain's fractal.
     * @param a is a triangle point
     * @param b is a triangle point
     * @param c is a triangle point
     */
    public Mountain(Point a, Point b, Point c) {
        super();
        m_a = a;
        m_b = b;
        m_c = c;
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
        triangle(turtle, order, m_a, m_b, m_c);
    }

    private Point half(Point from, Point to) {
        int x = from.getX() + (to.getX() - from.getX()) / 2;
        int y = from.getY() + (to.getY() - from.getY()) / 2;
        return new Point(x, y);
    }

    /*
     * Reursive method: Draws a recursive line of the triangle.
     */
    private void triangle(TurtleGraphics turtle, int order, Point a, Point b, Point c) {
        if(order == 0) {
            turtle.moveTo(a.getX(), a.getY());

            turtle.penDown();
            turtle.forwardTo(b.getX(), b.getY());
            turtle.forwardTo(c.getX(), c.getY());
            turtle.forwardTo(a.getX(), a.getY());
            return;
        }

        var aTob = half(a, b);
        var aToc = half(a, c);
        var bToc = half(b, c);

        triangle(turtle, order - 1, a, aTob, aToc);
        triangle(turtle, order - 1, aTob, b, bToc);
        triangle(turtle, order - 1, bToc, c, aToc);
        triangle(turtle, order - 1, aTob, bToc, aToc);
    }

}
