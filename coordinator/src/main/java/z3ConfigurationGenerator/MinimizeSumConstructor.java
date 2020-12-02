package z3ConfigurationGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class MinimizeSumConstructor {
	

	public BoolExpr minimizeSumConstructing(Context ctx, ArrayList<IntExpr> xlist, int numOfFBs, IntExpr[][] intensity,
			IntExpr sumExp) {

//		IntExpr sumExp = ctx.mkIntConst("sum");

		ArithExpr ae = (IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(xlist.get(0), xlist.get(0)), ctx.mkTrue()),
				ctx.mkInt(0), ctx.mkInt(0));
		BoolExpr be = ctx.mkAnd();

		for (int i = 0; i < numOfFBs - 1; i++) {
			for (int j = i + 1; j < numOfFBs; j++) {
				ae = ctx.mkAdd(ae,
						(IntExpr) ctx.mkITE(ctx.mkDistinct(ctx.mkEq(xlist.get(i), xlist.get(j)), ctx.mkTrue()),
								intensity[i][j], ctx.mkInt(0)));
			}
		}

//		BoolExpr f = ctx.parseSMTLIB2String(z3Expr, null, null, null, null)[0];
//		be = ctx.mkAnd(f, ctx.mkEq(sumExp, ae));
		be = ctx.mkAnd(ctx.mkEq(sumExp, ae));

		return be;
	}
}
