
public class Division {
	private Unit[] units;
	private String name;
	
	public Division(Unit[] units, String name)
	{
		this.units = units;
		this.name = name;
	}
	
	public Unit[] getUnits() { return units; }

	public void setUnits(Unit[] units) { this.units = units; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

}
