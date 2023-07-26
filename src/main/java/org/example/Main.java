package org.example;

public class Main {
    public static void runSystem(){
        Master master=new Master();
        Menu menu=new Menu();
        User user=new User();
        LogIn logIn=new LogIn();
        IsTrueEnter isTrueEnter=new IsTrueEnter();

        menu.showHome();
        System.out.print("请输入您的选择：");
        int command=isTrueEnter.inthefa(2);
        while(true){
            if(command==0){
                break;
            }else{
                switch (command){
                    case 1:
                        menu.showMaster();
                        System.out.print("请输入您的选择：");
                        command=isTrueEnter.inthefa(3);
                        master.master(command);
                        break;
                    case 2:
                        menu.showUser();
                        System.out.print("请输入您的选择：");
                        command=isTrueEnter.inthefa(3);
                        user.user(command);
                        break;
                }
            }
            menu.showHome();
            System.out.print("请输入您的选择：");
            command=isTrueEnter.inthefa(2);
        }
    }

    public static void main(String[] args) {
        runSystem();
    }
}