package z3Coordinator;

import java.util.HashMap;

import com.microsoft.z3.Context;

import z3ConfigurationGenerator.Z3Generator;
import z3ConfigurationGenerator.Z3Generator.TestFailedException;

public class Z3Coordinator {

	public static void main(String[] args) {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		
		
		int numMaxFBs = 5;
		int numOfContainers = 5;
		String filepath = "open62541Test.xml";

		Z3Generator z3Generator = new Z3Generator();
		z3Generator.initialization(filepath, z3Generator, ctx, numMaxFBs, numOfContainers);
		try {
			z3Generator.generating(ctx);
		} catch (TestFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
