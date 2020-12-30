package com.sophiego.ui;

import java.awt.Font;
import java.awt.Graphics;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;

public class StepCounterPanel {
	
	private String text;
	private int x, y;
	private Font font;
	private int numStep;
	private int targetNumStep;

	public StepCounterPanel(String text, int numStep, int targetNumStep, int x, int y, Font font) {
		this.text = text;
		this.numStep = numStep;
		this.targetNumStep = targetNumStep;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	public void update(int numStep) {
		this.numStep = numStep;
	}
	
	public void render(Graphics g){
		g.setFont(font);
//		fm = g.getFontMetrics();
		g.setColor(Assets.mColor);
//		g.fillRoundRect(0, 0, fm.stringWidth(text) + 40, fm.getHeight() + 6, 50, 50);
		Text.drawString(g, text + numStep + " / " + targetNumStep, x, y, true, Assets.mColor);
	}
}
