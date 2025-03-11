package com.example.DailyPractise;

import java.util.List;

public class Question3 {
    public static void main(String args[]){
        List<Integer> numbers = List.of(1,2,3,4,5);
        Integer sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);

        /*
        : mapToInt converts the stream to an IntStream, which provides the sum
method to get the total.
         */
    }

}
