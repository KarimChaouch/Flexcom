import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class FormatSDA {

	public static void main(String[] args) throws IOException {

		String pathToInputCsv = "C:\\Users\\karim.chaouch\\Desktop\\problem.csv";
		String pathToOutputCsv = "C:\\Users\\karim.chaouch\\Desktop\\solution.csv";

		String row;

		// First row of the output_file
		FileWriter csvWriter = new FileWriter(pathToOutputCsv);
		csvWriter.append("SITE");
		csvWriter.append(";");
		csvWriter.append("DESC");
		csvWriter.append(";");
		csvWriter.append("RANGES");
		csvWriter.append(";");
		csvWriter.append("FORBIDDEN");
		csvWriter.append(";");
		csvWriter.append("PREMIUM");
		csvWriter.append(";");
		csvWriter.append("PARTITION");
		csvWriter.append("\n");

		// Count for empty lines
		int count = 1;

		MultiValuedMap<String, List<String>> result = new ArrayListValuedHashMap<String, List<String>>();
		BufferedReader csvReader = new BufferedReader(new FileReader(pathToInputCsv));
		row = csvReader.readLine();

		while ((row = csvReader.readLine()) != null) {
			String[] data = row.split(";", -1);

			// Empty description
			if (data[0].isEmpty()) {
				data[0] = "NoValue_" + count;
				count++;
			}

			if (data[1].isEmpty()) {
				data[1] = "";
			}

			List<String> fields = new ArrayList<>();
			for (int i = 1; i < data.length; i++) {
				fields.add(data[i]);
			}

			result.put(data[0], fields);

		}

		for (String key : result.keySet()) {
			Collection<List<String>> lists = result.get(key);
			List<String> rangesList = new ArrayList<>();

			String site = key;
			String desc = "";
			String ranges = "";
			String Forbidden = "";
			String Premium = "";
			String partition = "";

			for (List<String> value : lists) {

				int size = lists.size();

				String max_range = value.get(3);
				String min_range = value.get(2);
				String prefix = value.get(1);
				// Exception min_range > max_range

				if (Integer.parseInt(value.get(3)) >= Integer.parseInt(value.get(2))) {
					max_range = value.get(3);
					min_range = value.get(2);
				} else {
					max_range = value.get(2);
					min_range = value.get(3);
				}

				// Exception min_range.length != max_range.length

				while (min_range.length() != max_range.length()) {

					if (min_range.length() < max_range.length()) {
						min_range = "0" + min_range;
					}

					else if (min_range.length() > max_range.length()) {
						max_range = "0" + max_range;
					}

				}

				// Exception max_range starts with "0"

				while (max_range.startsWith("0")) {
					max_range = max_range.substring(1, max_range.length());
					min_range = min_range.substring(1, min_range.length());
					prefix = prefix + "0";

				}

				// Exception description starts with space

				if (value.get(0).startsWith(" ")) {
					desc = value.get(0).substring(1, value.get(0).length());
				} else {
					desc = value.get(0);
				}

				// Exception duplicate ranges

				if (!rangesList.contains("+" + prefix + "[" + min_range + "-" + max_range + "]")) {
					rangesList.add("+" + prefix + "[" + min_range + "-" + max_range + "]");
					ranges = ranges + "+" + prefix + "[" + min_range + "-" + max_range + "]" + "|";

				}
				partition = value.get(4);

				// Retrieve forbidden numbers
				if (value.get(5).contains("|")) {
					String[] numbers = value.get(5).split("\\|");

					for (String n : numbers) {
						if (n.contains("-")) {
							Forbidden = Forbidden + "+" + prefix + "[" + n + "]|";
						} else {
							Forbidden = Forbidden + "+" + prefix + n + "|";
						}
					}

				}

				else if (value.get(5).contains("-")) {
					Forbidden = Forbidden + "+" + prefix + "[" + value.get(5) + "]|";
				} else if (!value.get(5).isEmpty()) {
					Forbidden = Forbidden + "+" + prefix + value.get(5) + "|";
				}

			}

			csvWriter.append(site);
			csvWriter.append(";");
			if (!desc.equals("")) {
				csvWriter.append(desc);
			}
			csvWriter.append(";");
			if (!ranges.equals("")) {
				csvWriter.append(ranges.substring(0, ranges.length() - 1));
			} else {
				csvWriter.append("");
			}

			csvWriter.append(";");
			if (!Forbidden.equals("")) {
				csvWriter.append(Forbidden.substring(0, Forbidden.length() - 1));
			} else {
				csvWriter.append("");
			}
			csvWriter.append(";");
			csvWriter.append("");
			csvWriter.append(";");

//			csvWriter.append(partition);
			csvWriter.append("pt-internal");

			csvWriter.append("\n");

		}

		csvReader.close();
		csvWriter.close();

	}

}
