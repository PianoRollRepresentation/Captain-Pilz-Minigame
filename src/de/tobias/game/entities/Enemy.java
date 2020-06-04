package de.tobias.game.entities;

import java.util.Random;

import de.tobias.game.Game;
import de.tobias.game.fight.AvailableField;
import de.tobias.game.level.Level;

/**
 * Realisierung des vom CPU gesteuerten "Feindes". Es handelt sich dabei auch um
 * einen "Pilzmenschen", welcher im Kreis herumläuft. Bei Kollidierung des
 * Spielers mit dem Feind erscheint die in der Klasse Background beschriebene
 * Zwischensequenz, und der Mainscreen wechselt zum Fightingscreen.
 * 
 * @author Tobias
 *
 */
public class Enemy extends Hero {

	private final float NEEDEDRUNNING;
	private float runningTime = 0;
	private long lastFrame = System.currentTimeMillis();

	private boolean right = false, up = false;

	private final int X_DEFAULT, Y_DEFAULT;

	/**
	 * Der Konstruktor weist den Konstanten X_DEFAULT und Y_DEFAULT den
	 * Start-y-, bzw. y-Wert zu.
	 * 
	 * @param level
	 *            Objekt ist in diesem Level vorhanden
	 * @param x
	 *            x-Koordinate im Frame
	 * @param y
	 *            y-Koordinate im Frame
	 */
	public Enemy(Game game, Level level, String name, int x, int y, int units) {
		super(game, level, name, x, y, 1, (int) (Math.random() * 555), units);
		this.X_DEFAULT = this.x;
		this.Y_DEFAULT = this.y;
		NEEDEDRUNNING = (float) (Math.random() * 2 + 0.5);
	}

	/**
	 * ständig aufgerufene Updatemethode. Das Objekt beweegt sich in einem
	 * Viereck immer in eine zufällige Richtung.Am Ende wird bei Bewegung die
	 * Methode move(int, int) der Klasse Mob aufgerufen und der tickCount um 1
	 * erhöht.
	 * 
	 * @see Mob#move(int, int)
	 */
	@Override
	public void tick() {
		super.tick();
		int xa = 0;
		int ya = 0;

		/*
		 * Die Variable runningTime gibt die aktuelle Zeit in Sekunden wieder,
		 * wie lang das Objekt schon "geht", thisFrame die aktuelle Zeit in
		 * Milliesekunden, lastFrame die Startzeit in Milliesekunden,
		 * timeSinceLastFrame die Differenz zwischen Startzeit und aktueller
		 * Zeit in Sekunden und NEEDEDRUNNING die Maximalzeit in Sekunden. Wenn
		 * runningTime groß genug ist, also größer als NEEDEDRUNNING ist, werden
		 * 2 zufällige Wahrheitswerte erzeugt.
		 */
		long thisFrame = System.currentTimeMillis();
		float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
		lastFrame = thisFrame;

		runningTime += timeSinceLastFrame;
		if (runningTime > NEEDEDRUNNING) {
			runningTime = 0;
			Random random = new Random();
			right = random.nextBoolean();
			up = random.nextBoolean();
		}

		if (right && x < X_DEFAULT + 16) {
			xa++;
			ya = 0;
		} else if (!right && x > X_DEFAULT - 16) {
			xa--;
			ya = 0;
		}

		if (!up && y < Y_DEFAULT + 16) {
			ya++;
			xa = 0;
		} else if (up && y > Y_DEFAULT - 16) {
			ya--;
			xa = 0;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}

		tickCount++;
	}

	@Override
	public void tickSelector() {
		int random = (int) ((Math.random() * team.getUnits().size()));
		int randomFields = (int) ((Math.random() * team.getUnits().get(random).getFields().size()));

		selector.setxToMove(team.getUnits().get(random).getFields().get(randomFields).getY());
		selector.setyToMove(team.getUnits().get(random).getFields().get(randomFields).getY());
		for (AvailableField af : team.getUnits().get(random).getFields()) {
			if (af.isFightingField())
				team.getUnits().get(random).attack(selector);
			else
				team.getUnits().get(random).run(selector);
		}
	}
}
