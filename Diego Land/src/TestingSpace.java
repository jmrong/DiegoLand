public class TestingSpace {
	
	String name;
	int age;
	boolean run = true;
	
	TestingSpace(String name, int age) {
		
		this.name = name;
		this.age = age;
		
	}
	
	void Loop()
	{
		while (run) {
			
			System.out.println("loop");
			
		}
	}
	
	String getName()
	{
		return this.name;
	}
	
	public static void main(String[] args) {
		TestingSpace t = new TestingSpace("Anish", 100);
		t.Loop();
		
	}
	
}

/*void cmd_erase_data() throws IOException  // May or may not work, need to test
{
	String csvFile = "save.csv";
	Saving save = new Saving();

	FileWriter writer = new FileWriter(csvFile);
	
	population = 20;
	day = 1;
	time = 0;
	apd = 4;
	int[] rsc = {5000, 200, 50, 40, 20, 20, 0, 0, 0};

	
	writer.flush();
    writer.close();
}
*/

/*int i = 0;
int j = 0;

while(!D1Lost() || !D2Lost())
{
	if(i > d1.getUnits().length)
	{
		System.out.println("1");
		d1.resetAttack();
		i = 0;
	}

	if(j > d2.getUnits().length)
	{
		System.out.println("2");
		d2.resetAttack();
		j = 0;
	}
	
	
	if(d1.getUnits()[i].getHasAttacked())
	{
		System.out.println("3");
		i++;
	}

	if(d2.getUnits()[j].getHasAttacked())
	{
		System.out.println("4");
		j++;
	}

	if(d1.getUnits()[i].getSpeed() > d2.getUnits()[j].getSpeed())
	{
		System.out.println("5");
		d1.getUnits()[i].Attack(d1.getUnits()[i], d2.getUnits()[j]);
		d1.getUnits()[i].setHasAttacked(true);
	}
	else
	{
		System.out.println("6");
		d2.getUnits()[i].Attack(d2.getUnits()[j], d1.getUnits()[i]);
		d2.getUnits()[i].setHasAttacked(true);
	}
}	

if(D1Lost())
{
	System.out.println("7");
	System.out.println("Division 2 Won");
	return 2;
}
else
{
	System.out.println("8");
	System.out.println("Division 1 Won");
	return 1;
}
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
}*/