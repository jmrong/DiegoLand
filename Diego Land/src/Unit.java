
public class Unit {
	private int hp;
	private int attk;
	private int speed;
	private String type;
	private int START_HEALTH;

	public Unit(String type)
	{
		this.type = type;
		switch(this.type)
		{
		case "Infantry":
			this.hp = 0;
			this.attk = 0;
			this.speed = 0;
			START_HEALTH = this.hp;
			break;

		case "Medic":
			this.hp = 0;
			this.attk = 0;
			this.speed = 0;
			START_HEALTH = this.hp;
			break;

		case "Sniper":
			this.hp = 0;
			this.attk = 0;
			this.speed = 0;
			START_HEALTH = this.hp;
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
			i.setHp(i.getSTART_HEALTH()/4);
	}

	public int getHp() { return hp; }

	public void setHp(int hp) { this.hp = hp; }

	public int getAttk() { return attk; }

	public void setAttk(int attk) { this.attk = attk; }

	public int getSpeed() { return speed; }

	public void setSpeed(int speed) { this.speed = speed; }

	public String getName() { return type; }

	public void setName(String name) { this.type = name; }

	public int getSTART_HEALTH() { return START_HEALTH; }
}

