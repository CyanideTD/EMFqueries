package main;
import connection.Connect;
import file.FileFunction;
import entities.Arguments;

public class Main {
	public static void main(String[] args) throws Exception {
		FileFunction f = new FileFunction();
		Arguments argument = f.load();
		System.out.print(argument.toString());
	}
}
