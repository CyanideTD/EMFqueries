package file;

import java.util.*;
import entities.Arguments;
import file.FileFunction;
import connection.Connect;

public class Output {
	HashMap<String, String> structure;
	String fileName;
	String packageName;
	Arguments variables;
	List<String> importList;
	public StringBuilder content;
	ArrayList<String> mfvariables;

	public void setArgument(Arguments ar) {
		variables = ar;
	}

	public Output(String fileName, String packageName, List<String> importList) {
		super();
		this.fileName = fileName;
		this.packageName = packageName;
		this.importList = importList;
		content = new StringBuilder();
	}

	public Output() {
		super();
	}

	public void printEntity() {
		content.append("\r\n class Tuple{\r\n");
		for (Map.Entry<String, String> entry : structure.entrySet()) {
			content.append("\tprivate " + entry.getValue() + " " + entry.getKey() + ";\r\n");
			String upperCaseString = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);

			content.append("\tpublic " + entry.getValue() + " get" + upperCaseString + "(){\r\n");
			content.append("\treturn " + entry.getKey() + ";\r\n");
			content.append("\t}\r\n");

			content.append(
					"\tpublic void set" + upperCaseString + "(" + entry.getValue() + " " + entry.getKey() + "){\r\n");
			content.append("\tthis." + entry.getKey() + " = " + entry.getKey() + ";\r\n");
			content.append("\t}\r\n");

		}

