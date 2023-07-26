package org.example;

public class Master {
    Menu menu=new Menu();
    IsTrueEnter isTrueEnter=new IsTrueEnter();
    public void passwordMaster(int command){

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
                        break;
                    case 3:
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

}
