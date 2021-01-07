package z3ConfigurationGenerator_Backup;

import java.util.HashMap;
import java.util.Map;

public class SkillMapGenerator {

	public static Map<String, Integer> swMapGenerating(int numOfFBs) {
		Map<String, Integer> swSkill = new HashMap<>();

		for (int i = 0; i < numOfFBs; i++) {
			swSkill.put("x" + (i + 1), 0);
		}

		swSkill.put("x2", 1);
		swSkill.put("x7", 1);
		swSkill.put("x8", 1);
		swSkill.put("x9", 1);
		swSkill.put("x10", 1);
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

		return swSkill;
	}

	public static Map<String, Integer> hwMapGenerating(int numOfFBs) {
		Map<String, Integer> hwSkill = new HashMap<>();

		for (int i = 0; i < numOfFBs; i++) {
			hwSkill.put(Integer.toString(i), 0);
		}
//		hwSkill.put(Integer.toString(3), 1);
		hwSkill.put(Integer.toString(1), 1);
//		hwSkill.put(Integer.toString(3), 1);

		return hwSkill;
	}
}
