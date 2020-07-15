import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class retrieveNames {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToTXT = "C:\\Users\\karim.chaouch\\Desktop\\Problem.txt";
		String line;
		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\solution.txt");

		BufferedReader csvReader = new BufferedReader(new FileReader(pathToTXT));
		String str ="";
		while ((line = csvReader.readLine()) != null) {
			String[] words = line.split("Â ");
			
			String lastWord = words[words.length-1];

			String rest = line.substring(0,line.indexOf(lastWord)).trim();
			rest.replace("Â", "");
			if(rest.isEmpty()) {
				rest = "-";
			}
		
			
		str = str + rest + "\n";
			
		}
		myWriter.write(str);
		myWriter.close();
		csvReader.close();

	}
}
