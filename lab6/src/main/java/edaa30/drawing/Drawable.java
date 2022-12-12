package main.java.edaa30.drawing;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Drawable {
	
	protected Color color;

	protected Drawable(Color color) {
		this.color = color;
	}
	
	public abstract void draw(Graphics g);
}
