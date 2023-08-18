package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LogIn {
    private final String basePath = System.getProperty("user.dir") + "//src//main//java//org//example//text//";
    Scanner scanner=new Scanner(System.in);
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    Regest regest=new Regest();
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
            writer.newLine();
            writer.write(userInfo);
        } catch (IOException e) {
            System.out.println("注册失败: " + e.getMessage());
        }
    }
    public void regestuser(){
        filePath=basePath+"UserData.txt";
        System.out.print("请输入您的用户名:");
        String userName=scanner.next();
        System.out.print("请输入您的密码：");
        String password= isTrueEnter.passwordhefa(scanner.next());
        System.out.print("请确认您的密码：");
        String password1= isTrueEnter.passwordhefa(scanner.next());
        if (password1.equals(password)) {
            registerUser(filePath, userName, password);
            regest.userRegest(userName);
        }else{
            System.out.println("密码不一致！");
        }
    }
    public boolean modifyUserPassword(String filePath, String username, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            // 读取文件并清空对应的用户字段
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String fileUsername = userInfo[0];
                if (fileUsername.equals(username)) {
                    line = fileUsername +","+ encryptPassword(password);
                }
                fileContentBuilder.append(line).append(System.lineSeparator());
            }

            reader.close();

            // 将清空后的内容保存回文本文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(fileContentBuilder.toString());
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("系统错误: " + e.getMessage());
            return false;
        }
    }
    public boolean isMatch(String filePath, String name, String password) {
        String encryptedPassword = encryptPassword(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 1&&userInfo[0].equals(name)) {
                    System.out.print("您的密码已被重置！请输入新密码");
                    password= isTrueEnter.passwordhefa(scanner.next());
                    return modifyUserPassword(filePath,name,password);
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
        String userName;
        String password;
        if (command==1){
            System.out.print("请输入管理员名称：");
            userName= scanner.next();
            System.out.print("请输入您的密码：");
            password = scanner.next();
            filePath=basePath+"MasterData.txt";
            boolean userExists = userExists(filePath, userName);
            if(!userExists){
                System.out.println("管理员不存在！");
                count=5;
            }else{
                while (!isMatch(filePath, userName, password) && count < 5) {
                    count++;
                        System.out.println("管理员不存在或密码错误！");
                        System.out.print("用户名：");
                        userName = scanner.next();
                        System.out.print("密码：");
                        password = isTrueEnter.passwordhefa(scanner.next());
                }
                regest.setCurrentUserName(userName);
            }
        }
        if (command == 2) {
            System.out.print("请输入用户名：");
            userName = scanner.next();
            System.out.print("请输您的密码：");
            password = isTrueEnter.passwordhefa(scanner.next());
            filePath = basePath + "UserData.txt";
            boolean userExists = userExists(filePath, userName);
            if (!userExists) {
                System.out.println("用户不存在！");
                count=5;
            }else {
                while (!isMatch(filePath, userName, password) && count < 5) {
                    count++;
                        System.out.println("用户名或密码错误！");
                        System.out.print("用户名：");
                        userName = scanner.next();
                        System.out.print("密码：");
                        password = isTrueEnter.passwordhefa(scanner.next());
                }
                regest.setCurrentUserName(userName);
            }
        }
        if (count > 5) {
            System.out.println("由于您多次失败，请三小时后再试！");
            Menu menu=new Menu();
            long yanchi=1000*60*60*3;
            menu.consoleDelay(yanchi);
        } else if (count==5) {
            System.out.println("登录失败！");
        } else {
            System.out.println("登录成功！");
        }
        return count;
    }
}
class Regest{
    Scanner scanner = new Scanner(System.in);
    private final String DATA_FOLDER=System.getProperty("user.dir")+"//src//main//java//org//example//userData//";
    private long idCounter=1;
    public void userRegest(String userName){
        String phoneNumber = getInput("请输入手机号：");
        String email = getInput("请输入邮箱：");

        // 创建用户ID
        String userID = createUserID();

        // 创建 userData 文件夹（如果不存在）
        createDataFolder();

        // 检查 userData 文件夹下是否存在重名的文件
        File file = new File(DATA_FOLDER  + userID + ".txt");
        while (file.exists()) {
            userID = createUserID();
            file = new File(DATA_FOLDER  + userID + ".txt");
        }

        try {
            // 创建 txt 文件并写入用户信息
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(userID + "," + userName + ",铜牌客户," + getCurrentDateTime() + ",0," + phoneNumber + "," + email);
            writer.newLine();
            writer.close();

            System.out.println("注册成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  String getInput(String prompt) {
        
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private  String createUserID() {
        return String.format("%04d", idCounter++);
    }

    private  void createDataFolder() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
        }
    }

    public   String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    public void setCurrentUserName(String content){
        final String filePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//CurrentUser.txt";
        try {
            // 创建 FileWriter 对象，传入文件路径
            FileWriter fileWriter = new FileWriter(filePath);
            // 将内容写入文件
            fileWriter.write(content);
            // 刷新缓冲区数据到文件
            fileWriter.flush();
            // 关闭 FileWriter 对象
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("写入文件时发生错误：" + e.getMessage());
        }
    }
    public String getCurrentuserName(){
        final String filePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//CurrentUser.txt";
        String fileContent;
        try {
            // 使用 Files 类的静态方法读取文件内容为字节数组
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            // 将字节数组转换为字符串
            fileContent = new String(fileBytes);
        } catch (IOException e) {
            System.out.println("读取文件时发生错误：" + e.getMessage());
            fileContent ="";
        }
        return fileContent;
    }
}
