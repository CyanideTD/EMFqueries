package file;

import java.util.*;
import entities.Arguments;
public class Output {
	HashMap<String, String> structure;
	String fileName;
	String packageName;
	Arguments variables;
	List<String> importList;
	StringBuilder content;
	ArrayList<String> mfvariables;
	
	public Output(String fileName, String packageName,
			List<String> importList) {
		super();
		this.fileName = fileName;
		this.packageName = packageName;
		this.importList = importList;
		content = new StringBuilder();
	}

	public void printEntity() {
		
	}
}
