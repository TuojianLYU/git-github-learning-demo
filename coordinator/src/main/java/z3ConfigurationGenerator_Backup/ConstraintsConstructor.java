package z3ConfigurationGenerator_Backup;

import java.lang.reflect.Array;

import javax.swing.SpringLayout.Constraints;

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

//			System.out.println("here is the 1rst loop of myLoop");

			constraints = constraintsConstructor(array, constraints);
		}

		for (int i = 0; i < array.length - 1; i++) {
//			System.out.println("here is the 2nd loop of myLoop");
			if (array[array.length - i - 2] != n - i - 1) {
				array[array.length - i - 2]++;
//				System.out.println("arrayUp:" + array[array.length - i - 2] + "  index" + (array.length - i - 2));
				for (int j = array.length - i - 1; j <= array.length - 1; j++) {

					array[j] = array[j - 1] + 1;
//					System.out.println("array[j]:" + array[j] + "  j:" + j);
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

//		System.out.println("here is the constraintsConstructor");

		for (int i = 0; i < array.length - 1; i++) {

//			System.out.println("here is the loop of constraintsConstructor, and the array[i+1] = " + array[i + 1]);

			temp += constraintsTemplate1.replace("le2", (String) ("x" + array[i + 1]));
		}

		constraints += constraintsTemplate2.replace("expr", temp);

//		System.out.println("constrains:" + constraints);

		return constraints;
	}
}
