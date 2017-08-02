public class Battle {
	
	
	
}

/* this will be migrated to the driver later, I'm trying to keep all non-object code in the driver
  
import java.util.Random;

public class Battle {
	private Division d1;
	private Division d2;

	public Battle(Division d1, Division d2)
	{
		this.d1 = d1;
		this.d2 = d2;
	}

	public void toBattle()
	{
		Random rand = new Random();
		d1.SortDivision();
		d2.SortDivision();
		int attacker = 0;
		if(fastestDivision() == d1)
		{
			System.out.println(d1.getName()+" attacks first!\n");
			attacker = 1;
		}
		else
		{
			System.out.println(d2.getName()+" attacks first!\n");
			attacker = 2;
		}


		while(!BattleLost())
		{
			if(attacker == 1)
			{
				for(Unit i : d1.getUnits())
				{
					int attk2 = rand.nextInt(d2.getUnits().length);
					System.out.println(i.getName()+" from "+d1.getName()+" attacks "+d2.getUnits()[attk2].getName()+" from "+d2.getName()+"!\n");
					i.Attack(i, d2.getUnits()[attk2]);
				}
			}
			else
			{
				for(Unit i : d2.getUnits())
				{
					int attk2 = rand.nextInt(d1.getUnits().length);
					System.out.println(i.getName()+" from "+d2.getName()+" attacks "+d1.getUnits()[attk2].getName()+" from "+d1.getName()+"!\n");
					i.Attack(i, d1.getUnits()[attk2]);		
				}
			}
			
			attacker = Switch(attacker);
		}
	}



	public boolean BattleLost()
	{
		if(d1.zeroHp() || d2.zeroHp())
		{
			if(d1.zeroHp()) System.out.println("d1 lost");
			else System.out.println("d2 lost");
			return true;
		}
		return false;
	}

	public Division fastestDivision()
	{
		if(d1.getMaxSpeed() > d2.getMaxSpeed())
			return d1;
		else if(d1.getMaxSpeed() == d2.getMaxSpeed())
		{
			Random rand = new Random();
			int d = rand.nextInt(2);
			if(d == 0)
				return d1;
			else 
				return d2;
		}
		else
			return d2;
	}

	public int Switch(int attacker)
	{
		if(attacker == 1)
			return 2;
		else
			return 1;
	}

	public Division getD1() {
		return d1;
	}

	public void setD1(Division d1) {
		this.d1 = d1;
	}

	public Division getD2() {
		return d2;
	}

	public void setD2(Division d2) {
		this.d2 = d2;
	}
	
} */