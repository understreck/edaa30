package main.java.edaa30.fractal;

import main.java.edaa30.koch.Koch;
import main.java.edaa30.mountain.Mountain;
import main.java.edaa30.mountain.Point;

public class FractalApplication {
	public static void main(String[] args) {
		var fractals = new Fractal[2];
		fractals[0] = new Koch(300);
		fractals[1] = new Mountain(new Point(100, 500), new Point(300, 100), new Point(550, 400), 50.0);
	    new FractalView(fractals, "Fraktaler", 600, 600);
	}

}
