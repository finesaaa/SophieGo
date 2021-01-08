package com.sophiego.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.input.MouseManager;
import com.sophiego.main.Window;

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
		int radius = 13;
		int space =8;
		g.setFont(font);
//		fm = g.getFontMetrics();
		g.setColor(Assets.mColor);
//		g.fillRoundRect(0, 0, fm.stringWidth(text) + 40, fm.getHeight() + 6, 50, 50);
		Text.drawString(g, (text).toUpperCase() , x, y, true, Assets.mColor);
		//draw coin
		for (int i=0; i<targetCoin; i++) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(130 + i*space+radius*2*i, 50-radius,  radius*2, radius*2);
		}
		for (int i=0; i<statusCoin; i++) {
			g.setColor(Color.yellow);
			g.fillOval( 130 + i*space+radius*2*i, 50-radius,  radius*2, radius*2);
//			g.drawImage(Assets.coin, 130 + i*space+radius*2*i, 50-radius,  radius*2, radius*2,null);
		}
	}

}
