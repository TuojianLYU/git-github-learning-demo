package simpleZ3Solution;

/*
This class was the step1solution before,
it only considers the max number of FBs which can be 
deployed to a single container.
*/
import java.util.HashMap;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Solver;

public class MaxFBsZ3Generator {

	public static void main(String[] args) {

		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		test1(ctx);
//		optimizeExample(ctx);
	}

	static IntExpr testfunc(IntExpr x, Context ctx) {
		return (IntExpr) ctx.mkAdd(x, ctx.mkInt(1));
	}

	static BoolExpr noZeroFunc(IntExpr var, Context ctx) {

		System.out.println("-----noZeroFunc-----");

		if (var.equals(ctx.mkInt(0))) {
			System.out.println("var=" + var);
			System.out.println("-----finished the noZeroFunc with 0-----");
			return ctx.mkFalse();
		} else {
			System.out.println("var=" + var);
			System.out.println("-----finished the noZeroFunc with not 0-----");
			return ctx.mkTrue();
		}

	}

	static void test1(Context ctx) {

		Optimize opt = ctx.mkOptimize();

		// Set constraints.
		IntExpr x1Exp = ctx.mkIntConst("x1");
		IntExpr x2Exp = ctx.mkIntConst("x2");
		IntExpr x3Exp = ctx.mkIntConst("x3");
		IntExpr x4Exp = ctx.mkIntConst("x4");
		IntExpr x5Exp = ctx.mkIntConst("x5");

		opt.Add(ctx.mkGe(x1Exp, ctx.mkInt(1)), ctx.mkLe(x1Exp, ctx.mkInt(3)), ctx.mkGe(x2Exp, ctx.mkInt(1)),
				ctx.mkLe(x2Exp, ctx.mkInt(3)), ctx.mkGe(x3Exp, ctx.mkInt(1)), ctx.mkLe(x3Exp, ctx.mkInt(3)),
				ctx.mkGe(x4Exp, ctx.mkInt(1)), ctx.mkLe(x4Exp, ctx.mkInt(3)), ctx.mkGe(x5Exp, ctx.mkInt(1)),
				ctx.mkLe(x5Exp, ctx.mkInt(3)),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x2Exp), ctx.mkEq(x1Exp, x3Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x2Exp), ctx.mkEq(x1Exp, x4Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x2Exp), ctx.mkEq(x1Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x3Exp), ctx.mkEq(x1Exp, x4Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x3Exp), ctx.mkEq(x1Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x1Exp, x4Exp), ctx.mkEq(x1Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x2Exp, x3Exp), ctx.mkEq(x2Exp, x4Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x2Exp, x3Exp), ctx.mkEq(x2Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x2Exp, x4Exp), ctx.mkEq(x2Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x3Exp, x4Exp), ctx.mkEq(x3Exp, x5Exp)), ctx.mkTrue()));

//		Optimize.Handle mx = opt.MkMaximize();

		System.out.println("--------------");
		System.out.println(opt.Check());
		System.out.println(opt.getModel());

	}
}
