package com.sophiego.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sophiego.sophie.Level;

public class Assets {
	public static final int TILESIZE = 48;
	public static final int LOGOSIZE = 240;
	public static final Color mColor = new Color(2, 167, 159);
	public static final Color sColor = new Color(255, 70, 70);
	
	public static Image logo, flag;
	public static Image playerLeft, playerBack, playerRight, PlayerFront;
	public static Image floor, floor2, wall, boxOn, boxOff, spot, outline, outline2, ufo, planet1;
	
	public static Font font48;
	public static Font font30;
	public static Font font22;
	public static Font font36;
	public static Font fontTitle;
	
	public static void init()
	{
		playerLeft = loadImage("/player/left.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerBack = loadImage("/player/back.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		PlayerFront = loadImage("/player/front.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerRight = loadImage("/player/right.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		floor = loadImage("/blocks/ground.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		floor2 = loadImage("/blocks/ground2.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		wall = loadImage("/blocks/redBrick.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		boxOn = loadImage("/blocks/boxOn.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		boxOff = loadImage("/blocks/boxOff.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		spot = loadImage("/blocks/coin.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		outline = loadImage("/blocks/outline.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		outline2 = loadImage("/blocks/outline2.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		flag = loadImage("/blocks/flag.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		
		logo = loadImage("/blocks/logo-hd.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		ufo = loadImage("/blocks/ufo 2.png").getScaledInstance(420, 420, BufferedImage.SCALE_DEFAULT);
		planet1 = loadImage("/blocks/planet1.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		font48 = loadFont("res/fonts/Poppins-SemiBold.ttf", 48);
		font22 = loadFont("res/fonts/Poppins-SemiBold.ttf", 22);
		font30 = loadFont("res/fonts/Poppins-SemiBold.ttf", 30);
		font36 = loadFont("res/fonts/Poppins-SemiBold.ttf", 36);
		fontTitle = loadFont("res/fonts/PoetsenOne.ttf", 56);
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
