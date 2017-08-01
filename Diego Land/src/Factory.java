// Diego Land
// Factory class

public class Factory {
	
	public int[][] input;
	public int[] output;
	public int[][] cost;
	public int time;
	public String name;
	
	public Factory(int[][] input, int[] output, int[][] cost, int time, String name) {
		
		// Input array: [standard, fauna, mined]
		// Input array inner: [index, amount, ...]
		// Onetime cost: [standard, amount], [land, amount], [flora-id, amount]
		//                                      `-> 5: cleared, 0: forest, 1: desert, etc.
		
		this.input = input;
		this.output = output;
		this.cost = cost;
		this.time = time;
		this.name = name;
		
	}
	

}