package entities;
import java.util.*;

public class Arguments {
	List<String> S;
	int N;
	List<String> V;
	List<String> F;
	List<List> sigma;
	List<String> G;
	List<String> where;
	public Arguments(List<String> s, Integer n, List<String> v,
					 List<String> f, List<List> sigma,
					 List<String> g, List<String> where) {
		super();
		S = s;
		N = n;
		V = v;
		F = f;
		this.sigma = sigma;
		G = g;
		this.where = where;
	}
	public Arguments() {
		super();
	}
	public String toString() {
		return "EMFVariables [S=" + S + ", N=" + N + ", V=" + V + ", F=" + F
				+ ", Sigma=" + sigma + ", G=" + G + ", where=" + where + "]";
	}
}
