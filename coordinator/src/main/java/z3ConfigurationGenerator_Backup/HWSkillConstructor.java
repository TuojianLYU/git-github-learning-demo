package z3ConfigurationGenerator_Backup;

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

		Map<String, Integer> swSkill = new HashMap<>();
		Map<String, Integer> hwSkill = new HashMap<>();
		swSkill = SkillMapGenerator.swMapGenerating(numOfFBs);
		hwSkill = SkillMapGenerator.hwMapGenerating(numOfFBs);

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
