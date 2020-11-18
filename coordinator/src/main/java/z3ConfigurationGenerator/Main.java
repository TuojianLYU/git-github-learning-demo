package z3ConfigurationGenerator;

import java.util.HashMap;

import com.microsoft.z3.Context;

import z3ConfigurationGenerator.Z3Generator.TestFailedException;

public class Main {

	public static void main(String[] args) {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);

		String numOfContainers = "5";
		int numOfFBs = 10;
		int numMaxFBs = 3;

		Z3Generator z3Generator = new Z3Generator();
		z3Generator.setNumMaxFBs(numMaxFBs);
		z3Generator.setNumOfContainers(numOfContainers);
		z3Generator.setNumOfFBs(numOfFBs);
		z3Generator.setCtx(ctx);
		z3Generator.setIntensity(ctx);
		
		try {
			z3Generator.generating();
		} catch (TestFailedException e) {
			e.printStackTrace();
		}
	}
}
