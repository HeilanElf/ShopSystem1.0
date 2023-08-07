package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Master {
    private String basePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//";
    Menu menu=new Menu();
    PasswordMaster passWordMaster=new PasswordMaster();
    UserMaster userMaster=new UserMaster();
    DuctionMaster ductionMaster=new DuctionMaster();
    Scanner scanner=new Scanner(System.in);
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    public void passwordMaster(int command1){
        String signfilePath;
        if(command1==1){
            signfilePath = basePath+"MasterData.txt";
        }else{
            signfilePath =basePath+ "UserData.txt";
        }
        ArrayList<String[]> userInfoList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(signfilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                userInfoList.add(userInfo);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("系统错误: " + e.getMessage());
        }
        if(command1==1){

            System.out.print("请确定用户名:");
            String username=scanner.next();
            boolean find=false;
            for(String[] userInfo:userInfoList){
                if(userInfo[0].equals(username)){
                    System.out.print("请输入新密码：");
                    String newPassword=scanner.next();
                    passWordMaster.modifyUserInfo(signfilePath,userInfo[0],newPassword);
                    find=true;
                }
            }
            if(!find){
                System.out.print("该管理员不存在！请联系主管添加。\n");
            }
        }else{
            System.out.print("请确定用户名:");
            String username=scanner.next();
            boolean find=false;
            for(String[] userInfo:userInfoList){
                if(userInfo[0].equals(username)) {
                    passWordMaster.resetUserInfo(signfilePath, userInfoList.get(0)[0]);
                    find=true;
                }
            }
            if(!find){
                System.out.print("该用户不存在！\n");
            }
        }
    }
    public void userMaster(int command){
        String filePath=basePath+"UserData.txt";
        if(command==1){

        }
    }
    public void ductionMaster(int command){

    }
    public void master(int command){
        while(true){
            if (command==0){
                break;
            }else{
                switch (command){
                    case 1:
                        while(true){
                            menu.showpasswordMaster();
                            System.out.print("请输入您的选择：");
                            command=isTrueEnter.inthefa(2);
                            if(command==0){
                                break;
                            }else{
                                passwordMaster(command);
                            }
                        }
                        break;
                    case 2:
                        while(true){
                            menu.showuserMaster();
                            System.out.print("请输入您的选择：");
                            command=isTrueEnter.inthefa(3);
                            if(command==0){
                                break;
                            }else{
                                userMaster(command);
                            }
                        }
                        break;
                    case 3:
                        while(true){
                            menu.showductionMaster();
                            System.out.print("请输入您的选择：");
                            command=isTrueEnter.inthefa(5);
                            if(command==0){
                                break;
                            }else{
                                ductionMaster(command);
                            }
                        }
                        break;
                }
            }
            menu.showMaster();
            System.out.print("请输入您的选择：");
            command=isTrueEnter.inthefa(3);
        }
    }
}
class PasswordMaster{
    LogIn logIn=new LogIn();
    public  void modifyUserInfo(String filePath, String username, String newPassword) {
        String enterpassword=logIn.encryptPassword(newPassword);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;
            // 读取文件并修改对应的用户信息
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String fileUsername = userInfo[0];
                if (fileUsername.equals(username)) {
                    StringBuilder modifiedUserInfoBuilder = new StringBuilder();
                    modifiedUserInfoBuilder.append(username).append(",").append(enterpassword);
                    line = modifiedUserInfoBuilder.toString();
                }
                fileContentBuilder.append(line).append(System.lineSeparator());
            }
            reader.close();
            // 将修改后的内容保存回文本文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(fileContentBuilder.toString());
            writer.close();
            System.out.println("修改成功！");
        } catch (IOException e) {
            System.out.println("修改失败: " + e.getMessage());
        }
    }

    // 清空用户信息，将对应字段清空并保存到文本文件中
    public  void resetUserInfo(String filePath, String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder fileContentBuilder = new StringBuilder();
            String line;

            // 读取文件并清空对应的用户字段
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                String fileUsername = userInfo[0];

                if (fileUsername.equals(username)) {
                    line = fileUsername + ",,";
                }

                fileContentBuilder.append(line).append(System.lineSeparator());
            }

            reader.close();

            // 将清空后的内容保存回文本文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(fileContentBuilder.toString());
            writer.close();

            System.out.println("重置成功！");
        } catch (IOException e) {
            System.out.println("重置失败: " + e.getMessage());
        }
    }
}
class UserMaster{
    public void showUserData(){

    }
    public void deleteUserData(){

    }
    public void searchUserData(){

    }
}
class DuctionMaster{
    public void showDuctionInfo(){

    }
    public void addDuctionInfo(){

    }
    public void modifyDuctionInfo(){

    }
    public void deleteDuctionInfo(){

    }
    public void searchDuctionInfo(){

    }
}
