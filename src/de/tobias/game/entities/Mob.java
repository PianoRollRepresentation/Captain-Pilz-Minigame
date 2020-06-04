package de.tobias.game.entities;

import de.tobias.game.Game;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;
import de.tobias.game.tiles.Tile;
import de.tobias.game.level.Level;

/**
 * Bildet das Grundgerüst für ein bewegbares Wesen im Spiel. In der Klasse wird
 * die komplette Bewegung und das Schwimmen eines Mobs implementiert, welche auf
 * dem Verfahren Interpolation basiert. Zudem ist eine Methode vorhanden, die
 * bei Kollidierung mit einem SolidTile dafür sorgt, das der Mob nicht weiter
 * gehen kann.
 * 
 * @author Tobias
 *
 */
public abstract class Mob extends Entity {

	protected String name;
	
	protected int speedFactor;
	protected int numSteps = 0;
	protected int dir = 1;
	protected int scale = 1;
	protected int flip = 0;
	protected int tickCount = 0;
	protected int colour;
	
	protected boolean moving = false;
	protected boolean attacking = false;
	protected boolean swimming = false;
	protected boolean[] completePos = new boolean[4];

	/**
	 * Erzeugt einen neuen Mob. Die x- und y-Koordinate des Mob wird direkt der
	 * kachelbasierten Spiellogik angepasst, das heißt wenn man beispielsweise
	 * den Mob an der Stelle x, y = 3 positioniert, wird er auf der 4. Kachel
	 * von oben und links positioniert. Zusätzlich wird bei der Zuweisung der
	 * Rand des Frames zu den jeweiligen Koordinaten addiert.
	 * 
	 * @param levels
	 *            Das Level in das der Mob eingefügt wird
	 * @param name
	 *            Name des Mob
	 * @param x
	 *            x-Koordinate des Mob im Frame
	 * @param y
	 *            y-Koordinate des Mob im Frame
	 * @param speed
	 *            Geschwindigkeit, mit der der Mob sich bewegt
	 * @param colour
	 *            Farbe des Mob
	 */
	public Mob(Level level, String name, int x, int y, int speed, int colour) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speedFactor = speed;
		this.colour = colour;
		this.x = this.x * Game.TILE_LENGTH;
		this.y = this.y * Game.TILE_LENGTH;
	}

	/**
	 * Die Methode ist für die Bewegung des Mob und die Bewegunsrichtung
	 * zuständig. Der Integer movingDir gibt Bewegunsgrichtung folgendermaßen
	 * an: movingDir = 0 oben movingDir = 1 unten movingDir = 2 links movingDir
	 * = 3 rechts. Zudem wird die x- und y-Koordinate immer neu berechnet, dazu
	 * wird einfach immer xa, bzw ya addiert und jeweils mit dem Faktor
	 * speedFactor multipliziert.
	 *
	 * @param xa
	 *            Geschwindigkeit in x-Richtung
	 * @param ya
	 *            Geschwindigkeit in y-Richtung
	 */
	public void move(int xa, int ya) {

		/*
		 * Die erste if-Anweisung sorgt dafür, dass bei Bewegung in x- und
		 * y-Richtung gleichzeitig, die Methode zweimal rekursiv aufgerufen
		 * wird, jedoch beim ersten mal mit ya = 0 und beim zweiten mal mit xa =
		 * 0. Somit ist sichergestellt, dass der Mob nicht eine
		 * Bewegungsrichtung in y-Richtung und gleichzeitig eine in x-Richtung
		 * hat. In der ersten if-Anweisung wird zudem numStep um 1 verkleinert,
		 * da die Methode ja zweimal aufgerufen wird somit numStep immer um 2
		 * vergrößert werden würde. In der zweiten if-Anweisung werden die
		 * Bewegungsrichtung festgelegt: movingDir = 0 oben movingDir = 1 unten
		 * movingDir = 2 links movingDir = 3 rechts. Zudem wird die x- und
		 * y-Koordinate immer neu berechnet, dazu wird einfach immer xa, bzw ya
		 * addiert und jeweils mit dem Faktor speedFactor multipliziert.
		 */
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			if (ya < 0)
				dir = 0;
			if (ya > 0)
				dir = 1;
			if (xa < 0)
				dir = 2;
			if (xa > 0)
				dir = 3;

			x += xa * speedFactor;
			y += ya * speedFactor;
		}
	}

	/**
	 * In der Updatemethode wird die Interpolation, das kachelbasierte Gehen der
	 * Figur, realisiert. Die Figur kann nur genau auf den Kacheln drauf stehen,
	 * niemals dazwischen und kann sich somit auch nur 16, 32, 48, etc. Pixel in
	 * eine Richtung bewegen, er kann in diesem Fall also beispielsweise nicht
	 * nur 11 Pixel in eine Richtung gehen. Zusätzlich kann der Charakter, wenn
	 * er sich im Wasser befindet schwimmen, dies wird auch in dieser Methode
	 * realisiert.
	 */
	@Override
	public void tick() {

		/*
		 * Die Konstante INTERPOLATION_WIDTH legt fest, wie viele Pixel die
		 * Figur gehen muss, damit sie wieder stehen bleibt. Der Charakter
		 * bewegt sich immer Vielfache von INTERPOLATION_WIDTH Pixeln weit, also
		 * wenn die Konstante 16 Pixel groß ist, kann sich der Charakter auch
		 * immer nur 16, 32, 48, etc. Pixel in alle Richtungen bewegen, er kann
		 * in diesem Fall also beispielsweise nicht nur 11 Pixel in eine
		 * Richtung gehen. Somit sind die Kacheln die Stützpunkte, zwischen
		 * denen in diesem Fall linear interpoliert wird. Das Array completePos
		 * hat den Wert true wenn eine Bewegung in die Richtung des Index
		 * abgeschlossen ist. Zusätzlich wird am Ende der Methode der boolean
		 * isSwimming auf true gesetzt, wenn sich der Mob auf einer Wasserkachel
		 * befindet, ansonsten wird isSwimming auf false gesetzt.
		 */
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for (int i = 0; i < completePos.length; i++) {
			if (y % Game.TILE_LENGTH == 0 && x % Game.TILE_LENGTH == 0) {
				completePos[i] = true;
			} else if (dir == i) {
				completePos[i] = false;
			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if (level.getTile(this.x >> 4, this.y >> 4).getId() == 3) {
			swimming = true;
		}
		if (swimming && level.getTile(this.x >> 4, this.y >> 4).getId() != 3) {
			swimming = false;
		}
	}

	/**
	 * Methode, welche für das Zeichnen und die Animation des Mobs zuständig
	 * ist.
	 * 
	 * @param screen
	 *            Screen in dem der Mob gezeichnet wird
	 * @param spriteLine
	 *            y-Koordinate des SpriteSheet
	 */
	@Override
	public void render(Screen screen, int spriteLine) {

		/*
		 * Die Variable walkingSpeed gibt die Animationsgeschwindigkeit an.
		 * flipBottom hat den Wert 0 oder 1, bei 1 wird der Mob gespielt, bei 0
		 * passiert nichts. Im Nächsten Teil der Methode, werden für die
		 * jeweiligen Bewegunsgrichtungen, dem Mob die entsprechenden Kacheln zu
		 * gewiesen, das heißt die Variable xTile erhöht, bzw. verringert sich
		 * dann. Im weiteren Verlauf wird der x und y Wert des Mob genau in die
		 * Mitte der Kachel gesetzt, dies wird mit den Variablen modifier,
		 * xOffset und yOffset erreicht. Dannach wird die Schwimmanimation
		 * realisiert; Der Mob bewegt sich immer leicht beim schwimmen. Am Ende
		 * wird der Mob gezeichnet, wenn er schwimmt wird nur der Kopf
		 * gezeichnet und der Körper werden durch Wasserwellen ersetzt.
		 */
		int xTile = 0;
		int yTile = spriteLine;
		int walkingSpeed = 4;
		int flipBottom = (numSteps >> walkingSpeed) & 1;

		if (dir == 1) {
			if (attacking) {
				xTile += 7;
			} else {
				if (moving) {
					xTile += 1;
				} else {
					xTile += 5;
				}
			}
		} else if (dir == 0) {
			if (attacking) {
				xTile += 6;
			} else {
				if (moving) {
					xTile += 0;
				} else {
					xTile += 6;
				}
			}
		} else if (dir > 1) {
			if (moving) {
				if (flip < walkingSpeed * 3) {
					xTile += 2;
				} else if (flip < walkingSpeed * 6) {
					xTile += 3;
				} else if (flip < walkingSpeed * 9) {
					xTile += 2;
				} else {
					xTile += 4;
				}
				flip++;
				if (flip == walkingSpeed * 12)
					flip = 0;

			} else {
				xTile += 2;
			}
			flipBottom = (dir - 1) % 2;
		}

		int modifier = scale << 4;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;

		if (swimming) {
			int waterColour = 0;
			yOffset += 4;
			if (tickCount % 60 < 15) {
				waterColour = Colours.get(-1, -1, 225, -1);
			} else if (15 <= tickCount % 60 && tickCount % 60 < 30) {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			} else if (30 <= tickCount % 60 && tickCount % 60 < 45) {
				waterColour = Colours.get(-1, 115, -1, 255);
			} else {
				yOffset -= 1;
				waterColour = Colours.get(-1, 225, 115, -1);
			}
			screen.render(xOffset + 8, yOffset + 15, 8 + yTile * 64, waterColour, 0x00, 4, scale);
			screen.render(xOffset + 8, yOffset + 11, 7 + yTile * 64, colour, 0x00, 4, scale);
		} else {
			screen.render(xOffset + 8, yOffset + 12, xTile + yTile * 64, colour, flipBottom, 4, scale);
		}
	}

	/**
	 * Methode, welche für die Kollidierung eines Mob mit einem SolidTile
	 * zuständig ist.
	 * 
	 * @see #isSolidTile(int, int, int, int)
	 * @param xa
	 *            x-Koordinate des Mob
	 * @param ya
	 *            y-Koordinate des Mob
	 * @return bei Kollidierung wird true zurückgegeben, sonst false
	 */
	public boolean hasCollided(int xa, int ya) {

		/*
		 * Die Variablen xMin, xMax, yMin und yMax geben die größe der Box an
		 * welche man als Mob nicht betreten kann.
		 */
		int xMin = 0;
		int xMax = 15;
		int yMin = 0;
		int yMax = 15;
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode, welche bei den eingegebenen Argumenten überprüft, ob eine dieser
	 * Stelle ein SolidTile vorhanden ist. Als Bezugspunkt wird hier nicht die
	 * linke, obere Ecke des Frames verwendet, sondern der Mob selber.
	 * 
	 * @see Tile#isSolid()
	 * @see Level#getTile(int, int)
	 * @param xa
	 *            x-Koordinate der Kachel
	 * @param ya
	 *            y-Koordinate der Kachel
	 * @param x
	 *            Bezugs-x-Koordinate
	 * @param y
	 *            Bezugs-y-Koordinate
	 * @return wenn es ein SolidTile ist wird true zurückgegeben, sonst false
	 */
	public boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null) {
			return false;
		}
		if (!level.stop) {
			Tile lastTile = level.getTile((this.x + x) >> 4, (this.y + y) >> 4);
			Tile newTile = level.getTile((this.x + x + xa) >> 4, (this.y + y + ya) >> 4);
			if (!lastTile.equals(newTile) && newTile.isSolid()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter für den String name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter für den int dir
	 * 
	 * @return movingDir
	 */
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * Getter für den boolean isSwimming
	 * 
	 * @return isSwimming
	 */
	public boolean getSwimming() {
		return swimming;
	}

	/**
	 * Setter für den boolean isSwimming
	 * 
	 * @param isSwimming
	 *            erwartet neuen Wert für isSwimming
	 */
	public void setSwimming(boolean isSwimming) {
		this.swimming = isSwimming;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}
}
