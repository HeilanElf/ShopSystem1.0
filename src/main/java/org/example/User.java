package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.io.File;
public class User {
    Menu menu=new Menu();
    Regest regest=new Regest();
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    PasswordUser passwordUser=new PasswordUser();
    ShopUser shopUser=new ShopUser();
    LogIn logIn=new LogIn();
    Scanner scanner=new Scanner(System.in);
    private final String basePath = System.getProperty("user.dir") + "//src//main//java//org//example//text//";
    String filePath;
    public int passwordUser(int command){
        int exit=1;
        if(command==1){
            filePath=basePath+"UserData.txt";
            String userName=regest.getCurrentuserName();
            System.out.print("请确认旧密码：");
            String passwordOld= isTrueEnter.passwordhefa(scanner.next());
            if(logIn.isMatch(filePath,userName,passwordOld)){
                System.out.print("请输入新密码：");
                String passwordNew= isTrueEnter.passwordhefa(scanner.next());
                System.out.print("请确认新密码：");
                String passwordNew1= isTrueEnter.passwordhefa(scanner.next());
                while(!passwordNew1.equals(passwordNew)){
                    System.out.println("密码不一致！");
                    System.out.print("请输入新密码：");
                    passwordNew= isTrueEnter.passwordhefa(scanner.next());
                    System.out.print("请确认新密码：");
                    passwordNew1= isTrueEnter.passwordhefa(scanner.next());
                }
                passwordUser.fixPassword(filePath,userName,passwordNew);
            }else{
                System.out.println("密码不匹配！");
            }
        }
        if(command==2){
            filePath=basePath+"UserData.txt";
            System.out.print("请输入用户名：");
            String username = scanner.next();
            System.out.print("请输入注册时使用的邮箱地址：");
            String email = scanner.next();
            boolean sucess=passwordUser.forgotPassword(filePath,username,email);
            if(sucess){
                exit=0;
            }
        }
        menu.next();
        return exit;
    }
    public int shopUser(int command){
        DuctionMaster ductionMaster=new DuctionMaster();
        if(command==1){
            ductionMaster.showDuctionInfo();
            System.out.println("-------------------------------------------------");
            System.out.print("请输入商品编号：");
            String shopID= scanner.next();
            System.out.print("请输入购买数量：");
            String shopNum=scanner.next();
            boolean sucess=shopUser.addDuction(shopID,shopNum);
            if(sucess){
                System.out.println("添加成功！");
                shopUser.showCurrentChat();
            }else{
                System.out.println("添加失败！");
            }
        }
        if(command==2){
            System.out.print("请输入商品编号：");
            String shopID= scanner.next();
            shopUser.showCurrentChat();
            shopUser.removeDuction(shopID);
            shopUser.showCurrentChat();
        }
        if(command==3){
            System.out.print("请输入商品编号：");
            String shopID= scanner.next();
            shopUser.modifyDuctionInfo(shopID);
            shopUser.showCurrentChat();
        }
        if(command==4){
            shopUser.checkout();
        }
        if(command==5){
            shopUser.showHistory();
        }
        menu.next();
        return 1;
    }
    public void user(int command,boolean susucessLoin){
        menu.showSecondUser();
        System.out.print("请输入您的选择：");
        command = isTrueEnter.inthefa(2);
        int exit=1;
        while(true){
            if(command==0){
                break;
            }else{
                switch(command){
                    case 1:
                        menu.showpasswordUser();
                        System.out.print("请输入您的选择：");
                        command=isTrueEnter.inthefa(2);
                        while(true){
                            if(command==0){
                                break;
                            }else{
                               exit=passwordUser(command);
                                if(exit==0){
                                    command=0;
                                    break;
                                }
                            }
                            if(exit!=0){
                                menu.showpasswordUser();
                                System.out.print("请输入您的选择：");
                                command=isTrueEnter.inthefa(2);
                            }
                        }
                        break;
                    case 2:
                        menu.showShopUser();
                        System.out.print("请输入您的选择：");
                        command=isTrueEnter.inthefa(5);
                        while(true) {
                            if(command==0){
                                break;
                            }else{
                                exit=shopUser(command);
                                if(exit==0){
                                    command=0;
                                    break;
                                }
                            }
                            if(exit!=0){
                                menu.showShopUser();
                                System.out.print("请输入您的选择：");
                                command=isTrueEnter.inthefa(5);
                            }
                        }
                        break;
                }
            }
            if(exit!=0){
                menu.showSecondUser();
                System.out.print("请输入您的选择：");
                command = isTrueEnter.inthefa(2);
            }
        }
    }
}
class PasswordUser{
    PasswordMaster passwordMaster=new PasswordMaster();
    public void fixPassword(String filePath, String userName, String passwordNew) {
        passwordMaster.modifyUserInfo(filePath,userName,passwordNew);
    }

