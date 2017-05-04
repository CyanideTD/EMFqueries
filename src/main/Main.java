package main;
import connection.Connect;
import file.FileFunction;
import entities.Arguments;
import file.Output;


public class Main {
	public static void main(String[] args) throws Exception {
		FileFunction f = new FileFunction();
		Arguments argument = f.load();
		Connect connectionFactory;
		try {
			connectionFactory = new Connect();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ConnectionFactory create fail!!!");
			return;
		}
		String fileName = f.writeFile(connectionFactory, argument);
		System.out.println(fileName + " create success!!!!!!");
	}
}
