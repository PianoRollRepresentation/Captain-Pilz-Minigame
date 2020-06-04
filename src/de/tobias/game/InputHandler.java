package de.tobias.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse ist für die Eingabe der Tastatur zuständig. Sie enthält eine
 * Anzahl an Keys, wobei die Klasse Key selber als innere Klasse in InputHandler
 * zu finden ist.
 * 
 * @author Tobias
 *
 */
public class InputHandler implements KeyListener {

	/**
	 * Erzeugen eines neuen InputHandlers, wobei dieser direkt in das Gameobjekt 
	 * eingefügt wird.
	 * 
	 * @param game	Gameobjekt in das der InputHandler eingefügt wird
	 */
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	/**
	 * Klasse für die Realisierung von Keys. Die Klasse
	 * besitzt einen boolean, welcher bei Drücken dieses Keys den Wert
	 * true hat und sonst den Wert false hat.
	 * 
	 * @author Tobias
	 *
	 */
	public class Key {
		private int numTimesPressed = 0;
		private boolean pressed = false;

		/**
		 * Getter für den int numTimesPressed.
		 * Die Methode gibt die Anzahl zurück, wie oft der Key
		 * gedrückt wurde.
		 * 
		 * @return numTimesPressed
		 */
		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		/**
		 * Getter für den boolean pressed.
		 * Die Methode gibt den Wahrheitswert zurück, welcher aussagt,
		 * ob der Key gerade gedrückt wird.
		 * 
		 * @return	pressed
		 */
		public boolean isPressed() {
			return pressed;
		}

		/**
		 * Setter für den boolean pressed. Zusätzlich wird noch, wenn pressed auf true gesetzt
		 * ist, numTimesPressed um 1 erhöht. 
		 * 
		 * @param isPressed erwartet neuen Wert für pressed
		 */
		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed)
				numTimesPressed++;
		}

	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key space = new Key();
	public Key enter = new Key();
	public Key esc = new Key();
	public List<Key> keys = new ArrayList<Key>();

	/**
	 * Überschriebene Methode wird aufgerufen wenn ein beliebiger Key gedrückt wird.
	 * Sie setzt für den Key welcher gedrückt wird, durch die 
	 * Methode toggleKey(int, boolean) den boolean pressed auf true.
	 * Wird eine Taste gedrückt gehalten, wird auch die Methode ständig aufgerufen. 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	/**
	 * Überschriebene Methode wird aufgerufen wenn ein beliebiger Key losgelasen wird.
	 * Sie setzt für den Key, welcher losgelassen wird, durch die 
	 * Methode toggleKey(int, boolean) den boolean pressed auf false. 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	/**
	 * Überschriebene Methode wird hier nicht benutzt.
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Die Methode wird entweder von keyPressed(KeyEvent), oder von keyReleased(KeyEvent)
	 * aufgerufen. Hier wird den entsprechenden Keys für den boolean pressed
	 * der boolean des in dieser Methode vorhanden Parameters, isPressed zugewiesen. 
	 * 
	 * @see Key#toggle(boolean)
	 * @param keyCode	Wert des entsprechenden Keys
	 * @param isPressed	setzt den boolean pressed des Keys auf den Wert von isPressed
	 */
	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
			up.toggle(isPressed);
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
			down.toggle(isPressed);
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
			left.toggle(isPressed);
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
			right.toggle(isPressed);
		if (keyCode == KeyEvent.VK_SPACE) {
			space.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_ENTER) {
			enter.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_ESCAPE) {
			esc.toggle(isPressed);
		}
	}
}
