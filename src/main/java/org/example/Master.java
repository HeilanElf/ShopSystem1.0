package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Master {
    private String basePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//";
    private String foderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData//";
    String signfilePath;
    Menu menu=new Menu();
    LogIn logIn=new LogIn();
    PasswordMaster passWordMaster=new PasswordMaster();
    UserMaster userMaster=new UserMaster();
    DuctionMaster ductionMaster=new DuctionMaster();
    Scanner scanner=new Scanner(System.in);
    Regest regest=new Regest();
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    public void passwordMaster(int command1){

        if(command1==1){
            signfilePath = basePath+"MasterData.txt";
        }
        if(command1==2){
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
            String username=regest.getCurrentuserName();
            boolean find=false;
            for(String[] userInfo:userInfoList){
                if(userInfo[0].equals(username)){
                    System.out.print("请输入新密码：");
                    String newPassword=scanner.next();
                    System.out.print("请确定新密码：");
                    String newPassword1=scanner.next();
                    while(!newPassword1.equals(newPassword)){
                        System.out.println("密码不一致！");
                        System.out.print("请输入新密码：");
                        newPassword=scanner.next();
                        System.out.print("请确定新密码：");
                        newPassword1=scanner.next();
                    }
                    passWordMaster.modifyUserInfo(signfilePath,userInfo[0],newPassword);
                    find=true;
                }
            }
            if(!find){
                System.out.println("该管理员不存在！");
            }
        }
        if(command1==2){
            System.out.print("请确定用户名:");
            String username=scanner.next();
            boolean find=false;
            for(String[] userInfo:userInfoList){
                if(userInfo[0].equals(username)) {
                    passWordMaster.resetUserInfo(signfilePath, userInfo[0]);
                    find=true;
                }
            }
            if(!find){
                System.out.println("该用户不存在！");
            }
        }
    }
    public void userMaster(int command){
        if(command==1){
            userMaster.showUserData();
            menu.next();
        }
        if(command==2){
            System.out.print("请输入要删除的用户ID：");
            String userID=scanner.next();
            if(!logIn.checkIfTxtFileExists(foderPath,userID)){
                System.out.print("该用户不存在！");
            }else{
                userMaster.deleteUserData(userID);
            }

            menu.next();
        }
        if(command==3){
            menu.showSearchStyle();
            System.out.print("请选择输入您的选择：");
            int fangshi=isTrueEnter.inthefa(3);
            while(true){
                if(fangshi==0){
                    break;
                }else{
                    switch(fangshi){
                        case 1:
                            System.out.print("请输入用户ID：");
                            String userID=scanner.next();
                            userMaster.searchUserData(userID,"ID");
                            break;
                        case 2:
                            System.out.print("请输入用户名：");
                            String username=scanner.next();
                            userMaster.searchUserData(username,"Name");
                            break;
                        case 3:
                            userMaster.showUserData();
                            break;
                    }
                    menu.next();
                    menu.showSearchStyle();
                    System.out.print("请选择您的查询方式：");
                    fangshi=isTrueEnter.inthefa(3);
                }
            }
        }
    }
    public void ductionMaster(int command){
        if(command==1){
            ductionMaster.showDuctionInfo();
        }
        if(command==2){
            ductionMaster.addDuctionInfo();
        }
        if(command==3){
            ductionMaster.modifyDuctionInfo();
        }
        if(command==4){
            System.out.print("请输入要删除的商品编号：");
            String id = scanner.nextLine();
            ductionMaster.deleteDuctionInfo(id);
        }
        if(command==5){
            ductionMaster.searchDuctionInfo();
        }
        menu.next();
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
    Scanner scanner=new Scanner(System.in);
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
                    String resetPassword=logIn.encryptPassword("@SPGL1234system");
                    line = fileUsername + ","+resetPassword+",";
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
    Scanner scanner=new Scanner(System.in);
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
    public boolean deleteUserData(String userID){
        String userID1=userID+".txt";
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        String folderPath1=System.getProperty("user.dir")+"//src//main//java//org//example//DuctionData";

        String filePath=System.getProperty("user.dir")+"//src//main//java//org//example//text//UserData.txt";
        File folder = new File(folderPath);
       
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(); // 获取文件夹下所有文件
            if (files == null) {
                System.out.println("用户列表为空！");
                return false;
            }
            for (File file : files) {
                if (file.isFile() && file.getName().equals(userID1)) { // 判断文件名是否匹配
                    System.out.print("请确认删除用户名：");
                    String userName=scanner.next();
                    System.out.print("是否确认删除该用户！Y/N:");
                    String confirmation = scanner.next();
                    if(confirmation.equalsIgnoreCase("Y")){
                        deleteLineByUsername(filePath,userName);
                        deleteFileByUserName(folderPath1,userName);
                        if (file.delete()) { // 删除文件
                            System.out.println("已删除ID为:"+userID+"的用户！");
                            return true;
                        } else {
                            System.out.println("删除失败！");
                            return false;
                        }
                    }else{
                        System.out.println("已取消删除！");
                        return false;
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在！");
            return false;
        }
        return false;
    }

    private boolean deleteFileByUserName(String folderPath1, String userName) {
        File folder = new File(folderPath1);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(userName)) {
                    if (file.delete()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void searchID(String key){
        String userID=key+".txt";
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        File folder = new File(folderPath);
        boolean find=false;
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(); // 获取文件夹下所有文件
            if (files == null) {
                System.out.println("用户列表为空！");
                return;
            }
            for (File file : files) {
                if (file.isFile() && file.getName().equals(userID)) { // 判断文件名是否匹配
                    find=true;
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        if (line != null) {
                            String[] userInfo = line.split(",");
                            if (userInfo.length == 7) {
                                String userID1 = userInfo[0].trim();
                                String userName = userInfo[1].trim();
                                String userLevel = userInfo[2].trim();
                                String registrationTime = userInfo[3].trim();
                                String totalConsumption = userInfo[4].trim();
                                String phoneNumber = userInfo[5].trim();
                                String email = userInfo[6].trim();

                                String output = "客户ID:" + userID1 + " 用户名:" + userName + " 用户级别:" + userLevel +
                                        " 用户注册时间:" + registrationTime + " 客户累计消费总金额:" + totalConsumption + " 用户手机号:" +
                                        phoneNumber + " 用户邮箱:" + email;
                                System.out.println(output);
                            } else {
                                System.out.println("文件" + userID + "的内容格式不正确！");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!find){
                System.out.println("该用户不存在！");
            }
        } else {
            System.out.println("系统错误！");
        }
    }
    public void serachName(String key){
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        File folder = new File(folderPath);
        boolean find=false;
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(); // 获取文件夹下所有文件
            if (files == null) {
                System.out.println("用户列表为空！");
                return;
            }
            for (File file : files) {
                if (file.isFile() &&file.getName().toLowerCase().endsWith(".txt")) { // 判断文件名是否匹配
                    String fileName=file.getName();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        if (line != null) {
                            String[] userInfo = line.split(",");
                            if (userInfo.length == 7) {
                                String userID1 = userInfo[0].trim();
                                String userName = userInfo[1].trim();
                                String userLevel = userInfo[2].trim();
                                String registrationTime = userInfo[3].trim();
                                String totalConsumption = userInfo[4].trim();
                                String phoneNumber = userInfo[5].trim();
                                String email = userInfo[6].trim();

                                String output = "客户ID:" + userID1 + " 用户名:" + userName + " 用户级别:" + userLevel +
                                        " 用户注册时间:" + registrationTime + " 客户累计消费总金额:" + totalConsumption + " 用户手机号:" +
                                        phoneNumber + " 用户邮箱:" + email;
                                if(userName.equalsIgnoreCase(key)){
                                    System.out.println(output);
                                    find=true;
                                }
                            } else {
                                System.out.println("文件" + fileName + "的内容格式不正确！");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!find){
                System.out.println("该用户不存在！");
            }
        } else {
            System.out.println("系统错误！");
        }
    }

    public void searchUserData(String value,String kinds){
        if(kinds.equalsIgnoreCase("ID")){
            searchID(value);
        }
        if(kinds.equalsIgnoreCase("Name")){
            serachName(value);
        }
    }
}
class DuctionMaster{
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    String basePath1=System.getProperty("user.dir")+"//src//main//java//org//example//text//";
    private  final String FILE_PATH=basePath1+"ductions.txt";
    Scanner scanner = new Scanner(System.in);
    public List<String> readProductsFromFile(String filePath) {
        List<String> productList = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                productList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public  void writeProductToFile(String productInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(productInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeProductsToFile(List<String> productList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String productInfo : productList) {
                writer.write(productInfo);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showDuctionInfo(){
        List<String> productList = readProductsFromFile(FILE_PATH);

        if (productList.isEmpty()) {
            System.out.println("没有商品信息。");
        } else {
            System.out.println("商品信息：");
            for (String productInfo : productList) {
                String[] parts = productInfo.split(",");
                String output="商品编号: " + parts[0]+" 商品名称: " + parts[1]+" 生产厂家: " + parts[2]+" 生产日期: " + parts[3]+
                        " 型号: " + parts[4]+" 进货价: " + parts[5]+" 零售价格: " + parts[6]+" 数量: " + parts[7];
                System.out.println(output);
            }
        }
    }
    public void addDuctionInfo(){
        System.out.print("请输入商品编号：");
        String id = scanner.nextLine();
        System.out.print("请输入商品名称：");
        String name = scanner.nextLine();
        System.out.print("请输入生产厂家：");
        String manufacturer = scanner.nextLine();
        System.out.print("请输入生产日期：");
        String productionDate = scanner.nextLine();
        System.out.print("请输入型号：");
        String model = scanner.nextLine();
        System.out.print("请输入进货价：");
        double purchasePrice = scanner.nextDouble();
        System.out.print("请输入零售价格：");
        double retailPrice = scanner.nextDouble();
        System.out.print("请输入数量：");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // 清空输入缓冲区

        String productInfo = String.format("%s,%s,%s,%s,%s,%.2f,%.2f,%d", id, name, manufacturer,
                productionDate, model, purchasePrice, retailPrice, quantity);
        writeProductToFile(productInfo);

        System.out.println("商品信息已添加。");
    }
    public void modifyDuctionInfo(){
        System.out.print("请输入要修改的商品编号：");
        String id = scanner.nextLine();

        List<String> productList = readProductsFromFile(FILE_PATH);
        boolean found = false;

        for (int i = 0; i < productList.size(); i++) {
            String productInfo = productList.get(i);
            String[] parts = productInfo.split(",");
            if (parts[0].equals(id)) {
                System.out.println("原商品信息：" + productInfo);

                System.out.print("请输入新的商品名称（不修改请直接回车）：");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    parts[1] = newName;
                }

                System.out.print("请输入新的生产厂家（不修改请直接回车）：");
                String newManufacturer = scanner.nextLine();
                if (!newManufacturer.isEmpty()) {
                    parts[2] = newManufacturer;
                }

                System.out.print("请输入新的生产日期（不修改请直接回车）：");
                String newProductionDate = scanner.nextLine();
                if (!newProductionDate.isEmpty()) {
                    parts[3] = newProductionDate;
                }

                System.out.print("请输入新的型号（不修改请直接回车）：");
                String newModel = scanner.nextLine();
                if (!newModel.isEmpty()) {
                    parts[4] = newModel;
                }

                System.out.print("请输入新的进货价（不修改请直接回车）：");
                String newPurchasePriceStr = scanner.nextLine();
                if (!newPurchasePriceStr.isEmpty()) {
                    double newPurchasePrice = Double.parseDouble(newPurchasePriceStr);
                    parts[5] = String.format("%.2f", newPurchasePrice);
                }

                System.out.print("请输入新的零售价格（不修改请直接回车）：");
                String newRetailPriceStr = scanner.nextLine();
                if (!newRetailPriceStr.isEmpty()) {
                    double newRetailPrice = Double.parseDouble(newRetailPriceStr);
                    parts[6] = String.format("%.2f", newRetailPrice);
                }

                System.out.print("请输入新的数量（不修改请直接回车）：");
                String newQuantityStr = scanner.nextLine();
                if (!newQuantityStr.isEmpty()) {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    parts[7] = String.valueOf(newQuantity);
                }

                productInfo = String.join(",", parts);
                productList.set(i, productInfo);
                found = true;

                writeProductsToFile(productList);

                System.out.println("商品信息已修改。");
                break;
            }
        }

        if (!found) {
            System.out.println("未找到商品编号为 " + id + " 的商品。");
        }
    }
    public void deleteDuctionInfo(String id){
        List<String> productList = readProductsFromFile(FILE_PATH);
        boolean found = false;

        for (int i = 0; i < productList.size(); i++) {
            String productInfo = productList.get(i);
            String[] parts = productInfo.split(",");
            if (parts[0].equals(id)) {
                System.out.println("商品信息：" + productInfo);

                System.out.print("确认要删除该商品吗？（Y/N）：");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("Y")) {
                    productList.remove(i);
                    writeProductsToFile(productList);

                    System.out.println("商品已删除。");
                } else {
                    System.out.println("取消删除操作。");
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("未找到商品编号为 " + id + " 的商品。");
        }
    }
    public void searchDuctionInfo(){
        System.out.println("请选择查询方式：");
        System.out.println("(1) 根据商品名称查询");
        System.out.println("(2) 根据生产厂家查询");
        System.out.println("(3) 根据零售价格查询");
        System.out.println("(4) 组合查询");

        System.out.print("请选择操作：");
        int choice = isTrueEnter.inthefa(4);
        List<String> productList = readProductsFromFile(FILE_PATH);
        List<String> searchResults = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.print("请输入商品名称：");
                String name = scanner.nextLine();

                for (String productInfo : productList) {
                    String[] parts = productInfo.split(",");
                    if (parts[1].equals(name)) {
                        searchResults.add(productInfo);
                    }
                }

                break;
            case 2:
                System.out.print("请输入生产厂家：");
                String manufacturer = scanner.nextLine();

                for (String productInfo : productList) {
                    String[] parts = productInfo.split(",");
                    if (parts[2].equals(manufacturer)) {
                        searchResults.add(productInfo);
                    }
                }

                break;
            case 3:
                System.out.print("请输入零售价格下限：");
                double minRetailPrice = scanner.nextDouble();
                scanner.nextLine(); // 清空输入缓冲区

                System.out.print("请输入零售价格上限：");
                double maxRetailPrice = scanner.nextDouble();
                scanner.nextLine(); // 清空输入缓冲区

                for (String productInfo : productList) {
                    String[] parts = productInfo.split(",");
                    double retailPrice = Double.parseDouble(parts[6]);
                    if (retailPrice >= minRetailPrice && retailPrice <= maxRetailPrice) {
                        searchResults.add(productInfo);
                    }
                }

                break;
            case 4:
                System.out.print("请输入商品名称：");
                name = scanner.nextLine();

                System.out.print("请输入生产厂家：");
                manufacturer = scanner.nextLine();

                System.out.print("请输入零售价格下限：");
                minRetailPrice = scanner.nextDouble();
                scanner.nextLine(); // 清空输入缓冲区

                System.out.print("请输入零售价格上限：");
                maxRetailPrice = scanner.nextDouble();
                scanner.nextLine(); // 清空输入缓冲区

                for (String productInfo : productList) {
                    String[] parts = productInfo.split(",");
                    if (parts[1].equals(name) && parts[2].equals(manufacturer)) {
                        double retailPrice = Double.parseDouble(parts[6]);
                        if (retailPrice >= minRetailPrice && retailPrice <= maxRetailPrice) {
                            searchResults.add(productInfo);
                        }
                    }
                }

                break;
            default:
                System.out.println("无效输入！");
                return;
        }

        if (searchResults.isEmpty()) {
            System.out.println("未找到符合条件的商品。");
        } else {
            System.out.println("查询结果：");
            for (String productInfo : searchResults) {
                System.out.println(productInfo);
            }
        }
    }
}
