package com.sophiego.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.input.MouseManager;
import com.sophiego.main.Window;
import com.sophiego.sophie.Level;
import com.sophiego.sophie.LevelButton;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;
import com.sophiego.states.State;

public class LevelSelectorState extends State{
	
	private final int DOUBLETILESIZE = 80;
	private final int NUMLEVEL = 15;
	private Level[] levels = new Level[NUMLEVEL];
	private final int SPACE = 10;
	private Graphics gL;
	
	private final int xOffset = (Window.WIDTH - (DOUBLETILESIZE + SPACE * 4)*5)/2;
	private final int yOffset = (Window.HEIGHT - (DOUBLETILESIZE + SPACE * 2)*3)/2;
	
	private Button back, reset;
	private LevelButton[] bLevels = new LevelButton[NUMLEVEL];
	
	public LevelSelectorState(Window window) {
		super(window);
		
		for(int id = 0; id < NUMLEVEL; id++)
			levels[id] = loadLevel("/levels/" + id + ".txt", id);
		
		int counter = 0;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) { 
				int spaceX = j * 45;
				int spaceY = i * 20;
				
				bLevels[counter] = new LevelButton(String.valueOf(counter + 1), xOffset + j*DOUBLETILESIZE + spaceX + DOUBLETILESIZE/2, 
						yOffset + i * DOUBLETILESIZE + spaceY + DOUBLETILESIZE/2, new Click() {
					@Override
					public void onClick() {
						if(levels[State.currentLevel-1].isSolved()) showResult();
						else {
							((GameState)window.getGameState()).setLevel(State.currentArrLevel);
							State.currentState = window.getGameState();
						}						
					}
					
				}, levels[counter].isSolved(), levels[counter].isPlayed(),  this);
				counter++;
			}
		}

		back = new Button("BACK", Window.WIDTH/2, Window.HEIGHT - 100, new Click() {
			
			@Override
			public void onClick() {
				State.currentState = window.getMenuState();
			}
			
		}, Assets.font30,new Color(0xFFDA77));
		
		reset = new Button("RESET", Window.WIDTH - 100, Window.HEIGHT - 100, new Click(){
			@Override
			public void onClick() {
				int isReset = JOptionPane.showConfirmDialog(null, 
						"Do you really want to reset the game?", "Confirm Reset", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (isReset == JOptionPane.YES_OPTION) {
					resetLevel();
				} 
			}
		}, Assets.font30,new Color(0xFF4646));
		
	}

	@Override
	public void update() {
		back.update();
		reset.update();
		for(int i = 0; i < NUMLEVEL; i++) {
			bLevels[i].update(levels[i].isSolved(), levels[i].isPlayed(), i);
		}
	}


	@Override
	public void render(Graphics g) {
	
		this.gL = g;
		//background image
		g.drawImage(Assets.moon, -100, -50, null);
		g.drawImage(Assets.uranus, 0, window.HEIGHT/2+20, null);
		g.drawImage(Assets.jupiter, window.WIDTH/2+180, -100, null);
		g.drawImage(Assets.saturn, window.WIDTH/2+150, window.HEIGHT/2, null);
		
		back.render(g);
		reset.render(g);
		
		//header level
		g.setFont(Assets.fontTitle);
		Text.drawString(g, "Level", window.WIDTH/2, window.HEIGHT/9, true, new Color (0, 45, 42));
		
		for(int i = 0; i < NUMLEVEL; i++) {
			bLevels[i].render(g);
		}
//		for(int i = 0; i < 3; i++) {
//			for (int j = 0; j < 5; j++) {
//			
//				State.currentArrLevel = levels[counter - 1];
//
//				//space column and row
//				int spaceX = j * 45;
//				int spaceY = i * 20;
//				
//				g.setFont(Assets.font22);
////				g.fillRoundRect(xOffset + j * DOUBLETILESIZE + spaceX, yOffset + i * DOUBLETILESIZE + spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
//				
//				//bounds for hover check
//				Rectangle bounds = new Rectangle(xOffset + j*DOUBLETILESIZE + spaceX, yOffset + i * DOUBLETILESIZE + spaceY , DOUBLETILESIZE, DOUBLETILESIZE);
//				
//				
//				//condition for hover, get Game Play state
//				if(bounds.contains(MouseManager.x, MouseManager.y)) {
//					if(MouseManager.left && State.currentArrLevel.isPlayed()) {
//						((GameState)window.getGameState()).setLevel(State.currentArrLevel);
//						State.currentState = window.getGameState();
//					}
//					if(State.currentArrLevel.isPlayed()) {
//						g.setColor(solvedColor.darker());
//						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
//						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY +i*DOUBLETILESIZE + DOUBLETILESIZE/2 - 6, true, Color.white.darker());
//					}	
//					else {
//						g.setColor(unsolvedColor.darker());
//						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
//						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY +i*DOUBLETILESIZE + DOUBLETILESIZE/2 - 6, true, Color.white.darker());
//					}	
//				} else {
//					if(State.currentArrLevel.isPlayed()) {
//						g.setColor(solvedColor);
//						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
//						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2 - 6, true, Color.white);
//					}
//					else {
//						g.setColor(unsolvedColor);
//						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
//						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2 - 6, true, Color.white);
//					}	
//				}
//				for (int s = 0;  s < 3; s++) {
//					if(State.currentArrLevel.isSolved()) 
//						g.drawImage(Assets.mini_star, xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2 + 20 * s - DOUBLETILESIZE/3, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2 + 10, null);
//					else
//						g.drawImage(Assets.mini_star_outline, xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2 + 20 * s - DOUBLETILESIZE/3, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2 + 10, null);
//				}
//				counter++;
//			}
//		}
		
	}

	public int getNUMLEVEL() {
		return NUMLEVEL;
	}

	private Level loadLevel(String path, int curr_level) {
		String file = loadFileAsString(path);
		String[] numbers = file.split("\\s+");
		
		int status_level = parseInt(numbers[0]);
		int cols = parseInt(numbers[1]);
		int rows = parseInt(numbers[2]);
//		System.out.println(status_level + " " + cols +" " + rows + " ");
	
		int player_col = parseInt(numbers[3]);
		int player_row = parseInt(numbers[4]);
		
		int[][] maze = new int[rows][cols];
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				maze[row][col] = parseInt(numbers[(col + (row*cols)) + 5]);
		
		return new Level(maze, curr_level, player_row, player_col, status_level, this);
	}

	private int parseInt(String num) {
		try {
			return Integer.parseInt(num);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}


	private String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = LevelSelectorState.class.getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public void writeToPosition(String fileName, String data, long position) throws IOException {
		RandomAccessFile writer = new RandomAccessFile(fileName, "rw");
	    writer.seek(position);
	    writer.writeBytes(data);
	    writer.close();
	}
	

	public void resetLevel() {
		for(int id = 0; id < NUMLEVEL; id++) {
			try {
				writeToPosition("./res/levels/"+(id) + ".txt", "0", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int id = 0; id < NUMLEVEL; id++)
			levels[id] = loadLevel("/levels/" + id + ".txt", id);
		
		render(this.gL);
	}


	public void showResult() {
		State.currentState = window.getResultState();
	}
	
	public void showGameOver() {
		State.currentState = window.getGameOverState();
	}
	
	public void showCongratsState() {
		State.currentState = window.getCongratsState();
	}
	
	public Level[] getLevels() {
		return levels;
	}

}
