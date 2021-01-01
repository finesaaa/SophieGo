package com.sophiego.sophie;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

import com.sophiego.gfx.Assets;
import com.sophiego.input.KeyBoard;
import com.sophiego.main.Window;
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
	private boolean solved;
	
	private int plaStartRow, plaStartCol, plaEndRow, plaEndCol;
	private LevelSelectorState levelSelectorState;
	
	private static int ID = 0;
	private int id;
	private FontMetrics fm;
	private String text;
	
	public Level(int[][] maze, int player_row, int player_col, LevelSelectorState levelSelectorState) {
		this.levelSelectorState = levelSelectorState;

		this.maze = maze;
		this.num_coin = 0;
		this.target_num_coin = 0;
		this.num_step = 0;
		
		ID ++;
		id = ID;
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
			
			if(ID == 1) 
			{
				solved = true;
//				System.out.println("this is ID" + ID + " and id = " + id);
			}
			else
				solved = false;
//			System.out.println("else this is ID" + ID + " and id = " + id);
			
			xOffset = (Window.WIDTH - maze[0].length * TILESIZE)/2;
			yOffset = (Window.HEIGHT - maze.length * TILESIZE)/2;
			
			texture = Assets.PlayerFront;	
			restart = new Button("RESTART", 100, Window.HEIGHT/2, new Click(){
				@Override
				public void onClick() {
					reset();
				}},
					Assets.font30);
			back = new Button("BACK", Window.WIDTH - 100, Window.HEIGHT/2, new Click() {
				@Override
				public void onClick() {
					State.currentState = levelSelectorState;
				}
			}, Assets.font30);
			coinPanel = new CoinPanel("Coin: ", num_coin, target_num_coin, 120, 50, Assets.font30);
			stepCounterPanel = new StepCounterPanel("Step: ", num_step, target_num_step, Window.WIDTH - 120, 50, Assets.font30);
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
//					levelSelectorState.showGameOver();
					reset();
				}
				if(maze[row][col] == 3 || maze[row][col] == 5) return;
			}
				
		levelSelectorState.getLevels()[id].setSolved(true);
		if (levelSelectorState.getLevels()[id].isSolved()) {
			System.out.println("I'm here boy "+id);
			State.currentLevel = id;
			levelSelectorState.showResult();
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
		System.out.print(State.currentLevel);
		this.text = "Level " + (State.currentLevel + 1);
		g.setFont(Assets.fontLevel);
		g.setColor(Assets.mColor);
		fm = g.getFontMetrics();
		g.drawString(text, Window.WIDTH/2 - fm.stringWidth(text)/2, 75);
		
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0;  col < maze[row].length; col++) {
				g.drawImage(Assets.floor, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
				if(maze[row][col] == 1)
					g.drawImage(Assets.wall, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
				if(maze[row][col] == 3)
					g.drawImage(Assets.spot, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
				if(maze[row][col] == 4)
					g.drawImage(Assets.boxOn, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
				if(maze[row][col] == 5)
					g.drawImage(Assets.flag, xOffset + col*TILESIZE, yOffset + row*TILESIZE, null);
			}
		}
	
		g.drawImage(texture, xOffset + player_col*TILESIZE, yOffset + player_row*TILESIZE, null);
	}
	
	
	public boolean isSolved() {return solved;};
	public void setSolved(boolean bool) {solved = bool;}; 

}
