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
	
	
	int[][][] getStats(int lastCollection) {
		
		int[][] _input = this.input;
		int[] _output = this.output;
		int[][][] _return = new int[2][][];
		if (this.time - lastCollection >= 0) {
			
			for (int i = 0; i < _input.length; i++) {
				
				for (int j = 0; j < _input[i].length; j++) {
					
					_input[i][j] *= time - lastCollection + 1;
					
				}
				
			}
			for (int i = 0; i < _output.length; i++) {

					_output[i] *= time - lastCollection + 1;
				
			}
			
		}
		_return[0] = _input;
		_return[1] = new int[][]{_output};
		return _return;
		
	}

}
