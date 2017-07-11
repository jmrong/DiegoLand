import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public class Saving {
	public void Save(int[] arr, FileWriter writer) throws IOException
	{
		String[] list = new String[arr.length];
		for(int i = 0; i < list.length; i++)
		{
			list[i] = arr[i]+"";
		}

		
		CSVUtils.writeLine(writer, Arrays.asList(list));



	}
	
	public void Save(int a, FileWriter writer) throws IOException
	{
		String[] arr = {a+""};
		
		
		CSVUtils.writeLine(writer, Arrays.asList(arr));
	
	}
	
	public void Save(String a, FileWriter writer) throws IOException
	{
		String[] arr = {a};
		
		CSVUtils.writeLine(writer, Arrays.asList(arr));
	}
	

}
