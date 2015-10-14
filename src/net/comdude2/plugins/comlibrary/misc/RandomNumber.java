package net.comdude2.plugins.comlibrary.misc;

import java.util.Random;

public class RandomNumber {
	
	private static Random random = new Random();
	
	public static int nextInt(int max){
		return random.nextInt(max);
	}
	
}
