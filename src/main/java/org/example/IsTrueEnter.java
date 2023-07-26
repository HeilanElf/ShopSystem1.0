package org.example;

import java.util.Scanner;

public class IsTrueEnter {
    Scanner scanner=new Scanner(System.in);
    public int inthefa(int n){
        int command;
        while (true) {
            try {
                command = Integer.parseInt(scanner.nextLine());
                if (command >= 0 && command <= n) {
                    break;
                } else {
                    System.out.print("选择错误，请重新输入：");
                }
            } catch (NumberFormatException e) {
                System.out.print("输入非法，请重新输入：");
            }
        }
        return command;
    }
}
