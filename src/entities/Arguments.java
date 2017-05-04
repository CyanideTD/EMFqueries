package entities;
import java.util.*;

public class Arguments {
	List<String> S;
	int N;
	List<String> V;
	List<String> F;
	List<List<String>> sigma;
	List<String> G;
	public Arguments(List<String> s, Integer n, List<String> v,
					 List<String> f, List<List<String>> sigma,
					 List<String> g) {
		super();
		S = s;
		N = n;
		V = v;
		F = f;
		this.sigma = sigma;
		G = g;
	}
	
	public List<String> getV () {
		return V;
	}
	
	public List<String> getS () {
		return S;
	}
	
	public List<String> getF () {
		return F;
	}
	
	public int getN () {
		return N;
	}
	
	public List<String> getG () {
		return G;
	}
	
	public List<List<String>> getSigma() {
		return sigma;
	}
	
	public String toString() {
		return "EMFVariables [S=" + S + ", N=" + N + ", V=" + V + ", F=" + F
				+ ", Sigma=" + sigma + ", G=" + G + "]";
	}
}
