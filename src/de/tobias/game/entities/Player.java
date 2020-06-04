package de.tobias.game.entities;

import de.tobias.game.Game;
import de.tobias.game.InputHandler;
import de.tobias.game.fight.AvailableField;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;
import de.tobias.game.level.FightingScreen;
import de.tobias.game.level.Level;
import de.tobias.game.menus.Menu;
import de.tobias.game.menus.PlayerMenu;

/**
 * Die Klasse realisiert den Spieler, welcher vom Benutzer gesteuert wird. Es
 * handelt sich dabei um einen "Pilzmenschen" und einen Helden. Er besitzt also
 * eine bestimmte Anzahl an Einheiten, welche er in der Kampfsequenz benutzen
 * kann. Bei Kollidierung mit einem Gegner entsteht eine solche Sequenz.
 * 
 * @author Tobias
 *
 */
public class Player extends Hero {

	private InputHandler input;
	private Enemy enemyInt = null;

	/**
	 * Erzeugt einen Spieler in einem Level, der sich per Pfeiltasten bewegen
	 * kann und per Leertaste angreifen kann.
	 * 
	 * @param level
	 *            Level in das der Spieler eingefügt wird
	 * @param x
	 *            x-Koordinate im Frame
	 * @param y
	 *            y-Koordinate im Frame
	 * @param input
	 *            Objekt für die Benutzereingabe
	 */
	public Player(Game game, Level level, String name, int x, int y, InputHandler input, int units) {
		super(game, level, name, x, y, 1, 511, units);

		this.input = input;
		selector.setInput(input);
		selector.setActive(true);
	}

	/**
	 * Überschriebene Updatemethode welche zusätzlich noch die Eingabe der
	 * Tastatur implementiert. Zuerst wird geprüft, ob der Spieler sich in einer
	 * Kampfsequenz befindet. Wenn ja, bewegt er sich bei Eingabe der
	 * entsprechenden Pfeiltasten, immer um 1 Pixel in die entsprechende
	 * Richtung. Wird eine Pfeiltaste gedrückt gehalten, bewegt er sich auch
	 * ständig. Dannach wird die Eingabe der Leertaste implementiert. Wenn in
	 * diesem Fall die Taste gedrückt gehalten wird, greift der Spieler nicht
	 * ständig an. Am Ende wird noch bei Kollidierung die Methode, welche für
	 * das Updaten der Kampfsequenz zuständig ist aufgerufen.
	 * 
	 * @see InputHandler.Key#isPressed()
	 * @see Mob#tick()
	 */
	@Override
	public void tick() {
		super.tick();

		int xa = 0;
		int ya = 0;

		if (input.space.isPressed()) {
			Menu menu = new PlayerMenu(game, level, input);
			level.stop();
			game.setMenu(menu);
			((PlayerMenu) menu).setTeam(team);
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if (dir == 0 && input.up.isPressed() || !completePos[0]) {
			xa = 0;
			ya--;
		}
		if (dir == 1 && input.down.isPressed() || !completePos[1]) {
			xa = 0;
			ya++;
		}
		if (dir == 2 && input.left.isPressed() || !completePos[2]) {
			ya = 0;
			xa--;
		}
		if (dir == 3 && input.right.isPressed() || !completePos[3]) {
			ya = 0;
			xa++;
		}

		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		if (input.up.isPressed())
			dir = 0;
		if (input.down.isPressed())
			dir = 1;
		if (input.left.isPressed())
			dir = 2;
		if (input.right.isPressed())
			dir = 3;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}

		if (hasCollidedEnemy()) {
			for (Entity e : level.entities) {
				((Hero) e).savedX = e.x >> 4;
				((Hero) e).savedY = e.y >> 4;
			}
		}

		tickCount++;
	}

	@Override
	public void render(Screen screen, int spriteLine) {
		super.render(screen, spriteLine);

		if (level instanceof FightingScreen)
			selector.render(screen);
	}

	@Override
	public void tickSelector() {
		for (Unit u : team.getUnits()) {
			if (u.isChosen())
				selector.setNoneChosen(false);

			if (u.getX() == selector.getX() && u.getY() == selector.getY() && input.enter.isPressed() && !u.isChosen()
					&& selector.isNoneChosen()) {
				u.setChosen(true);
			}

			if (u.isChosen()) {
				selector.colour = Colours.get(-1, 0, 333, -1);
				for (AvailableField af : u.getFields()) {
					if (input.enter.isPressed() && selector.getX() == af.getX() && selector.getY() == af.getY()
							&& af.isAvailable()) {
						if (af.isFightingField()) {
							u.setAttacking(true);
							
							for (Unit attackedUnit : af.getUnits()) {
								if (af.getX() == attackedUnit.getX() && af.getY() == attackedUnit.getY()) {
									u.setAttackedUnit(attackedUnit);
								}
							}						
						} else {
							u.setRun(true);
						}

						u.setChosen(false);
						selector.setField(af);
						selector.setNoneChosen(true);
						selector.colour = Colours.get(-1, 0, 555, -1);
					}
				}
			}

			if (u.isRun())
				u.run(selector);
			else if (u.isAttacking())
				u.attack(selector);

			if (input.esc.isPressed()) {
				u.setChosen(false);
				selector.setNoneChosen(true);
				selector.colour = Colours.get(-1, 0, 555, -1);
			}
		}

		selector.tick();
	}

	/**
	 * Der Referenz collidedEnemyObj wird bei Kollidierung mit einem Enemy
	 * Objekt dieses Enemy Objekt zugewiesen.
	 * 
	 * @return Bei Kollidierung wird true zurückgegeben, sonst false
	 */
	public boolean hasCollidedEnemy() {
		for (Entity e : level.entities) {
			if (e instanceof Enemy) {
				if (e.x == x && e.y == y) {
					enemyInt = (Enemy) e;
					for (Unit u : team.getUnits()) {
						for (AvailableField af : u.getFields()) {
							af.setUnits(enemyInt.getTeam().getUnits());
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	public Enemy getEnemyInt() {
		return enemyInt;
	}

	public void setEnemyInt(Enemy enemy) {
		this.enemyInt = enemy;
	}
}
