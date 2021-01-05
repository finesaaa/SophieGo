package com.sophiego.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.main.Window;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;
import com.sophiego.states.State;

public class MenuState extends State {

	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public MenuState(Window window) {
		super(window);
		buttons.add(new Button("PLAY", Window.WIDTH/2 - 100, Window.HEIGHT/2 + 125 , new Click(){
			@Override
			public void onClick() {
				State.currentState = window.getLevelSelectorState();
			}
		}, Assets.font30,new Color(0x02A79F)));

		buttons.add(new Button("EXIT", Window.WIDTH/2 + 100, Window.HEIGHT/2 + 125, new Click(){
			@Override
			public void onClick() {
				int choose = JOptionPane.showConfirmDialog(null, 
						"Do you really want to exit the app ?", "Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (choose == JOptionPane.YES_OPTION)
					System.exit(1);
			}
		}, Assets.font30,new Color(0xFF4646)));
	}

	@Override
	public void update() {
		for(int i = 0; i < buttons.size(); i++)
			buttons.get(i).update();
	}

	@Override	
	public void render(Graphics g) {
		g.drawImage(Assets.logo, Window.WIDTH/2 - Assets.LOGOSIZE/2, Window.HEIGHT/2 - 240, null);
		
		g.drawImage(Assets.ufo, -100 , Window.HEIGHT/2 - 30, null);
		g.drawImage(Assets.saturn, Window.WIDTH/2 + 180 , -100, null);
		
		g.setFont(Assets.fontTitle);
		Text.drawString(g, "Shopie Go", Window.WIDTH/2, Window.HEIGHT/2 + 35, true, new Color (0, 45, 42));
		for(int i = 0; i < buttons.size(); i++)
			buttons.get(i).render(g);
	}
}
