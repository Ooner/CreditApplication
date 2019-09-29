package com.kocfinans.creditApplication.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DummyCreditScoreService {

    public long getCustomerScoreByTcNo(String TcNo){
        int[] score = {100,500,700,1000,2000};
        Random random = new Random();
        int randomInteger = random.nextInt(4);
        System.out.println(score[randomInteger]);
        return score[randomInteger];
    }
}
