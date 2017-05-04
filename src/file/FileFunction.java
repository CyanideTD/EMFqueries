package file;

import java.util.*;
import java.io.*;
import connection.Connect;
import entities.Arguments;
import file.Output;

public class FileFunction {
	private String fileName;

	public BufferedReader readFile() {
		String path = System.getProperty("user.dir");
		System.out.println("Please input the file name");
		Scanner scan = new Scanner(System.in);
		fileName = scan.nextLine();
		String filePath = path + "\\src\\" + fileName;
		File file = new File(filePath);
		BufferedReader buffer = null;
		boolean flag = false;
		try {
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
				buffer = new BufferedReader(read);
				flag = true;
			} else {
				while (!flag) {
					System.out.println("Please reEnter the file name");
					fileName = scan.nextLine();
					path = path + "\\src\\" + fileName;
					file = new File(path);
					if (file.isFile() && file.exists()) {
						InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GDK");
						buffer = new BufferedReader(read);
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public String writeFile(Connect connection, Arguments variables) throws IOException {
		FileWriter writer = null;

		String fileName = "Result.java";
		String packageName = "file";

		ArrayList<String> importList = new ArrayList<>();
		importList.add("java.util.*");
		importList.add("java.sql.*");
		Output outputFile = new Output(fileName, packageName, importList);
		outputFile.structure = connection.getStructure();
		outputFile.variables = variables;

		try {
			writer = new FileWriter("src//file//" + fileName);
			writer.write(outputFile.write(connection));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return fileName;
	}

	public Arguments load() throws IOException {
		BufferedReader buffer = readFile();

		String clause = buffer.readLine();
		String read = buffer.readLine();
		String[] proAttributes = read.split(",");
		List<String> argumentS = new ArrayList<>();
		for (int i = 0; i < proAttributes.length; i++) {
			String att = proAttributes[i].trim();
			argumentS.add(att);
		}

		clause = buffer.readLine();
		String number = buffer.readLine();

		clause = buffer.readLine();
		read = buffer.readLine();
		String[] groupAttributes = read.split(",");
		List<String> argumentV = new ArrayList<>();
		for (int i = 0; i < groupAttributes.length; i++) {
			String att = groupAttributes[i].trim();
			argumentV.add(att);
		}

		clause = buffer.readLine();
		read = buffer.readLine();
		String[] fVect = read.split(",");
		List<String> argumentF = new ArrayList<>();
		for (int i = 0; i < fVect.length; i++) {
			argumentF.add(fVect[i].trim());
		}

		clause = buffer.readLine();
		List<List<String>> variblesSigmas = new ArrayList<List<String>>();

		for (int i = 0; i < Integer.parseInt(number); i++) {
			String sigma = buffer.readLine();
			String[] sigmas = sigma.split("and");
			ArrayList<String> varibles_sigma = new ArrayList<String>();
			for (int j = 0; j < sigmas.length; j++) {
				String sigmaValue = sigmas[j].trim();
				varibles_sigma.add(sigmaValue);
			}
			variblesSigmas.add(varibles_sigma);
		}

		clause = buffer.readLine();
		List<String> having = new ArrayList<>();
		read = buffer.readLine();
		if (read != null) {
			String[] have = read.split("and");
			for (int i = 0; i < have.length; i++) {
				having.add(have[i].trim());
			}
			read = buffer.readLine();
		}

		Arguments result = new Arguments(argumentS, Integer.parseInt(number), argumentV, argumentF, variblesSigmas, having);
		return result;
	}

}
