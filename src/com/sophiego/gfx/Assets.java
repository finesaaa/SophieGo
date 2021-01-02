package com.sophiego.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sophiego.main.Window;
import com.sophiego.sophie.Level;

public class Assets {
	public static final int TILESIZE = 48;
	public static final int LOGOSIZE = 240;
	public static final Color mColor = new Color(2, 167, 159);
	public static final Color sColor = new Color(255, 70, 70);
	
	public static Image logo, flag, star, mini_star,mini_star_outline, star_outline, ufo;
	public static Image playerLeft, playerBack, playerRight, PlayerFront, playerDead;
	public static Image floor, floor2, wall, boxOn, boxOff, spot, outline, outline2;
	public static Image moon,uranus,jupiter,saturn, planet1;
	public static Image splashBG, spaceBG;
	
	public static Font square48;
	public static Font font48;
	public static Font font30;
	public static Font font22;
	public static Font font36;
	public static Font fontTitle;
	public static Font fontLoading, fontLevel;
	
	public static void init()
	{
		//player image
		playerLeft = loadImage("/player/left.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerBack = loadImage("/player/back.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		PlayerFront = loadImage("/player/front.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerRight = loadImage("/player/right.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerDead = loadImage("/player/dead.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		//map
		floor = loadImage("/blocks/ground.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		floor2 = loadImage("/blocks/ground2.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		wall = loadImage("/blocks/redBrick.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		boxOn = loadImage("/blocks/ground_01.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		spot = loadImage("/blocks/coin.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		outline = loadImage("/blocks/outline.png").getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT);
		outline2 = loadImage("/blocks/outline2.png").getScaledInstance(100, 100, BufferedImage.SCALE_DEFAULT);
		flag = loadImage("/blocks/flag.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		star = loadImage("/blocks/star.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		star_outline = loadImage("/blocks/star_outline.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		mini_star = loadImage("/blocks/star.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		mini_star_outline =  loadImage("/blocks/star_outline.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		//menu state
		logo = loadImage("/blocks/logo-hd.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		ufo = loadImage("/blocks/ufo 2.png").getScaledInstance(420, 420, BufferedImage.SCALE_DEFAULT);
		planet1 = loadImage("/blocks/planet1.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//level selector state
		moon = loadImage("/blocks/moon 1.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		uranus = loadImage("/blocks/uranus 1.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		jupiter = loadImage("/blocks/jupiter 1.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		saturn = loadImage("/blocks/saturn (1) 2.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//background
		splashBG = loadImage("/blocks/splash.png").getScaledInstance(Window.WIDTH, Window.HEIGHT, BufferedImage.SCALE_DEFAULT);
//		spaceBG = loadImage("/blocks/space.jpg").getScaledInstance(Window.WIDTH, Window.HEIGHT, BufferedImage.SCALE_DEFAULT);
		
		//font
		square48 = loadFont("res/fonts/square.ttf", 48);
		font48 = loadFont("res/fonts/Poppins-SemiBold.ttf", 48);
		font22 = loadFont("res/fonts/Poppins-SemiBold.ttf", 22);
		font30 = loadFont("res/fonts/Poppins-SemiBold.ttf", 30);
		font36 = loadFont("res/fonts/Poppins-SemiBold.ttf", 36);
		fontTitle = loadFont("res/fonts/PoetsenOne.ttf", 56);
		fontLoading = loadFont("res/fonts/square.ttf", 32);
		fontLevel = loadFont("res/fonts/square.ttf", 30);
	}

	public static void setStar(Image star) {
		Assets.star = star;
	}

	public static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(Assets.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Font loadFont(String path, int size){
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
