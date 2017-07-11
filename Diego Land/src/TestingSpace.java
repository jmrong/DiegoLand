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