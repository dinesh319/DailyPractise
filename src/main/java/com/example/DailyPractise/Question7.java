package com.example.DailyPractise;

import java.util.List;
import java.util.stream.Collectors;

public class Question7 {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(9,0,1,4,1,0,1,9,8,4);
        List<Integer> distinctNumbers = numbers.stream().distinct().toList();
        System.out.println(distinctNumbers);

        Long distinctElementsCount = numbers.stream().distinct().count();
        System.out.println(distinctElementsCount);
    }
}