		content.append("}\r\n");
	}

	public void printMFStructure(Connect conn) {
		content.append("\r\n class MFStructure{\r\n");
		ArrayList<String> attrubutes = new ArrayList<String>();

		List<String> list = variables.getV();
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = "int";
			for (Map.Entry<String, String> entry : structure.entrySet()) {

				if (entry.getKey() == name || entry.getKey().equals(name)) {
					type = entry.getValue();
					break;
				}
			}
			attrubutes.add(name);
			content.append("\tprivate " + type + " " + name + ";\r\n");
			String upperCaseString = name.substring(0, 1).toUpperCase() + name.substring(1);

			content.append("\tpublic " + type + " get" + upperCaseString + "(){\r\n");
			content.append("\treturn " + name + ";\r\n");
			content.append("\t}\r\n");

			content.append("\tpublic void set" + upperCaseString + "(" + type + " " + name + "){\r\n");
			content.append("\tthis." + name + " = " + name + ";\r\n");
			content.append("\t}\r\n");

		}

		list = variables.getF();

		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			String type = "int";
			type = "double";
			name = "mf" + name;

			if (name.contains("avg_")) {

				name = name.replace("avg_", "count_");
				attrubutes.add(name);
				content.append("\tprivate " + type + " " + name + ";\r\n");
				String upperCaseString = name.substring(0, 1).toUpperCase() + name.substring(1);

				content.append("\tpublic " + type + " get" + upperCaseString + "(){\r\n");
				content.append("\treturn " + name + ";\r\n");
				content.append("\t}\r\n");

				content.append("\tpublic void set" + upperCaseString + "(" + type + " " + name + "){\r\n");
				content.append("\tthis." + name + " = " + name + ";\r\n");
				content.append("\t}\r\n");

				name = name.replace("count_", "sum_");
				attrubutes.add(name);
				content.append("\tprivate " + type + " " + name + ";\r\n");
				upperCaseString = name.substring(0, 1).toUpperCase() + name.substring(1);

				content.append("\tpublic " + type + " get" + upperCaseString + "(){\r\n");
				content.append("\treturn " + name + ";\r\n");
				content.append("\t}\r\n");

				content.append("\tpublic void set" + upperCaseString + "(" + type + " " + name + "){\r\n");
				content.append("\tthis." + name + " = " + name + ";\r\n");
				content.append("\t}\r\n");

			} else {
				attrubutes.add(name);
				content.append("\tprivate " + type + " " + name + ";\r\n");
				String upperCaseString = name.substring(0, 1).toUpperCase() + name.substring(1);

				content.append("\tpublic " + type + " get" + upperCaseString + "(){\r\n");
				content.append("\treturn " + name + ";\r\n");
				content.append("\t}\r\n");

				content.append("\tpublic void set" + upperCaseString + "(" + type + " " + name + "){\r\n");
				content.append("\tthis." + name + " = " + name + ";\r\n");
				content.append("\t}\r\n");
			}
		}

		List<String> S = variables.getS();
		for (int j = 0; j < S.size(); j++) {
			String name = S.get(j);
			if (!(attrubutes.contains(name)) && !(name.contains("_"))) {
				String type = conn.getStructure().get(name);
				attrubutes.add(name);
				content.append("\tprivate " + type + " " + name + ";\r\n");
				String upperCaseString = name.substring(0, 1).toUpperCase() + name.substring(1);

				content.append("\tpublic " + type + " get" + upperCaseString + "(){\r\n");
				content.append("\treturn " + name + ";\r\n");
				content.append("\t}\r\n");

				content.append("\tpublic void set" + upperCaseString + "(" + type + " " + name + "){\r\n");
				content.append("\tthis." + name + " = " + name + ";\r\n");
				content.append("\t}\r\n");

			}
		}
		this.mfvariables = attrubutes;

		content.append("}\r\n");
	}

	public void printPackage() {
		String packageName = "package " + this.packageName + ";\r\n\r\n";
		content.append(packageName);
	}

	public void printImport() {
		for (int i = 0; i < importList.size(); i++) {
			String importItem = importList.get(i);
			content.append("import " + importItem + ";\r\n");

		}
		content.append("\r\n");
	}

	public void printDatabaseConnt(Connect connection) {
		StringBuffer database = new StringBuffer();
		Properties prop = connection.getProp();
		String driver = prop.getProperty("DRIVER_CLASS");
		String url = prop.getProperty("CONNECTION_URL");
		String user = prop.getProperty("CONNECTION_USERNAME");
		String password = prop.getProperty("CONNECTION_PASSWORD");
		database.append("\tClass.forName(\"" + driver + "\");\r\n");
		database.append("\tConnection con = DriverManager.getConnection(\r\n");
		database.append("\t\"" + url + "\", \"" + user + "\", \"" + password + "\");\r\n");
		database.append("\tStatement st = con.createStatement();\r\n");
		database.append("\tResultSet rs;\r\n");
		database.append("\tSystem.out.println(\"Connect to databse success!!!\");\r\n");

		content.append("\t" + database + "\r\n");

		content.append("\t\r\n");

	}

	public void printFirstScan(Connect conn) {
		StringBuilder firstScan = new StringBuilder();
		String tableName = conn.getTableName();
		firstScan.append("\tArrayList<MFStructure> mfs = new ArrayList<>(); \r\n");
		firstScan.append("\trs = st.executeQuery(\"select * from " + tableName + "\" ); \r\n");
		firstScan.append("\twhile (rs.next()) { \r\n");
		firstScan.append("\t    boolean flag = false; \r\n");
		firstScan.append("\tTuple t = new Tuple();\r\n");

		for (Map.Entry<String, String> entry : structure.entrySet()) {
			String upper = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
			String upper1 = entry.getValue().substring(0, 1).toUpperCase() + entry.getValue().substring(1);
			firstScan.append("\tt.set" + upper + "(rs.get" + upper1 + "(\"" + entry.getKey() + "\"));\r\n");
		}

		List<String> F = variables.getF();
		List<String> agg0 = new ArrayList<>();

		for (int i = 0; i < F.size(); i++) {
			String f = F.get(i);
			if (f.contains("0")) {
				agg0.add(f);
			}
		}

		firstScan.append("\tfor (int i = 0; i < mfs.size() && !flag; i++){\r\n");
		firstScan.append("\tif (");
		List<String> tupleV = variables.getV();

		for (int j = 0; j < tupleV.size(); j++) {
			String attr = tupleV.get(j).substring(0, 1).toUpperCase() + tupleV.get(j).substring(1);
			if (conn.getStructure().get(tupleV.get(j)).equals("int")) {
				firstScan.append("mfs.get(i).get" + attr + "() == t.get" + attr + "() &&");
			} else {
				firstScan.append("mfs.get(i).get" + attr + "().equals(t.get" + attr + "())&&");
			}
		}

		firstScan.append("!flag) {\r\n");
		firstScan.append("\t\tflag = true;\r\n");
		firstScan.append("\t}\r\n");
		firstScan.append("\t}\r\n");
		firstScan.append("\t");

		firstScan.append("\tif (!flag){\r\n");
		firstScan.append("\tMFStructure mfstructure = new MFStructure();\r\n");
		for (int j = 0; j < tupleV.size(); j++) {
			String attr = tupleV.get(j).substring(0, 1).toUpperCase() + tupleV.get(j).substring(1);
			firstScan.append("\tmfstructure.set" + attr + "(t.get" + attr + "());\r\n");
		}

		for (int i = 0; i < mfvariables.size(); i++) {
			String mfvariable = mfvariables.get(i);
			if (mfvariable.contains("0_")) {
				String var = mfvariable;

				if (var.contains("avg_")) {
					String attr = mfvariable.replace("mf0_avg_", "");
					var = var.replace("mf", "Mf");
					String mfcount = var.replace("avg_", "count");
					String mfsum = var.replace("avg_", "sum_");

					String Uattr = attr.substring(0, 1).toUpperCase() + attr.substring(1);
					firstScan.append("\tmfstructure.set" + mfsum + "(mfstrucure.get" + mfsum + "() + 1);\r\n");
				} else {
					String attr = mfvariable.replace("mf0_sum_", "").replace("mf0_max_", "").replace("mf0_min_", "")
							.replace("mf0_count_", "");
					String Uattr = attr.substring(0, 1).toUpperCase() + attr.substring(1);
					var = var.replace("mf", "Mf");
					if (mfvariable.contains("_sum_")) {
						firstScan.append(
								"\tmfstructure.set" + var + "(t.get" + Uattr + "()+mfstructure.get" + var + "());\r\n");
					} else if (mfvariable.contains("_count_")) {
						firstScan.append("\tmfstructure.set" + var + "(mfstructure.get" + var + "()+1);\r\n");
					} else if (mfvariable.contains("_max_")) {
						firstScan.append("\tif(mfstructure.get" + var + "() <  t.get" + Uattr + "()){\r\n");
						firstScan.append("\tmfstructure.set" + var + "( t.get" + Uattr + "());\r\n");
						firstScan.append("}\r\n");

					} else if (mfvariable.contains("_min_")) {
						firstScan.append("\tif(mfstructure.get" + var + "() > t.get" + Uattr + "()){\r\n");
						firstScan.append("\tmfstructure.set" + var + "( t.get" + Uattr + "());\r\n");
						firstScan.append("}\r\n");
					}
				}
			} else if (conn.getStructure().containsKey(mfvariable)) {
				if (!tupleV.contains(mfvariable)) {
					String upperCaseString = mfvariable.substring(0, 1).toUpperCase() + mfvariable.substring(1);
					firstScan.append("\tmfstructure.set" + upperCaseString + "(t.get" + upperCaseString + "());\r\n");
				}
			} else if (mfvariables.contains(mfvariable) && !(mfvariable.contains("0_"))) {
				if (mfvariable.contains("avg_")) {
					String fakeMfvariable = mfvariable.replace("mf", "Mf");
					String mfcount = fakeMfvariable.replace("avg_", "count_");
					String mfsum = fakeMfvariable.replace("avg_", "sum_");

					firstScan.append("\tmfstructure.set" + mfsum + "(0);\r\n");
					firstScan.append("\tmfstructure.set" + mfcount + "0.00000001);\r\n");
				} else {
					if (mfvariable.contains("count_")) {
						String fakeMfvariable = mfvariable.replace("mf", "Mf");
						firstScan.append("\tmfstructure.set" + fakeMfvariable + "(0.00000001);\r\n");
					} else {
						String fakeMfvariable = mfvariable.replace("mf", "Mf");
						firstScan.append("\tmfstructure.set" + fakeMfvariable + "(0);\r\n");
					}
				}
			}
		}
		firstScan.append("\tmfs.add(mfstructure);\r\n");

		firstScan.append("\t}\r\n");

		firstScan.append("\t}\r\n");
		firstScan.append("\t\r\n");
		firstScan.append("\t\r\n");
		firstScan.append("\t\r\n");
		firstScan.append("\t\r\n");

		content.append(firstScan);
	}

	public void printRestScan(Connect conn) {
		StringBuilder result = new StringBuilder();
		int num = variables.getN();
		List<List<String>> sigmas = variables.getSigma();

		String tableName = conn.getTableName();

		for (int i = 0; i < num; i++) {
			List<String> sigma = sigmas.get(i);
			result.append("\trs = st.executeQuery(\"select * from " + tableName + "\"); \r\n");
			result.append("\twhile (rs.next()){ \r\n");
			result.append("\tTuple t = new Tuple();\r\n");
			for (Map.Entry<String, String> entry : structure.entrySet()) {
				String upper = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
				String upper1 = entry.getValue().substring(0, 1).toUpperCase() + entry.getValue().substring(1);
				result.append("\tt.set" + upper + "(rs.get" + upper1 + "(\"" + entry.getKey() + "\"));\r\n");
			}
			result.append("\tfor(int i = 0;i < mfs.size(); i ++){ \r\n");
			result.append("\t \r\n");
			result.append("\tMFStructure mf = mfs.get(i); \r\n");
			result.append("\tif (");
			for (int j = 0; j < sigma.size(); j++) {
				String sg = sigma.get(j);
				String type = "";
				String left = "";
				String right = "";
				String operater = "";
				HashMap<String, String> tableStructure = conn.getStructure();
				if (sg.contains("=")) {
					operater = "=";
				} else if (sg.contains("<>")) {
					operater = "<>";
				} else if (sg.contains("<")) {
					operater = "<";
				} else if (sg.contains(">")) {
					operater = ">";
				}

				left = sg.split(operater)[0].trim().split("_")[1].trim();
				right = sg.split(operater)[1].trim();

				type = tableStructure.get(left);
				if (operater.equals("=")) {
					if (type.equals("int")) {
						operater = "=";
					} else {
						operater = "equals";
					}
				}
				if (operater.equals("<>")) {
					if (type.equals("int")) {
						operater = "!=";
					} else {
						operater = "!equals";
					}
				}
				String upperCaseLf = left.substring(0, 1).toUpperCase() + left.substring(1);
				if (operater.equals("=")) {
					result.append("(t.get" + upperCaseLf + "() == ");
				} else if (operater.equals("equals")) {
					result.append("(t.get" + upperCaseLf + "().equals(");
				} else if (operater.equals("!=")) {
					result.append("(t.get" + upperCaseLf + "() !=");
				} else if (operater.equals("!equals")) {
					result.append("!(t.get" + upperCaseLf + "().equals(");
				} else {
					result.append("(t.get" + upperCaseLf + "() " + operater);
				}

				String fakeRight = right.substring(0, 1).toUpperCase() + right.substring(1);
				if (mfvariables.contains(right)) {
					if (operater.equals("equals") || operater.equals("!equals")) {
						result.append("mfs.get(i).get" + fakeRight + "()))");
					} else {
						result.append("mfs.get(i).get" + fakeRight + "())");
					}

				} else {
					fakeRight = "mf" + right;
					if (fakeRight.contains("avg_")) {
						fakeRight = fakeRight.replace("mf", "Mf");
						String mfcount = fakeRight.replace("avg_", "count_");
						String mfsum = fakeRight.replace("avg_", "sum_");
						result.append("(mfs.get(i).get" + mfsum + "() / mfs.get(i).get" + mfcount + "()))");
					} else if (mfvariables.contains(fakeRight)) {
						if (operater.equals("=")) {
							result.append("mfs.get(i).get" + fakeRight + "())");
						} else {
							result.append("mfs.get(i).get" + fakeRight + "()))");
						}
					} else {
						if ((right.contains("+") || right.contains("-") || right.contains("*"))
								|| right.contains("/")) {
							if (right.contains("+")) {
								String l = right.split("\\+")[0].trim();
								String r = right.split("\\+")[1].trim();
								if (this.mfvariables.contains(l)) {
									l = l.substring(0, 1).toUpperCase() + l.substring(1);
									l = "mf.get" + l + "()";

								} else if (this.mfvariables.contains(r)) {
									r = r.substring(0, 1).toUpperCase() + l.substring(1);
									r = "mf.get" + r + "()";

								}
								result.append(l + "+ " + r + ")");
							} else if (right.contains("-")) {
								String l = right.split("\\-")[0].trim();
								String r = right.split("\\-")[1].trim();
								if (this.mfvariables.contains(l)) {
									l = l.substring(0, 1).toUpperCase() + l.substring(1);
									l = "mf.get" + l + "()";

								} else if (this.mfvariables.contains(r)) {
									r = r.substring(0, 1).toUpperCase() + l.substring(1);
									r = "mf.get" + r + "()";

								}
								result.append(l + "- " + r + ")");
							} else if (right.contains("*")) {
								String l = right.split("\\*")[0].trim();
								String r = right.split("\\*")[1].trim();
								if (this.mfvariables.contains(l)) {
									l = l.substring(0, 1).toUpperCase() + l.substring(1);
									l = "mf.get" + l + "()";

								} else if (this.mfvariables.contains(r)) {
									r = r.substring(0, 1).toUpperCase() + l.substring(1);
									r = "mf.get" + r + "()";

								}
								result.append(l + "* " + r + ")");
							} else if (right.contains("/")) {
								String l = right.split("\\/")[0].trim();
								String r = right.split("\\/")[1].trim();
								if (this.mfvariables.contains(l)) {
									l = l.substring(0, 1).toUpperCase() + l.substring(1);
									l = "mf.get" + l + "()";

								} else if (this.mfvariables.contains(r)) {
									r = r.substring(0, 1).toUpperCase() + l.substring(1);
									r = "mf.get" + r + "()";

								}
								result.append(l + "/ " + r + ")");
							}

						} else {
							right = right.replace("'", "\"");
							result.append(right + "))");

						}

					}

				}

				result.append("&&");

			}
			result.deleteCharAt(result.length() - 1);
			result.deleteCharAt(result.length() - 1);
			result.append("){\r\n");

			List<String> f = variables.getF();

			for (int j = 0; j < f.size(); j++) {
				String fect = f.get(j);
				int x = i + 1;
				if (fect.contains(x + "_")) {
					if (fect.contains("avg_")) {
						String mfcount = "Mf" + x + "_count_" + fect.split("_")[2];
						String mfcount1 = "Mf" + x + "_sum_" + fect.split("_")[2];
						String upperCaseString = fect.split("_")[2].substring(0, 1).toUpperCase()
								+ fect.split("_")[2].substring(1);
						result.append("\tmf.set" + mfcount1 + "(mf.get" + mfcount1 + "()+ t.get" + upperCaseString
								+ "());\r\n");

						result.append("\tmf.set" + mfcount + "(mf.get" + mfcount + "()+1);\r\n");
					} else if (fect.contains("max_")) {
						{

							String mMax = "Mf" + x + "_max_" + fect.split("_")[2];

							String upperCaseString = fect.split("_")[2].substring(0, 1).toUpperCase()
									+ fect.split("_")[2].substring(1);
							result.append("\tif(mf.get" + mMax + "() <  t.get" + upperCaseString + "()){\r\n");

							result.append("\tmf.set" + mMax + "( t.get" + upperCaseString + "());\r\n");
							result.append("}\r\n");
						}
					} else if (fect.contains("min_")) {
						String mMin = "Mf" + x + "_min_" + fect.split("_")[2];

						String upperCaseString = fect.split("_")[2].substring(0, 1).toUpperCase()
								+ fect.split("_")[2].substring(1);
						result.append("\tif(mf.get" + mMin + "() >  t.get" + upperCaseString + "()){\r\n");

						result.append("\tmf.set" + mMin + "( t.get" + upperCaseString + "());\r\n");
						result.append("}\r\n");

					} else if (fect.contains("count_")) {
						String mfcount = "Mf" + x + "_count_" + fect.split("_")[2];
						String upperCaseString = fect.split("_")[2].substring(0, 1).toUpperCase()
								+ fect.split("_")[2].substring(1);
						result.append("\tmf.set" + mfcount + "(mf.get" + mfcount + "()+ t.get" + upperCaseString
								+ "());\r\n");

					} else if (fect.contains("sum_")) {

						String mfcount = "Mf" + x + "_sum_" + fect.split("_")[2];
						String upperCaseString = fect.split("_")[2].substring(0, 1).toUpperCase()
								+ fect.split("_")[2].substring(1);
						result.append("\tmf.set" + mfcount + "(mf.get" + mfcount + "()+ t.get" + upperCaseString
								+ "());\r\n");

					}
					result.append("\tmfs.set(i, mf);\r\n");

				}
			}
			result.append("\t}\r\n");
			result.append("\t}\r\n");
			result.append("\t}\r\n");
		}
		content.append(result);
	}

	public void printHaving(Connect conn) {
		StringBuffer havingClause = new StringBuffer();
		List<String> havings = variables.getG();
		if (havings.size() != 0) {
			havingClause.append("for(int i = 0;i < mfs.size(); i ++){ \r\n");
			havingClause.append("\tMFStructure mf = mfs.get(i);\r\n");
			havingClause.append("if(!(");
			for (int i = 0; i < havings.size(); i++) {
				String left = "";
				String right = "";
				String operater = "";
				String sg = havings.get(i);
				if (sg.contains("=")) {
					operater = "=";
				} else if (sg.contains("<>")) {
					operater = "<>";
				} else if (sg.contains("<")) {
					operater = "<";
				} else if (sg.contains(">")) {
					operater = ">";
				}
				left = sg.split(operater)[0].trim();
				right = sg.split(operater)[1].trim();
				if (left.contains("avg_")) {
					String sum = left.replace("avg_", "sum_");
					String count = left.replace("avg_", "count_");
					sum = "Mf" + sum;
					count = "Mf" + count;
					havingClause.append("(mf.get" + sum + "() / mf.get" + count + "())");
				} else {
					String fakeLeft = "mf" + left;
					if (mfvariables.contains(fakeLeft)) {
						havingClause.append("(mf.get" + fakeLeft + "()");

					}
				}
				if (operater.equals("<>")) {
					operater = "!=";
				}
				if (operater.equals("=")) {
					operater = "==";
				}
				havingClause.append(operater);

				String fakeRight = "mf" + right;
				if (mfvariables.contains(right)) {
					fakeRight = right.substring(0, 1).toUpperCase() + right.substring(1);
					havingClause.append("(mf.get" + fakeRight + "()");

				}

				if (right.contains("avg_")) {
					String sum = right.replace("avg_", "sum_");
					String count = right.replace("avg_", "count_");
					sum = "Mf" + sum;
					count = "Mf" + count;

					havingClause.append("(mf.get" + sum + "() / mf.get" + count + "())");
				}

				if (mfvariables.contains(fakeRight)) {

					fakeRight = right.substring(0, 1).toUpperCase() + right.substring(1);
					havingClause.append("(mf.get" + fakeRight + "()");

				}

				else if ((right.contains("+") || right.contains("-") || right.contains("*")) || right.contains("/")) {
					if (right.contains("+")) {
						String l = right.split("+")[0];
						String r = right.split("+")[1];
						if (this.mfvariables.contains(l)) {
							l = l.substring(0, 1).toUpperCase() + l.substring(1);
							l = "mf.get" + l + "()";

						} else if (this.mfvariables.contains(r)) {
							r = r.substring(0, 1).toUpperCase() + l.substring(1);
							r = "mf.get" + r + "()";

						}
						havingClause.append(l + "+ " + r + ")");
					} else if (right.contains("-")) {
						String l = right.split("-")[0];
						String r = right.split("-")[1];
						if (this.mfvariables.contains(l)) {
							l = l.substring(0, 1).toUpperCase() + l.substring(1);
							l = "mf.get" + l + "()";

						} else if (this.mfvariables.contains(r)) {
							r = r.substring(0, 1).toUpperCase() + l.substring(1);
							r = "mf.get" + r + "()";

						}
						havingClause.append(l + "- " + r + ")");
					} else if (right.contains("*")) {
						String l = right.split("*")[0];
						String r = right.split("*")[1];
						if (this.mfvariables.contains(l)) {
							l = l.substring(0, 1).toUpperCase() + l.substring(1);
							l = "mf.get" + l + "()";

						} else if (this.mfvariables.contains(r)) {
							r = r.substring(0, 1).toUpperCase() + l.substring(1);
							r = "mf.get" + r + "()";

						}
						havingClause.append(l + "* " + r + ")");
					} else if (right.contains("/")) {
						String l = right.split("/")[0];
						String r = right.split("/")[1];
						if (this.mfvariables.contains(l)) {
							l = l.substring(0, 1).toUpperCase() + l.substring(1);
							l = "mf.get" + l + "()";

						} else if (this.mfvariables.contains(r)) {
							r = r.substring(0, 1).toUpperCase() + l.substring(1);
							r = "mf.get" + r + "()";

						}
						havingClause.append(l + "/ " + r + ")");
					}
				}

				havingClause.append("&&");

			}
			havingClause.deleteCharAt(havingClause.length() - 1);
			havingClause.deleteCharAt(havingClause.length() - 1);
			havingClause.append(")){\r\n");
			havingClause.append("\tmfs.remove(i);\r\n");
			havingClause.append("\ti--;\r\n");

			havingClause.append("}\r\n");

			havingClause.append("}\r\n");
		}
		content.append(havingClause);
	}

	private void print(Connect conn) {
		StringBuilder result = new StringBuilder();
		List<String> select = variables.getS();
		ArrayList<Integer> lengths = new ArrayList<Integer>();
		for (int i = 0; i < select.size(); i++) {
			int length = 0;
			String attr = select.get(i);
			length = attr.length();
			if (conn.getStructure().containsKey(attr)) {
				length = 8;
			}
			if (attr.contains("_")) {
				result.append("\tSystem.out.printf(\"%-" + length + "s\",\"" + attr + "\");\r\n");
			} else if (attr.contains("+") || attr.contains("-") || attr.contains("*") || attr.contains("/")) {
				result.append("\tSystem.out.printf(\"%-" + length + "d\",\"" + attr + "\");\r\n");
			} else if (conn.getStructure().containsKey(attr)) {
				if (conn.getStructure().get(attr).equals("int")) {
					result.append("\tSystem.out.printf(\"%-" + length + "s\",\"" + attr + "\");\r\n");
				} else {
					result.append("\tSystem.out.printf(\"%-" + length + "s\",\"" + attr + "\");\r\n");
				}
			} else {
				result.append("\tSystem.out.printf(\"%-" + length + "s\",\"" + attr + "\");\r\n");
			}
			result.append("\tSystem.out.print(\"    \");\r\n");
			lengths.add(length);
		}
		result.append("\tSystem.out.println(\"\");\r\n");
		for (int i = 0; i < select.size(); i++) {
			int length = lengths.get(i);
			result.append("\tSystem.out.print(\"");
			for (int j = 0; j < length; j++) {
				result.append("=");
			}
			result.append("\");\r\n");
			result.append("\tSystem.out.print(\"    \");\r\n");
		}
		result.append("\tSystem.out.println(\"\");\r\n");
		result.append("\tfor (int i = 0; i < mfs.size(); i++){\r\n");
		result.append("\tMFStructure mf = mfs.get(i);\r\n");

		result.append("\r\n");

		for (int i = 0; i < select.size(); i++) {
			String attr = select.get(i);
			int length = lengths.get(i);
			if (attr.contains("/") || attr.contains("+") || attr.contains("*") || attr.contains("-")) {
				result.append("\tSystem.out.printf(\"");
				result.append("%" + length);
				result.append("f\",");
				String left = "";
				String right = "";
				String operater = "";
				if (attr.contains("+")) {
					operater = "+";
				} else if (attr.contains("-")) {
					operater = "-";
				} else if (attr.contains("*")) {
					operater = "*";
				} else if (attr.contains("/")) {
					operater = "/";
				}
				left = attr.split(operater)[0].trim();
				right = attr.split(operater)[1].trim();
				if (left.contains("avg_")) {
					String sum = left.replace("avg_", "sum_").trim();
					String count = left.replace("avg_", "count_").trim();
					sum = "Mf" + sum;
					count = "Mf" + count;
					result.append("\tDouble.parseDouble(mf.get" + sum + "())+\"\" / Double.parseDouble(mf.get" + count
							+ "()+\"\")+);\r\n");
				} else {
					String agg = "Mf" + left;
					result.append("Double.parseDouble(mf.get" + agg + "()+\"\")");
				}
				result.append(operater);
				if (right.contains("avg_")) {
					String sum = right.replace("avg_", "sum_").trim();
					String count = right.replace("avg_", "count_").trim();
					;
					sum = "Mf" + sum;
					count = "Mf" + count;
					result.append("Double.parseDouble(mf.get" + sum + "())+\"\" / Double.parseDouble(mf.get" + count
							+ "()+\"\")+);\r\n");
				} else {
					String agg = "Mf" + right;
					result.append("Double.parseDouble(mf.get" + agg + "()+\"\")");

				}
				result.append(");\r\n");

			} else if (attr.contains("_")) {
				result.append("\tSystem.out.printf(\"");
				result.append("%" + length);
				result.append("f\",");
				if (attr.contains("avg_")) {
					String sum = attr.replace("avg_", "sum_").trim();
					String count = attr.replace("avg_", "count_").trim();
					;
					sum = "Mf" + sum;
					count = "Mf" + count;
					result.append("\tDouble.parseDouble(mf.get" + sum + "()+\"\") / Double.parseDouble(mf.get" + count
							+ "()+\"\"));\r\n");
				} else {
					String agg = "Mf" + attr;
					result.append("\tmf.get" + agg + "());\r\n");
				}
			} else {
				result.append("\tSystem.out.printf(\"");
				if (conn.getStructure().get(attr).equals("int")) {
					result.append("%" + length);
					result.append("d\",");
				} else {
					result.append("%-" + length);
					result.append("s\",");
				}

				String upperCaseString = attr.substring(0, 1).toUpperCase() + attr.substring(1);
				result.append("mf.get" + upperCaseString + "());\r\n");
			}
			result.append("\tSystem.out.print(\"    \");\r\n");
		}
		result.append("\tSystem.out.println(\"\");\r\n");

		result.append("\t}\r\n");

		content.append(result);
	}

	public void printClass(Connect conn) {
		String classString = "public class  " + fileName.split("\\.")[0] + " {\r\n";
		content.append(classString);
		String mainString = "\tpublic static void main(String[] args) throws Exception {\r\n";
		content.append(mainString);
		content.append("\tlong startTime=System.currentTimeMillis();\r\n");
		printDatabaseConnt(conn);
		printFirstScan(conn);
		printRestScan(conn);
		printHaving(conn);
		print(conn);
		content.append("\tlong endTime=System.currentTimeMillis();\r\n");
		content.append("\tlong Time=endTime-startTime;\r\n");
		content.append("\tSystem.out.println(\"the program run£º \"+Time+\"ms\");\r\n");
		content.append("}\r\n");
		content.append("}\r\n");
	}

	public String write(Connect conn) {
		content = new StringBuilder();
		printPackage();
		printImport();
		printEntity();
		printMFStructure(conn);
		printClass(conn);
		return content.toString();
	}
}
