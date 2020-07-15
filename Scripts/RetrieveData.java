import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RetrieveData {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String pathToCsv = "C:\\Users\\karim.chaouch\\Desktop\\Call_Details_614902923_000024_20200308.csv";
		String row;

		FileWriter myWriter = new FileWriter("C:\\Users\\karim.chaouch\\Desktop\\data.txt");
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));

		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(";");
			String str = "(";
			for (String part : data) {
				str = str + part + ",";
			}
			str = str.substring(0, str.length() - 1);
			str = str + "),\n";

			myWriter.write(str);

		}
		myWriter.close();
		csvReader.close();

	}

}
