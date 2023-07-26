package org.example;

public class User {
    Menu menu=new Menu();
    IsTrueEnter isTrueEnter=new IsTrueEnter();

    LogIn logIn=new LogIn();

    public void passwordUser(){

    }
    public void shopUser(){

    }
    public void user(int command){
        while(true){
            if(command==0){
                break;
            }else{
                switch(command){
                    case 1:
                        break;
                    case 2:
                        while(true){
                            menu.showpasswordUser();
                            System.out.print("请输入您的选择：");
                            command=isTrueEnter.inthefa(2);
                            if(command==0){
                                break;
                            }else{
                                passwordUser();
                            }
                        }
                        break;
                    case 3:
                        while(true) {
                            menu.showShopUser();
                            System.out.print("请输入您的选择：");
                            command=isTrueEnter.inthefa(5);
                            if(command==0){
                                break;
                            }else{
                                shopUser();
                            }
                        }
                        break;
                }
            }
            menu.showUser();
            System.out.print("请输入您的选择：");
            command=isTrueEnter.inthefa(3);
        }
    }
}
class PasswordUser{

}
class ShopUser{

}