import java.util.Random;

public class Unit {
	
	public int hp;
	public int type;
	
	public static int[][] stats_unit = {{3, 3, 1, 20}, {2, 2, 1, 20}, {6, 1, 6, 20}, {5, 3, 3, 20}, {4, 5, 2, 45}, {8, 2, 7, 30}, {15, 2, 6, 20}, {12, 7, 4, 100}, {12, 4, 10, 85}};
	public static String[] names_unit = {"Infantry", "Medic", "Sniper", "Grenadier", "Tanks", "Fighter Jet", "Bomber", "T-80 Rodriguez", "C.H.R.I.Scopter"};
	public static int[] types_unit = {0, 0, 0, 0, 1, 2, 2, 1, 2};
	public static int[][] cost_unit = {{150, 5, 0, 0, 0, 0, 1}, {180, 4, 0, 0, 0, 0, 2}, {300, 7, 0, 0, 0, 0, 2}, {300, 5, 0, 0, 0, 0, 2}, {900, 20, 45, 40, 0, 0, 6}, {1900, 30, 95, 70, 0, 0, 10}, {2400, 36, 100, 80, 0, 4, 11}, {3100, 85, 300, 250, 0, 6, 12}, {5000, 100, 400, 300, 0, 7, 14}};
	public static int[] ref_unit = {0, 6, 3, 5, 7, 8};
	public static int[][] upkeep_unit = {{10, 3, 0, 0, 0, 0}, {20, 2, 0, 0, 0, 0}, {35, 3, 0, 0, 0, 0}, {35, 3, 0, 0, 0, 0}, {100, 7, 0, 0, 1, 0}, {360, 15, 0, 0, 3, 0}, {410, 16, 0, 0, 4, 1}, {450, 30, 0, 0, 12, 3}, {500, 45, 0, 0, 15, 3}};

	public Unit(int type) {
		
		this.hp = stats_unit[type][3];
		this.type = type;
		
	}
	
	public Unit(int type, int hp) {
		
		this.hp = hp;
		this.type = type;
		
	}

	public void Attack(Unit a, Unit b) {
		
		// This will need MAJOR rewriting b.setHp(b.getHp()-a.getAttk());
		
	}

	/* public void SplashAttack(Unit[] units, Unit a) {
		
		for(Unit i : units)
			Attack(a, i);
		
	} */

	/* Will be controlled by the driver
	public void Heal(Unit[] units, Unit a) {
		for(Unit i : units)
			i.setHp(i.getSTART_HEALTH()/5);
	} */

	// Getters and setters no longer needed as all variables made public
	
}

