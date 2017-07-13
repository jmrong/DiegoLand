import java.util.Random;


public class Battle {
	private Division d1;
	private Division d2;

	public Battle(Division d1, Division d2)
	{
		this.d1 = d1;
		this.d2 = d2;
	}

	public int toBattle()
	{
		Random rand = new Random();
		for(Unit i : d1.getUnits())
		{
			double val = rand.nextDouble();
			i.setVal(val+i.getSpeed());
		}

		for(Unit i : d2.getUnits())
		{
			double val = rand.nextDouble();
			i.setVal(val+i.getSpeed());
		}

		d1.SortDivision();
		d2.SortDivision();

		int i = 0;
		int j = 0;

		while(!D1Lost() || !D2Lost())
		{
			if(i > d1.getUnits().length)
			{
				d1.resetAttack();
				i = 0;
			}

			if(j > d2.getUnits().length)
			{
				d2.resetAttack();
				j = 0;
			}
			
			
			if(d1.getUnits()[i].getHasAttacked())
			{
				i++;
			}

			if(d2.getUnits()[j].getHasAttacked())
			{
				j++;
			}

			if(d1.getUnits()[i].getSpeed() > d2.getUnits()[j].getSpeed())
			{
				d1.getUnits()[i].Attack(d1.getUnits()[i], d2.getUnits()[j]);
				d1.getUnits()[i].setHasAttacked(true);
			}
			else
			{
				d2.getUnits()[i].Attack(d2.getUnits()[j], d1.getUnits()[i]);
				d2.getUnits()[i].setHasAttacked(true);
			}
		}	
		
		if(D1Lost())
			return 2;
		else
			return 1;
	}

	public boolean D1Lost()
	{
		int count1 = 0;
		for(Unit i : d1.getUnits())
		{
			if(i.getHp() <= 0)
				count1++;
		}

		if(count1 == d1.getUnits().length)
			return true;

		return false;


	}

	public boolean D2Lost()
	{
		int count2 = 0;
		for(Unit i : d2.getUnits())
		{
			if(i.getHp() <= 0)
				count2++;
		}

		if(count2 == d2.getUnits().length)
			return true;

		return false;
	}
}
