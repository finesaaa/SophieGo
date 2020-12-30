package com.sophiego.states;

import java.awt.Graphics;

import com.sophiego.main.Window;
import com.sophiego.sophie.Level;

public abstract class State {

	public static State currentState = null;
	public static int currentLevel = 1;
	public static Level currentArrLevel;
	protected Window window;
	
	public State(Window window) {
		this.window = window;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
}
