package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {
    private final String basePath = System.getProperty("user.dir") + "//src//main//java//org//example//";

    public void clear(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showFileContent(String fileName) {
        clear();
        String filePath = basePath + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHome(){showFileContent("Home.txt"); }

    public void showMaster(){showFileContent("MasterHome.txt");}

    public void showpasswordMaster(){showFileContent("passwordMaster.txt");}
}
