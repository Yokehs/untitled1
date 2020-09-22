package com.company;

import javax.swing.*;
import java.io.*;
import java.util.*;

public final class Add {
    public static void message(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Выполнилось");
    }
    public void add(String Delimiter, String... Folders) throws IOException {
        FileReader filein = new FileReader(Folders[0]);
        Scanner scan = new Scanner(filein);
        FileReader filein2 = new FileReader(Folders[1]);//Файл для чтения предсказания
        Scanner scan2 = new Scanner(filein2);
        FileWriter fileout = new FileWriter(Folders[2]);//Файл для записи
        String s = scan.nextLine();
        String s2 = scan2.nextLine();
        s2 = s2.replace(Delimiter,"second,");
        fileout.write(s + Delimiter + s2 + "\n");
        while(scan.hasNext()){
            s = scan.nextLine();
            s2 = scan2.nextLine();
            //s2 = s2.replace("\"","");
            fileout.write(s + Delimiter + s2 + "\n");
        }
        filein.close();
        filein2.close();
        fileout.close();
        scan.close();
        scan2.close();
        message();
    }
}