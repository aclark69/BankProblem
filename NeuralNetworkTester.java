
import java.io.*;
import java.text.DecimalFormat;
//part4 Bank problem
//Andrew Clark

public class NeuralNetworkTester {

	// main method p
	public static String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

	public static void main(String[] args) throws IOException {

		/*************************
		 * Creates file for training data
		 ********************/
		createFileForTrainingFile();
		/*********************************************/

		/**************************
		 * Creates file for test data
		 ************************/
		createFileForTestData();
		/*********************************************/

		// 3 files: testData, validationtData, actualTestData

		String file1 = "/Users/andrewclark/Desktop/Part2/trainingDataFile.txt";

		String file2 = "/Users/andrewclark/Desktop/Part2/validationDataFile.txt";

		String file3 = "/Users/andrewclark/Desktop/Part2/testDataFile.txt";

		String file4 = "/Users/andrewclark/Desktop/Part2/outputfile.txt";

		// Construct nueral network
		NeuralNetworks network = new NeuralNetworks();

		// load training data
		network.loadTraningData(file1);

		// set parameters
		network.setParameters(3, 1000, 2376, 0.6);

		// train network
		network.train();

		// test network
		network.testData(file3, file4);

	}

	public static void createFileForTestData() throws IOException {
		DecimalFormat df = new DecimalFormat("#.##");

		File testData = new File("/Users/andrewclark/Desktop/Part2/testDataFile.txt");

		if (testData.createNewFile()) {
			System.out.println("File is created for training data!");
		} else {
			System.out.println("File already exists training data.");
		}

		int creditMinTestData = findMinCreditScoreTestData();
		int creditMaxTestData = findMaxCreditScoreTestData();

		int incomeMinTestData = findMinIncomeTestData();
		int incomeMaxTestData = findMaxIncomeTestData();

		int ageMinTestData = findMinAgeTestData();
		int ageMaxTestData = findMaxAgeTestData();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader fw = new BufferedReader(fr);

			FileWriter fileWriter = new FileWriter(testData);

			String content = null;
			content = fw.readLine();

			int count = 0;

			while (content != null) {
				// System.out.println(content);

				if (count > 117 && count < 126) {

					// gets credit score
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String i = content.substring(0, 3);
					double k = Integer.valueOf(String.valueOf(i));
					double scaled = (k - creditMinTestData) / (creditMaxTestData - creditMinTestData);
					String change1 = df.format(scaled);

					// System.out.println(change1);

					// gets income
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String income = content.substring(4, 6);
					double incomeInteger = Integer.valueOf(String.valueOf(income));
					double incomeScaled = (incomeInteger - incomeMinTestData) / (incomeMaxTestData - incomeMinTestData);
					String change2 = df.format(incomeScaled);
					// String convertedIncomeNumber =
					// numberAsString.substring(0,5);

					// get age
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String age = content.substring(7, 9);
					double ageInteger = Integer.valueOf(String.valueOf(age));
					double ageScaled = (ageInteger - incomeMinTestData) / (incomeMaxTestData - incomeMinTestData);
					String change3 = df.format(ageScaled);
					// String convertedAgeNumber =
					// numberAsString.substring(0,5);

					// sex of individual already in proper sacled size

					// marital satus of individual already in proper scaled size

					// out of individual already in scaled size

					String combined = change1 + " " + change2 + " " + change3;

					if (content.contains("male")) {
						combined = combined + " 1 ";
					} else if (content.contains("female")) {
						combined = combined + " 0 ";
					}

					if (content.contains("single")) {
						combined = combined + " 1 ";

					} else if (content.contains("married")) {
						combined = combined + " .5 ";
					} else if (content.contains("divorced")) {
						combined = combined + " 0 ";
					}

					fileWriter.write(combined);
					fileWriter.write("\n");

				}

				content = fw.readLine();
				count++;

			}

			fileWriter.close();

		} catch (Exception e) {

		}

	}

	public static void createFileForTrainingFile() throws IOException {
		DecimalFormat df = new DecimalFormat("#.##");

		File testData = new File("/Users/andrewclark/Desktop/Part2/trainingDataFile.txt");

		if (testData.createNewFile()) {
			System.out.println("File is created for training data!");
		} else {
			System.out.println("File already exists training data.");
		}

		int creditMin = findMinCreditScore();
		int creditMax = findMaxCreditScore();

		int incomeMax = findMaxIncome();
		int incomeMin = findMinIncome();

		int ageMin = findMinAge();
		int ageMax = findMaxAge();

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader fw = new BufferedReader(fr);

			FileWriter fileWriter = new FileWriter(testData);

			String content = null;
			content = fw.readLine();

			int count = 0;

			while (content != null) {
				// System.out.println(content);

				if (count > 12 && count < 93) {

					// gets credit score
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String i = content.substring(0, 3);
					double k = Integer.valueOf(String.valueOf(i));
					double scaled = (k - creditMin) / (creditMax - creditMin);
					String change1 = df.format(scaled);

					// String numberAsString = String.valueOf(scaled);
					// String convertedCreditNumber =
					// numberAsString.substring(0,5);

					// gets income
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String income = content.substring(4, 6);
					double incomeInteger = Integer.valueOf(String.valueOf(income));
					double incomeScaled = (incomeInteger - incomeMin) / (incomeMax - incomeMin);
					String change2 = df.format(incomeScaled);
					// String convertedIncomeNumber =
					// numberAsString.substring(0,5);

					// get age
					// and scales data with the formula y =
					// (x-minimum)/(maximum-minimum)
					String age = content.substring(7, 9);
					double ageInteger = Integer.valueOf(String.valueOf(age));
					double ageScaled = (ageInteger - ageMin) / (ageMax - incomeMin);
					String change3 = df.format(incomeScaled);
					// String convertedAgeNumber =
					// numberAsString.substring(0,5);

					// sex of individual already in proper sacled size

					// marital satus of individual already in proper scaled size

					// out of individual already in scaled size

					String combined = change1 + " " + change2 + " " + change3;

					if (content.contains("male")) {
						combined = combined + " 1 ";
					} else if (content.contains("female")) {
						combined = combined + " 0 ";
					}

					if (content.contains("single")) {
						combined = combined + " 1 ";

					} else if (content.contains("married")) {
						combined = combined + " .5 ";
					} else if (content.contains("divorced")) {
						combined = combined + " 0 ";
					}

					if (content.contains("high")) {
						combined = combined + " 1 ";
					} else if (content.contains("medium")) {
						combined = combined + " .66 ";
					} else if (content.contains("low")) {
						combined = combined + " .33 ";
					} else if (content.contains("undetermined")) {
						combined = combined + " 0 ";

					}

					fileWriter.write(combined);
					fileWriter.write("\n");

				}
				content = fw.readLine();
				count++;

			}

			fileWriter.close();

		} catch (Exception e) {

		}

	}

	/*********************** Finds max and mins for training data ************/
	private static int findMinCreditScore() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(0, 3);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 12 && count < 93) {

				String i = content.substring(0, 3);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	private static int findMaxCreditScore() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();

		int count = 0;
		while (content != null) {
			// System.out.println(content);

			if (count > 12 && count < 93) {
				String i = content.substring(0, 3);
				// System.out.println("old = " +i);
				int k = Integer.valueOf(String.valueOf(i));
				// System.out.println(k);
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	private static int findMaxIncome() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();

		int count = 0;
		while (content != null) {
			// System.out.println(content);

			if (count > 12 && count < 93) {
				String i = content.substring(4, 6);
				// System.out.println("old = " +i);
				int k = Integer.valueOf(String.valueOf(i));
				// System.out.println(k);
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	private static int findMinIncome() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(4, 6);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 12 && count < 93) {

				String i = content.substring(4, 6);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	private static int findMinAge() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(7, 9);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 12 && count < 93) {

				String i = content.substring(7, 9);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	private static int findMaxAge() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();

		int count = 0;
		while (content != null) {
			// System.out.println(content);

			if (count > 12 && count < 93) {
				String i = content.substring(7, 9);
				// System.out.println("old = " +i);
				int k = Integer.valueOf(String.valueOf(i));
				// System.out.println(k);
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	/*************************************************************************/

	/*************************
	 * finds the max and mins for test data
	 ************/

	public static int findMaxCreditScoreTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();
		int count = 0;
		while (content != null) {

			if (count > 117 && count < 126) {

				String i = content.substring(0, 3);
				int k = Integer.valueOf(String.valueOf(i));
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	public static int findMinCreditScoreTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(0, 3);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 117 && count < 126) {

				String i = content.substring(0, 3);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	public static int findMaxIncomeTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();

		int count = 0;
		while (content != null) {
			// System.out.println(content);

			if (count > 117 && count < 126) {
				String i = content.substring(4, 6);
				// System.out.println("old = " +i);
				int k = Integer.valueOf(String.valueOf(i));
				// System.out.println(k);
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	public static int findMinIncomeTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(4, 6);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 117 && count < 126) {

				String i = content.substring(4, 6);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	public static int findMinAgeTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);

		String content = null;
		content = fw.readLine();

		int count = 0;
		int min = 0;

		while (content != null) {
			if (count == 13) {
				String i = content.substring(7, 9);
				int k = Integer.valueOf(String.valueOf(i));
				min = k;
			}
			if (count > 117 && count < 126) {

				String i = content.substring(7, 9);
				int k = Integer.valueOf(String.valueOf(i));
				if (k < min) {
					min = k;
				}
			}
			content = fw.readLine();
			count++;
		}

		return min;

	}

	public static int findMaxAgeTestData() throws IOException {
		String fileName = "/Users/andrewclark/Desktop/Part2/TrainingFile";

		FileReader fr = new FileReader(fileName);
		BufferedReader fw = new BufferedReader(fr);
		int count2 = 0;

		int max = 0;
		String content = null;
		content = fw.readLine();

		int count = 0;
		while (content != null) {
			// System.out.println(content);

			if (count > 117 && count < 126) {
				String i = content.substring(7, 9);
				// System.out.println("old = " +i);
				int k = Integer.valueOf(String.valueOf(i));
				// System.out.println(k);
				if (k > max) {
					max = k;
				}

			}
			content = fw.readLine();
			count++;
		}

		return max;

	}

	/**************************************************************************/

}