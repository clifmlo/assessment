package com.java.assessment.util;

import org.springframework.stereotype.Component;

@Component
public class HighestCommonFactor {

    public int findHighestCommonFactor(final int intArray[]) {
        return findHighestCommonFactorForMultipleNumbers(intArray);
    }

    private int findHighestCommonFactorForTwoNumbers(int a, int b) {
        if (a == 0) {
            return b;
        }

        return findHighestCommonFactorForTwoNumbers(b % a, a);
    }

    private int findHighestCommonFactorForMultipleNumbers(final int intArray[]) {
        int highestCommonFactor = intArray[0];
        int arrayLength = intArray.length;
        for (int i = 1; i < arrayLength; i++)
            highestCommonFactor = findHighestCommonFactorForTwoNumbers(intArray[i], highestCommonFactor);

        return highestCommonFactor;
    }
}
