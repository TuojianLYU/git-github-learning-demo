/*
Author: Tuojian
This is the correct version z3 configuration generator but 
what is missing is the functionality to receive the variables 
from SysFileParser compoenent which is now implemented now.
*/
package z3ConfigurationGenerator.copy;

import java.util.ArrayList;
import java.util.HashMap;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Optimize;

import z3ConfigurationGenerator.MinimizeSumConstructor;

public class Z3Generator {
	String numOfContainers = "3";
	int numOfFBs = 10;
	int numMaxFBs = 4;
	IntExpr[][] intensity = new IntExpr[numOfFBs][];
	HashMap<String, String> cfg = new HashMap<String, String>();
	Context ctx = new Context(cfg);

	public Context getCtx() {
		return this.ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
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
	
	

//	public IntExpr[][] setIntensity(Context ctx) {
//		for (int i = 0; i < numOfFBs; i++) {
//			intensity[i] = new IntExpr[numOfFBs];
//			for (int j = 0; j < numOfFBs; j++)
//				intensity[i][j] = ctx.mkInt(0);
//		}
//		intensity[0][1] = ctx.mkInt(3);
//		intensity[1][0] = ctx.mkInt(3);
//		intensity[1][2] = ctx.mkInt(3);
//		intensity[2][1] = ctx.mkInt(3);
//		intensity[2][3] = ctx.mkInt(2);
//		intensity[3][2] = ctx.mkInt(2);
//		intensity[3][4] = ctx.mkInt(1);
//		intensity[4][3] = ctx.mkInt(1);
//		return intensity;
//	}

	public IntExpr[][] getIntensity() {
		return intensity;
	}

	public void setIntensity(IntExpr[][] intensity) {
		this.intensity = intensity;
	}

	public void generating() throws TestFailedException {

		ConstraintsConstructor constraintsConstructor = new ConstraintsConstructor();
		InitializationConstructor initializationConstructor = new InitializationConstructor();
		MinimizeSumConstructor minimizeSumConstructor = new MinimizeSumConstructor();

		String initialization = new String();
		String constraints = new String();
		String assertExpr = "(assert (and expr))";
		String z3Expr = new String();
		String z3ExprTemplate = "(declare-const expr Int)";
		String rangeLow = "1";
		BoolExpr be = ctx.mkAnd();
		IntExpr sumExp = ctx.mkIntConst("sum");

		// this loop is for defineing the x1, x2 ... IntExpr type variables
		ArrayList<IntExpr> xlist = new ArrayList();
		for (int i = 0; i < numOfFBs; i++) {
			IntExpr xExp = ctx.mkIntConst("x" + (i + 1));
			xlist.add(xExp);
		}

		// generating the (declare-const x Int) and (>= x 1)(<= x 3) and (= x1 x2)(=
		// x1 x3)
		// and the final assert expr
		numMaxFBs += 1;	// the real number is the number of taken balls not the maximum number, so it
						// should plus 1
		initialization = initializationConstructor.initializationConstructing(numOfFBs, rangeLow, numOfContainers);
		constraints = constraintsConstructor.getConstraints(numOfFBs, numMaxFBs);
		assertExpr = assertExpr.replace("expr", initialization + constraints);

		// combine all the generated constraints into final z3 configuration z3Expr
		for (int i = 0; i < numOfFBs; i++) {
			z3Expr += z3ExprTemplate.replace("expr", (String) ("x" + (i + 1)));
		}
		z3Expr += assertExpr;
		be = minimizeSumConstructor.minimizeSumConstructing(ctx, xlist, numOfFBs, intensity, z3Expr, sumExp);

		// optimize the z3 question
		Optimize opt = ctx.mkOptimize();
		opt.Add(be);
		Optimize.Handle ms = opt.MkMinimize(sumExp);
		System.out.println(opt.Check());
		System.out.println(opt.getModel());

	}

	@SuppressWarnings("serial")
	public
	class TestFailedException extends Exception {
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

}