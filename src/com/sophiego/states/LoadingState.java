package com.sophiego.states;

import java.awt.Color;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.main.Window;
import com.sophiego.states.State;

public class LoadingState extends State {

	private final String NAME = "H E L L O  W O R L D . . . ";
	private String text = "";
	private int index = 0;
	private long time, lastTime;
	
	public LoadingState (Window window){
		super(window);
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		if(time > 50) {
			text = NAME.substring(0, index);
			if(index < NAME.length()){
				index++;
			} else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				State.currentState = window.getMenuState();
			}
			time = 0;
		}	
	}

	@Override
	public void render(Graphics g) {
		g.setFont(Assets.font30);
		Text.drawString(g, text, Window.WIDTH/2, Window.HEIGHT/2, true, Assets.mColor);
	}

}
