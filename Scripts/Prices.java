import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Prices {

	public static void main(String[] args) throws IOException {

		String PathToZone = "C:\\Users\\karim.chaouch\\Desktop\\Workspace\\PricePlan\\Zone.txt";
		String PathToRules = "C:\\Users\\karim.chaouch\\Desktop\\Workspace\\PricePlan\\Rules.csv";
		String PathToPrefix = "C:\\Users\\karim.chaouch\\Desktop\\Workspace\\PricePlan\\Prefix.csv";
		String PathToTranslation = "C:\\Users\\karim.chaouch\\Desktop\\Workspace\\PricePlan\\Translation.csv";
		String PathToPrice = "C:\\Users\\karim.chaouch\\Desktop\\Workspace\\PricePlan\\Price.csv";

		String zone;
		String rules;
		String prefix;
		String translation;
		String price;



		BufferedReader zoneReader = new BufferedReader(new FileReader(PathToZone));

		while ((zone = zoneReader.readLine()) != null) {
			String result = "--";
			boolean rulesB = true;
			boolean prefixB = true;
			boolean translationB = true;
			boolean priceB = true;
			BufferedReader rulesReader = new BufferedReader(new FileReader(PathToRules));
			while ((rules = rulesReader.readLine()) != null && rulesB) {
				String[] data1 = rules.split(";");

				if (data1[0].equals(zone)) {

					rulesB = false;
					BufferedReader prefixReader = new BufferedReader(new FileReader(PathToPrefix));
					while ((prefix = prefixReader.readLine()) != null && prefixB) {
						String[] data2 = prefix.split(";");
						if (data2[0].equals(data1[1])) {

							prefixB = false;
							BufferedReader translationReader = new BufferedReader(new FileReader(PathToTranslation));
							while ((translation = translationReader.readLine()) != null && translationB) {
								String[] data3 = translation.split(";");

								if (data3[0].equals(data2[1].toUpperCase()) || data3[0].equals(data2[1])) {

									translationB = false;
									BufferedReader priceReader = new BufferedReader(new FileReader(PathToPrice));
									while ((price = priceReader.readLine()) != null && priceB) {
										String[] data4 = price.split(";");
										if (data4[0].equals(data3[1])) {

										priceB = false;
											result = data4[1];
//											System.out.println(zone + "  " + result);

										}

									}
									priceReader.close();

								}
								
								
								else if (data2[1].contains("GSM") && (data3[0]+" GSM").equals(data2[1].toUpperCase())) {
									
									translationB = false;
									BufferedReader priceReader = new BufferedReader(new FileReader(PathToPrice));
									while ((price = priceReader.readLine()) != null && priceB) {
										String[] data4 = price.split(";");
										if (data4[0].equals(data3[1]+" Mobile")) {

											priceB = false;
											result = data4[1];

										}

									}
									priceReader.close();
									
									
								}
								
								
								
								
								

							}
							translationReader.close();
						}

					}

					prefixReader.close();

				}

			}
			rulesReader.close();
			
			System.out.println(result);


		}

		zoneReader.close();

	}
}
