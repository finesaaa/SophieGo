package com.sophiego.sophie;

public class SelectedLevel {
	public int[] statusLevels;
	private int num_level;
	
	public SelectedLevel(int num_level) {
		this.num_level = num_level;
		statusLevels = new int[num_level];
		for (int i = 0; i < statusLevels.length; i++)
			statusLevels[i] =  0;
	}
}

