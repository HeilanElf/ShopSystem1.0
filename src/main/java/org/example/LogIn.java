package org.example;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LogIn {
    private final String basePath = System.getProperty("user.dir") + "//src//main//java//org//example//text//";
    Scanner scanner=new Scanner(System.in);
    String filePath;
    public  String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder encryptedPassword = new StringBuilder();
            for (byte b : hash) {
                encryptedPassword.append(String.format("%02x", b));
            }
            return encryptedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("加密算法不可用", e);
        }
    }
    public  void registerUser(String filePath, String username, String password) {
        String encryptedPassword = encryptPassword(password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String userInfo = username + "," + encryptedPassword;
            writer.write(userInfo);
            writer.newLine();
            System.out.println("注册成功！");
        } catch (IOException e) {
            System.out.println("注册失败: " + e.getMessage());
        }
    }
    public boolean isMatch(String filePath, String name, String password) {
        String encryptedPassword = encryptPassword(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 1) {
                    System.out.println("账户密码被管理员(主管）重置过！");
                    System.out.print("请确认密码：");
                    String newpass = scanner.nextLine();
                    registerUser(filePath, name, newpass);
                    return true;
                } else {
                    if (name.equals(userInfo[0]) && encryptedPassword.equals(userInfo[1])) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("系统错误: " + e.getMessage());
        }
        return false;
    }

    public boolean userExists(String filePath, String userName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo[0].equals(userName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("系统错误: " + e.getMessage());
        }
        return false;
    }
    public int userSign(int command) {
        int count = 0;
        System.out.print("用户名：");
        String userName = scanner.next();
        System.out.print("密码：");
        String password = scanner.next();
        if (command==1){
            filePath=basePath+"MasterData.txt";
        }
        if (command == 2) {
            filePath = basePath + "UserData.txt";
        }
        boolean userExists = userExists(filePath, userName);
        if (!userExists && command==2) {
            System.out.println("用户不存在！是否自动注册？Yes/No:");
            String choice = scanner.next();
            while(true){
                if (choice.equalsIgnoreCase("Yes")) {
                    registerUser(filePath, userName, password);
                    break;
                } else if(choice.equals("No")){
                    System.out.println("登录失败！");
                    break;
                }  else{
                    System.out.print("输入非法，请重新输入：");
                    choice = scanner.next();
                }
            }
        } else {
            while (!isMatch(filePath, userName, password) && count < 3) {
                count++;
                if(command==1){
                    System.out.println("管理员不存在或密码错误！");
                    System.out.print("用户名：");
                    userName = scanner.next();
                    System.out.print("密码：");
                    password = scanner.next();
                }
                if(command==2){
                    System.out.println("用户名或密码错误！");
                    System.out.print("用户名：");
                    userName = scanner.next();
                    System.out.print("密码：");
                    password = scanner.next();
                }

            }
            if (count >= 5) {
                System.out.println("由于您多次失败，请稍后再试！");
            } else {
                System.out.println("登录成功！");
            }
        }
        return count;
    }
    public int sign(int command) {
        int count = 0;
        if (command == 1) {
            filePath = basePath + "MasterData.txt";
        } else if (command == 2) {
            filePath = basePath + "UserData.txt";
        }
        if (command == 2) {
            System.out.println("欢迎成为商品管理系统新用户！");
            System.out.print("请输入用户名：");
            String userName = scanner.nextLine();
            System.out.print("请输入密码：");
            String password = scanner.nextLine();
            registerUser(filePath, userName, password);
        } else {
            count = userSign(2);
        }
        return count;
    }
}
