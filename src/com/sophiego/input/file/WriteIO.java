package com.sophiego.input.file;

import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;

public class WriteIO {
	
	private static Formatter output;

	public static void openFile(String fileName) {
		try {
			output = new Formatter(fileName);
		} catch (SecurityException securityException) {
			System.err.println("Write permission denied. Terminating.");
			System.exit(1);
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}

	public static void writeNumData(int data, int level) {
		openFile(level+ ".txt");
		
		try {
			output.format("%d", data);
		} catch (FormatterClosedException formatterClosedException) {
			System.err.println("Error writing to file. Terminating.");
		} catch (NoSuchElementException elementException) {
			System.err.println("Invalid input. Please try again.");
		}
		
		closeFile();
	}
	
	public static void closeFile() {
		if (output != null)
			output.close();
	}
	
}
