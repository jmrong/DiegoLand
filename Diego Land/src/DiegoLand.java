// Diego Land 1.0
// Nation management game
// Jack and Anish

import java.util.*;
import java.math.*;
import java.io.*;

public class DiegoLand {
	
	// HELPER FUNCTIONS
	
	public static int round(double value) {
		
		BigDecimal bigDec = new BigDecimal(value);
		bigDec = bigDec.setScale(0, RoundingMode.HALF_UP);
		return bigDec.intValue();
		
	}

	// IMPORTS

	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();

	// VARIABLES

	int population = 20;
	int day = 1;
	int time = 0;
	boolean run = true;
	int apd = 4;
	double happiness = 2;
	int tax = 22;
	int growth = 2;
	int usage = 2;
	
	int[] techs = {0, 0, 0, 0, 0};
	String[][] names_techs = {{"Military", "Allows recruitment of powerful units"}, {"Productivity", "Increases commands per day by 1 per level"}, {"Public Happiness", "Increases happiness by +0.5 per level"}, {"Refining", "Allows processing of advanced mined resources"}, {"Population Growth", "Increase population growth by +1% per level"}};
	int[] max_techs = {4, 3, 3, 2, 4};
	int[][] costs_techs = {{700, 2000, 4800, 10000}, {500, 4000, 10000}, {5000, 11000, 31000}, {6000, 12000}, {4000, 9000, 13000, 28000}};
	int[][] req_techs = {{-3, -4, -5, -6}, {-2, -3, -4}, {-3, -5, -6}, {-4, 4}, {-2, -3, -5, -6}};
	int[][] time_techs = {{2, 5, 8, 12}, {1, 2, 3}, {2, 4, 5}, {2, 5}, {2, 4, 6, 8}};
	int counter_techs = -1;
	int pending_techs = -1;
	String[] fancy = {"I", "II", "III", "IV"};
	
	int[] buildings = {0, 0, 0, 0, 0};
	String[][] names_buildings = {{"Research Lab", "Allows new technologies to be researched"}, {"Barracks", "Allows training and recruitment of infantry-type units"}, {"Armored Vehicle Factory", "Allows construction and recruitment of tank-type units"}, {"Airfield", "Allows construction and recruitment of air-based units"}, {"Radiology Lab", "Allows research into radioactive materials and their properties"}};
	int[][] costs_buildings = {{100, 80, 0}, {400, 100, 0}, {2500, 500, 150}, {6000, 1000, 500}, {6000, 1000, 500}};
	int[] req_buildings = {0, 3, 3, 5, 6, 6};
	int[] time_buildings = {2, 4, 7, 15, 21};
	
	int rtd = -1;
	ArrayList<UnitPending> pending_unit = new ArrayList<UnitPending>();
	ArrayList<Division> divisions = new ArrayList<Division>();
	War[] wars = {new War("Masekovia"), new War("Bell Republic"), new War("Goldedge"), new War("Isle of Vaktovia"), new War("Davis Land")};
	int military_announce = 0;
	
	int[] tiers = {0, 100, 300, 800, 1500, 3200, 9000, 20000};
	int getTier() {

		int _return = -1;
		for (int i = 0; i < tiers.length && _return == -1; i++) {

			if (tiers[i] > population) {

				_return = i;

			}

		}
		return _return;

	}

	// RESOURCES

	int[] rsc = {5000, 200, 50, 40, 20, 20, 0, 0, 0};
	String[] rsc_names = {"Money", "Food", "Power", "Building Materials", "Consumer Goods", "Metal", "Ammunition", "Fuel", "Uranium"};
	int[] rsc_change = {tax, usage * -1, -1, 0, 0,0, 0, 0, 0};

		// NATURAL
		
		int[] rsc_land = {0, 0, 0, 0, 20};
		int[] rsc_fauna = new int[3 + 1];
		int[] rsc_flora = new int[4 + 1];
		int[] rsc_mined = new int[9 + 1];
		
		String[] names_land = {"Forest", "Desert", "Lake", "Grassland", "Cleared Land"};
		String[] names_fauna = {"Bear", "Salmon", "Shark", "Cow"};
		String[] names_flora = {"Berry Bush", "Oak Tree", "Cactus", "Coral", "Apple Tree"};
		String[] names_mined = {"Coal", "Iron", "Copper", "Sulfur", "Saltpeter", "Clay", "Sandstone", "Petroleum", "Crude Oil", "Uraninite"};
		
		double[] stats_land = {0.45, 0.45 + 0.17, 0.45 + 0.17 + 0.2, 0.45 + 0.17 + 0.2 + 0.19};
		double[][] stats_fauna = {{0, 0.2, 1, 4, 3, 0.08, 1, 3}, {2, 0.6, 2, 15}, {2, 0.2, 1, 2}, {3, 0.5, 3, 9}};
		double[][] stats_flora = {{0, 0.75, 1, 6, 3, 0.08, 1, 3}, {0, 0.75, 2, 8, 3, 0.45, 1, 5}, {1, 0.7, 1, 3}, {2, 0.35, 3, 10}, {0, 0.3, 1, 5, 3, 0.45, 1, 5}};
		double[][] stats_mined = {{0, 0.6, 3, 20, 1, 0.65, 3, 20, 2, 0.1, 2, 20, 3, 0.35, 3, 12}, {0, 0.3, 2, 15, 1, 0.65, 1, 18, 3, 0.3, 1, 10}, {0, 0.25, 3, 12, 1, 0.35, 3, 12, 3, 0.1, 2, 8}, {0, 0.4, 5, 35, 1, 0.55, 5, 45, 2, 0.3, 5, 35, 3, 0.1, 5, 30}, {0, 0.1, 3, 8, 1, 0.5, 5, 45, 2, 0.05, 3, 14, 3, 0.3, 3, 10}, {0, 0.05, 3, 30, 1, 0.2, 3, 40, 2, 0.5, 3, 12, 3, 0.05, 3, 20}, {1, 0.9, 3, 35}, {1, 0.3, 3, 18, 2, 0.3, 1, 25}, {0, 0.3, 3, 20, 3, 0.1, 3, 15}, {0, 0.03, 1, 5, 1, 0.08, 1, 8, 2, 0.01, 1, 4, 3, 0.02, 1, 5}};

	// FACTORIES
	
	Factory[] factory_templates = {
			new Factory(new int[][] {{0, 3}, {}, {}}, new int[]{1, 2}, new int[][] {{0, 10}, {}, {0, 1}}, 2, "Berry Picker"),
			new Factory(new int[][] {{0, 4}, {1, 1}, {}}, new int[] {1, 4}, new int[][] {{0, 10}, {2, 1}, {}}, 1, "Salmon Fisher"),
			new Factory(new int[][] {{0, 5}, {}, {}}, new int[] {1, 2}, new int[][] {{0, 15, 3, 5}, {}, {4, 1}}, 2, "Orchard"),
			new Factory(new int[][] {{0, 4, 2, 1}, {3, 1}, {}}, new int[] {1, 6}, new int[][] {{0, 15, 3, 5}, {4, 1}, {}}, 1, "Farm"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {2, 4}, new int[][] {{0, 10, 3, 10}, {4, 1}, {}}, 1, "Windmill"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {3, 3}, new int[][] {{0, 10}, {}, {1, 1}}, 1, "Lumberjack"),
			new Factory(new int[][] {{0, 4, 2, 3}, {}, {5, 2}}, new int[] {3, 8}, new int[][] {{0, 8, 3, 10, 5, 2}, {4, 1}, {}}, 2, "Brickworks"),
			new Factory(new int[][] {{0, 6, 2, 2}, {}, {6, 2}}, new int[] {3, 8}, new int[][] {{0, 10, 3, 4}, {1, 1}, {}}, 2, "Sandstone Quarry"),
			new Factory(new int[][] {{0, 10}, {}, {}}, new int[] {2, 12}, new int[][] {{0, 30, 3, 10, 5, 10}, {2, 1}, {}}, 1, "Hydroelectric Plant"),
			new Factory(new int[][] {{0, 5}, {}, {0, 2}}, new int[] {2, 15}, new int[][] {{0, 25, 3, 10, 5, 8}, {4, 1}, {}}, 1, "Power Plant"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {1, 1, 4, 1}, new int[][] {{0, 6}, {}, {2, 1}}, 1, "Cactus Potter"),
			new Factory(new int[][] {{0, 12}, {0, 1}, {}}, new int[] {1, 5, 4, 3}, new int[][] {{0, 15}, {4, 1}, {}}, 2, "Hunter"),
			new Factory(new int[][] {{0, 10}, {2, 1}, {}}, new int[] {1, 7, 4, 5}, new int[][] {{0, 8, 3, 4}, {2, 1}, {}}, 2, "Shark Trapper"),
			new Factory(new int[][] {{0, 10, 2, 1}, {}, {}}, new int[] {4, 4}, new int[][] {{0, 8, 3, 8, 5, 2}, {4, 1}, {}}, 2, "Jeweler"),
			new Factory(new int[][] {{0, 15, 5, 5}, {}, {}}, new int[] {4, 18}, new int[][] {{0, 30, 3, 20, 5, 20}, {4, 1}, {}}, 2, "Automobile Factory"),
			new Factory(new int[][] {{0, 5, 2, 3}, {}, {1, 2}}, new int[] {5, 5}, new int[][] {{0, 25, 3, 10}, {4, 1}, {}}, 1, "Iron Smelter"),
			new Factory(new int[][] {{0, 6, 2, 4}, {}, {2, 2}}, new int[] {5, 8}, new int[][] {{0, 28, 3, 15}, {4, 1}, {}}, 1, "Copper Mine"),
			new Factory(new int[][] {{0, 8, 2, 5, 5, 1}, {}, {3, 2}}, new int[] {6, 15}, new int[][] {{0, 30, 3, 30, 5, 10}, {4, 1}, {}}, 1, "Ammunition Factory"),
			new Factory(new int[][] {{0, 10, 2, 6, 5, 1}, {}, {4, 2}}, new int[] {6, 25}, new int[][] {{0, 35, 3, 35, 5, 12}, {4, 1}, {}}, 2, "Explosives Factory"),
			new Factory(new int[][] {{0, 10, 2, 8}, {}, {7, 1}}, new int[] {7, 6}, new int[][] {{0, 32, 3, 22, 5, 15}, {4, 1}, {}}, 2, "Petroleum Refinery"),
			new Factory(new int[][] {{0, 10, 2, 8}, {}, {8, 1}}, new int[] {7, 6}, new int[][] {{0, 32, 3, 24, 5, 14}, {4, 1}, {}}, 2, "Crude Oil Refinery"),
			new Factory(new int[][] {{0, 40, 2, 12}, {}, {9, 1}}, new int[] {8, 4}, new int[][] {{0, 100, 3, 150, 5, 60}, {4, 2}, {}}, 1, "Nuclear Reactor"),
			new Factory(new int[][] {{0, 25, 9, 1}, {}, {}}, new int[] {2, 50}, new int[][] {{0, 65, 3, 100, 5, 50}, {4, 2}, {}}, 1, "Nuclear Power Plant")
	};
	int[] factories = new int[factory_templates.length];
	int[] factories_lastCollected = new int[factory_templates.length];
	
	//List<Division> divisions = new ArrayList<Division>();

	// ALL COMMANDS

	void cmd_resources() {

		System.out.println("RESOURCES");
		for (int i = 0; i < rsc.length; i++) {

			String sign = (rsc_change[i] >= 0) ? "+" : "";
			System.out.println(rsc_names[i] + ": " + rsc[i] + " (" + sign + rsc_change[i] + ")");

		}
		if (day == 1 && time == 0) {

			System.out.println();
			System.out.println("Nice job!");
			System.out.println("There are many different commands that perform various actions.");
			System.out.println("You can view all unlocked commands with the HELP command, or view some basic starting tips with TIPS.");
			System.out.println("Your next step should be constructing some factories to help you gain food.");
			System.out.println("Keep on building your Diego Land, and grow your population tier by tier (the next tier is Tier 2 at 100 population).");
			System.out.println("Good luck!");

		}

	}

