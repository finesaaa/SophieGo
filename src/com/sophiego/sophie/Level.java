package com.sophiego.sophie;

import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import com.sophiego.gfx.Assets;
import com.sophiego.input.KeyBoard;
import com.sophiego.main.Window;
import com.sophiego.states.GameState;
import com.sophiego.states.LevelSelectorState;
import com.sophiego.states.State;
import com.sophiego.ui.*;

public class Level {

	public static final int TILESIZE = 48;
	
	private int[][] maze;
	private int[][] copy;
	
	private int player_row, player_col;
	private int num_coin, target_num_coin, num_step, target_num_step;
	
	private Image texture;
	
	private int xOffset, yOffset;
	private long time, lastTime;
	
	private final int DELAY = 150;
	
	private Button restart, back;
	private CoinPanel coinPanel;
	private StepCounterPanel stepCounterPanel;
	private ShortestPath shortestPath;
	private boolean solved, played;
	
	private int plaStartRow, plaStartCol, plaEndRow, plaEndCol;
	private int statusLevel;
	private LevelSelectorState levelSelectorState;
	
	private static int ID = -1;
	private int id;
	private FontMetrics fm;
	private String text;
	
	public Level(int[][] maze, int id_level, int player_row, int player_col, int status_level, LevelSelectorState levelSelectorState) {
		this.levelSelectorState = levelSelectorState;

		this.statusLevel = status_level;
		this.maze = maze;
		this.num_coin = 0;
		this.target_num_coin = 0;
		this.num_step = 0;
		this.id = id_level;
		
		copy = new int[maze.length][maze[0].length];
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++)
			{
				copy[row][col] = maze[row][col];
				if (maze[row][col] == 3) this.target_num_coin++;
				if (maze[row][col] == 5) {
					plaEndRow = row;
					plaEndCol = col;
				}
			}
				
			plaStartRow = player_row;
			plaStartCol = player_col;
			
			this.player_row = player_row;
			this.player_col = player_col;
			
			shortestPath = new ShortestPath(this.maze, plaStartRow, plaStartCol, plaEndRow, plaEndCol);
			target_num_step = shortestPath.minMoves();
			
			if(this.statusLevel == 1) {
				solved = true;
				played = true;
			} else if (this.statusLevel == 0){
				if (id_level == 0) played = true;
				if (id_level > 0) 
					if(levelSelectorState.getLevels()[id_level - 1].isSolved()) 
						played = true;
				solved = false;
			}
			
			xOffset = (Window.WIDTH - maze[0].length * TILESIZE)/2;
			yOffset = (Window.HEIGHT - maze.length * TILESIZE)/2;
			
			texture = Assets.PlayerFront;	
			restart = new Button("RESTART", 100, Window.HEIGHT/2, new Click(){
				@Override
				public void onClick() {
					reset();
				}}, Assets.font30,new Color(0x02A79F));
			back = new Button("BACK", Window.WIDTH - 100, Window.HEIGHT/2, new Click() {
				@Override
				public void onClick() {
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					State.currentState = levelSelectorState;
				}
			}, Assets.font30,new Color(0x02A79F));
			coinPanel = new CoinPanel("Coin: ", num_coin, target_num_coin, 80, 50, Assets.font30);
			stepCounterPanel = new StepCounterPanel("Energy: ", num_step, target_num_step, Window.WIDTH - 130, 52, Assets.font20);
			time = 0;
			lastTime = System.currentTimeMillis();
		}
	}
	
	private void reset() {
		for(int row = 0; row < maze.length; row++)
			for(int col = 0; col < maze[row].length; col++)
				maze[row][col] = copy[row][col];
		
		num_step = 0;
		num_coin = 0;
		stepCounterPanel.update(num_step);
		coinPanel.update(num_coin);
		player_row = plaStartRow;
		player_col = plaStartCol;
		texture = Assets.PlayerFront;
	}
	
	public void update() {
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if (KeyBoard.isPressed && time > DELAY) {
			if(KeyBoard.UP) {
				move(-1, 0);
				texture = Assets.playerBack;
			}
			if(KeyBoard.LEFT) {
				move(0, -1);
				texture = Assets.playerLeft;
			}
			if(KeyBoard.DOWN) {
				move(1, 0);
				texture = Assets.PlayerFront;
			}
			if(KeyBoard.RIGHT) {
				move(0, 1);
				texture = Assets.playerRight;
			}
		}
		
		restart.update();
		back.update();
		
		for(int row = 0; row < maze.length; row++)
			for(int col = 0; col < maze[row].length; col++)
			{
				if (num_step == target_num_step) {
					texture = Assets.playerDead;
				}
				if(num_step > target_num_step) {
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					levelSelectorState.showGameOver();
					reset();
				}
				if(maze[row][col] == 3 || maze[row][col] == 5) return;
			}
	
		levelSelectorState.getLevels()[id].setPlayed(true);
		levelSelectorState.getLevels()[id].setSolved(true);
		if (levelSelectorState.getLevels()[id].isSolved()) {
			State.currentLevel = id + 1;
			try {
				levelSelectorState.writeToPosition("./res/levels/"+(State.currentLevel - 1) + ".txt", "1", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (id == (levelSelectorState.getNUMLEVEL() - 1)) {
				levelSelectorState.showCongratsState();
			} else {
				levelSelectorState.getLevels()[State.currentLevel].setPlayed(true);
				levelSelectorState.showResult();
			}
		}
	}

	private void move(int row, int col) {

		if(maze[player_row + row][player_col + col] != 1) {
			num_step++;
			stepCounterPanel.update(num_step);
			if(maze[player_row + row ][player_col + col] == 3)  {
				maze[player_row + row][player_col + col] = 4; //ganti brick
				this.num_coin++;
				coinPanel.update(num_coin);
			}
			if(maze[player_row + row ][player_col + col] == 5 && num_coin == target_num_coin)  {
				maze[player_row + row][player_col + col] = 4; //ganti brick
			}
			
			player_row += row;
			player_col += col;
		}
		time = 0;
	}
	
	public void render(Graphics g) {
		restart.render(g);
		back.render(g);
		coinPanel.render(g);
		stepCounterPanel.render(g);

		this.text = "Level " + (this.id + 1);
		g.setFont(Assets.fontLevel);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, 75);
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0;  col < maze[row].length; col++) {
				drawMapAsset(g, Assets.floor, col, row);
				if(maze[row][col] == 1) drawMapAsset(g, Assets.wall, col, row);
				if(maze[row][col] == 3) drawMapAsset(g, Assets.coin, col, row);
				if(maze[row][col] == 4) drawMapAsset(g, Assets.boxOn, col, row);
				if(maze[row][col] == 5) drawMapAsset(g, Assets.flag, col, row);
			}
		}
	
		g.drawImage(texture, xOffset + player_col*TILESIZE, yOffset + player_row*TILESIZE, null);
	}
	
	public void drawMapAsset(Graphics g, Image img, int col, int row) {
		g.drawImage(img, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
	}

	public int getId() {
		return id;
	}

	public boolean isPlayed() {return played;};
	public void setPlayed(boolean bool) {played = bool;};
	public boolean isSolved() {return solved;};
	public void setSolved(boolean bool) {solved = bool;}; 
	public void tryAgainLevel(int id_level) {
		reset();
		levelSelectorState.playThisLevel(id_level);
	}

}
