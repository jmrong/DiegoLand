public class Division {
	
	public Unit[] units = new Unit[10];
	public String name;
	public int id;
	public static int _id = 0;
	// Removed ready to defend; this will be stored in the driver
	
	public Division(String name) {
		
		this.name = name;
		this.id = _id;
		_id++;
	
	}
	
	public int length() {
		
		int count = 0;
		for (int i = 0; i < units.length; i++) {
			
			if (units[i] != null) {
				
				count++;
				
			}
			
		}
		return count;
		
	}
	
	public boolean add(Unit unit) {
		
		for (int i = 0; i < units.length; i++) {
			
			if (units[i] == null) {
				
				units[i] = unit;
				return true;
				
			}
			
		}
		return false;
		
	}
	
	public Unit[] get() {
		
		int count = 0;
		Unit[] a = new Unit[this.length()];
		for (int i = 0; i < units.length; i++) {
			
			if (units[i] != null) {
				
				a[count] = units[i];
				count++;
				
			}
			
		}
		return a;
		
	}
	
	// SortDivision() removed to make way for new speed mechanics
	
	public boolean testSurrender() {
		
		return true;
		
	}
	
	/* might be removed but could be useful as quick utility function
	public boolean zeroHp() {
		
		int sum = 0;
		for (Unit i : units)
			sum += i.getHp();
		if (sum <= 0)
			return true;
		return false;
		
	} */
	
	// getMaxSpeed() removed for new speed mechanics

}