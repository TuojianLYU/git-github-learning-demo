package z3ConfigurationGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

public class HWSkillConstructor {

	public BoolExpr skillConstructing(Context ctx, ArrayList<IntExpr> xlist, int numOfFBs, IntExpr[][] intensity,
			int numOfMax, IntExpr sumExp, Map<String, Integer> swSkill1, Map<String, Integer> hwSkill1) {
		System.out.println("-------------This is the testing block info-------------------");

		BoolExpr andBool = ctx.mkAnd();
//		BoolExpr orBool = ctx.mkOr();

		Map<String, Integer> swSkill = new HashMap<>();
		Map<String, Integer> hwSkill = new HashMap<>();

		for (int i = 0; i < numOfFBs; i++) {
			swSkill.put("x" + (i + 1), 0);
		}

		for (int i = 1; i <= numOfMax; i++) {
			hwSkill.put(Integer.toString(i), 0);
		}

		// TODO: initialize the hwSkill and swSkill
//		swSkill.put("x1", 1);
		swSkill.put("x2", 1);
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
		hwSkill.put(Integer.toString(1), 1);
		hwSkill.put(Integer.toString(2), 1);
		hwSkill.put(Integer.toString(3), 1);

		// constructing the z3 configuration
		for (int i = 0; i < numOfFBs; i++) {
			BoolExpr orBool = ctx.mkOr();
			for (int j = 1; j <= numOfMax; j++) {
				orBool = ctx.mkOr(orBool, ctx.mkEq(ctx.mkITE(ctx.mkEq(xlist.get(i), ctx.mkInt(j)),
						ctx.mkITE(ctx.mkEq(ctx.mkInt(swSkill.get("x" + (i + 1))),
								ctx.mkInt(hwSkill.get(Integer.toString(j)))), ctx.mkTrue(), ctx.mkFalse()),
						ctx.mkFalse()), ctx.mkTrue()));

			}
			andBool = ctx.mkAnd(andBool, ctx.mkEq(
					ctx.mkITE(ctx.mkGt(ctx.mkInt(swSkill.get("x" + (i + 1))), ctx.mkInt(0)), orBool, ctx.mkTrue()),
					ctx.mkTrue()));
		}

		return andBool;
	}
}
