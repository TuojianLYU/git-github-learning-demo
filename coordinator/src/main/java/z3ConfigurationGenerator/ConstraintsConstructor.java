package z3ConfigurationGenerator;

public class ConstraintsConstructor {

	public String getConstraints(int numOfFBs, int numMaxFBs) {

		String constraints = "";
		String temp = "(and expr)";

		int[] array = new int[numMaxFBs];

		for (int i = 0; i < array.length; i++) {
			array[i] = i + 1;
		}

		constraints = myLoop(array, numOfFBs, constraints);

		constraints = temp.replace("expr", constraints);

		return constraints;

	}

	static String myLoop(int[] array, int n, String constraints) {

		for (int i = array[array.length - 1]; i <= n; i++) {
			array[array.length - 1] = i;
			constraints = constraintsConstructor(array, constraints);
		}

		for (int i = 0; i < array.length - 1; i++) {
			if (array[array.length - i - 2] != n - i - 1) {
				array[array.length - i - 2]++;
				for (int j = array.length - i - 1; j <= array.length - 1; j++) {
					array[j] = array[j - 1] + 1;
				}
				return myLoop(array, n, constraints);
			}
		}

		return constraints;
	}

	static String constraintsConstructor(int[] array, String constraints) {

		String constraintsTemplate1 = new String();
		String constraintsTemplate2 = new String();
		String temp = new String();

		constraintsTemplate1 = "(= le1 le2)";
		constraintsTemplate2 = "(not (and expr))";

		constraintsTemplate1 = constraintsTemplate1.replace("le1", (String) ("x" + array[0]));

		for (int i = 0; i < array.length - 1; i++) {

			temp += constraintsTemplate1.replace("le2", (String) ("x" + array[i + 1]));
		}

		constraints += constraintsTemplate2.replace("expr", temp);

		return constraints;
	}
}
