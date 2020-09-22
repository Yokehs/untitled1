package com.company;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
public final class Filter {

    public static boolean my_pattern(String str, String Pat) {
//        Pattern p = Pattern.compile("^.+/2018,");// нужное выражение
        //Pattern p = Pattern.compile("^.+/2020,.+$");
        Pattern p = Pattern.compile(Pat);
        Matcher m = p.matcher(str);
        return m.matches();// true если нашел
    }
    public static void message(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Выполнилось");
    }

    public void filter(Boolean Save_First_Str, String Pattern, Integer Value, String Folder_in, String Folder_out) throws IOException {
        FileReader fileReader = new FileReader(Folder_in);
        Scanner scan = new Scanner(fileReader);
        FileWriter fileWriter = new FileWriter(Folder_out);
        String s = scan.nextLine();
        if(Save_First_Str) {
            fileWriter.write(s + "\n");
        }
        int i = 0;
        while (scan.hasNext()) {
            s = scan.nextLine();
            //System.out.println(s + " - " + my_pattern(s));
            if (my_pattern(s, Pattern) && i < Value) {
//               System.out.print(s);
                fileWriter.write(s + "\n");
            }
            i++;
        }
        fileReader.close();
        fileWriter.close();
        scan.close();
        message();
    }
}