    public boolean forgotPassword(String filePath, String username, String email) {
        boolean sucess=false;
        if (verify(username, email)) {
            String newPassword = generateRandomPassword();
            sendEmail(email, newPassword);
            System.out.println("新密码已发送到您的邮箱，请查收。");
            System.out.println("请使用新密码登录，并尽快修改为您熟悉的密码。");
            fixPassword(filePath,username,newPassword);
            sucess=true;
        } else {
            System.out.println("用户名和邮箱地址不匹配!");
        }
        return sucess;
    }

    private void sendEmail(String email, String newPassword) {
        System.out.println("已向邮箱 " + email + " 发送新密码：" + newPassword);
    }

    private String generateRandomPassword() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$%^&*()_+";
        String numbers = "0123456789";

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // 生成包含至少一个大写字母、一个小写字母、一个特殊字符和一个数字的密码
        password.append(uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length())));
        password.append(lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));

        // 生成剩余的密码字符
        for (int i = 4; i < 10; i++) {
            String allCharacters = uppercaseLetters + lowercaseLetters + specialCharacters + numbers;
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        return password.toString();
    }

    public boolean verify(String username, String email){
        String folderPath=System.getProperty("user.dir")+"//src//main//java//org//example//userData";
        File folder = new File(folderPath);
        boolean find=false;
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles(); // 获取文件夹下所有文件
            if (files == null) {
                System.out.println("用户列表为空！");
                find = false;
            }
            for (File file : files) {
                if (file.isFile() &&file.getName().toLowerCase().endsWith(".txt")) { // 判断文件名是否匹配
                    //String fileName=file.getName();
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        if (line != null) {
                            String[] userInfo = line.split(",");
                            if (userInfo.length == 7) {
                                String userName = userInfo[1].trim();
                                String currentemail = userInfo[6].trim();
                                if(userName.equals(username)&&email.equals(currentemail)){
                                    find=true;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("系统错误！");
        }
        return find;
    }
}
class ShopUser {
    String basePath1 = System.getProperty("user.dir") + "//src//main//java//org//example//DuctionData//";
    private final String FILE_PATH = basePath1 + "ShopChat.txt";
    private final String filePath = System.getProperty("user.dir") + "//src//main//java//org//example//text//ductions.txt";
    Scanner scanner = new Scanner(System.in);
    DuctionMaster ductionMaster = new DuctionMaster();

    public void writeProductToFile(String productInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(productInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clearFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("");
            fileWriter.close();
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

    public void showCurrentChat(){
        List<String> productList = ductionMaster.readProductsFromFile(FILE_PATH);
        if (productList.isEmpty()) {
            System.out.println("当前购物车为空！");
        } else {
            System.out.println("当前购物车商品信息：");
            for (String productInfo : productList) {
                String[] parts = productInfo.split(",");
                String output="商品编号: " + parts[0]+" 商品名称: " + parts[1]+" 生产厂家: " + parts[2]+" 加入购物车时间: " + parts[3]+
                        " 型号: " + parts[4]+" 零售价格: " + parts[5]+" 数量: " + parts[6];
                System.out.println(output);
            }
        }
    }
    public boolean addDuction(String shopID, String shopNum) {
        Regest regest=new Regest();
        boolean sucess = false;
        List<String> productList = ductionMaster.readProductsFromFile(filePath);
        if (productList.isEmpty()) {
            System.out.println("没有该商品。");
        } else {
            for (String productInfo : productList) {
                String[] parts = productInfo.split(",");
                if (parts[0].equalsIgnoreCase(shopID)) {
                    String output = parts[0] + "," + parts[1] + "," + parts[2] + "," +regest.getCurrentDateTime() +
                            "," + parts[4] + "," + parts[6] + "," + shopNum;
                    writeProductToFile(output);
                    sucess = true;
                }
            }
        }
        return sucess;
    }
    public void removeDuction(String id){
        List<String> productList = ductionMaster.readProductsFromFile(FILE_PATH);
        boolean found = false;

        for (int i = 0; i < productList.size(); i++) {
            String productInfo = productList.get(i);
            String[] parts = productInfo.split(",");
            if (parts[0].equals(id)) {
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

    public void modifyDuctionInfo(String id){
        List<String> productList = ductionMaster.readProductsFromFile(FILE_PATH);
        boolean found = false;

        for (int i = 0; i < productList.size(); i++) {
            String productInfo = productList.get(i);
            String[] parts = productInfo.split(",");
            if (parts[0].equals(id)) {
                String output="商品编号: " + parts[0]+" 商品名称: " + parts[1]+" 生产厂家: " + parts[2]+" 加入购物车时间: " + parts[3]+
                        " 型号: " + parts[4]+" 零售价格: " + parts[5]+" 数量: " + parts[6];
                System.out.println("商品信息：" + output);

                System.out.print("请输入新的数量（不修改请直接回车）：");
                String newQuantityStr = scanner.nextLine();

                if (!newQuantityStr.isEmpty()) {
                    int newQuantity = Integer.parseInt(newQuantityStr);
                    if(newQuantity<=0){
                        removeDuction(id);
                    }else{
                        parts[6] = String.valueOf(newQuantity);
                    }
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
    public void checkout(){
        Menu menu=new Menu();
        menu.showCheck();
        System.out.print("请选择您的支付方式：");
        IsTrueEnter isTrueEnter=new IsTrueEnter();
        int check=isTrueEnter.inthefa(3);
        double xiaofei=getTotalPrice();
        System.out.print("是否支付:(Y/N)");
        String confirm=scanner.next();
        if(confirm.equalsIgnoreCase("Y")){
            switch (check){
                case 1:
                    System.out.println("支付宝支付中！");
                    break;
                case 2:
                    System.out.println("微信支付中！");
                    break;
                case 3:
                    System.out.println("银行卡支付中！");
                    break;
            }
            if(updataInfo(xiaofei)){
                clearFile(FILE_PATH);
                System.out.println("支付成功！");
            }else{
                System.out.println("支付失败！");
            }
        }else{
            System.out.println("您已取消支付！");
        }

    }

    private boolean updataInfo(double price) {
        boolean suces=false;
        Regest regest=new Regest();
        if(createHistory()){
            updateUserInfo(regest.getCurrentuserName(),price);
            updateStock(filePath,FILE_PATH);
            suces=true;
        }
        return suces;
    }

    public void updateStock(String stockFilePath, String chatFilePath) {
        DuctionMaster ductionMaster=new DuctionMaster();
        try {
            // 读取库存文件
            BufferedReader stockReader = new BufferedReader(new FileReader(stockFilePath));
            String stockLine;
            StringBuilder stockData = new StringBuilder();

            // 将库存文件的内容保存到字符串中
            while ((stockLine = stockReader.readLine()) != null) {
                stockData.append(stockLine).append("\n");
            }

            // 读取购物车文件
            BufferedReader cartReader = new BufferedReader(new FileReader(chatFilePath));
            String cartLine;
            StringBuilder updatedStockData = new StringBuilder();

            // 根据购物车文件更新库存数据
            while ((cartLine = cartReader.readLine()) != null) {
                String[] cartData = cartLine.split(",");
                String productId = cartData[0].trim();
                int quantityChange = Integer.parseInt(cartData[6].trim());

                // 在库存数据中查找对应的商品，并更新剩余库存数量
                String[] stockLines = stockData.toString().split("\n");
                for (String stock : stockLines) {
                    String[] stockDataArray = stock.split(",");
                    String stockProductId = stockDataArray[0].trim();
                    int stockQuantity = Integer.parseInt(stockDataArray[7].trim());

                    if (stockProductId.equals(productId)) {
                        // 更新剩余库存数量
                        stockQuantity -= quantityChange;
                        if (stockQuantity <=0) {
                            ductionMaster.deleteDuctionInfo(productId);
                        }
                    }

                    // 构建更新后的库存数据字符串
                    StringBuilder updatedStockLine = new StringBuilder();
                    for (int i = 0; i < stockDataArray.length; i++) {
                        if (i == 7) {
                            updatedStockLine.append(stockQuantity);
                        } else {
                            updatedStockLine.append(stockDataArray[i]);
                        }

                        if (i != stockDataArray.length - 1) {
                            updatedStockLine.append(",");
                        }
                    }
                    updatedStockData.append(updatedStockLine).append("\n");
                }
            }

            // 关闭读取器
            stockReader.close();
            cartReader.close();

            // 将更新后的库存数据保存回原库存文件
            BufferedWriter stockWriter = new BufferedWriter(new FileWriter(stockFilePath));
            stockWriter.write(updatedStockData.toString());
            stockWriter.close();
        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }

    private final String DATA_FOLDER=System.getProperty("user.dir")+"//src//main//java//org//example//DuctionData//";
    private boolean createHistory() {
        boolean success = false;
        List<String> productList = ductionMaster.readProductsFromFile(FILE_PATH);
        String fileName = createFile();
        String filePath = DATA_FOLDER+fileName;
        File file = new File(filePath);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            for (String productInfo : productList) {
                writer.write(productInfo);
                writer.newLine();
                success = true;
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    private String createFile() {
         Regest regest=new Regest();
         return regest.getCurrentuserName()+".txt";
    }
    private final  String userFolder=System.getProperty("user.dir")+"//src//main//java//org//example//userData//";

    public boolean updateUserInfo(String currentUserName, double price) {
        String file=searchFile(userFolder,currentUserName);
        updateField(file,currentUserName,price);
        return true;
    }

    private String searchFile(String userFolder, String currentUserName) {
        File folder = new File(userFolder);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        String filePath = file.getAbsolutePath();
                        if (isUsernameInFile(filePath, currentUserName)) {
                            return filePath;
                        }
                    }
                }
            }
        }
        return null; // 如果没有找到匹配的文件，则返回null
    }
    private boolean isUsernameInFile(String filePath, String currentUserName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 7 && userInfo[1].trim().equals(currentUserName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void updateField(String filePath, String fieldToUpdate, double price) {
        try {
            List<String> lines = new ArrayList<>();
            File file = new File(filePath);

            // 读取文件内容到列表中
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

            // 根据字段更新对应的值
            for (int i = 0; i < lines.size(); i++) {
                String[] userInfo = lines.get(i).split(",");
                if (userInfo.length == 7 && userInfo[1].trim().equals(fieldToUpdate)) {
                    userInfo[4] = String.valueOf(price+Double.parseDouble(userInfo[4]));
                    userInfo[2]=getleve(Double.parseDouble(userInfo[4]));
                    lines.set(i, String.join(",", userInfo));
                    break;
                }
            }

            // 将更新后的内容写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,false))) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getleve(double ptice) {
        if(ptice<=5000&&ptice>1000){
            return "银牌客户";
        }
        if(ptice>5000){
            return "金牌客户";
        }
        if(ptice<1000){
            return "铜牌客户";
        }
        return "";
    }

    public double getTotalPrice(){
        double totalprice=0;
        List<String> productList = ductionMaster.readProductsFromFile(FILE_PATH);
        if(productList.isEmpty()){
            System.out.println("购物车为空！");
        }else{
            for (String productInfo : productList) {
                String[] parts = productInfo.split(",");
                totalprice+=Double.parseDouble(parts[5])*Integer.parseInt(parts[6]);
            }
            System.out.println("您共需支付"+totalprice+"￥");
        }
        return totalprice;
    }
    public void showHistory(){
        String file_path=DATA_FOLDER+createFile();
        List<String> productList = ductionMaster.readProductsFromFile(file_path);
        if (productList.isEmpty()) {
            System.out.println("历史信息为空！");
        } else {
            System.out.println("购物历史信息如下：");
            for (String productInfo : productList) {
                String[] parts = productInfo.split(",");
                String output="商品编号: " + parts[0]+" 商品名称: " + parts[1]+" 生产厂家: " + parts[2]+" 加入购物车时间: " + parts[3]+
                        " 型号: " + parts[4]+" 零售价格: " + parts[5]+" 数量: " + parts[6];
                System.out.println(output);
            }
        }
    }
}