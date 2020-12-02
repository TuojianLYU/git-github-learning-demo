package z3Coordinator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.microsoft.z3.Context;

import z3ConfigurationGenerator.Z3Generator;
import z3ConfigurationGenerator.Z3Generator.TestFailedException;
import z3Parser.Z3Parser;

public class Z3Coordinator {

	Context ctx;
	Z3Generator z3Generator = new Z3Generator();
	Z3Parser z3Parser = new Z3Parser();

	public static void main(String[] args) {
		long startTime = System.nanoTime();

		Z3Coordinator z3Coordinator = new Z3Coordinator();
		z3Coordinator.initialization(z3Coordinator);
		Map<String, Integer> swSkill = new HashMap<>();
		Map<String, Integer> hwSkill = new HashMap<>();

		// This definition block is to define all the required variables for z3
		// generator
		// The swSkill and hwSkill are now manually defined
		int numMaxFBs = 5;
		int numOfContainers = 5;
		String filepath = "xml/QX10.xml";
		int numOfFBs = z3Coordinator.getZ3Parser().getNumOfFBs();
		for (int i = 0; i < numOfFBs; i++) {
			swSkill.put("x" + (i + 1), 0);
		}

		for (int i = 1; i <= numOfFBs; i++) {
			hwSkill.put(Integer.toString(i), 0);
		}
//		swSkill.put("x2", 1);
//		swSkill.put("x7", 1);
//		swSkill.put("x8", 1);
//		swSkill.put("x9", 1);
//		swSkill.put("x10", 1);
//		swSkill.put("x11", 1);
//		swSkill.put("x12", 1);
//		swSkill.put("x13", 1);
//		swSkill.put("x14", 1);
//		swSkill.put("x15", 1);
//		swSkill.put("x16", 1);
//		swSkill.put("x17", 1);
//		swSkill.put("x18", 1);
//		swSkill.put("x19", 1);
//		swSkill.put("x20", 1);
//		hwSkill.put(Integer.toString(2), 1);
//		hwSkill.put(Integer.toString(3), 1);

		z3Coordinator.getZ3Generator().initialization(z3Coordinator.getZ3Parser(), filepath,
				z3Coordinator.getZ3Generator(), z3Coordinator.getCtx(), numMaxFBs, numOfContainers, hwSkill, hwSkill);

		try {
			z3Coordinator.getZ3Generator().generating(z3Coordinator.getCtx());
		} catch (TestFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  
		System.out.println("Execution time = " + duration);
	}

	public void initialization(Z3Coordinator z3Coordinator) {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		Z3Generator z3Generator = new Z3Generator();

		// -------------------
		String file = "xml/QX10.xml";
		Z3Parser z3Parser = new Z3Parser();
		try {

			z3Parser.parsing(file, z3Parser);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// -------------------
		z3Coordinator.setCtx(ctx);
		z3Coordinator.setZ3Generator(z3Generator);
		z3Coordinator.setZ3Parser(z3Parser);

	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public Z3Generator getZ3Generator() {
		return z3Generator;
	}

	public void setZ3Generator(Z3Generator z3Generator) {
		this.z3Generator = z3Generator;
	}

	public Z3Parser getZ3Parser() {
		return z3Parser;
	}

	public void setZ3Parser(Z3Parser z3Parser) {
		this.z3Parser = z3Parser;
	}

}
