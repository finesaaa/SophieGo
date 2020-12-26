package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.input.MouseManager;

public class CoinPanel {

	private String text;
	private int x, y;
//	private FontMetrics fm;
	private Font font;
	private int statusCoin, targetCoin;
	
	public CoinPanel(String text, int statusCoin, int targetCoin, int x, int y, Font font) {
		this.text = text;
		this.statusCoin = statusCoin;
		this.targetCoin = targetCoin;
		this.x = x;
		this.y = y;
		this.font = font;
	}

	public void update(int statusCoin) {
		this.statusCoin = statusCoin;
	}
	
	public void render(Graphics g){
		g.setFont(font);
//		fm = g.getFontMetrics();
		g.setColor(Assets.mColor);
//		g.fillRoundRect(0, 0, fm.stringWidth(text) + 40, fm.getHeight() + 6, 50, 50);
		Text.drawString(g, text + statusCoin + " / " + targetCoin , x, y, true, Assets.mColor);
	}

}
