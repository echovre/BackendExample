package com.example.MyApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

	File file = null;

	BufferedWriter output = null;

	public WriteToFile(String logFile) {
		file = new File(logFile);
		try {
			output = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		if ( output != null ) {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void log(String logLine) {
		try {
			output.write(logLine+"\n");
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}