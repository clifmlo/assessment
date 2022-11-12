package com.java.assessment;

import com.java.assessment.util.HighestCommonFactor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
class HighestCommonFactorTests {

	@Autowired
	HighestCommonFactor highestCommonFactor;

	@Test
	void testFindHighestCommonFactor() {
		int intsArray1[] = {5, 6, 7, 9 , 10};
		int intsArray2[] = { 10, 20, 2, 6 };
		int intsArray3[] = { 180, 15, 60, 45 };
		int intsArray4[] = { 160, 10, 30, 90 };
		int intsArray5[] = { 49, 28, 56, 7 };

		int test1 = highestCommonFactor.findHighestCommonFactor(intsArray1);
		int test2 = highestCommonFactor.findHighestCommonFactor(intsArray2);
		int test3 = highestCommonFactor.findHighestCommonFactor(intsArray3);
		int test4 = highestCommonFactor.findHighestCommonFactor(intsArray4);
		int test5 = highestCommonFactor.findHighestCommonFactor(intsArray5);

		assertEquals(1, test1);
		assertEquals(2, test2);
		assertEquals(15, test3);
		assertEquals(10, test4);
		assertEquals(7, test5);

	}
}
