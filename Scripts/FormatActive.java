import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FormatActive {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToTXT = "C:\\Users\\karim.chaouch\\Desktop\\Problem.txt";
		String line;
		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\solution.txt");

		BufferedReader csvReader = new BufferedReader(new FileReader(pathToTXT));
		String str = "";
		while ((line = csvReader.readLine()) != null) {
			
			if(line.contains("Active")) {
			line = line.replaceAll("Active", "on");
			}
			else if(line.contains("Inactive")) {
				line = line.replaceAll("Inactive", "off");
				
			}

			str = str + line + "\n";

		}
		myWriter.write(str);
		myWriter.close();
		csvReader.close();

	}
}
