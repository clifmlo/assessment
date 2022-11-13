package com.java.assessment.util;

import org.springframework.stereotype.Component;

@Component
public class HighestCommonFactor {

    public int findHighestCommonFactor(final int intArray[]) {
        return findHighestCommonFactorForMultipleNumbers(intArray);
    }

    private int findHighestCommonFactorForTwoNumbers(int a, int b) {
        if (a == 0) {
            return b; //b becomes the only common factor
        }

        return findHighestCommonFactorForTwoNumbers(b % a, a); //the recursive function is called until a is 0. In the end, the value of b is the highest common factor of the given two numbers.
    }

    private int findHighestCommonFactorForMultipleNumbers(final int intArray[]) {
        int highestCommonFactor = intArray[0]; //default it to first number
        int arrayLength = intArray.length;
        for (int i = 1; i < arrayLength; i++) //loop times the number of elements in the array
            highestCommonFactor = findHighestCommonFactorForTwoNumbers(intArray[i], highestCommonFactor); //re-use findHighestCommonFactorForTwoNumbers() and test every element against the current highest common factor

        return highestCommonFactor;
    }
}
