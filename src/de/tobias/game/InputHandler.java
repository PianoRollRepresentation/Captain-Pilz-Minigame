package de.tobias.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse ist f�r die Eingabe der Tastatur zust�ndig. Sie enth�lt eine
 * Anzahl an Keys, wobei die Klasse Key selber als innere Klasse in InputHandler
 * zu finden ist.
 * 
 * @author Tobias
 *
 */
public class InputHandler implements KeyListener {

	/**
	 * Erzeugen eines neuen InputHandlers, wobei dieser direkt in das Gameobjekt 
	 * eingef�gt wird.
	 * 
	 * @param game	Gameobjekt in das der InputHandler eingef�gt wird
	 */
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	/**
	 * Klasse f�r die Realisierung von Keys. Die Klasse
	 * besitzt einen boolean, welcher bei Dr�cken dieses Keys den Wert
	 * true hat und sonst den Wert false hat.
	 * 
	 * @author Tobias
	 *
	 */
	public class Key {
		private int numTimesPressed = 0;
		private boolean pressed = false;

		/**
		 * Getter f�r den int numTimesPressed.
		 * Die Methode gibt die Anzahl zur�ck, wie oft der Key
		 * gedr�ckt wurde.
		 * 
		 * @return numTimesPressed
		 */
		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		/**
		 * Getter f�r den boolean pressed.
		 * Die Methode gibt den Wahrheitswert zur�ck, welcher aussagt,
		 * ob der Key gerade gedr�ckt wird.
		 * 
		 * @return	pressed
		 */
		public boolean isPressed() {
			return pressed;
		}

		/**
		 * Setter f�r den boolean pressed. Zus�tzlich wird noch, wenn pressed auf true gesetzt
		 * ist, numTimesPressed um 1 erh�ht. 
		 * 
		 * @param isPressed erwartet neuen Wert f�r pressed
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
	 * �berschriebene Methode wird aufgerufen wenn ein beliebiger Key gedr�ckt wird.
	 * Sie setzt f�r den Key welcher gedr�ckt wird, durch die 
	 * Methode toggleKey(int, boolean) den boolean pressed auf true.
	 * Wird eine Taste gedr�ckt gehalten, wird auch die Methode st�ndig aufgerufen. 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	/**
	 * �berschriebene Methode wird aufgerufen wenn ein beliebiger Key losgelasen wird.
	 * Sie setzt f�r den Key, welcher losgelassen wird, durch die 
	 * Methode toggleKey(int, boolean) den boolean pressed auf false. 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	/**
	 * �berschriebene Methode wird hier nicht benutzt.
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Die Methode wird entweder von keyPressed(KeyEvent), oder von keyReleased(KeyEvent)
	 * aufgerufen. Hier wird den entsprechenden Keys f�r den boolean pressed
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
