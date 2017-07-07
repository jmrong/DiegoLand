// Diego Land
// Nation management game
// Jack and Anish

import java.util.Scanner;
import java.util.Random;
import java.math.*;

public class DiegoLand {
	
	// IMPORTED MODULES
	
	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();
	
	// VARIABLES
	
	int population = 20;
	int day = 1;
	int time = 0;
	boolean run = true;
	int apd = 4;
	
	// RESOURCES

	int[] rsc = {20000, 500, 100, 150, 20, 50, 30, 5, 0};
	String[] rsc_names = {"Money", "Food", "Power", "Building Materials", "Consumer Goods", "Metal", "Ammunition", "Fuel", "Uranium"};
	
		// NATURAL
		
		int[] rsc_land = new int[3 + 1];
		int[] rsc_fauna = new int[3 + 1];
		int[] rsc_flora = new int[4 + 1];
		int[] rsc_mined = new int[8 + 1];
		
		String[] names_land = {"Forest", "Desert", "Lake", "Grassland"};
		String[] names_fauna = {"Bear", "Salmon", "Shark", "Cow"};
		String[] names_flora = {"Berry Bush", "Oak Tree", "Cactus", "Coral", "Apple Tree"};
		String[] names_mined = {"Coal", "Iron", "Copper", "Sulfur", "Saltpeter", "Clay", "Petroleum", "Crude Oil", "Uraninite"};
		
		double[] stats_land = {0.45, 0.45 + 0.17, 0.45 + 0.17 + 0.2, 0.45 + 0.17 + 0.2 + 0.19};
		double[][] stats_fauna = {{0, 0.2, 1, 4, 3, 0.08, 1, 3}, {2, 0.6, 2, 15}, {2, 0.2, 1, 2}, {3, 0.5, 3, 9}};
		double[][] stats_flora = {{0, 0.75, 1, 6, 3, 0.08, 1, 3}, {0, 0.75, 2, 8, 3, 0.45, 1, 5}, {1, 0.7, 1, 3}, {2, 0.35, 3, 10}, {0, 0.3, 1, 5, 3, 0.45, 1, 5}};
		double[][] stats_mined = {{0, 0.6, 3, 20, 1, 0.65, 3, 20, 2, 0.1, 2, 20, 3, 0.35, 3, 12}, {0, 0.3, 2, 15, 1, 0.65, 1, 18, 3, 0.3, 1, 10}, {0, 0.25, 3, 12, 1, 0.35, 3, 12, 3, 0.1, 2, 8}, {0, 0.4, 5, 35, 1, 0.55, 5, 45, 2, 0.3, 5, 35, 3, 0.1, 5, 30}, {0, 0.1, 3, 8, 1, 0.5, 5, 45, 2, 0.05, 3, 14, 3, 0.3, 3, 10}, {1, 0.2, 3, 40, 2, 0.5, 3, 12}, {1, 0.3, 3, 18, 2, 0.3, 1, 25}, {0, 0.3, 3, 20, 3, 0.1, 3, 15}, {0, 0.03, 1, 5, 1, 0.08, 1, 8, 2, 0.01, 1, 4, 3, 0.02, 1, 5}};
		
	// ALL COMMANDS
		
	void cmd_resources() {
		
		System.out.println("RESOURCES");
		for (int i = 0; i < rsc.length; i++) {
			
			System.out.println(rsc_names[i] + ": " + rsc[i]);
			
		}
		
	}
		
	// COMMAND HANDLER
		
	void command(String command) {
		
		switch (command) {
		
		case "RESOURCES":
			cmd_resources();
		
		}
		
	}
		
	// GAME LOOP
		
	void GameLoop() {
		
		while (run) {
			
			System.out.println("DAY " + day);
			System.out.println();
			
			if (day == 1) {
				
				System.out.println("Welcome to Diego Land!");
				System.out.println("Your job is to allow your newly generated virtual nation to thrive.");
				System.out.println("You must manage your resources, build up a military, and survive attacks from neighboring rivals.");
				System.out.println("To do this, you can type in COMMANDS every turn.");
				System.out.println("Start off by typing in the RESOURCES command below.");
				System.out.println();
				
			}
			
			for (time = 0; time < apd; ) {
				
				System.out.print("Command: ");
				String command = scan.next();
				command(command);
				System.out.println();
				
			}
			
			scan.nextLine();
			System.out.println("Day is over! Press ENTER to continue to DAY " + (day + 1) + "...");
			scan.nextLine();
			System.out.println("***");
			System.out.println();
			day++;
			
		}
		
	}

