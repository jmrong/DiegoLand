import java.io.*;
import java.util.*;

public class Saving {
	
	public void Save(int[] arr, FileWriter writer) throws IOException {
		
		String[] list = new String[arr.length];
		for (int i = 0; i < list.length; i++) {
			
			list[i] = arr[i] + "";
		
		}
		CSVUtils.writeLine(writer, Arrays.asList(list));

	}
	
	public void Save(int a, FileWriter writer) throws IOException {
		
		String[] arr = {a + ""};
		CSVUtils.writeLine(writer, Arrays.asList(arr));
	
	}
	
	public void Save(double a, FileWriter writer) throws IOException {
		
		String[] arr = {a + ""};
		CSVUtils.writeLine(writer, Arrays.asList(arr));
	
	}
	
	public void Save(War[] a, FileWriter writer) throws IOException {
		
		List<String> list = new ArrayList<String>();
		int active = -1;
		for (int i = 0; i < 5; i++) {
			
			list.add(a[i].probability + "");
			list.add(a[i].name);
			list.add(a[i].score[0] + "");
			list.add(a[i].score[1] + "");
			if (a[i].active) {
				
				active = i;
				list.add("true");
				
			} else {
				
				list.add("false");
				
			}
			
		}
		if (active != -1) {
			
			for (int j = 0; j < a[active].attack.size(); j++) {
				
				String[] units = new String[21];
				units[0] = a[active].attack.get(j).name;
				int _count = 0;
				for (int k = 1; k < 21; k += 2) {
				
					units[k] = a[active].attack.get(j).units[_count].type + "";
					units[j + 1] = a[active].attack.get(j).units[_count].hp + "";
					_count++;
					
				}
				list.addAll(Arrays.asList(units));
				
			}
			list.add(">");
			int _count = 0;
			list.add(a[active].defense.name);
			for (int k = 1; k < 21; k += 2) {
				
				list.add(a[active].defense.units[_count].type + "");
				list.add(a[active].defense.units[_count].hp + "");
				_count++;
				
			}
			list.add(">");
			for (int k = 0; k < a[active].reserves.size(); k++) {
				
				list.add(a[active].reserves.get(k).type + "");
				list.add(a[active].reserves.get(k).hp + "");
				
			}
			
		}
		CSVUtils.writeLine(writer, list);
		
	}
	
	public void Save(ArrayList<Division> a, FileWriter writer) throws IOException {
		
		for (int i = 0; i < a.size(); i++) {
			
			String[] list = new String[21];
			list[0] = a.get(i).name;
			int count = 0;
			for (int j = 1; j < 21; j += 2) {
			
				list[j] = a.get(i).units[count].type + "";
				list[j + 1] = a.get(i).units[count].hp + "";
				count++;
				
			}
			System.out.println(list);
			CSVUtils.writeLine(writer, Arrays.asList(list));
			
		}
		
	}
	
	public void Save(String a, FileWriter writer) throws IOException {
		
		String[] arr = {a};
		CSVUtils.writeLine(writer, Arrays.asList(arr));
	
	}

}