
public class Unit {
	private int hp;
	private int attk;
	private int speed;
	private String name;
	
	public Unit(int hp, int attk, int speed, String name)
	{
		this.hp = hp;
		this.attk = attk;
		this.speed = speed;
		this.name = name;
	}
	
	public void Attack(Unit a, Unit b)
	{
		b.setHp(b.getHp()-a.getAttk());
	}

	public int getHp() { return hp; }

	public void setHp(int hp) { this.hp = hp; }

	public int getAttk() { return attk; }

	public void setAttk(int attk) { this.attk = attk; }

	public int getSpeed() { return speed; }

	public void setSpeed(int speed) { this.speed = speed; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
}

