/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.ui;

import java.util.Scanner;

/**
 *
 * @author hschuler2992
 */
public class UserIOConsoleImpl implements UserIO{
    Scanner scanner = new Scanner(System.in);
    
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        double result;
        print(prompt);
        result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0.0;
        boolean badInput = true;
        while (badInput) {
            result = readDouble(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                System.out.println("Input needs to be >= " + min + " and <= " + max);
            }
        }
        return result;
    }

    @Override
    public float readFloat(String prompt) {
        float result;
        print(prompt);
        result = scanner.nextFloat();
        scanner.nextLine();
        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        boolean badInput = true;
        while (badInput) {
            result = readFloat(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                System.out.println("Input needs to be >= " + min + " and <= " + max);
            }
        }
        return result;
    }

    @Override
    public int readInt(String prompt) {
        int result;
        print(prompt);
        result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean badInput = true;
        while (badInput) {
            result = readInt(prompt);
            
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                System.out.println("Input need to be >= " + min + " and <= " + max);
            }
        }
        return result;

    }

    @Override
    public long readLong(String prompt) {
        long result;
        print(prompt);
        result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        boolean badInput = true;
        while (badInput) {
            result = readLong(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                System.out.println("Input need to be >= " + min + " and <= " + max);
            }
        }
        return result;}

    @Override
    public String readString(String prompt) {
        String result = "";

        print(prompt);
        result = scanner.nextLine();
        return result;
    }
}
