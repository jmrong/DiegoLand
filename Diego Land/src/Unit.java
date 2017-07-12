
public class Unit {
	private int hp;
	private int attk;
	private int speed;
	private String name;
	private int START_HEALTH;

	public Unit(int hp, int attk, int speed, String name)
	{
		START_HEALTH = hp;
		this.hp = hp;
		this.attk = attk;
		this.speed = speed;
		this.name = name;
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

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
	public int getSTART_HEALTH() { return START_HEALTH; }
}

