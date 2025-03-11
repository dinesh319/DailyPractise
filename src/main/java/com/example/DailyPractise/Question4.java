package com.example.DailyPractise;

import java.util.List;

public class Question4 {
    public static void main(String[] args) {
        List<String> names = List.of("dinesh" , "hanumanthu" ,"dhinu");
        List<String> uperCasednames = names.stream().map(String::toUpperCase).toList();
        System.out.println(uperCasednames);
    }
}