	public static void main(String[] args) {
			
		// MAIN
		
		DiegoLand game = new DiegoLand();
		
		System.out.println();
		System.out.println("*** DIEGO LAND 1.0 ***");
		System.out.println("***  MADE BY JACK  ***");
		System.out.println("***   AND  ANISH   ***");
		System.out.println();
		System.out.println("***");
		System.out.println("***");
		System.out.println("***");
		System.out.println();
		
		// GENERATE
		
		System.out.print("Press ENTER to generate resources... ");
		scan.nextLine();
		
		System.out.println("Generating resources...");
		
			// LAND
		
			double rsc_total = 0;
			for (int i = 0; i < 80; i++) {
				
				double prob = rand.nextDouble();
				if (prob < game.stats_land[0]) {
					
					game.rsc_land[0]++;
					
				} else if (prob < game.stats_land[1]) {
					
					game.rsc_land[1]++;
					
				} else if (prob < game.stats_land[2]) {
					
					game.rsc_land[2]++;
					
				} else if (prob < game.stats_land[3]) {
					
					game.rsc_land[3]++;
					
				}
				
			}
			
			// i = land type, j = land tile #, k = rsc #, l = rsc land #, m = # of rsc, n = rsc gen min
			
			for (int i = 0; i < game.rsc_land.length; i++) {
				
				for (int j = 0; j < game.rsc_land[i]; j++) {
					
					for (int k = 0; k < game.stats_fauna.length; k++) {
						
						for (int l = 0; l < game.stats_fauna[k].length; l += 4) {
							
							if (game.stats_fauna[k][l] == (double)i) {
								
								double random = rand.nextDouble();
								if (random < game.stats_fauna[k][l + 1]) {
									
									Double m = new Double(game.stats_fauna[k][l + 3] + 1 - game.stats_fauna[k][l + 2]);
									Double n = new Double(game.stats_fauna[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_fauna[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
					for (int k = 0; k < game.stats_flora.length; k++) {
						
						for (int l = 0; l < game.stats_flora[k].length; l += 4) {
							
							if (game.stats_flora[k][l] == (double)i) {
								
								double random = rand.nextDouble();
								if (random < game.stats_flora[k][l + 1]) {
									
									Double m = new Double(game.stats_flora[k][l + 3] + 1 - game.stats_flora[k][l + 2]);
									Double n = new Double(game.stats_flora[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_flora[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
					for (int k = 0; k < game.stats_mined.length; k++) {
						
						for (int l = 0; l < game.stats_mined[k].length; l += 4) {
							
							if (game.stats_mined[k][l] == (double)i) {
								
								if (rand.nextDouble() < game.stats_mined[k][l + 1]) {
									
									Double m = new Double(game.stats_mined[k][l + 3] + 1 - game.stats_mined[k][l + 2]);
									Double n = new Double(game.stats_mined[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_mined[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
			// PRINT
			
			System.out.println();
			System.out.println("Land:  | " + game.rsc_land[0] + " " + game.names_land[0] + " | " + game.rsc_land[1] + " " + game.names_land[1] + " | " + game.rsc_land[2] + " " + game.names_land[2] + " | " + game.rsc_land[3] + " " + game.names_land[3] + " |");
			System.out.print("Fauna: | ");
			for (int i = 0; i < game.rsc_fauna.length; i++) {
				
				System.out.print(game.rsc_fauna[i] + " " + game.names_fauna[i] + " | ");
				
			}
			System.out.println();
			System.out.print("Flora: | ");
			for (int i = 0; i < game.rsc_flora.length; i++) {
				
				System.out.print(game.rsc_flora[i] + " " + game.names_flora[i] + " | ");
				
			}
			System.out.println();
			System.out.print("Mined: | ");
			for (int i = 0; i < game.rsc_mined.length; i++) {
				
				System.out.print(game.rsc_mined[i] + " " + game.names_mined[i] + " | ");
				
			}
			System.out.println();
			rsc_total = (rsc_total - 2250) / 1000;
			if (rsc_total > 0) {
				
				System.out.println("Luck: +" + rsc_total);
				
			} else {
			
				System.out.println("Luck: " + rsc_total);
				
			}
		
		System.out.println();
		System.out.println("Press ENTER to continue to DAY 1...");
		scan.nextLine();
		System.out.println("***");
		System.out.println();
		game.GameLoop();
		
	}

}