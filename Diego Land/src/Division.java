
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
		int last = units.length - 1;
		boolean ifSwapped = true;
		while(ifSwapped)
		{
			ifSwapped = false;
			for(int i = 0; i < last; i++)
			{
				Unit start = units[i];
				if(units[i].getSpeed() > units[i+1].getSpeed())
				{
					units[i] = units[i+1];
					units[i+1] = start;
					//System.out.println("It swaped");
					//System.out.println("values[i]="+units[i]);
					//System.out.println("values[i+1]="+units[i+1]+"\n");
					ifSwapped = true;
				}
				else if(units[i].getSpeed() < units[i+1].getSpeed())
				{
					//System.out.println("It is correct");
				}

			}

		}
	}

	public void resetAttack()
	{
		for(Unit i : units)
			i.setHasAttacked(false);
	}
	
	public boolean zeroHp()
	{
		int sum = 0;
		for( Unit i : units)
			sum += i.getHp();
		if(sum <= 0)
			return true;
		return false;
	}
	
	public double getMaxSpeed()
	{
		double max = units[0].getSpeed();
		for(Unit i : units)
		{
			if(max <= i.getSpeed())
				max = i.getSpeed();
		}
		return max;
	}

}
