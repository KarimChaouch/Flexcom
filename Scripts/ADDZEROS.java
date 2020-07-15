import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ADDZEROS {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToCsv = "C:\\Users\\karim.chaouch\\Desktop\\directory_formatted.csv";
		String row;
		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\data2.txt");
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
		row = csvReader.readLine();
		String str=row + "\n";

		while ((row = csvReader.readLine()) != null) {
		str = str + "00" + row +"\n";
		}
		myWriter.write(str);
		myWriter.close();
		csvReader.close();

	}
}
