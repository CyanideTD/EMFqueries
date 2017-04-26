package entities;
import java.util.*;

public class Arguments {
	List<String> S;
	int N;
	List<String> V;
	List<String> F;
	List<List> sigma;
	List<String> G;
	public Arguments(List<String> s, Integer n, List<String> v,
					 List<String> f, List<List> sigma,
					 List<String> g) {
		super();
		S = s;
		N = n;
		V = v;
		F = f;
		this.sigma = sigma;
		G = g;
	}
	public Arguments() {
		super();
	}
	public String toString() {
		return "EMFVariables [S=" + S + ", N=" + N + ", V=" + V + ", F=" + F
				+ ", Sigma=" + sigma + ", G=" + G + "]";
	}
}
