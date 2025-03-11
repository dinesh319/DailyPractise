package com.example.DailyPractise;

import java.util.List;

public class Question6 {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(9,0,1,4,1,0,1,9,8,4);
        Long countGreaterThanFive = numbers.stream().filter(num -> num>5).count();
        System.out.println(countGreaterThanFive);

        Long countLesserThanFive = numbers.stream().filter(num->num<5).count();
        System.out.println(countLesserThanFive);


    }
}
