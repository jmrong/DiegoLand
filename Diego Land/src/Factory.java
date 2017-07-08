// Diego Land
// Factory class

public class Factory {
	
	public int[][] input;
	public int[][] output;
	public int time;
	public String name;
	
	public Factory(int[][] input, int[][] output, int time, String name) {
		
		// Input array: [standard, fauna, flora, mined]
		// Input array inner: [index, amount, ...]
		this.input = input;
		this.output = output;
		this.time = time;
		this.name = name;
		
	}
	
	int[][][] getStats(int lastCollection) {
		
		int[][] _input = this.input;
		int[][] _output = this.output;
		int[][][] _return = null;
		if (this.time - lastCollection >= 0) {
			
			for (int i = 0; i < _input.length; i++) {
				
				for (int j = 0; j < _input[i].length; j++) {
					
					_input[i][j] *= time - lastCollection + 1;
					
				}
				
			}
			for (int i = 0; i < _output.length; i++) {
				
				for (int j = 0; j < _output[i].length; j++) {
					
					_output[i][j] *= time - lastCollection + 1;
					
				}
				
			}
			
		}
		return _return;
		
	}

}
