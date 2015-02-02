package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import config.Config;

public class FileUtil {
	
	public static Object[][] getRowsFromFile(String path) {
		
		StringBuilder stringBuilder = new StringBuilder();
		String line = "";
		BufferedReader bufferedReader = null;
		System.out.println(path);
		
		try {
			bufferedReader = new BufferedReader(new FileReader(path));
			while((line = bufferedReader.readLine()) != null) {
				line = line.replaceAll("\t", "   ");
				stringBuilder.append(line);
				stringBuilder.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String[] programArray = stringBuilder.toString().split("\n");
//		System.out.println("1. riadok = " + programArray[0]);
//		System.out.println("programArray.length = " + programArray.length);
		Object[][] finalArray = new Object[programArray.length][2];
		
		for(int i = 0; i < programArray.length; i++) {
			finalArray[i][0] = i+1; //because of lines (instead of 0 there will be 1)
			finalArray[i][1] = programArray[i];
		}
		
//		System.out.println("length = " + programArray.length);
//		System.out.println("0. prvok = " + finalArray[0][0] + ". " + finalArray[0][1]);
		return finalArray;
		
	}

}
