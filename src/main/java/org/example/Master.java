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
        String basePath1=System.getProperty("user.dir")+"//src//main//java//org//example//userData//";
        String filePath=basePath+"UserData.txt";
        if(command==1){
            userMaster.showUserData();
            System.out.println("是否返回上一级菜单？请按任意键继续..");
            Scanner scanner=new Scanner(System.in);
            String xuanze=scanner.nextLine();

        }
        if(command==2){
            System.out.print("请输入要删除的用户ID：");
            String userID=scanner.next();
            userMaster.deleteUserData(userID);
            menu.consoleDelay();
        }
        if(command==3){
            menu.showSearchStyle();
            System.out.print("请选择您的查询方式：");
            int fangshi=isTrueEnter.inthefa(3);
            while(true){
                if(fangshi==0){
                    break;
                }else{
                    switch(fangshi){
                        case 1:
                            System.out.print("请输入用户ID：");
                            String userID=scanner.next();
                            userMaster.showUserData();
                            menu.consoleDelay();
                            break;
                        case 2:
                            System.out.print("请输入用户名：");
                            String username=scanner.next();
                            userMaster.showUserData();
                            menu.consoleDelay();
                            break;
                        case 3:
                            userMaster.showUserData();
                            menu.consoleDelay();
                            break;
                    }
                    menu.showSearchStyle();
                    System.out.print("请选择您的查询方式：");
                    fangshi=isTrueEnter.inthefa(3);
                }
            }
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
                                menu.consoleDelay();
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
                                menu.consoleDelay();
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
                                menu.consoleDelay();
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
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            readUserDataFiles(folder);
        } else {
            System.out.println("文件夹不存在！");
        }
    }
    public void readUserDataFiles(File folder) {
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                String fileName = file.getName();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] userInfo = line.split(",");
                        if (userInfo.length == 7) {
                            String userID = userInfo[0].trim();
                            String userName = userInfo[1].trim();
                            String userLevel = userInfo[2].trim();
                            String registrationTime = userInfo[3].trim();
                            String totalConsumption = userInfo[4].trim();
                            String phoneNumber = userInfo[5].trim();
                            String email = userInfo[6].trim();

                            String output = "客户ID:" + userID + " 用户名:" + userName + " 用户级别:" + userLevel +
                                    " 用户注册时间:" + registrationTime + " 客户累计消费总金额:" + totalConsumption + " 用户手机号:" +
                                    phoneNumber + " 用户邮箱:" + email;
                            System.out.println(output);
                        } else {
                            System.out.println("文件" + fileName + "的内容格式不正确！");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public  void deleteLineByUsername(String filePath, String usernameToDelete) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.txt"); // 创建一个临时文件用于保存更新后的内容

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean matchFound = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                String username = parts[0];

                if (username.equals(usernameToDelete)) {
                    matchFound = true;
                    continue; // 跳过匹配的行，不写入临时文件
                }

                writer.write(currentLine);
                writer.newLine();
            }

            if (!matchFound) {
                System.out.println("该用户不存在！");
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除原始文件
        if (!inputFile.delete()) {
            System.out.println("系统错误！");
            return;
        }

        // 将临时文件重命名为原始文件名
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("系统错误！");
        }
    }
    public void deleteUserData(String userID){
        String userID1=userID+".txt";
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        String filePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//UserData.txt";
        File folder = new File(folderPath);
        Scanner scanner=new Scanner(System.in);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(); // 获取文件夹下所有文件
            if (files == null) {
                System.out.println("用户为空！");
                return;
            }
            for (File file : files) {
                if (file.isFile() && file.getName().equals(userID1)) { // 判断文件名是否匹配
                    System.out.print("请确认删除用户名：");
                    String userName=scanner.next();
                    System.out.print("是否确认删除该用户！Y/N:");
                    String confirmation = scanner.next();
                    if(confirmation.equalsIgnoreCase("Y")){
                        deleteLineByUsername(filePath,userName);
                        if (file.delete()) { // 删除文件
                            System.out.println("已删除ID为:"+userID+"的用户！");
                        } else {
                            System.out.println("删除失败！");
                        }
                    }else{
                        System.out.println("已取消删除！");
                        return;
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在！");
        }

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