	void cmd_constructf() {

		System.out.println("CONSTRUCT FACTORIES");
		int last = 0;
		if (getTier() == 1) {

			last = 8;

		} else if (getTier() == 2) {

			last = 16;

		} else if (getTier() == 3 || getTier() == 4) {

			last = 19;

		} else if (getTier() == 5 || getTier() == 6) {

			if (techs[3] == 1) {
				
				last = 21;

			} else {
				
				last = 19;
				
			}
				
		} else if (getTier() >= 7) {

			if (techs[3] == 2) {
				
				last = 23;

			} else {
				
				last = 21;
				
			}

		}
		for (int i = 0; i < last; i++) {

			String function = "";
			for (int j = 0; j < factory_templates[i].output.length; j += 2) {
				
				function += rsc_names[factory_templates[i].output[j]];
				if (j + 2 < factory_templates[i].output.length) {
			
					function += ", ";
					
				}
				
			}
			System.out.print((i + 1) + ") " + factory_templates[i].name + " (" + function + ")  ");
			if ((i + 1) % 2 == 0) {

				System.out.println();

			}

		}
		System.out.println();
		System.out.print("Type # to view info/buy, 0 to cancel: ");
		int index = scan.nextInt();
		if (index >= 1 && index <= last) {

			String _input = "";
			String _output = "";
			String _cost = "";
			// _input
			for (int i = 0; i < 3; i++) {

				if (i == 0) {

					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {

						_input += factory_templates[index - 1].input[i][j + 1] + " " + rsc_names[factory_templates[index - 1].input[i][j]] + " ";

					}

				}
				if (i == 1 && factory_templates[index - 1].input[1].length != 0) {

					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {

						_input += factory_templates[index - 1].input[i][j + 1] + " " + names_fauna[factory_templates[index - 1].input[i][j]] + " ";

					}

				}
				if (i == 2 && factory_templates[index - 1].input[2].length != 0) {

					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {

						_input += factory_templates[index - 1].input[i][j + 1] + " " + names_mined[factory_templates[index - 1].input[i][j]] + " ";

					}

				}

			}
			// _output
			for (int i = 0; i < factory_templates[index - 1].output.length; i += 2) {

				_output += factory_templates[index - 1].output[i + 1] + " " + rsc_names[factory_templates[index - 1].output[i]] + " ";

			}
			//_cost
			for (int i = 0; i < 3; i++) {

				if (i == 0) {

					for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {

						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";

					}

				}
				if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {

					for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {

						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";

					}

				}
				if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {

					for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {

						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";

					}

				}

			}
			System.out.println(factory_templates[index - 1].name + ": " + _input + "-> " + _output + "(Time: " + factory_templates[index - 1].time + " day(s))");
			System.out.println(_cost + "to construct; you have " + factories[index - 1] + " " + factory_templates[index - 1].name + " already");
			System.out.print("Enter quantity to build, 0 to cancel: ");
			int quantity = scan.nextInt();
			boolean valid = true;
			String used = "";
			// valid, used
			if (quantity < 1) {

				System.out.println("CONSTRUCTF cancelled");

			} else {

				for (int i = 0; i < 3; i++) {

					if (i == 0) {

						for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {

							if (rsc[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {

								valid = false;

							} else {

								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}

					}
					if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {

						for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {

							if (rsc_land[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {

								valid = false;

							} else {

								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}

					}
					if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {

						for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {

							if (rsc_flora[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {

								valid = false;

							} else {

								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}

					}

				}

				if (!valid) {

					System.out.println("You don't have enough resources");
					System.out.println("CONSTRUCTF cancelled");

				} else {

					System.out.println("Bought " + quantity + " " + factory_templates[index - 1].name + " for " + used);
					String left = "";
					for (int i = 0; i < 3; i++) {

						if (i == 0) {

							for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {

								rsc[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1] * quantity;
								left += rsc[factory_templates[index - 1].cost[i][j]] + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}
						if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {

							for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {
							
								rsc_land[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1] * quantity;
								left += rsc_land[factory_templates[index - 1].cost[i][j]] + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}
						if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {

							for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
								
								rsc_flora[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1] * quantity;
								left += rsc_flora[factory_templates[index - 1].cost[i][j]] + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";

							}

						}

					}
					System.out.println("You now have " + left);
					factories[index - 1] += quantity;
					factories_lastCollected[index - 1] = -1;
					time++;
					
				}

			}

		} else {

			System.out.println("CONSTRUCTF cancelled");

		}

	}

	void cmd_factories() {
		
		System.out.println("FACTORIES");
		boolean empty = true;
		int last = 0;
		int[] _factories = new int[factories.length];
		for (int i = 0; i < factories.length; i++) {
			
			if (factories[i] != 0) {
				
				String ready = "";
				if (factories_lastCollected[i] / factory_templates[i].time >= 0 && factories_lastCollected[i] != -1) {
					
					ready = " (" + (factories_lastCollected[i] / factory_templates[i].time) + " output(s) available)";
					
				} else if (factories_lastCollected[i] == -1) {
					
					ready = " (under construction)";
					
				}
				System.out.println((last + 1) + ") " + factory_templates[i].name + " x " + factories[i] + ready);
				_factories[last] = i;
				last++;
				empty = false;
				
			}
			
		}
		if (empty) {
			
			System.out.println("No factories yet! Use CONSTRUCTF to construct factories");
			
		} else {
			
			System.out.print("Type # to view/collect/sell, 0 to cancel: ");
			int index = scan.nextInt();
			if (index < 1 || index > last) {
				
				System.out.println("FACTORIES cancelled");
				
			} else {
				
				index = _factories[index - 1] + 1;
				// info
				String _input = "";
				String _output = "";
				for (int i = 0; i < 3; i++) {
					
					if (i == 0) {
						
						for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
							
							_input += factory_templates[index - 1].input[i][j + 1] * factories[index - 1] + " " + rsc_names[factory_templates[index - 1].input[i][j]] + " ";
							
						}
						
					}
					if (i == 1 && factory_templates[index - 1].input[1].length != 0) {
						
						for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
							
							_input += factory_templates[index - 1].input[i][j + 1] * factories[index - 1] + " " + names_fauna[factory_templates[index - 1].input[i][j]] + " ";
							
						}
						
					}
					if (i == 2 && factory_templates[index - 1].input[2].length != 0) {
						
						for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
							
							_input += factory_templates[index - 1].input[i][j + 1] * factories[index - 1] + " " + names_mined[factory_templates[index - 1].input[i][j]] + " ";
							
						}
						
					}
					
				}
				if (factory_templates[index - 1].cost[2].length != 0) {
					
					_input += "(";
					for (int j = 0; j < factory_templates[index - 1].cost[2].length; j += 2) {
						
						_input += factory_templates[index - 1].cost[2][j + 1] * factories[index - 1] + " " + names_flora[factory_templates[index - 1].cost[2][j]];
						if (j + 2 < factory_templates[index - 1].cost[2].length) {
							
							_input += " ";
							
						}
						
					}
					_input += ") ";
					
				}
				
				for (int i = 0; i < factory_templates[index - 1].output.length; i += 2) {
					
					_output += factory_templates[index - 1].output[i + 1] * factories[index - 1] + " " + rsc_names[factory_templates[index - 1].output[i]] + " ";
					
				}
				
				System.out.println(factory_templates[index - 1].name + " x " + factories[index - 1] + ": " + _input + "-> " + _output + "(" + factory_templates[index - 1].time + " day(s)/output)");
				if (factories_lastCollected[index - 1] == -1) {
					
					System.out.println("Factory under construction, collection unavailable...");
					
				} else {
					// max output
					String max = "";
					if (factories_lastCollected[index - 1] / factory_templates[index - 1].time >= 1) {
						
						for (int i = 0; i < 3; i++) {
							
							if (i == 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
									
									max += (factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (factories_lastCollected[index - 1] / factory_templates[index - 1].time)) + " " + rsc_names[factory_templates[index - 1].input[i][j]] + " ";
									
								}
								
							}
							if (i == 1 && factory_templates[index - 1].input[1].length != 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
									
									max += (factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (factories_lastCollected[index - 1] / factory_templates[index - 1].time)) + " " + names_fauna[factory_templates[index - 1].input[i][j]] + " ";
									
								}
								
							}
							if (i == 2 && factory_templates[index - 1].input[2].length != 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
									
									max += (factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (factories_lastCollected[index - 1] / factory_templates[index - 1].time)) + " " + names_mined[factory_templates[index - 1].input[i][j]] + " ";
									
								}
								
							}
							
						}
						max += "->";
						for (int i = 0; i < factory_templates[index - 1].output.length; i += 2) {
							
							max += " " + (factory_templates[index - 1].output[i + 1] * factories[index - 1] * (factories_lastCollected[index - 1]) / factory_templates[index - 1].time) + " " + rsc_names[factory_templates[index - 1].output[i]];
							
						}
						
					}
					if (max != "") {
						
						System.out.println(factories_lastCollected[index - 1] + " day(s) since last collection -> max. " + (factories_lastCollected[index - 1] / factory_templates[index - 1].time) + " x output (" + max + ")");
					
					} else {
						
						System.out.println(factories_lastCollected[index - 1] + " day(s) since last collection -> max. " + (factories_lastCollected[index - 1] / factory_templates[index - 1].time) + " x output");
						
					}
					System.out.print("Enter positive # to collect # of outputs from factory, negative # to sell # of factories, 0 to cancel: ");
					int choice = scan.nextInt();
					if (choice == 0) {
						
						System.out.println("FACTORIES cancelled");
						
					} else if (choice > 0) {
						
						boolean valid = true;
						for (int i = 0; i < 3 && valid; i++) {
							
							if (i == 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
									
									if (rsc[factory_templates[index - 1].input[i][j]] - factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (choice) < 0) {
										
										valid = false;
										
									}
										
								}
								
							}
							if (i == 1 && factory_templates[index - 1].input[1].length != 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[1].length; j += 2) {
								
									if (rsc_fauna[factory_templates[index - 1].input[i][j]] - factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (choice) < 0) {
										
										valid = false;
										
									}									
								}
								
							}
							if (i == 2 && factory_templates[index - 1].input[2].length != 0) {
								
								for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
									
									if (rsc_mined[factory_templates[index - 1].input[i][j]] - factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * (choice) < 0) {
										
										valid = false;
										
									}
									
								}
								
							}
						
						}
						if (choice > (factories_lastCollected[index - 1] / factory_templates[index - 1].time)) {
							
							System.out.println("You can't collect this many outputs yet");
							System.out.println("FACTORIES cancelled");
							
						} else if (!valid) {
							
							System.out.println("You don't have enough resources");
							System.out.println("FACTORIES cancelled");
							
						} else {
							// collect
							String left = "";
							for (int i = 0; i < 3; i++) {
								
								if (i == 0) {
									
									for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
										
										int old = rsc[factory_templates[index - 1].input[i][j]];
										rsc[factory_templates[index - 1].input[i][j]] -= factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * choice;
										left += rsc[factory_templates[index - 1].input[i][j]] + " " + rsc_names[factory_templates[index - 1].input[i][j]] + " (-" + (old - rsc[factory_templates[index - 1].input[i][j]]) + ")";
										if (j + 2 <= factory_templates[index - 1].input[i].length || factory_templates[index - 1].input[1].length != 0 || factory_templates[index - 1].input[2].length != 0) {
											
											left += " ";
											
										}
										
									}
									
								}
								if (i == 1 && factory_templates[index - 1].input[1].length != 0) {
									
									for (int j = 0; j < factory_templates[index - 1].input[1].length; j += 2) {
										
										int old = rsc_fauna[factory_templates[index - 1].input[i][j]];
										rsc_fauna[factory_templates[index - 1].input[i][j]] -= factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * choice;
										left += rsc_fauna[factory_templates[index - 1].input[i][j]] + " " + names_fauna[factory_templates[index - 1].input[i][j]] + " (-" + (old - rsc_fauna[factory_templates[index - 1].input[i][j]]) + ")";
										if (j + 2 <= factory_templates[index - 1].input[i].length || factory_templates[index - 1].input[2].length != 0) {
											
											left += " ";
											
										}
										
									}
									
								}
								if (i == 2 && factory_templates[index - 1].input[2].length != 0) {
									
									for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
										
										int old = rsc_mined[factory_templates[index - 1].input[i][j]];
										rsc_mined[factory_templates[index - 1].input[i][j]] -= factory_templates[index - 1].input[i][j + 1] * factories[index - 1] * choice;
										left += rsc_mined[factory_templates[index - 1].input[i][j]] + " " + names_mined[factory_templates[index - 1].input[i][j]] + " (-" + (old - rsc_mined[factory_templates[index - 1].input[i][j]]) + ") ";
										
									}
									
								}
							
							}
							left += "& ";
							for (int i = 0; i < factory_templates[index - 1].output.length; i += 2) {
								
								int old = rsc[factory_templates[index - 1].output[i]];
								rsc[factory_templates[index - 1].output[i]] += factory_templates[index - 1].output[i + 1] * factories[index - 1] * choice;
								left += rsc[factory_templates[index - 1].output[i]] + " " + rsc_names[factory_templates[index - 1].output[i]] + " (+" + (rsc[factory_templates[index - 1].output[i]] - old) + ") ";
								
							}
							System.out.println("Collected " + choice + " output(s) from " + factory_templates[index - 1].name + " x " + factories[index - 1]);
							System.out.println("You now have " + left);
							factories_lastCollected[index - 1] -= factory_templates[index - 1].time * choice;
							time++;
							
						}
						
					} else {
						// sell
						if (choice * -1 > factories[index - 1]) {
							
							System.out.println("You can't sell more factories than you have");
							System.out.println("FACTORIES cancelled");
							
						} else {
							
							choice *= -1;
							int back = 2;
							String _back = "";
							for (int i = 0; i < factory_templates[index - 1].cost.length; i++) {
								
								if (i == 0) {
								
									for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
									
										int old = rsc[factory_templates[index - 1].cost[i][j]];
										rsc[factory_templates[index - 1].cost[i][j]] += (factory_templates[index - 1].cost[i][j + 1] * choice) / back;
										_back += rsc[factory_templates[index - 1].cost[i][j]] + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " (+" + (rsc[factory_templates[index - 1].cost[i][j]] - old) + ") ";
									
									}
									
								}
								
								if (i == 1) {
									
									for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
									
										int old = rsc_land[factory_templates[index - 1].cost[i][j]];
										rsc_land[factory_templates[index - 1].cost[i][j]] += (factory_templates[index - 1].cost[i][j + 1] * choice) / back;
										_back += rsc_land[factory_templates[index - 1].cost[i][j]] + " " + names_land[factory_templates[index - 1].cost[i][j]] + " (+" + (rsc_land[factory_templates[index - 1].cost[i][j]] - old) + ") ";
									
									}
									
								}
								
								if (i == 2) {
									
									for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
									
										int old = rsc_flora[factory_templates[index - 1].cost[i][j]];
										rsc_flora[factory_templates[index - 1].cost[i][j]] += (factory_templates[index - 1].cost[i][j + 1] * choice) / back;
										_back += rsc_flora[factory_templates[index - 1].cost[i][j]] + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " (+" + (rsc_flora[factory_templates[index - 1].cost[i][j]] - old) + ") ";
									
									}
									
								}
								
							}
							factories[index - 1] -= choice;
							if (choice == factories_lastCollected[index - 1] / factory_templates[index - 1].time) {
								
								factories_lastCollected[index - 1] = 0;
							
							}	
							System.out.println("Sold " + choice + " x " + factory_templates[index - 1].name + " " + "(" + factories[index - 1] + " remaining)");
							System.out.println("You now have " + _back);
							time++;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	void cmd_stats() {
		
		System.out.println("STATS");
		System.out.println("Population: " + population + " (+" + growth + ")");
		System.out.println("Tier: " + getTier() + " (next tier: " + (getTier()  + 1) + ", " + tiers[getTier()] + " pop.)");
		if (tax >= 0) {
			
			System.out.println("Tax income: +" + tax);
			
		} else {
			
			System.out.println("Tax income: " + tax);
			
		}
		System.out.println("Growth food usage: " + usage);
		if (happiness >= 0) {
			
			System.out.println("Happiness: +" + happiness);
			
		} else {
			
			System.out.println("Happiness: " + happiness);
			
		}
		
	}
	
	void cmd_expand() {
		
		System.out.println("EXPAND BORDERS");
		System.out.println("This will give you 40 random pieces of land, as well as their random resources.");
		System.out.println("Price: 3,500 Money 400 Food 50 Power 50 Building Materials 50 Consumer Goods");
		System.out.print("Continue? Y/N ");
		if (!scan.next().equalsIgnoreCase("Y")) {
			
			System.out.println("EXPANDBORDERS cancelled");
			
		} else if (rsc[0] < 3500 || rsc[1] < 400 || rsc[2] < 50 || rsc[3] < 50 || rsc[4] < 50) {
			
			System.out.println("You don't have enough resources");
			System.out.println("EXPANDBORDERS cancelled");
			
		} else {
			
			rsc[0] -= 3500;
			rsc[1] -= 400;
			rsc[2] -= 50;
			rsc[3] -= 50;
			rsc[4] -= 50;
			System.out.println("Scouting for land...");
			int divider = rand.nextInt(40 + 1 - 28) + 28;
			int[] _land = new int[rsc_land.length - 1];
			int[] land_change = new int[rsc_land.length];
			int[] fauna_change = new int[rsc_fauna.length];
			int[] flora_change = new int[rsc_flora.length];
			int[] mined_change = new int[rsc_mined.length];
			for (int i = 0; i < divider; i++) {
	
				double prob = rand.nextDouble();
				if (prob < stats_land[0]) {
	
					rsc_land[0]++;
					_land[0]++;
					land_change[0]++;
	
				} else if (prob < stats_land[1]) {
	
					rsc_land[1]++;
					_land[1]++;
					land_change[1]++;
	
				} else if (prob < stats_land[2]) {
	
					rsc_land[2]++;
					_land[2]++;
					land_change[2]++;
	
				} else if (prob < stats_land[3]) {
	
					rsc_land[3]++;
					_land[3]++;
					land_change[3]++;
					
				}
	
			}
	
			// i = land type, j = land tile #, k = rsc #, l = rsc land #, m = # of rsc, n = rsc gen min
	
			for (int i = 0; i < _land.length; i++) {
	
				for (int j = 0; j < _land[i]; j++) {
	
					for (int k = 0; k < stats_fauna.length; k++) {
	
						for (int l = 0; l < stats_fauna[k].length; l += 4) {
	
							if (stats_fauna[k][l] == (double)i) {
	
								double random = rand.nextDouble();
								if (random < stats_fauna[k][l + 1]) {
	
									Double m = new Double(stats_fauna[k][l + 3] + 1 - stats_fauna[k][l + 2]);
									Double n = new Double(stats_fauna[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									rsc_fauna[k] += add;
									fauna_change[k] += add;
	
								}
	
							}
	
						}
	
					}
	
					for (int k = 0; k < stats_flora.length; k++) {
	
						for (int l = 0; l < stats_flora[k].length; l += 4) {
	
							if (stats_flora[k][l] == (double)i) {
	
								double random = rand.nextDouble();
								if (random < stats_flora[k][l + 1]) {
	
									Double m = new Double(stats_flora[k][l + 3] + 1 - stats_flora[k][l + 2]);
									Double n = new Double(stats_flora[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									rsc_flora[k] += add;
									flora_change[k] += add;
	
								}
	
							}
	
						}
	
					}
	
					for (int k = 0; k < stats_mined.length; k++) {
	
						for (int l = 0; l < stats_mined[k].length; l += 4) {
	
							if (stats_mined[k][l] == (double)i) {
	
								if (rand.nextDouble() < stats_mined[k][l + 1]) {
	
									Double m = new Double(stats_mined[k][l + 3] + 1 - stats_mined[k][l + 2]);
									Double n = new Double(stats_mined[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									rsc_mined[k] += add;
									mined_change[k] += add;
	
								}
	
							}
	
						}
	
					}
	
				}
	
			}
			
			int old = rsc_land[4];
			rsc_land[4] += (50 - divider);
			land_change[4] = rsc_land[4] - old;
			System.out.println();
			System.out.println("Your search party discovered new resources and you now have: ");
			System.out.println("Land:  | " + rsc_land[0] + " " + names_land[0] + " (+" + land_change[0] + ") | " + rsc_land[1] + " " + names_land[1] + " (+" + land_change[1] + ") | " + rsc_land[2] + " " + names_land[2] + " (+" + land_change[2] + ") | " + rsc_land[3] + " " + names_land[3] + " (+" + land_change[3] + ") | " + rsc_land[4] + " " + names_land[4] + " (+" + land_change[4] + ") |");
			System.out.print("Fauna: | ");
			for (int i = 0; i < rsc_fauna.length; i++) {
	
				System.out.print(rsc_fauna[i] + " " + names_fauna[i] + " (+" + fauna_change[i] + ") | ");
	
			}
			System.out.println();
			System.out.print("Flora: | ");
			for (int i = 0; i < rsc_flora.length; i++) {
	
				System.out.print(rsc_flora[i] + " " + names_flora[i] + " (+" + flora_change[i] + ") | ");
	
			}
			System.out.println();
			System.out.print("Mined: | ");
			for (int i = 0; i < rsc_mined.length; i++) {
	
				System.out.print(rsc_mined[i] + " " + names_mined[i] + " (+" + mined_change[i] + ") | ");
	
			}
			System.out.println("");
			
		}
		
	}
	
	void cmd_expedition() {
		
		System.out.println("EXPEDITION");
		System.out.println("This will give you 25 pieces of a land type of your choice.\nHOWEVER, UNLIKE EXPAND BORDERS, IT HAS A CHANCE TO DISCOVER AN ENEMY KINGDOM AND INITIATE A WAR.");
		System.out.println("Price: 4,800 Money 620 Food 125 Power 125 Building Materials 80 Consumer Goods");
		System.out.print("Continue? Y/N ");
		if (!scan.next().equalsIgnoreCase("Y")) {
			
			System.out.println("EXPEDITION cancelled");
			
		} else if (rsc[0] < 4800 || rsc[1] < 620 || rsc[2] < 125 || rsc[3] < 125 || rsc[4] < 80) {
			
			System.out.println("You don't have enough resources");
			System.out.println("EXPEDITION cancelled");
			
		} else {
			
			System.out.print("Would you like to scout for 1) Forest, 2) Desert, 3) Lake, or 4) Grassland? ");
			int choice = scan.nextInt();
			if (choice < 1 || choice > 4) {
				
				System.out.println("Invalid value entered");
				System.out.println("EXPEDITION cancelled");
				
			} else {
				
				rsc[0] -= 4800;
				rsc[1] -= 620;
				rsc[2] -= 125;
				rsc[3] -= 125;
				rsc[4] -= 80;
				System.out.println("Scouting for " + names_land[choice - 1] + "...");
				int[] fauna_change = new int[rsc_fauna.length];
				int[] flora_change = new int[rsc_flora.length];
				int[] mined_change = new int[rsc_mined.length];
				rsc_land[choice - 1] += 25;
		
				// i = land type, j = land tile #, k = rsc #, l = rsc land #, m = # of rsc, n = rsc gen min
		
				for (int i = choice - 1; i < choice; i++) {
		
					for (int j = 0; j < 25; j++) {
		
						for (int k = 0; k < stats_fauna.length; k++) {
		
							for (int l = 0; l < stats_fauna[k].length; l += 4) {
		
								if (stats_fauna[k][l] == (double)i) {
		
									double random = rand.nextDouble();
									if (random < stats_fauna[k][l + 1]) {
		
										Double m = new Double(stats_fauna[k][l + 3] + 1 - stats_fauna[k][l + 2]);
										Double n = new Double(stats_fauna[k][l + 2]);
										int add = rand.nextInt(m.intValue()) + n.intValue();
										rsc_fauna[k] += add;
										fauna_change[k] += add;
		
									}
		
								}
		
							}
		
						}
		
						for (int k = 0; k < stats_flora.length; k++) {
		
							for (int l = 0; l < stats_flora[k].length; l += 4) {
		
								if (stats_flora[k][l] == (double)i) {
		
									double random = rand.nextDouble();
									if (random < stats_flora[k][l + 1]) {
		
										Double m = new Double(stats_flora[k][l + 3] + 1 - stats_flora[k][l + 2]);
										Double n = new Double(stats_flora[k][l + 2]);
										int add = rand.nextInt(m.intValue()) + n.intValue();
										rsc_flora[k] += add;
										flora_change[k] += add;
		
									}
		
								}
		
							}
		
						}
		
						for (int k = 0; k < stats_mined.length; k++) {
		
							for (int l = 0; l < stats_mined[k].length; l += 4) {
		
								if (stats_mined[k][l] == (double)i) {
		
									if (rand.nextDouble() < stats_mined[k][l + 1]) {
		
										Double m = new Double(stats_mined[k][l + 3] + 1 - stats_mined[k][l + 2]);
										Double n = new Double(stats_mined[k][l + 2]);
										int add = rand.nextInt(m.intValue()) + n.intValue();
										rsc_mined[k] += add;
										mined_change[k] += add;
		
									}
		
								}
		
							}
		
						}
		
					}
		
				}
			
				System.out.println();
				System.out.println("Your search party discovered new resources and you now have: ");
				System.out.println("Land:  | " + rsc_land[choice - 1] + " " + names_land[choice - 1] + " (+25) | ");
				System.out.print("Fauna: | ");
				for (int i = 0; i < rsc_fauna.length; i++) {
		
					if (fauna_change[i] != 0) {
						
						System.out.print(rsc_fauna[i] + " " + names_fauna[i] + " (+" + fauna_change[i] + ") | ");
						
					}
		
				}
				System.out.println();
				System.out.print("Flora: | ");
				for (int i = 0; i < rsc_flora.length; i++) {
					
					if (flora_change[i] != 0) {
						
						System.out.print(rsc_flora[i] + " " + names_flora[i] + " (+" + flora_change[i] + ") | ");
		
					}
						
				}
				System.out.println();
				System.out.print("Mined: | ");
				for (int i = 0; i < rsc_mined.length; i++) {
		
					if (mined_change[i] != 0) {
					
						System.out.print(rsc_mined[i] + " " + names_mined[i] + " (+" + mined_change[i] + ") | ");
		
					}
						
				}
				System.out.println("");
				
			}
			
		}
		
	}
	
	// command CREATEDIVISION removed in favor of the more general DIVISION command
	
	void cmd_status() {
		
		if (military_announce == 0) {
			
			System.out.println("MILITARY STATUS");
			boolean none = true;
			boolean all = true;
			System.out.print("Current status: ");
			for (War war : wars) {
				
				if (war.active) {
					
					none = false;
					System.out.println("In war with " + war.name.toUpperCase() + " (war score is " + war.score[0] + "-" + war.score[1] + ")");
					System.out.println();
					
				} else {
					
					if (war.score[0] != 5) {
						
						all = false;
						
					}
					
				}
				
			}
			if (none) {
				
				if (!all) {
					
					System.out.println("Not in war (yet)...");
					
				} else {
					
					System.out.println("You rule the world!");
					
				}
				
			}
			
		} else if (military_announce == 1) {
			
			if (getTier() == 3) {
				
				System.out.println("MILITARY TUTORIAL");
				System.out.println("Welcome to your Diego Land's military!\nSince you have grown so far from the beginning, other neighboring nations are starting to perceive you as a potential threat.\nIt is now your job to defend your land from their attacks, and conquer your rivals.\nBefore wars unlock at Tier 4, you must build up your military by recruiting UNITS.\nUNITS are organized into 10-unit DIVISIONS, and these DIVISIONS are what fight for you.\nStart off by recruiting a UNIT using the RECRUIT command.\nThe command will walk you through purchasing one of the several kinds of UNITS and assigning it to a DIVISION.\nGood luck!");
				military_announce = 2;
				
			} else if (getTier() == 4) {
				
				System.out.println("WARS TUTORIAL");
				System.out.println("Hopefully you have built up your military to a size of considerable strength.\nStarting from today, other nations will be more and more tempted to attack and declare WAR on you every day.\nEXPEDITIONS also now come at a cost- they have a chance of discovering an enemy nation and initiating a war.\nDuring WAR, the enemy will regularly send DIVISIONS to attack you.\nYou must fend off their attacks by marking one of your DIVISIONS as READY-TO-DEFEND.\nThis division will automatically fight for you should an enemy division arrive (if the enemy arrives and no DIVISION is READY-TO-DEFEND, they automatically win the battle!)\nYou can also send divisions to attack your enemy, which they will defend against.\nIf you eliminate ALL of your enemy's UNITS, you win. If you go bankrupt during a war, they win.\nOtherwise, whoever wins 3 battles claims victory with a valuable reward! Good luck!");
				military_announce = 0;
				
			}
			
		}
		
	}
	
	void cmd_resourcesn() {
		
		System.out.println("NATURAL RESOURCES");
		System.out.println("Land:  | " + rsc_land[0] + " " + names_land[0] + " | " + rsc_land[1] + " " + names_land[1] + " | " + rsc_land[2] + " " + names_land[2] + " | " + rsc_land[3] + " " + names_land[3] + " | " + rsc_land[4] + " " + names_land[4] + " |");
		System.out.print("Fauna: | ");
		for (int i = 0; i < rsc_fauna.length; i++) {

			System.out.print(rsc_fauna[i] + " " + names_fauna[i] + " | ");

		}
		System.out.println();
		System.out.print("Flora: | ");
		for (int i = 0; i < rsc_flora.length; i++) {

			System.out.print(rsc_flora[i] + " " + names_flora[i] + " | ");

		}
		System.out.println();
		System.out.print("Mined: | ");
		for (int i = 0; i < rsc_mined.length; i++) {

			System.out.print(rsc_mined[i] + " " + names_mined[i] + " | ");

		}
		System.out.println("");
		
	}
	
	void cmd_clearland() {
		
		System.out.println("CLEAR LAND");
		System.out.print("Would you like to convert 1) Forest ($700, you have " + rsc_land[0] + "), 2) Desert ($500, you have " + rsc_land[1] + "), 3) Lake ($900, you have " + rsc_land[2] + "), or 4) Grassland ($300, you have " + rsc_land[3] + ") to Cleared Land? (0 to cancel) ");
		int choice = scan.nextInt();
		if (choice < 1 || choice > 4) {
			
			System.out.println("CLEARLAND cancelled");
			
		} else {
			
			int[] costs = {700, 500, 900, 300};
			System.out.print("Enter # to convert # of land, 0 to cancel: ");
			int land = scan.nextInt();
			if (land > rsc_land[choice - 1]) {
				
				System.out.println("You don't have enough land");
				System.out.println("CLEARLAND cancelled");
				
			} else if (land <= 0) {
				
				System.out.println("CLEARLAND cancelled");
				
			} else if (land * costs[choice - 1] > rsc[0]) {
				
				System.out.println("You don't have enough money");
				System.out.println("CLEARLAND cancelled");
				
			} else {
				
				rsc_land[choice - 1] -= land;
				rsc_land[4] += land;
				rsc[0] -= land * costs[choice - 1];
				System.out.println("Converted " + land + " " + names_land[choice - 1] + " to Cleared Land (you now have " + rsc_land[4] + ") for $" + (land * costs[choice - 1]) + " (you now have $" + rsc[0] + ")");
				
			}
			
		}
		
	}
	
	void cmd_techs() {
		
		System.out.println("TECHS");
		boolean valid = false;
		for (int i = 0; i < names_techs.length; i++) {
			
			System.out.print(names_techs[i][0] + ": ");
			if (techs[i] != 0) {
				
				if (techs[i] > 0) {
					
					if (techs[i] != max_techs[i]) {
						
						valid = true;
						System.out.println("Level " + fancy[techs[i] - 1]);
						
					} else {
						
						System.out.println("Level " + fancy[techs[i] - 1] + " (MAX)");
						
					}
					
				} else {
					
					System.out.println("Currently researching...");
					
				}
				
			} else {
				
				if (techs[i] != max_techs[i]) {
					
					valid = true;
					
				}
				System.out.println("No advancements yet...");
				
			}
			
		}
		if (buildings[0] == 0) {
			
			System.out.println("Construct the RESEARCH LAB first to research techs!");
			
		} else if (valid) {
			
			System.out.print("Would you like to research a tech? Y/N ");
			if (scan.next().equalsIgnoreCase("Y")) {
				
				int[] correspond = new int[techs.length];
				int last = 0;
				for (int i = 0; i < techs.length; i++) {
					
					if (techs[i] != max_techs[i] && techs[i] >= 0) {
						
						System.out.println((last + 1) + ") " + names_techs[i][0] + " " + fancy[techs[i]] + " ($" + costs_techs[i][techs[i]] + ", " + time_techs[i][techs[i]] + " day(s)): " + names_techs[i][1]);
						correspond[last] = i;
						last++;
					
					}
						
				}
				System.out.print("Type # to research #, 0 to cancel: ");
				int choice = scan.nextInt();
				if (choice > 0 && choice <= last) {
					
					int index = correspond[choice - 1];
					if (req_techs[index][techs[index]] < 0 && req_techs[index][techs[index]] * -1 > getTier()) {
						
						System.out.println("You must be Tier " + (req_techs[index][techs[index]] * -1) + " to research this");
						
					} else if (req_techs[index][techs[index]] > 0 && buildings[req_techs[index][techs[index]]] != 1) {
						
						System.out.println("You must have a " + names_buildings[req_techs[index][techs[index]]][0] + " to research this");
						
					} else if (rsc[0] < costs_techs[index][techs[index]]) {
						
						System.out.println("You don't have enough money");
						
					} else if (counter_techs != -1) {
						
						System.out.println("You must wait for " + names_techs[counter_techs][0] + " " + fancy[pending_techs] + " to finish researching before you can start new research (" + (techs[counter_techs] * -1) + " day(s))");
						
					} else {
						
						pending_techs = techs[index];
						techs[index] = time_techs[index][techs[index]] * -1;
						rsc[0] -= costs_techs[index][pending_techs];
						counter_techs = index;
						System.out.println("You are now researching " + names_techs[index][0] + " " + fancy[pending_techs] + "; it will take " + time_techs[index][pending_techs] + " day(s) to complete");
						System.out.println("You now have $" + rsc[0]);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	void cmd_buildings() {
		
		System.out.println("BUILDINGS");
		boolean any = false;
		for (int i = 0; i < buildings.length; i++) {
			
			if (buildings[i] == 1) {
				
				System.out.println(names_buildings[i][0] + "- " + names_buildings[i][1]);
				
			} else if (buildings[i] <= -1) {
				
				System.out.println(names_buildings[i][0] + "- under construction...");
				
			} else if (buildings[i] == 0 && req_buildings[i] <= getTier()) {
				
				String cost = "";
				for (int j = 0; j < costs_buildings[i].length; j++) {
					
					if (costs_buildings[i][j] != 0) {
						
						int[] relative = {0, 3, 5};
						cost += costs_buildings[i][j] + " " + rsc_names[relative[j]] + " ";
						
					}
					
				}
				System.out.println(names_buildings[i][0] + "- " + cost);
				any = true;
				
			} else if (buildings[i] == 0 && req_buildings[i] == getTier() + 1) {
				
				System.out.println(names_buildings[i][0] + "- unlock at Tier " + (getTier() + 1));
				
			}
			
		}
		if (any) {
			
			System.out.print("Would you like to construct a building? Y/N ");
			if (scan.next().equalsIgnoreCase("Y")) {
				
				System.out.print("Which of the above buildings would you like to construct? ");
				scan.nextLine();
				String name = scan.nextLine();
				boolean found = false;
				for (int i = 0; i < names_buildings.length && !found; i++) {
					
					if (name.equalsIgnoreCase(names_buildings[i][0])) {
						
						found = true;
						if (req_buildings[i] > getTier()) {
							
							System.out.println("You must be Tier " + req_buildings[i] + " to construct this");
							System.out.println("BUILDINGS cancelled");
							
						} else if (buildings[i] != 0) {
							
							System.out.println("You already have this building");
							System.out.println("BUILDINGS cancelled");
							
						} else {
							
							boolean valid = true;
							for (int j = 0; j < costs_buildings[i].length && valid; j++) {
								
								int[] relative = {0, 3, 5};
								if (rsc[relative[j]] < costs_buildings[i][j]) {
									
									valid = false;
									
								}
								
							}
							if (!valid) {
								
								System.out.println("You don't have enough resources");
								System.out.println("BUILDINGS cancelled");
								
							} else if (rsc_land[4] == 0) {
							
								System.out.println("You don't have enough land (buildings require 1 Cleared Land to build)");
								System.out.println("BUILDINGS cancelled");
							
							} else {
								
								String left = "";
								buildings[i] = time_buildings[i] * -1;
								for (int j = 0; j < costs_buildings[i].length; j++) {
									
									int[] relative = {0, 3, 5};
									rsc[relative[j]] -= costs_buildings[i][j];
									left += rsc[relative[j]] + " " + rsc_names[relative[j]] + " ";
									
								}
								rsc_land[4]--;
								System.out.println("A(n) " + names_buildings[i][0] + " is now under construction; it will be complete in " + time_buildings[i] + " days");
								System.out.println("You now have " + left);
								time++;
								
							}
							
						}
						
					}
					
				}
				if (!found) {
					
					System.out.println("Building not found");
					System.out.println("BUILDINGS cancelled");
					
				}
				
			}
			
		}
		
		
	}
	
	void cmd_divisions() {
		
		System.out.println("DIVISIONS");
		if (divisions.size() == 0) {
			
			System.out.print("No divisions yet! Create a division? Y/N ");
			if (scan.next().equalsIgnoreCase("Y")) {
				
				System.out.print("Name of new division: ");
				scan.nextLine();
				String name = scan.nextLine();
				if (name == "") {
					
					System.out.println("Invalid name");
					System.out.println("DIVISIONS cancelled");
					
				} else if (name.indexOf(",") != -1 || name.indexOf("\\") != -1) {
					
					System.out.println("Name contains invalid characters (, and \\ are not allowed)");
					System.out.println("DIVISIONS cancelled");
					
				} else {
					
					divisions.add(new Division(name));
					System.out.println("Created new division " + name);
					
				}
				
			} else {
				
				System.out.println("DIVISIONS cancelled");
				
			}
			
		} else {
			
			for (int i = 0; i < divisions.size(); i++) {
			
				String info = " empty";
				if (divisions.get(i).length() != 0) {
					
					info = "";
					int num = divisions.get(i).length();
					boolean truncate = false;
					if (num > 3) {
						
						num = 3;
						truncate = true;
						
					}
					for (int j = 0; j < num; j++) {
						
						info += " " + Unit.names_unit[divisions.get(i).get()[j].type];
						
					}
					if (truncate) {
						
						info += "... [" + divisions.get(i).length() + "]";
						
					} else {
						
						info += " [" + divisions.get(i).length() + "]";
						
					}
					
				}
				if (i == rtd) {
					
					System.out.println((i + 1) + ") " + divisions.get(i).name + " [RTD]:" + info);
				
				} else {
					
					System.out.println((i + 1) + ") " + divisions.get(i).name + ":" + info);
					
				}
				
			}
			System.out.print("0 to cancel, 1 to manage, 2 to inspect, 3 to create, 4 to change ready-to-defend status: ");
			int choice = scan.nextInt();
			if (choice == 1) {
				
				System.out.print("Enter division's # to manage: ");
				int manage = scan.nextInt();
				if (manage < 1 || manage > divisions.size()) {
					
					System.out.println("Invalid value entered");
					System.out.println("DIVISIONS cancelled");
					
				} else {
					
					manage--;
					System.out.print(divisions.get(manage).name + ": 0 to cancel, 1 to move/delete unit, 2 to reorder/swap, 3 to rename, 4 to delete: ");
					switch (scan.nextInt()) {
					
						case 1:
							if (divisions.get(manage).length() == 0) {
								
								System.out.println("This division has no units");
								System.out.println("DIVISIONS cancelled");
								
							} else {
								
								for (int i = 0; i < divisions.get(manage).length(); i++) {
									
									Unit cur = divisions.get(manage).get()[i];
									System.out.println((i + 1) + ") [ " + cur.hp + "/" + Unit.stats_unit[cur.type][3] + " HP ] " + Unit.names_unit[cur.type]);
									
								}
								System.out.print("0 to cancel, 1 to move between divisions (single unit, 2 for multiple units), 3 to reorder/swap, 4 to disband: ");
								int _choice = scan.nextInt();
								if (_choice <= 0 || _choice > 4) {
									
									System.out.println("DIVISIONS cancelled");
									
								} else if ((_choice == 1 || _choice == 2) && divisions.size() == 1) {
									
									System.out.println("Can't move units to other divisions since there are no others");
									System.out.println("DIVISIONS cancelled");
									
								} else {
										
										switch (_choice) {
										
											case 1:
												System.out.print("Which unit to perform action on? ");
												int __choice = scan.nextInt();
												int[] arr = new int[divisions.size()];
												int count = 0;
												if (__choice < 1 || __choice > divisions.get(manage).length()) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													
												} else {
													
													System.out.println("Listing non-full divisions:");
													for (int i = 0; i < divisions.size(); i++) {
														
														if (divisions.get(i).length() != 10 && i != manage) {
															
															arr[count] = i;
															count++;
															String info = " empty";
															if (divisions.get(i).length() != 0) {
																
																info = "";
																int num = divisions.get(i).length();
																boolean truncate = false;
																if (num > 3) {
																	
																	num = 3;
																	truncate = true;
																	
																}
																for (int j = 0; j < num; j++) {
																	
																	info += " " + Unit.names_unit[divisions.get(i).get()[j].type];
																	
																}
																if (truncate) {
																	
																	info += "... [" + divisions.get(i).length() + "]";
																	
																} else {
																	
																	info += " [" + divisions.get(i).length() + "]";
																	
																}
																
															}
															if (i == rtd) {
																
																System.out.println((count) + ") " + divisions.get(i).name + " [RTD]:" + info);
															
															} else {
																
																System.out.println((count) + ") " + divisions.get(i).name + ":" + info);
																
															}
															
														}
														
													}
													System.out.print("Which division would you like to move " + Unit.names_unit[divisions.get(manage).units[__choice - 1].type] + " to? ");
													int move = scan.nextInt();
													if (move < 1 || move > count) {
														
														System.out.println("Invalid value entered");
														System.out.println("DIVISIONS cancelled");
														
													} else {
														
														divisions.get(arr[move - 1]).add(divisions.get(manage).units[__choice - 1]);
														System.out.println(Unit.names_unit[divisions.get(manage).units[__choice - 1].type] + " moved to " + divisions.get(arr[move - 1]).name);
														divisions.get(manage).units[__choice - 1] = null;
														
													}
																										
												}
												break;
												
											case 2:
												System.out.println("Input the range of unit #'s which will be moved...");
												System.out.print("Enter lower #: ");
												int lower = scan.nextInt();
												if (lower < 1 || lower > divisions.get(manage).length()) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													break;
													
												}
												System.out.print("Enter higher #: ");
												int high = scan.nextInt();
												if (high < 1 || high > divisions.get(manage).length() || high <= lower) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													break;
													
												}
												System.out.println("Listing valid divisions:");
												arr = new int[divisions.size()];
												count = 0;
												for (int i = 0; i < divisions.size(); i++) {
													
													if (divisions.get(i).length() != 10 && i != manage && divisions.get(i).length() + (high - lower + 1) <= 10) {
														
														arr[count] = i;
														count++;
														String info = " empty";
														if (divisions.get(i).length() != 0) {
															
															info = "";
															int num = divisions.get(i).length();
															boolean truncate = false;
															if (num > 3) {
																
																num = 3;
																truncate = true;
																
															}
															for (int j = 0; j < num; j++) {
																
																info += " " + Unit.names_unit[divisions.get(i).get()[j].type];
																
															}
															if (truncate) {
																
																info += "... [" + divisions.get(i).length() + "]";
																
															} else {
																
																info += " [" + divisions.get(i).length() + "]";
																
															}
															
														}
														if (i == rtd) {
															
															System.out.println((count) + ") " + divisions.get(i).name + " [RTD]:" + info);
														
														} else {
															
															System.out.println((count) + ") " + divisions.get(i).name + ":" + info);
															
														}
														
													}
													
												}
												System.out.print("Which division to move units to? ");
												int move = scan.nextInt();
												if (move < 1 || move > count) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													
												} else {
													
													for (int i = lower - 1; i < high; i++) {
														
														divisions.get(arr[move - 1]).add(divisions.get(manage).units[i]);
														
													}
													for (int i = lower - 1; i < high; i++) {
														
														divisions.get(manage).units[i] = null;
														
													}
													System.out.println((high - lower + 1) + " unit(s) moved to " + divisions.get(arr[move - 1]).name);
													
												}
												break;
											
											case 3:
												System.out.print("Swap which unit? ");
												__choice = scan.nextInt();
												if (__choice < 1 || __choice > divisions.get(manage).length()) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													
												} else {
													
													System.out.print("With which unit? ");
													int swap = scan.nextInt();
													if (swap < 1 || swap > divisions.get(manage).length()) {
														
														System.out.println("Invalid value entered");
														System.out.println("DIVISIONS cancelled");
														
													} else if (swap == __choice) {
														
														System.out.println("Can't swap a unit with itself");
														System.out.println("DIVISIONS cancelled");
														
													} else {
														
														Unit _swap = divisions.get(manage).units[__choice - 1];
														divisions.get(manage).units[__choice - 1] = divisions.get(manage).units[swap - 1];
														divisions.get(manage).units[swap - 1] = _swap;
														System.out.println(Unit.names_unit[_swap.type] + " was swapped with " + Unit.names_unit[divisions.get(manage).units[__choice - 1].type]);
														
													}
													
												}
												break;
												
											case 4:
												System.out.print("Disband which unit? ");
												__choice = scan.nextInt();
												if (__choice < 1 || __choice > divisions.get(manage).length()) {
													
													System.out.println("Invalid value entered");
													System.out.println("DIVISIONS cancelled");
													
												} else {
													
													System.out.println("Disband " + Unit.names_unit[divisions.get(manage).units[__choice - 1].type] + "?");
													String back = "";
													int _type = divisions.get(manage).units[__choice - 1].type;
													for (int i = 0; i < Unit.cost_unit[_type].length - 1; i++) {
														
														if (Unit.cost_unit[_type][i] > 1) {
															
															back += (Unit.cost_unit[_type][i] / 3 * 2) + " " + rsc_names[Unit.ref_unit[i]] + " ";
															
														}
														
													}
													if (back != "") {
														
														System.out.println("You will receive 66% of the original cost: " + back);
														
													}
													System.out.print("Confirm Y/N ");
													if (scan.next().equalsIgnoreCase("Y")) {
														
														String _new = "";														
														for (int i = 0; i < Unit.cost_unit[_type].length - 1; i++) {
															
															if (Unit.cost_unit[_type][i] > 1) {
																
																int old = rsc[Unit.ref_unit[i]];
																rsc[Unit.ref_unit[i]] += Unit.cost_unit[_type][i] / 3 * 2;
																_new = rsc[Unit.ref_unit[i]] + " " + rsc_names[rsc[Unit.ref_unit[i]]] + " (+" + (rsc[Unit.ref_unit[i]] - old) + ") ";
																
															}
															
														}
														divisions.get(manage).units[__choice - 1] = null;
														System.out.println("Disbanded " + Unit.names_unit[_type]);
														System.out.println("You now have " + _new);
														
													} else {
														
														System.out.println("DIVISIONS cancelled");
														
													}
													
												}
												break;
												
										}
										
									}
									
								}
								
							break;
							
						case 2:
							System.out.print("Swap " + divisions.get(manage).name + " with which division? (Enter #) ");
							int swap = scan.nextInt();
							if (swap < 1 || swap > divisions.size()) {
								
								System.out.println("Invalid value entered");
								System.out.println("DIVISIONS cancelled");
								
							} else if (swap - 1 == manage) {
								
								System.out.println("You can't swap a division with itself");
								System.out.println("DIVISIONS cancelled");
								
							} else {
								
								swap--;
								Division change = divisions.get(manage);
								divisions.set(manage, divisions.get(swap));
								divisions.set(swap, change);
								System.out.println(divisions.get(swap).name + " swapped with " + divisions.get(manage).name);
								
							}
							break;
							
						case 3:
							System.out.print("Enter a new division name: ");
							scan.nextLine();
							String name = scan.nextLine();
							if (name == "") {
								
								System.out.println("Invalid name");
								System.out.println("DIVISIONS cancelled");
								
							} else if (name.indexOf(",") != -1 || name.indexOf("\\") != -1) {
								
								System.out.println("Name contains invalid characters (, and \\ are not allowed)");
								System.out.println("DIVISIONS cancelled");
								
							} else {
								
								String old = divisions.get(manage).name;
								divisions.get(manage).name = name;
								System.out.println(old + " renamed to " + name);
								
							}
							break;
							
						case 4:
							boolean empty = true;
							for (int i = 0; i < 10; i++) {
								
								if (divisions.get(manage).units[i] != null) {
									
									empty = false;
									break;
									
								}
								
							}
							if (empty) {
								
								System.out.println(divisions.get(manage).name + " deleted");
								divisions.remove(manage);
								
							} else {
								
								System.out.println(divisions.get(manage).name + " still has units in it, move/disband these units to a different division first");
								System.out.println("DIVISIONS cancelled");
								
							}
							break;
							
						default:
							System.out.println("DIVISIONS cancelled");
					
					}
					
				}
				
			} else if (choice == 2) {
				
				System.out.print("Enter division # to inspect: ");
				int _choice = scan.nextInt();
				if (_choice <= 0 || _choice > divisions.size()) {
					
					System.out.println("Invalid value entered");
					System.out.println("DIVISIONS cancelled");
					
				} else {
					
					_choice--;
					System.out.println(divisions.get(_choice).name.toUpperCase());
					if (divisions.get(_choice).length() == 0) {
						
						System.out.println("(empty division)");
						
					} else {
						
						for (int i = 0; i < divisions.get(_choice).length(); i++) {
							
							Unit cur = divisions.get(_choice).get()[i];
							System.out.println("[ " + Unit.stats_unit[cur.type][0] + " A | " + Unit.stats_unit[cur.type][1] + " D | " + Unit.stats_unit[cur.type][2] + " S | " + cur.hp + "/" + Unit.stats_unit[cur.type][3] + " HP ] " + Unit.names_unit[cur.type]);
							
						}
						
					}
					
				}
				
			} else if (choice == 3) {
				
				System.out.print("Name of new division: ");
				scan.nextLine();
				String name = scan.nextLine();
				if (name == "") {
					
					System.out.println("Invalid name");
					System.out.println("DIVISIONS cancelled");
					
				} else if (name.indexOf(",") != -1 || name.indexOf("\\") != -1) {
					
					System.out.println("Name contains invalid characters (, and \\ are not allowed)");
					System.out.println("DIVISIONS cancelled");
					
				} else {
					
					divisions.add(new Division(name));
					System.out.println("Created new division " + name);
					
				}
				
			} else if (choice == 4) {
				
				System.out.print("Which division # should be ready-to-defend? (-1 to indicate no division is ready-to-defend) ");
				int _choice = scan.nextInt();
				if (_choice > divisions.size() || _choice == 0 || _choice < -1) {
					
					System.out.println("Invalid value entered");
					System.out.println("DIVISIONS cancelled");
					
				} else if (divisions.get(_choice - 1).length() == 0) {
					
					System.out.println("Empty divisions can't be ready-to-defend");
					System.out.println("DIVISIONS cancelled");
					
				} else if (_choice != -1) {
					
					rtd = _choice - 1;
					System.out.println(divisions.get(_choice - 1).name + " marked as ready-to-defend");
					
				} else {
					
					rtd = -1;
					System.out.println("No divisions are ready-to-defend anymore");
					
				}
				
			}
			
		}
		
	}
	
	void cmd_recruit() {
		
		System.out.println("RECRUIT UNITS");
		String[] _names = {"Money", "Ammo", "BM", "Metal", "Fuel", "Uranium"};
		int[] actual = new int[9];
		int count = 0;
		if (buildings[1] != 1 && techs[0] != 1) {
			
			System.out.println("You need to research Military I and construct a Barracks before you are able to recruit basic units.\nCome back later after this is done!");
			
		} else {
			
			if (military_announce == 2) {
				
				System.out.println("\nFirstly, let's recruit an INFANTRY.\nThese are the most basic unit of the game.\nBelow, you can see their stats next to their name: 3 A | 3 D | 1 S | 20 HP.\nThe A stands for ATTACK. This determines the base damage that your unit deals.\nThe D stands for DEFENSE. This value is used to calculate how much damage is reduced from an attack on your unit.\nThe S stands for SPEED. This affects the order in which units attack in a battle, and also the accuracy of your unit's attack.\nFinally, HP is the base health that your unit starts off at when it is first purchased.\nIt is also the maximum health that your unit can heal up to (more on this later).\nIn the rest of the description, you can see the one-time COST that the unit requires to hire/construct, and its UPKEEP which is used by the unit every day.\nSome units have special traits and they are denoted with a * next to its name.\nIn addition, all units cost 2 food to maintain.\nStart off by typing 1 in the dialog to purchase an INFANTRY.\n");
				
			}
			
			for (int i = 0; i < Unit.names_unit.length; i++) {
				
				if (techs[0] >= (Unit.req_unit[i])[0] && buildings[(Unit.req_unit[i])[1]] == 1) {
					
					actual[count] = i;
					count++;
					String stats = (Unit.stats_unit[i])[0] + " A | " + (Unit.stats_unit[i])[1] + " D | " + (Unit.stats_unit[i])[2] + " S | " + (Unit.stats_unit[i])[3] + " HP";
					String cost = "";
					for (int j = 0; j < Unit.cost_unit[i].length - 1; j++) {
						
						if ((Unit.cost_unit[i])[j] != 0) {
							
							cost += " " + (Unit.cost_unit[i])[j] + " " + _names[j];
							
						}
						
					}
					cost += ", " + (Unit.cost_unit[i])[6] + " day(s)";
					String upkeep = "";
					for (int j = 0; j < Unit.upkeep_unit[i].length; j++) {
						
						if ((Unit.upkeep_unit[i])[j] != 0) {
							
							upkeep += " " + (Unit.upkeep_unit[i])[j] + " " + _names[j];
							
						}
						
					}
					if (i == 1 || i == 3 || i == 4 || i == 6 || i == 7) {
						
						System.out.println((i + 1) + ") " + Unit.names_unit[i] + "* [ " + stats + " ]:" + cost + " (upkeep" + upkeep + ")");
					
					} else {
						
						System.out.println((i + 1) + ") " + Unit.names_unit[i] + " [ " + stats + " ]:" + cost + " (upkeep" + upkeep + ")");
						
					}
						
				}
				
			}
			System.out.print("Enter unit # to purchase, 0 to cancel: ");
			int choice = scan.nextInt();
			if (choice <= 0 || choice > count) {
				
				System.out.println("RECRUIT cancelled");
				
			} else {
				
				choice = actual[choice - 1];
				boolean valid = true;
				for (int i = 0; i < Unit.cost_unit[choice].length - 1; i++) {
					
					if (rsc[Unit.ref_unit[i]] < (Unit.cost_unit[choice])[i]) {
						
						valid = false;
						break;
						
					}
					
				}
				if (!valid) {
					
					System.out.println("You don't have enough resources");
					System.out.println("RECRUIT cancelled");
					
				} else {
					
					if (military_announce == 2) {
						
						if (choice == 0) {
							
							System.out.println("\nNice! You're one step away from recruiting your first unit!\nNow, you must select a division to place the unit into upon hire/construction completion.\nEnter 1 to select Division #1 below.\n");
						
						} else {
							
							military_announce = 0;
							
						}
							
					}
					if (choice == 1) {
						
						System.out.println("Medic Special Trait: Has a 40% chance to heal an infantry-class unit by 6-12 HP");
						
					} else if (choice == 3) {
						
						System.out.println("Grenadier Special Trait: Can damage 0-4 additional non-air units by 10-55% original damage");
						
					} else if (choice == 4) {
						
						System.out.println("Tank Special Trait: Has 8 attack against infantry-class units");
						
					} else if (choice == 6) {
						
						System.out.println("Bomber Special Trait: 0 attack against air units but damages up to 4 units at a time");
						
					} else if (choice == 7) {
						
						System.out.println("T-80 Rodriguez Special Trait: Has 13 attack against non-air units");
						
					}
					System.out.println("Place unit into...");
					for (int i = 0; i < divisions.size(); i++) {
					
						System.out.println((i + 1) + ") " + divisions.get(i).name + " (" + divisions.get(i).length() + " unit(s))");
						
					}
					System.out.print("Enter division # to place unit into: ");
					int _choice = scan.nextInt();
					if (_choice < 1 || _choice > divisions.size()) {
						
						System.out.println("Invalid value entered");
						System.out.println("RECRUIT cancelled");
						
					} else {
						
						String _new = "";
						for (int i = 0; i < Unit.cost_unit[choice].length - 1; i++) {
							
							if ((Unit.cost_unit[choice])[i] != 0) {
								
								rsc[Unit.ref_unit[i]] -= (Unit.cost_unit[choice])[i];
								_new += " " + rsc[Unit.ref_unit[i]] + " " + rsc_names[Unit.ref_unit[i]];
								
							}
							
						}
						pending_unit.add(new UnitPending(choice, (Unit.cost_unit[choice])[6], divisions.get(_choice - 1).id));
						System.out.println("Recruited a(n) " + Unit.names_unit[choice] + " into " + divisions.get(_choice - 1).name);
						System.out.println("You now have" + _new + "; the recruitment will take " + Unit.cost_unit[choice][6] + " day(s)");
						time++;
						if (military_announce == 2) {
							
							System.out.println("\nAwesome!\nNow you must wait a day for the INFANTRY to finish recruitment (recruitment starts on completion of today).\nAfter it is finished, you'll see a message in the DAY OVER screen, and you should then run DIVISIONS to see the DIVISIONS tutorial.");
							military_announce = 3;
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}

	// COMMAND HANDLER

	void command(String command) {
		
		switch (command.toUpperCase()) {
		
		case "RESOURCES":
		case "R":
			cmd_resources();
			break;

		case "CONSTRUCT":
			System.out.println("Trying to construct? Use CONSTRUCTF for factories and CONSTRUCTB for buildings.");
			break;

		case "CONSTRUCTF":
		case "CF":
			cmd_constructf();
			break;
			
		case "FACTORIES":
		case "F":
			cmd_factories();
			break;
			
		case "STATS":
		case "S":
			cmd_stats();
			break;
		
		case "PASS":
		case "P":
			System.out.println("Day passed!\n");
			System.out.println("***");
			time = apd;
			break;
			
		case "EXPANDBORDERS":
		case "EXPAND":
		case "EB":
			if (getTier() == 1) {
				
				System.out.println("You must be Tier 2 or above to do this...");
				
			} else {
				
				cmd_expand();
				
			}
			break;
			
		case "EXPEDITION":
		case "EX":
			if (getTier() < 4) {
				
				System.out.println("You must be Tier 4 or above to do this...");
				
			} else {
				
				cmd_expedition();
				
			}
			break;
			
		case "RESOURCESN":
		case "RN":
			cmd_resourcesn();
			break;
			
		case "CLEARLAND":
		case "CL":
		case "CLEAR":
			cmd_clearland();
			break;
			
		case "TECHS":
		case "RESEARCH":
		case "T":
			cmd_techs();
			break;
			
		case "CONSTRUCTB":
		case "BUILDINGS":
		case "CB":
		case "B":
			cmd_buildings();
			break;
			
		case "TIME":
			System.out.println("Time: " + time + " (remaining commands: " + (apd - time) + ")\nAPD: " + apd);
			break;
			
		case "STATUS":
		case "MILITARY":
		case "M":
			if (getTier() < 3) {
				
				System.out.println("You must be Tier 3 or above to do this...");
				
			} else {
				
				cmd_status();
			
			}	
			break;
			
		case "DIVISIONS":
		case "D":
		case "UNITS":
			if (getTier() < 3) {
				
				System.out.println("You must be Tier 3 or above to do this...");
				
			} else {
				
				cmd_divisions();
			
			}	
			break;
			
		case "RECRUIT":
		case "RC":
			if (getTier() < 3) {
				
				System.out.println("You must be Tier 3 or above to do this...");
				
			} else {
				
				cmd_recruit();
			
			}	
			break;
			
		case "HELP":
		case "COMMANDS":
		case "H":
		case "?":
			System.out.println("LIST OF COMMANDS");
			System.out.println("RESOURCES- view resource stockpiles");
			System.out.println("CONSTRUCTF- buy factories");
			System.out.println("FACTORIES- view status, collect from, and sell factories");
			System.out.println("STATS- view in-game stats");
			System.out.println("RESOURCESN- view natural resources");
			System.out.println("CLEARLAND- convert land to cleared land");
			System.out.println("TECHS- view/research technologies");
			System.out.println("PASS- skip day");
			if (getTier() != 1) {
				
				System.out.println("EXPANDBORDERS- expand your borders");
				
			}
			if (getTier() >= 4) {
				
				System.out.println("EXPEDITION- launch an expedition");
				
			}
			if (getTier() >= 3) {
				
				System.out.println("STATUS- view your military's current status");
				System.out.println("DIVISIONS- view, create, and manage your divisions/units");
				System.out.println("RECRUIT- recruit units");
				
			}
			System.out.println("TIME- view current in-game time");
			System.out.println("HELP- view this list");
			System.out.println("Most commands have shorthand aliases (usually their first letter)");
			System.out.println("\nStill need help? Run TIPS to see some starter tips.");
			break;
			
		case "TIPS":
			System.out.println("TIPS AND GAME MECHANICS");
			System.out.println("Your Diego Land has a stockpile of nine different resources: money, food, power, building materials, consumer goods, metal, ammunition, fuel, and uranium.");
			System.out.println("In addition, you also have natural resources such as bears, lakes, cacti, etc.");
			System.out.println("You can spend/gain stockpiled resources through factories, but you can't replenish your natural resources except for when you expand your borders, available at Tier 2.");
			System.out.println("Therefore, you must manage your resources carefully! Don't construct too many factories which will drain your resources very quickly, but don't construct too little or you will run out.");
			System.out.println("\nYour population grows every day based on your food stockpile. When your population hits certain milestones, you advance a tier and unlock new capabilities.");
			System.out.println("Your population also consumes food, power, and (later in the game) consumer goods, so make sure they have enough.");
			System.out.println("They will also pay taxes, which becomes your money. Use money to buy factories, research technologies, and more.");
			System.out.println("\nEventually, your Diego Land will have grown to the point where neighboring nations consider you a threat.");
			System.out.println("For this reason, you will need to build up a military.");
			System.out.println("You must recruit units (which use up extra money, food, ammunition, fuel, and/or uranium) to defend and fight for you.");
			System.out.println("But don't worry! Wars start late-game, which is why you should use your starting days to stockpile resources and develop your Diego Land.");
			System.out.println("\nGood luck and have fun!");
			break;
			
		default:
			System.out.println("Invalid command entered, run HELP to view a list of commands");

		}

	}

	// GAME LOOP

	boolean announce = false;
	
	void GameLoop() {

		while (run) {

			if (announce) {
				
				String[] features = {"-Consumer goods production unlocked\n-Metal production unlocked\n-Expand borders unlocked", "-Consumer goods population requirement\n! MILITARY UNLOCKED (run STATUS for more important information) !\n-Ammunition production unlocked", "-Expeditions unlocked\n! WARS UNLOCKED (run STATUS for more important info) !", "-Fuel production unlocked", "-???", "-Uranium production unlocked", "-You have reached the population cap! Great job!"};
				System.out.println("*** TIER " + getTier() + " UNLOCKED!!! ***");
				System.out.println("New Features:");
				System.out.println(features[getTier() - 2]);
				System.out.println();
				if (getTier() != 8) {
					
					System.out.println("Next Tier: " + (getTier() + 1) + " (" + tiers[getTier()] + " pop.)");
					System.out.println();
					
				}
				System.out.println("***");
				System.out.println();
				announce = false;
				if (getTier() == 3) {
					
					divisions.add(new Division("Division #1"));
					military_announce = 1;
					
				} else if (getTier() == 4) {
					
					military_announce = 1;
					
				}
				
			} else {
				
				System.out.println("***");
				System.out.println();
				
			}
			
			System.out.println("DAY " + day);
			System.out.println();

			if (day == 1) {

				System.out.println("Welcome to Diego Land!");
				System.out.println("Your job is to allow your newly generated virtual nation to thrive.");
				System.out.println("You must manage your resources, build up a military, and survive attacks from neighboring rivals.");
				System.out.println("To do this, you can type in various COMMANDS every turn.");
				System.out.println("Start off by typing in the RESOURCES command below.");
				System.out.println();

			}

			for (time = 0; time < apd;) {

				System.out.print("Command: ");
				String command = scan.next();
				System.out.println();
				command(command);
				System.out.println();

			}

			for (int i = 0; i < factories.length; i++) {
				
				if (factories[i] != 0) {
					
					factories_lastCollected[i]++;
					
				}
				
			}
			int oldTier = getTier();
			if (population + growth <= tiers[tiers.length - 1]) {
				
				population += growth;
			
			}	
			if (getTier() > oldTier) {
				
				announce = true;
				
			}
			for (int i = 0; i < rsc.length; i++) {
				
				if (rsc[i] + rsc_change[i] < 0) {
					
					rsc[i] = 0;
					
				} else {
					
					rsc[i] += rsc_change[i];
					
				}
				
			}
			happiness = 2;
			if (rsc[0] == 0) {
				
				happiness -= 4;
				
			}
			if (rsc[1] == 0) {
				
				happiness -= 4;
				
			}
			if (rsc[2] == 0) {
				
				happiness -= 2;
				
			}
			if (rsc[4] == 0 && getTier() >= 3) {
				
				happiness -= 3;
			
			}
			happiness += (techs[2] >= 0) ? (techs[2] * 0.5) : ((pending_techs) * 0.5);
			int oldtax = tax;
			int oldusage = usage;
			tax = round(population + (happiness * population / 2));
			if (counter_techs != 4) {
				
				growth = round(rsc[1] * ((techs[4] + 1) * 0.01));
				
			} else {
				
				growth = round(rsc[1] * ((pending_techs + 1) * 0.01));
				
			}
			usage = round(population * 0.1);
			rsc_change[2] = round(population * -0.05);
			if (getTier() >= 3) {
				
				rsc_change[4] = -1 * round(population * 0.02);
				
			}
			rsc_change[0] = rsc_change[0] - oldtax + tax;
			rsc_change[1] = rsc_change[1] + oldusage - usage;
			scan.nextLine();
			System.out.println("DAY " + day + " OVER!");
			System.out.println("New population: " + population + " (new growth: +" + growth + ")");
			String[] shorts = {"$", "F", "P", "BM", "CG", "M", "A", "FL", "U"};
			for (int i = 0; i < rsc.length; i++) {
				
				String sign = (rsc_change[i] >= 0) ? "+" : "";
				System.out.print(shorts[i] + " " + rsc[i] + " (" + sign + rsc_change[i] + ")   ");
				
			}
			String sign = (happiness >= 0) ? "+" : "";
			System.out.println("\nHappiness: " + sign + happiness);
			System.out.println();
			for (int i = 0; i < pending_unit.size(); i++) {
				
				pending_unit.get(i).time--;
				if (pending_unit.get(i).time >= 0) {
					
					System.out.println("Your " + Unit.names_unit[pending_unit.get(i).type] + " still has " + (pending_unit.get(i).time + 1) + " day(s) of recruitment left");
					
				} else {
					
					int division = -1;
					for (int j = 0; j < divisions.size(); j++) {
						
						System.out.println(divisions.get(j).name + " id is " + divisions.get(j).id);
						if (divisions.get(j).id == pending_unit.get(i).division_id) {
							
							division = j;
							break;
							
						}
						
					}
					if (division == -1) {
						
						divisions.add(new Division("Division #" + (divisions.size() + 1)));
						divisions.get(divisions.size() - 1).add(new Unit(pending_unit.get(i).type, (Unit.stats_unit[pending_unit.get(i).type])[3]));
						division = divisions.size() - 1;
						System.out.println("Your " + Unit.names_unit[pending_unit.get(i).type] + " has finished construction, and since " + divisions.get(division).name + " was deleted, it was placed into a new division: " + divisions.get(divisions.size() - 1).name);
						
					} else {
						
						if (divisions.get(division).add(new Unit(pending_unit.get(i).type, (Unit.stats_unit[pending_unit.get(i).type])[3]))) {
							
							System.out.println("Your " + Unit.names_unit[pending_unit.get(i).type] + " has finished construction and has been placed into " + divisions.get(division).name);
							
						} else {
							
							divisions.add(new Division("Division #" + (divisions.size() + 1)));
							divisions.get(divisions.size() - 1).add(new Unit(pending_unit.get(i).type, (Unit.stats_unit[pending_unit.get(i).type])[3]));
							System.out.println("Your " + Unit.names_unit[pending_unit.get(i).type] + " has finished construction, and since " + divisions.get(division).name + " was full, it was placed into a new division: " + divisions.get(divisions.size() - 1).name);
							
						}
						
					}
					pending_unit.remove(i);
					i--;
					
				}
				System.out.println();
				
			}
			for (int i = 0; i < buildings.length; i++) {
				
				if (buildings[i] <= -1) {
					
					buildings[i]++;
					if (buildings[i] == 0) {
						
						buildings[i] = 1;
						System.out.println("Your " + names_buildings[i][0] + " is now complete!");
						System.out.println();
						
					} else {
						
						System.out.println("Your " + names_buildings[i][0] + " still has " + (buildings[i] * -1) + " day(s) of construction left");
						System.out.println();
						
					}
					
				}
				
			}
			if (counter_techs != -1) {
				
				techs[counter_techs]++;
				if (techs[counter_techs] == 0) {
					
					techs[counter_techs] = pending_techs + 1;
					System.out.println("You have finished researching " + names_techs[counter_techs][0] + " " + fancy[pending_techs] + "!");
					if (counter_techs == 1) {
						
						apd++;
						
					};
					counter_techs = -1;
					pending_techs = -1;
					System.out.println();
					
				} else {
					
					System.out.println("You have " + (techs[counter_techs] * -1) + " day(s) left to finish researching " + names_techs[counter_techs][0] + " " + fancy[pending_techs]);
					System.out.println();
					
				}
				
			}
			System.out.println("Press ENTER to save and continue to DAY " + (day + 1) + "...");
			if (scan.nextLine().equals("DELETE")) {
				
				System.out.print("Confirm Y/N ");
				if (scan.next().equalsIgnoreCase("Y")) {
					
					System.out.println("Deleting data...");
					population = 20;
					day = 1;
					apd = 4;
					data_debug = 0;
					rsc = new int[]{5000, 200, 50, 40, 20, 20, 0, 0, 0};
					rsc_land = new int[]{0, 0, 0, 0, 20};
					rsc_fauna = new int[3 + 1];
					rsc_flora = new int[4 + 1];
					rsc_mined = new int[9 + 1];
					factories = new int[factory_templates.length];
					factories_lastCollected = new int[factory_templates.length];
					rsc_change = new int[]{tax, growth * -1, -1, 0, 0,0, 0, 0, 0};
					happiness = 2;
					tax = 22;
					growth = 2;
					usage = 2;
					techs = new int[5];
					pending_techs = -1;
					counter_techs = -1;
					announce = false;
					buildings = new int[5];
					wars = new War[]{new War("Masekovia"), new War("Bell Republic"), new War("Goldedge"), new War("Isle of Vaktovia"), new War("Davis Land")};
					divisions = new ArrayList<Division>();
					rtd = -1;
					military_announce = 0;
					pending_unit = new ArrayList<UnitPending>();
					Division._id = 0;
					try {
						
						saveData();
						System.out.println("Data deleted! Please restart game...");
						System.out.println();
						System.out.println("***");
						System.out.println();
						System.exit(0);
						
					} catch (IOException e) {
						
						System.out.println("Error: data could not be deleted; please restart game");
						e.printStackTrace();
						System.exit(-1);
						
					}
					
				} else {
					
					System.out.println();
					
				}
				
			}
			day++;
			try {
				
				data_debug = 1;
				saveData();
				
			} catch (IOException e) {
				
				data_debug = 0;
				System.out.println("Error: data could not be saved");
				e.printStackTrace();
				
			}
			
		}

	}
	
	// SAVE/LOAD
	
	int data_debug = 0;
	
	void saveData() throws IOException {
		
		String csvFile = "save.csv";
		Saving save = new Saving();

		FileWriter writer = new FileWriter(csvFile);

		save.Save(population, writer);
		save.Save(day, writer);
		save.Save(data_debug, writer);
		save.Save(apd, writer);

		save.Save(rsc, writer);
		save.Save(rsc_land, writer);
		save.Save(rsc_fauna, writer);
		save.Save(rsc_flora, writer);
		save.Save(rsc_mined, writer);
		
		save.Save(factories, writer);
		save.Save(factories_lastCollected, writer);
		
		save.Save(rsc_change, writer);
		save.Save(growth, writer);
		save.Save(tax, writer);
		save.Save(usage, writer);
		save.Save((int)(happiness * 2), writer);
		
		save.Save(techs, writer);
		save.Save(counter_techs, writer);
		save.Save(pending_techs, writer);
		save.Save(buildings, writer);
		int _announce = (!announce) ? 0 : 1;
		save.Save(_announce, writer);
		
		save.Save(military_announce, writer);
		save.Save(rtd, writer);
		save.Save(writer, pending_unit);
		save.Save(wars, writer);
		save.Save(divisions, writer);

		writer.flush();
        writer.close();
        
	}
	
	boolean loadData() {
		
		String csvFile = "save.csv";
		String line = "";
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			
			Division._id = 0;
			int count = 0;
			while ((line = br.readLine()) != null) {
				
				count++;
				// use comma as separator
				String[] data = line.split(csvSplitBy);
				
				if (count < 24) {
					
					int[] int_data = new int[data.length];
					for (int i = 0; i < int_data.length; i++) {
				
						int_data[i] = Integer.parseInt(data[i]);
							
					}
					// TO-DO turn into a switch
					if (count == 1) {
							
						population = int_data[0];
						
					} else if (count == 2) {
						
						day = int_data[0];
					
					} else if (count == 3) {
						
						data_debug = int_data[0];
					
					} else if (count == 4) {
						
						apd = int_data[0];
						
					} else if (count == 5) {
						
						rsc = int_data;
						
					} else if (count == 6) {
						
						rsc_land = int_data;
						
					} else if (count == 7) {
						
						rsc_fauna = int_data;
						
					} else if (count == 8) {
						
						rsc_flora = int_data;
						
					} else if (count == 9) {
						
						rsc_mined = int_data;
						
					} else if (count == 10) {
						
						factories = int_data;
						
					} else if (count == 11) {
						
						factories_lastCollected = int_data;
						
					} else if (count == 12) {
						
						rsc_change = int_data;
						
					} else if (count == 13) {
						
						growth = int_data[0];
						
					} else if (count == 14) {
						
						tax = int_data[0];
						
					} else if (count == 15) {
						
						usage = int_data[0];
						
					} else if (count == 16) {
						
						happiness = int_data[0] / 2.0;
						
					} else if (count == 17) {
						
						techs = int_data;
						
					} else if (count == 18) {
						
						counter_techs = int_data[0];
						
					} else if (count == 19) {
						
						pending_techs = int_data[0];
					
					} else if (count == 20) {
						
						buildings = int_data;
						
					} else if (count == 21) {
						
						announce = (int_data[0] == 0) ? false : true;
						
					} else if (count == 22) {
						
						military_announce = int_data[0];
						
					} else if (count == 23) {
						
						rtd = int_data[0];
						
					}
					
				} else if (count == 24) {
					
					for (int i = 1; i < data.length; i += 3) {
						
						pending_unit.add(new UnitPending(Integer.parseInt(data[i]), Integer.parseInt(data[i + 1]), Integer.parseInt(data[i + 2])));
						
					}
			
				} else if (count == 25) {
				
					int _count = 0;
					int active = -1;
					for (int i = 0; i < 30; i += 6) {
						
						wars[_count].probability = Double.parseDouble(data[i]);
						wars[_count].name = data[i + 1];
						wars[_count].score[0] = Integer.parseInt(data[i + 2]);
						wars[_count].score[1] = Integer.parseInt(data[i + 3]);
						wars[_count].streak = Integer.parseInt(data[i + 4]);
						wars[_count].active = Boolean.parseBoolean(data[i + 5]);
						if (wars[_count].active) {
							
							active = _count;
							
						}
						_count++;
						
					}
					if (active != -1) {
						
						int i = 30;
						String cur = "";
						while (cur != ">") {
							
							cur = data[i];
							Division add = new Division(cur);
							_count = 0;
							for (int j = 1; j < 21; j += 2) {
								
								if (data[i + j] != "-1") {
									
									add.units[_count] = new Unit(Integer.parseInt(data[i + j]), Integer.parseInt(data[i + j + 1]));
									
								}
								
							}
							wars[active].attack.add(add);
							i += 21;
							
						}
						i++;
						Division def = new Division(data[i]);
						for (int j = 1; j < 21; j += 2) {
							
							if (data[i + j] != "-1") {
								
								def.units[_count] = new Unit(Integer.parseInt(data[i + j]), Integer.parseInt(data[i + j + 1]));
							
							}	
							
						}
						wars[active].defense = def;
						i += 22;
						while (i != data.length) {
							
							wars[active].reserves.add(new Unit(Integer.parseInt(data[i]), Integer.parseInt(data[i + 1])));
							i += 2;
							
						}
						
					}
				
				} else {
					
					Division add = new Division(data[0]);
					int _count = 0;
					for (int i = 1; i < 21; i += 2) {
						
						if (Integer.parseInt(data[i]) != -1) {
							
							add.units[_count] = new Unit(Integer.parseInt(data[i]), Integer.parseInt(data[i + 1]));
							
						}
						_count++;
						
					}
					if (add.name != "" && add.name != null) {
						
						divisions.add(add);
						
					}
					
				}
				
			}
			
			if (data_debug == 1) {
				
				return true;
				
			} else {
				
				return false;
				
			}

		} catch (IOException e) {
			
			return false;
			
		}
		
	}

	void resourceGen() {
		
		// GENERATE
		
		System.out.print("Press ENTER to generate resources... ");
		scan.nextLine();

		System.out.println("Generating resources...");

		// LAND

		double rsc_total = 0;
		for (int i = 0; i < 80; i++) {

			double prob = rand.nextDouble();
			if (prob < stats_land[0]) {

				rsc_land[0]++;

			} else if (prob < stats_land[1]) {

				rsc_land[1]++;

			} else if (prob < stats_land[2]) {

				rsc_land[2]++;

			} else if (prob < stats_land[3]) {

				rsc_land[3]++;

			}

		}

		// i = land type, j = land tile #, k = rsc #, l = rsc land #, m = # of rsc, n = rsc gen min

		for (int i = 0; i < rsc_land.length; i++) {

			for (int j = 0; j < rsc_land[i]; j++) {

				for (int k = 0; k < stats_fauna.length; k++) {

					for (int l = 0; l < stats_fauna[k].length; l += 4) {

						if (stats_fauna[k][l] == (double)i) {

							double random = rand.nextDouble();
							if (random < stats_fauna[k][l + 1]) {

								Double m = new Double(stats_fauna[k][l + 3] + 1 - stats_fauna[k][l + 2]);
								Double n = new Double(stats_fauna[k][l + 2]);
								int add = rand.nextInt(m.intValue()) + n.intValue();
								rsc_fauna[k] += add;
								rsc_total += add;

							}

						}

					}

				}

				for (int k = 0; k < stats_flora.length; k++) {

					for (int l = 0; l < stats_flora[k].length; l += 4) {

						if (stats_flora[k][l] == (double)i) {

							double random = rand.nextDouble();
							if (random < stats_flora[k][l + 1]) {

								Double m = new Double(stats_flora[k][l + 3] + 1 - stats_flora[k][l + 2]);
								Double n = new Double(stats_flora[k][l + 2]);
								int add = rand.nextInt(m.intValue()) + n.intValue();
								rsc_flora[k] += add;
								rsc_total += add;

							}

						}

					}

				}

				for (int k = 0; k < stats_mined.length; k++) {

					for (int l = 0; l < stats_mined[k].length; l += 4) {

						if (stats_mined[k][l] == (double)i) {

							if (rand.nextDouble() < stats_mined[k][l + 1]) {

								Double m = new Double(stats_mined[k][l + 3] + 1 - stats_mined[k][l + 2]);
								Double n = new Double(stats_mined[k][l + 2]);
								int add = rand.nextInt(m.intValue()) + n.intValue();
								rsc_mined[k] += add;
								rsc_total += add;

							}

						}

					}

				}

			}

		}

		// PRINT

		System.out.println();
		System.out.println("Land:  | " + rsc_land[0] + " " + names_land[0] + " | " + rsc_land[1] + " " + names_land[1] + " | " + rsc_land[2] + " " + names_land[2] + " | " + rsc_land[3] + " " + names_land[3] + " |");
		System.out.print("Fauna: | ");
		for (int i = 0; i < rsc_fauna.length; i++) {

			System.out.print(rsc_fauna[i] + " " + names_fauna[i] + " | ");

		}
		System.out.println();
		System.out.print("Flora: | ");
		for (int i = 0; i < rsc_flora.length; i++) {

			System.out.print(rsc_flora[i] + " " + names_flora[i] + " | ");

		}
		System.out.println();
		System.out.print("Mined: | ");
		for (int i = 0; i < rsc_mined.length; i++) {

			System.out.print(rsc_mined[i] + " " + names_mined[i] + " | ");

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
		if (game.loadData()) {
			
			System.out.print("Save data was found (currently on DAY " + game.day + "), load? Y/N ");
			if (scan.next().equalsIgnoreCase("Y")) {
				
				System.out.println("Loading data...");
				System.out.println();
				game.GameLoop();
				
			} else {
				
				System.out.println("Start over with a new Diego Land?");
				System.out.println("This will delete all your current save data!");
				System.out.print("Continue? Y/N ");
				if (scan.next().equalsIgnoreCase("N")) {
					
					System.out.println("Delete data cancelled");
					System.out.println("Loading data...");
					System.out.println();
					game.GameLoop();
					
				} else {
					
					System.out.println("Deleting data...");
					game.population = 20;
					game.day = 1;
					game.apd = 4;
					game.data_debug = 0;
					game.rsc = new int[]{5000, 200, 50, 40, 20, 20, 0, 0, 0};
					game.rsc_land = new int[]{0, 0, 0, 0, 20};
					game.rsc_fauna = new int[3 + 1];
					game.rsc_flora = new int[4 + 1];
					game.rsc_mined = new int[9 + 1];
					game.factories = new int[game.factory_templates.length];
					game.factories_lastCollected = new int[game.factory_templates.length];
					game.rsc_change = new int[]{game.tax, game.growth * -1, -1, 0, 0,0, 0, 0, 0};
					game.happiness = 2;
					game.tax = 22;
					game.growth = 2;
					game.usage = 2;
					game.techs = new int[5];
					game.pending_techs = -1;
					game.counter_techs = -1;
					game.announce = false;
					game.buildings = new int[5];
					game.wars = new War[]{new War("Masekovia"), new War("Bell Republic"), new War("Goldedge"), new War("Isle of Vaktovia"), new War("Davis Land")};
					game.divisions = new ArrayList<Division>();
					game.rtd = -1;
					game.military_announce = 0;
					game.pending_unit = new ArrayList<UnitPending>();
					Division._id = 0;
					try {
						
						game.saveData();
						System.out.println("Data deleted!");
						System.out.println("Starting new game...");
						scan.nextLine();
						System.out.println();
						System.out.println("***");
						System.out.println();
						
						game.resourceGen();
						
					} catch (IOException e) {
						
						System.out.println("Error: data deletion error");
						e.printStackTrace();
						
					}
					
					game.GameLoop();
					
				}
				
			}
			
		} else {

			game.population = 20;
			game.day = 1;
			game.apd = 4;
			game.data_debug = 0;
			game.rsc = new int[]{5000, 200, 50, 40, 20, 20, 0, 0, 0};
			game.rsc_land = new int[]{0, 0, 0, 0, 20};
			game.rsc_fauna = new int[3 + 1];
			game.rsc_flora = new int[4 + 1];
			game.rsc_mined = new int[9 + 1];
			game.factories = new int[game.factory_templates.length];
			game.factories_lastCollected = new int[game.factory_templates.length];
			game.rsc_change = new int[]{game.tax, game.growth * -1, -1, 0, 0,0, 0, 0, 0};
			game.happiness = 2;
			game.tax = 22;
			game.growth = 2;
			game.usage = 2;
			game.techs = new int[5];
			game.pending_techs = -1;
			game.counter_techs = -1;
			game.announce = false;
			game.buildings = new int[5];
			game.wars = new War[]{new War("Masekovia"), new War("Bell Republic"), new War("Goldedge"), new War("Isle of Vaktovia"), new War("Davis Land")};
			game.divisions = new ArrayList<Division>();
			game.rtd = -1;
			game.military_announce = 0;
			game.pending_unit = new ArrayList<UnitPending>();
			Division._id = 0;
			
			game.resourceGen();
	
			game.GameLoop();
			
		}

	}

}
