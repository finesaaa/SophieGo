package com.sophiego.states;

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
		
		try_again = new Button("TRY AGAIN", Window.WIDTH/2 + 100, Window.HEIGHT/2 + 75, new Click() {
			
			@Override
			public void onClick() {
				((GameState)window.getGameState()).setLevel(State.currentArrLevel);
				State.currentState = window.getGameState();
			}
			
		}, Assets.font30, Assets.mColor);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		try_again.update();
//		try_again.update();
	}

	@Override
	public void render(Graphics g) {
		try_again.render(g);
//		try_again.render(g);
		this.text = "GAME OVER";
		
		g.setFont(Assets.fontLoading);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, Window.HEIGHT/2 - 75);
//		for (int i = 0;  i < 3; i++) 
//			g.drawImage(Assets.star, Window.WIDTH/2 - 100 + i*64, Window.HEIGHT/2 - 45, null);
		
	}

}
