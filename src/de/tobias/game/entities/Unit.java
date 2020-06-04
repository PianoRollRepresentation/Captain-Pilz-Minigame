package de.tobias.game.entities;

import java.util.ArrayList;
import java.util.List;

import de.tobias.game.fight.AvailableField;
import de.tobias.game.fight.Hitpoints;
import de.tobias.game.fight.UnitSelector;
import de.tobias.game.gfx.Colours;
import de.tobias.game.level.Level;

/**
 * Die Klasse ist das Gerüst für die Einheiten, welche von einem Helden, also
 * von einem Spieler, oder einem Feind gesteuert werden. Einheiten kommen nur in
 * der Kampfsequenz zum Einsatz.
 * 
 * @author Tobias
 *
 */
public class Unit extends Mob {

	private boolean chosen = false;
	private boolean run = false;
	
	private int dead = 0;

	private Hitpoints hp;
	private Unit attackedUnit = null;
	private List<AvailableField> fields = new ArrayList<>();

	/**
	 * Erzeugt eine neue Einheit in einem Level. render und tick werden als
	 * Default auf false gesetzt, weil Einheiten erst in der Kampfsequenz zum
	 * Einsatz kommen.
	 * 
	 * @param level
	 *            Level in das der Spieler eingefügt wird
	 * @param x
	 *            x-Koordinate des Spielers im Frame
	 * @param y
	 *            y-Koordinate des Spielers im Frame
	 * @param colour
	 *            Farbe des Spielers
	 */
	public Unit(Level level, int colour) {
		super(level, "einfacher Pilz", 0, 0, 1, Colours.get(-1, 000, colour, 555));
		
		hp = new Hitpoints();
		fields.add(new AvailableField(x + 1, y));
		fields.add(new AvailableField(x - 1, y));
		fields.add(new AvailableField(x, y + 1));
		fields.add(new AvailableField(x, y - 1));
		fields.add(new AvailableField(x + 1, y + 1));
		fields.add(new AvailableField(x + 1, y - 1));
		fields.add(new AvailableField(x - 1, y + 1));
		fields.add(new AvailableField( x - 1, y - 1));
	}

	/**
	 * Überschriebene Updatemethode, welche zusätzlich bei Wechsel in die
	 * Kampfsequenz die Variablen render und tick auf true setzt.
	 * 
	 * @see Mob#tick()
	 */
	@Override
	public void tick() {
		super.tick();
		
		if (hp.getPixels() <= 0) {
			remove();
			dead = 1;
		}

		hp.setX(x);
		hp.setY(y - 8);
	}

	public void run(UnitSelector selector) {
		int xa = 0;
		int ya = 0;
		
		if (selector.getField().getX() > x) {
			ya = 0;
			xa++;
		} else if (selector.getField().getX() < x) {
			ya = 0;
			xa--;
		} else if (selector.getField().getY() > y) {
			xa = 0;
			ya++;
		} else if (selector.getField().getY() < y) {
			xa = 0;
			ya--;
		} else {
			run = false;
			setFieldLoc();
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}
	}

	public void attack(UnitSelector selector) {
		if (attackedUnit != null) {
			attacking = false;
			attackedUnit.getHp().setPixels(attackedUnit.getHp().getPixels() - 4);
			attackedUnit = null;
		}
	}
	
	public void setFieldLoc(){
		for (AvailableField af : fields) {
			af.setX(x + af.getLocX());
			af.setY(y + af.getLocY());
		}
	}

	/**
	 * Getter für den boolean chosen
	 * 
	 * @return chosen
	 */
	public boolean isChosen() {
		return chosen;
	}

	/**
	 * Setter für den boolean chosen
	 * 
	 * @param chosen
	 *            erwartet neuen Wert für chosen
	 */
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public Hitpoints getHp() {
		return hp;
	}

	/**
	 * Getter für die Liste fields
	 * 
	 * @return fields
	 */
	public List<AvailableField> getFields() {
		return fields;
	}

	public Unit getAttackedUnit() {
		return attackedUnit;
	}

	public void setAttackedUnit(Unit attackedUnit) {
		this.attackedUnit = attackedUnit;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
}
