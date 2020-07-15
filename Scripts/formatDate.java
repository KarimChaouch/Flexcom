import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class formatDate {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToTXT = "C:\\Users\\karim.chaouch\\Desktop\\Problem.txt";
		String line;
		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\solution.txt");

		BufferedReader csvReader = new BufferedReader(new FileReader(pathToTXT));
		String str = "";
		while ((line = csvReader.readLine()) != null) {
			String[] all = line.split(";");
			

		
			System.out.println(all[10]);
			String[] data = all[10].split("\\s");
			
			line = line.replaceAll(all[10], data[0]);
			
			if(!all[11].isEmpty()) {
				String[] end = all[11].split("\\s");
				line = line.replaceAll(all[11], end[0]);

				
			}

			str = str +  line + "\n";

		}
		myWriter.write(str);
		myWriter.close();
		csvReader.close();

	}
}
