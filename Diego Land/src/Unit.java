import java.util.Random;


public class Unit {
	private int hp;
	private int attk;
	private double speed;
	private String type;
	private double defense;
	private int START_HEALTH;
	private int[] rsc;
	private String[] rsc_names = {"Money", "Food", "Ammo"};
	private boolean hasAttacked = false;

	public Unit(String type)
	{
		Random rand = new Random();
		this.type = type;
		switch(this.type)
		{
		case "Infantry":
			this.hp = 100;
			this.attk = 10;
			this.speed = 1 + rand.nextDouble();
			START_HEALTH = this.hp;
			this.defense = 0;
			rsc = new int[] {0,0,0};
			break;

		case "Medic":
			this.hp = 0;
			this.attk = 0;
			this.speed = 2 + rand.nextDouble();
			START_HEALTH = this.hp;
			this.defense = 0;
			rsc = new int[] {0,0,0};
			break;

		case "Sniper":
			this.hp = 0;
			this.attk = 0;
			this.speed = 3 + rand.nextDouble();
			START_HEALTH = this.hp;
			this.defense = 0;
			rsc = new int[] {0,0,0};
			break;
		}
	}

	public void Attack(Unit a, Unit b)
	{
		b.setHp(b.getHp()-a.getAttk());
	}

	public void SplashAttack(Unit[] units, Unit a) 
	{
		for(Unit i : units)
			Attack(a, i);
	}

	public void Heal(Unit[] units, Unit a) //Kind-of weird integer division stuff...oh well I don't really care that much about the specifics
	{
		for(Unit i : units)
			i.setHp(i.getSTART_HEALTH()/5);
	}

	public int getHp() { return hp; }

	public void setHp(int hp) { this.hp = hp; }

	public int getAttk() { return attk; }

	public void setAttk(int attk) { this.attk = attk; }

	public double getSpeed() { return speed; }

	public void setSpeed(int speed) { this.speed = speed; }

	public String getName() { return type; }

	public void setName(String name) { this.type = name; }

	public int getSTART_HEALTH() { return START_HEALTH; }
	
	public boolean getHasAttacked() { return hasAttacked; }
	
	public void setHasAttacked(boolean val) { hasAttacked = val; }
}

