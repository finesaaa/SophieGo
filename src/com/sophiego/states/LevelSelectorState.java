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
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;
import com.sophiego.ui.LevelButton;
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
						if(levels[State.currentLevel-1].isSolved()) {
							if(State.currentLevel == NUMLEVEL) showCongratsState();
							else showResult();
						}
						else {
							playThisLevel(State.currentLevel-1);
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
		g.drawImage(Assets.saturn2, window.WIDTH/2+150, window.HEIGHT/2, null);
		
		back.render(g);
		reset.render(g);
		
		//header level
		g.setFont(Assets.fontTitle);
		Text.drawString(g, "Level", window.WIDTH/2, window.HEIGHT/9, true, new Color (0, 45, 42));
		
		for(int i = 0; i < NUMLEVEL; i++) {
			bLevels[i].render(g);
		}
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

	public void  playThisLevel(int id_level) {
		((GameState)window.getGameState()).setLevel(levels[id_level]);
		State.currentState = window.getGameState();
	}

}
