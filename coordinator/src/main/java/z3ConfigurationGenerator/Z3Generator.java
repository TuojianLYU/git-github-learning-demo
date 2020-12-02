/*
Author: Tuojian
This is the correct version z3 configuration generator but 
what is missing is the functionality to receive the variables 
from SysFileParser compoenent which is now implemented now.
*/
package z3ConfigurationGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Optimize;

import z3Parser.Z3Parser;

public class Z3Generator {
	String numOfContainers = "3";
	int numOfFBs;
	int numMaxFBs = 4;
	IntExpr[][] intensity = new IntExpr[numOfFBs][];
	Map<String, Integer> swSkill = new HashMap<>();
	Map<String, Integer> hwSkill = new HashMap<>();

	public void initialization(Z3Parser z3Parser, String file, Z3Generator z3Generator, Context ctx, int numMaxFBs,
			int numOfContainers, Map<String, Integer> swSkill, Map<String, Integer> hwSkill) {

		int[][] intensityTemp = new int[z3Parser.getNumOfFBs()][z3Parser.getNumOfFBs()];
		IntExpr[][] intensity = new IntExpr[z3Parser.getNumOfFBs()][z3Parser.getNumOfFBs()];
		intensityTemp = z3Parser.getIntensity();

		for (int i = 0; i < intensityTemp.length; i++) {
			for (int j = 0; j < intensityTemp.length; j++) {
				intensity[i][j] = ctx.mkInt(intensityTemp[i][j]);
			}
		}

		z3Generator.setNumOfContainers(Integer.toString(numOfContainers));
		z3Generator.setNumMaxFBs(numMaxFBs);
		z3Generator.setNumOfFBs(z3Parser.getNumOfFBs());
		z3Generator.setIntensity(intensity);
		z3Generator.setHwSkill(hwSkill);
		z3Generator.setSwSkill(swSkill);

	}

	public void generating(Context ctx) throws TestFailedException {

		ConstraintsConstructor constraintsConstructor = new ConstraintsConstructor();
		InitializationConstructor initializationConstructor = new InitializationConstructor();
		MinimizeSumConstructor minimizeSumConstructor = new MinimizeSumConstructor();
		HWSkillConstructor hwSkillConstructor = new HWSkillConstructor();

		String initialization = new String();
		String constraints = new String();
		String assertExpr = "(assert (and expr))";
		String z3Expr = new String();
		String z3ExprTemplate = "(declare-const expr Int)";
		String rangeLow = "1";
		BoolExpr be = ctx.mkAnd();
		BoolExpr be1 = ctx.mkAnd();
		IntExpr sumExp = ctx.mkIntConst("MinSumOfIntensity");

		// this loop is for defineing the x1, x2 ... IntExpr type variables
		ArrayList<IntExpr> xlist = new ArrayList();
		for (int i = 0; i < numOfFBs; i++) {
			IntExpr xExp = ctx.mkIntConst("x" + (i + 1));
			xlist.add(xExp);
		}

		// generating the (declare-const x Int) and (>= x 1)(<= x 3) and (= x1 x2)(=
		// x1 x3)
		// and the final assert expr
		numMaxFBs += 1; // the real number is the number of taken balls not the maximum number, so it
						// should plus 1
		initialization = initializationConstructor.initializationConstructing(numOfFBs, rangeLow, numOfContainers);
		constraints = constraintsConstructor.getConstraints(numOfFBs, numMaxFBs);
		assertExpr = assertExpr.replace("expr", initialization + constraints);

		// combine all the generated constraints into final z3 configuration z3Expr
		for (int i = 0; i < numOfFBs; i++) {
			z3Expr += z3ExprTemplate.replace("expr", (String) ("x" + (i + 1)));
		}

		// combine all the constraints and objectives together as z3 format
		z3Expr += assertExpr;
		BoolExpr f = ctx.parseSMTLIB2String(z3Expr, null, null, null, null)[0];
		be = minimizeSumConstructor.minimizeSumConstructing(ctx, xlist, numOfFBs, intensity, sumExp);
		be = ctx.mkAnd(f, be);
		be1 = hwSkillConstructor.skillConstructing(ctx, xlist, numOfFBs, intensity, numMaxFBs - 1, sumExp, swSkill,
				hwSkill);
		be = ctx.mkAnd(be, be1);
		System.out.println(be);

		// optimize the z3 question
		Optimize opt = ctx.mkOptimize();
		opt.Add(be);
		Optimize.Handle ms = opt.MkMinimize(sumExp);
		System.out.println(opt.Check());
		System.out.println(opt.getModel());

	}

	@SuppressWarnings("serial")
	public class TestFailedException extends Exception {
		public TestFailedException() {
			super("Check FAILED");
		}
	};

	static long binomi(int n, int k) {
		if ((n == k) || (k == 0))
			return 1;
		else
			return binomi(n - 1, k) + binomi(n - 1, k - 1);
	}

	public Map<String, Integer> getSwSkill() {
		return swSkill;
	}

	public void setSwSkill(Map<String, Integer> swSkill) {
		this.swSkill = swSkill;
	}

	public Map<String, Integer> getHwSkill() {
		return hwSkill;
	}

	public void setHwSkill(Map<String, Integer> hwSkill) {
		this.hwSkill = hwSkill;
	}

	public String getNumOfContainers() {
		return this.numOfContainers;
	}

	public void setNumOfContainers(String numOfContainers) {
		this.numOfContainers = numOfContainers;
	}

	public int getNumOfFBs() {
		return this.numOfFBs;
	}

	public void setNumOfFBs(int numOfFBs) {
		this.numOfFBs = numOfFBs;
	}

	public int getNumMaxFBs() {
		return this.numMaxFBs;
	}

	public void setNumMaxFBs(int numMaxFBs) {
		this.numMaxFBs = numMaxFBs;
	}

	public IntExpr[][] getIntensity() {
		return intensity;
	}

	public void setIntensity(IntExpr[][] intensity) {
		this.intensity = intensity;
	}

}