package com.codecool.WareStoreProject;

import org.assertj.core.util.Arrays;

import java.util.List;

public class GenericArrayContentEqual {
    public static <T> boolean isEqual(T[] expected, T[] actual){
        List<T> expectedList = (List<T>) Arrays.asList(expected);
        List<T> actualList = (List<T>) Arrays.asList(actual);

        return (expectedList.size() == actualList.size()
                && expectedList.containsAll(actualList)
                && actualList.containsAll(expectedList));
    }
}
