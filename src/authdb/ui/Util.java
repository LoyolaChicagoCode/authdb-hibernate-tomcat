package authdb.ui;

import java.util.*;

public class Util {

	public static Set stringAsSet(String string) {
		Set result = new HashSet();
		StringTokenizer st = new StringTokenizer(string);
		while (st.hasMoreTokens()) {
			result.add(st.nextToken());
		}
		return result;
  }

	public static String setAsString(Collection collection) {
		StringBuffer result = new StringBuffer();
		Iterator i = collection.iterator();
		while (i.hasNext()) {
			result.append(i.next());
			result.append(" ");
		}
		int length = result.length();
		if (length > 1) {
			result.deleteCharAt(result.length() - 1);
		}
		return result.toString();
	}
}
