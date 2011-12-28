package ch.fixme.cowsay;

import android.test.AndroidTestCase;

public class CowTest extends AndroidTestCase {
	public void testGetCowTypes() {
		Cow cow = new Cow(getContext());
		String[] cows = cow.getCowTypes();
		
		for (int i = 0; i < cows.length; i++) {
			String string = cows[i];
			cow.style = string;
			cow.asString();
		}
	}
}
