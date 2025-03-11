package com.example.DailyPractise;

import java.util.List;
import java.util.stream.Collectors;

public class Question5 {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(9,0,1,4,1,0,1,9,8,4);
        List<Integer> sortedNumbersAsc = numbers.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedNumbersAsc);

        List<Integer> sortedNumbersDesc = numbers.stream().sorted((num1,num2)->num2-num1).collect(Collectors.toList());
        System.out.println(sortedNumbersDesc);
    }
}
