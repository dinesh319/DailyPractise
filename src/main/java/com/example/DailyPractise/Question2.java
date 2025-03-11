package com.example.DailyPractise;

import java.util.List;

public class Question2 {
    public static void main(String args[]){
        List<Integer> numbers = List.of(1,2,3,4,5);
        Integer max = numbers.stream().max(Integer::compare).get();
        System.out.println(max);

        Integer min = numbers.stream().min(Integer::compare).get();
        System.out.println(min);

        /*
             Optional<Integer> max = numbers.stream()
             .max(Integer::compare);
            Explanation: The max method takes a comparator and returns the maximum element
            wrapped in an Optional.
         */
    }
}
