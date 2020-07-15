import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class modifyPoint {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToCsv = "C:\\Users\\karim.chaouch\\Desktop\\CALLS.csv";
		String row;
		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\data.txt");
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
		String str = "";
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(";");
				
				String replaced = data[21].replace(",", ".");
				
				str = str+ replaced+"\n";
				
				

			}
			
			myWriter.write(str);
			myWriter.close();
			csvReader.close();
		}

		

}
