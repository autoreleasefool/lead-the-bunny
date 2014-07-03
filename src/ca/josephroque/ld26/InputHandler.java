package ca.josephroque.ld26;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener, FocusListener {
	
	public boolean focused = true;
	public boolean w, a, s, d, enter, escape, up, down;
	public int cX, cY, mX, mY;
	public boolean mousePressed;
	
	public void keyPressed(KeyEvent ke) {
		switch (ke.getKeyCode()) {
			case KeyEvent.VK_W: w = true; break; 
			case KeyEvent.VK_A: a = true; break;
			case KeyEvent.VK_S: s = true; break;
			case KeyEvent.VK_D: d = true; break;
			case KeyEvent.VK_ENTER: enter = true; break;
			case KeyEvent.VK_ESCAPE: escape = true; break;
			case KeyEvent.VK_UP: up = true; break;
			case KeyEvent.VK_DOWN: down = true; break;
		}
		ke.consume();
	}
	public void keyReleased(KeyEvent ke) {
		switch (ke.getKeyCode()) {
			case KeyEvent.VK_W: w = false; break; 
			case KeyEvent.VK_A: a = false; break;
			case KeyEvent.VK_S: s = false; break;
			case KeyEvent.VK_D: d = false; break;
			case KeyEvent.VK_ENTER: enter = false; break;
			case KeyEvent.VK_ESCAPE: escape = false; break;
			case KeyEvent.VK_UP: up = false; break;
			case KeyEvent.VK_DOWN: down = false; break;
		}
		ke.consume();
	}
	public void keyTyped(KeyEvent ke) {}
	
	public void mousePressed(MouseEvent me) {
		cX = me.getX();
		cY = me.getY();
		mousePressed = true;
	}
	public void mouseReleased(MouseEvent me) {
		mousePressed = false;
	}
	public void mouseExited(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}
	
	public void mouseMoved(MouseEvent me) { mX = me.getX(); mY = me.getY(); }
	public void mouseDragged(MouseEvent me) { mX = me.getX(); mY = me.getY(); }
	
	public void focusGained(FocusEvent fe) { focused = true; }
	public void focusLost(FocusEvent fe) { focused = false; }
	
	public InputHandler(Component c) {
		c.addMouseListener(this);
		c.addKeyListener(this);
		c.addMouseMotionListener(this);
		c.addFocusListener(this);
	}
}
