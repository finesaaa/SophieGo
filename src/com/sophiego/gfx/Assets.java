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
	
	public static Image playerLeft, playerBack, playerRight, PlayerFront, playerDead;
	public static Image logo, flag, star_outline, star, mini_star, mini_star_outline, ufo;
	public static Image floor, wall, boxOn, boxOff, coin;
	public static Image moon, uranus, jupiter, saturn, saturn2;
	public static Image splashBG, baseBG;
	
	public static Font square48;
	public static Font font48;
	public static Font font30;
	public static Font font22;
	public static Font font20;
	public static Font font36;
	public static Font fontTitle;
	public static Font fontLoading, fontLevel, fontOver;
	
	public static void init()
	{
		//player image
		playerLeft = loadImage("/player/left.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerBack = loadImage("/player/back.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		PlayerFront = loadImage("/player/front.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerRight = loadImage("/player/right.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		playerDead = loadImage("/player/dead.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		//splash background
		splashBG = loadImage("/blocks/bg_splash.png").getScaledInstance(Window.WIDTH, Window.HEIGHT, BufferedImage.SCALE_DEFAULT);
		
		//main background
		baseBG = loadImage("/blocks/bg_base.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		//map
		floor = loadImage("/blocks/ground.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		boxOn = loadImage("/blocks/ground_after.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		wall = loadImage("/blocks/ground_redbrick.png").getScaledInstance(TILESIZE, TILESIZE, BufferedImage.SCALE_DEFAULT);
		
		coin = loadImage("/blocks/coin.png").getScaledInstance(Level.TILESIZE, Level.TILESIZE, BufferedImage.SCALE_DEFAULT);
		flag = loadImage("/blocks/flag.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		
		star = loadImage("/blocks/star.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		star_outline = loadImage("/blocks/star_outline.png").getScaledInstance(64, 64, BufferedImage.SCALE_DEFAULT);
		mini_star = loadImage("/blocks/star.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		mini_star_outline =  loadImage("/blocks/star_outline.png").getScaledInstance(14, 14, BufferedImage.SCALE_DEFAULT);
		
		//menu state
		logo = loadImage("/blocks/logo-hd.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		ufo = loadImage("/blocks/ufo.png").getScaledInstance(420, 420, BufferedImage.SCALE_DEFAULT);
		saturn = loadImage("/blocks/planets/saturnus.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//level selector state
		moon = loadImage("/blocks/planets/moon.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		uranus = loadImage("/blocks/planets/uranus.png").getScaledInstance(LOGOSIZE, LOGOSIZE, BufferedImage.SCALE_DEFAULT);
		jupiter = loadImage("/blocks/planets/jupiter.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		saturn2 = loadImage("/blocks/planets/saturnus2.png").getScaledInstance(360, 360, BufferedImage.SCALE_DEFAULT);
		
		//font
		square48 = loadFont("res/fonts/square.ttf", 48);
		font48 = loadFont("res/fonts/Poppins-SemiBold.ttf", 48);
		font22 = loadFont("res/fonts/Poppins-SemiBold.ttf", 22);
		font20 = loadFont("res/fonts/Poppins-SemiBold.ttf", 20);
		font30 = loadFont("res/fonts/Poppins-SemiBold.ttf", 30);
		font36 = loadFont("res/fonts/Poppins-SemiBold.ttf", 36);
		fontTitle = loadFont("res/fonts/PoetsenOne.ttf", 56);
		fontLoading = loadFont("res/fonts/square.ttf", 32);
		fontLevel = loadFont("res/fonts/square.ttf", 30);
		fontOver = loadFont("res/fonts/square.ttf", 40);
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
