package com.sophiego.states;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;

public class GameOverState extends State{
	
	private Button back, try_again;
	private FontMetrics fm;
	private String text;

	public GameOverState(Window window) {
		super(window);
		
		try_again = new Button("RETRY", Window.WIDTH/2 + 120, Window.HEIGHT/2 + 75, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getGameState();
			}
			
		}, Assets.font30, Assets.mColor);
		
		back = new Button("RETURN", Window.WIDTH/2 - 120, Window.HEIGHT/2 + 75, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getLevelSelectorState();
			}
			
		}, Assets.font30, new Color(0xFFDA77));
	}

	@Override
	public void update() {
		try_again.update();
		back.update();
	}

	@Override
	public void render(Graphics g) {
		try_again.render(g);
		back.render(g);
		this.text = "GAME OVER";
		
		//setting text
		g.setFont(Assets.fontOver);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, Window.HEIGHT/2 - 150);

	}

}
