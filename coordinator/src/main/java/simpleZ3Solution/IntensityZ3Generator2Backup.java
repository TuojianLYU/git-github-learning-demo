package simpleZ3Solution;

/*
This class was the step2solution before,
it consideres the max number of FBs can be deployed 
to a single container with the introduced intensity[][].
*/
import java.util.HashMap;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Optimize;

public class IntensityZ3Generator2Backup {

	public static void main(String[] args) {

		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		Context ctx = new Context(cfg);
		test1(ctx);
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

		// 5x5 matrix of integer variables
		IntExpr[][] intensity = new IntExpr[5][];
		for (int i = 0; i < 5; i++) {
			intensity[i] = new IntExpr[5];
			for (int j = 0; j < 5; j++)
				intensity[i][j] = ctx.mkInt(0);
		}

		intensity[0][1] = ctx.mkInt(2);
		intensity[1][0] = ctx.mkInt(2);
		intensity[1][2] = ctx.mkInt(1);
		intensity[2][1] = ctx.mkInt(1);
		intensity[2][3] = ctx.mkInt(1);
		intensity[3][2] = ctx.mkInt(1);
		intensity[3][4] = ctx.mkInt(2);
		intensity[4][3] = ctx.mkInt(2);

		// Set constraints.
		IntExpr x1Exp = ctx.mkIntConst("x1");
		IntExpr x2Exp = ctx.mkIntConst("x2");
		IntExpr x3Exp = ctx.mkIntConst("x3");
		IntExpr x4Exp = ctx.mkIntConst("x4");
		IntExpr x5Exp = ctx.mkIntConst("x5");
		IntExpr sumExp = ctx.mkIntConst("sum");

		ArithExpr xr = (IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x2Exp), ctx.mkTrue()), intensity[0][1],
				ctx.mkInt(0));
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				xr = ctx.mkAdd((IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x2Exp), ctx.mkTrue()),
						intensity[i][j], ctx.mkInt(0)), xr);
			}
		}

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
				ctx.mkDistinct(ctx.mkAnd(ctx.mkEq(x3Exp, x4Exp), ctx.mkEq(x3Exp, x5Exp)), ctx.mkTrue()),
				ctx.mkEq(sumExp,
						ctx.mkAdd(
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x2Exp), ctx.mkTrue()),
										intensity[0][1], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x3Exp), ctx.mkTrue()),
										intensity[0][2], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x4Exp), ctx.mkTrue()),
										intensity[0][3], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x1Exp, x5Exp), ctx.mkTrue()),
										intensity[0][4], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x2Exp, x3Exp), ctx.mkTrue()),
										intensity[1][2], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x2Exp, x4Exp), ctx.mkTrue()),
										intensity[1][3], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x2Exp, x5Exp), ctx.mkTrue()),
										intensity[1][4], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x3Exp, x4Exp), ctx.mkTrue()),
										intensity[2][3], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x3Exp, x5Exp), ctx.mkTrue()),
										intensity[2][4], ctx.mkInt(0)),
								(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(x4Exp, x5Exp), ctx.mkTrue()),
										intensity[3][4], ctx.mkInt(0)))));

		Optimize.Handle ms = opt.MkMinimize(sumExp);

		System.out.println("--------------");
		System.out.println(opt.Check());
		System.out.println(opt.getModel());

	}
}
