package com.example.DailyPractise;

import java.util.List;

public class Question1 {

    public static void main(String args[]){
        List<Integer> numbers = List.of(1,2,3,4,5);
        List<Integer> evenNumbers = numbers.stream().filter(num -> num%2==0).toList();
        System.out.println(evenNumbers);
    }
}
