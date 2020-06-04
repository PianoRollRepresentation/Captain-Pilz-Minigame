package de.tobias.game.fight;

import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;

public class Hitpoints {

	private int x;
	private int y;
	private int pixels = 12;
	private int colour = Colours.get(-1, -1, -1, 252);
	
	public void render(Screen screen) {
		screen.render(x, y, 32 * 16 + 1, Colours.get(-1, 000, 555, -1), 0, 4, 1);
		for (int i = 0; i < pixels; i++)
			screen.render(x + i, y + 8, 32 * 16 + 1, colour, 0, 3, 1);
	}

	public int getPixels() {
		return pixels;
	}

	public void setPixels(int pixels) {
		this.pixels = pixels;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
