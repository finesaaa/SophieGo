package com.sophiego.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener{

	private boolean[] keys;
	public static boolean UP, LEFT, RIGHT, DOWN;
	public static boolean isPressed;
	
	public KeyBoard() {
		keys = new boolean[256];
		UP = false;
		DOWN = false;
		RIGHT = false;
		LEFT = false;
		isPressed = false;
	}

	public void update() {
		UP = keys[KeyEvent.VK_UP];
		LEFT =  keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		DOWN = keys[KeyEvent.VK_DOWN];
		isPressed = (UP || LEFT || RIGHT || DOWN);
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
