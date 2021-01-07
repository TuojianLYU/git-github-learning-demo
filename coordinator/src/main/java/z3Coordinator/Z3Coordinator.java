package z3Coordinator;

import java.io.IOException;
import java.util.HashMap;

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
	String filePath;

	public static void main(String[] args) {

		long startTime = System.nanoTime();
		// This definition block is to define all the required variables for z3
		// generator
		// The swSkill and hwSkill are now manually defined
		int numMaxFBs = 5;
		int numOfContainers = 5;
		String filepath = "xml/QX10.xml";

		Z3Coordinator z3Coordinator = new Z3Coordinator();
		z3Coordinator.initialization(z3Coordinator, filepath);

		z3Coordinator.getZ3Generator().initialization(z3Coordinator.getZ3Parser(), z3Coordinator.getFilePath(),
				z3Coordinator.getZ3Generator(), z3Coordinator.getCtx(), numMaxFBs, numOfContainers);

		try {
			z3Coordinator.getZ3Generator().generating(z3Coordinator.getCtx());
		} catch (TestFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Execution time = " + ((System.nanoTime() - startTime) / 1000000));

	}

	public void initialization(Z3Coordinator z3Coordinator, String filepath) {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		Z3Generator z3Generator = new Z3Generator();

		// -------------------
		z3Coordinator.setFilePath(filepath);
		String file = z3Coordinator.getFilePath();
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
