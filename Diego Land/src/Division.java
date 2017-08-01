public class Division {
	
	public Unit[] units = new Unit[10];
	public String name;
	// Removed ready to defend; this will be stored in the driver
	
	public Division(String name) {
		
		this.name = name;
	
	}
	
	public int length() {
		
		for (int i = 0; i < units.length; i++) {
			
			if (units[i] == null) {
				
				return i + 1;
				
			}
			
		}
		return -1;
		
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