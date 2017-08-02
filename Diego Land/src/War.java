import java.util.*;

public class War {
	
	public double probability = 0.0;
	public String name;
	public int[] score = {0, 0};
	public int streak = 0;
	public boolean active = false;
	public ArrayList<Division> attack = new ArrayList<Division>();
	public Division defense = new Division("");
	public ArrayList<Unit> reserves;
	
	public War(String name) {
		
		this.name = name;
		
	}
	
}