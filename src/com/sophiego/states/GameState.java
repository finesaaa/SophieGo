package com.sophiego.states;

import java.awt.Graphics;

import com.sophiego.main.Window;
import com.sophiego.sophie.Level;

public class GameState extends State{

	private Level level;
	
	public GameState(Window window) {
		super(window);
	}

	@Override
	public void update() {
		level.update();
	}

	@Override
	public void render(Graphics g) {
		level.render(g);
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

}
