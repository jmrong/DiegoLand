
public class Division {
	private Unit[] units;
	private String name;
	private boolean ready_to_defend = false;
	
	public Division(Unit[] units, String name)
	{
		this.units = units;
		this.name = name;
	}
	
	public boolean isReady_to_defend() { return ready_to_defend; }

	public void setReady_to_defend(boolean ready_to_defend) { this.ready_to_defend = ready_to_defend; }

	public Unit[] getUnits() { return units; }

	public void setUnits(Unit[] units) { this.units = units; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
	public void SortDivision()
	{
		
	}

	public void resetAttack()
	{
		for(Unit i : units)
			i.setHasAttacked(false);
	}

}
