package com.company;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Scorer {
    private String Delimiter;
    private int Value_of_TF;
    private int index_first_TF;
    private String Folder_File;
    private String Folder_File_out;
    private List<ArrayList<String>> Table;
    private FileReader file_Reader;

    public final void scorer(String Delimiter, int Value_Of_TF, int index_first_TF, String Folder_File, String Folder_File_out) throws IOException {
        this.Delimiter = Delimiter;
        this.Value_of_TF = Value_Of_TF;
        this.index_first_TF = index_first_TF;
        this.Folder_File = Folder_File;
        this.Folder_File_out = Folder_File_out;
        Learn_Table(Open_File());
        Add_New_Column("Row_Sum");
        for (int i = 0; i < Value_Of_TF; i++) {
            Add_New_Column("Accuracy_tip1(" + i + ")");
            Amount_Two_Column(Table.get(0).indexOf("Row_Sum"), Table.get(0).indexOf("" + i));
        }
        for (int i = 0; i < Value_Of_TF; i++){
            Division_Column(Table.get(0).indexOf("" + i), Table.get(0).indexOf("Row_Sum"), Table.get(0).indexOf("Accuracy_tip1(" + i + ")"));
        }
        True_Negative();
        False_Positive();
        False_Negative();
        Precision();
        Recall();
        F_score();
        System.out.println("F для всех: " + Avg_F());
        Avg_F_in_row();
        Avg_F_in_column();
        max_F_in_row();
        Write_File();
        message();
    }
    private Scanner Open_File() throws FileNotFoundException {
        file_Reader = new FileReader(Folder_File);
        return new Scanner(file_Reader);
    }
    private void Learn_Table(Scanner Scanner_name){
        Table = new ArrayList<>();
        int row = 0;
        while (Scanner_name.hasNext()){
            Table.add(new ArrayList<>());
            String str = Scanner_name.nextLine();
            str = str.replace("\"","");
            String[] Buffer = str.split(Delimiter);
            for (String s : Buffer) {
                Table.get(row).add(s);
            }
            row++;
        }
    }
    private void Write_File() throws IOException {
        FileWriter file_Writer = new FileWriter(Folder_File_out);
        for (ArrayList<String> strings : Table) {
            for (int column = 0; column < Table.get(0).size(); column++) {
                file_Writer.write(strings.get(column) + Delimiter);
            }
            file_Writer.write("\n");
        }
        file_Writer.close();
        file_Reader.close();
    }
    private void Add_New_Column(String Name_Column){
        int row = 0;
        while (row < Table.size()){
            if(row == 0){
                Table.get(row).add(Name_Column);
            }else if(row > 0) {
                Table.get(row).add("0");
            }
            row++;
        }
    }
    private int Cell_String_To_Integer (Integer Row_id, Integer Column_id){
        return Integer.parseInt(Table.get(Row_id).get(Column_id));
    }
    private double Cell_String_To_Double (Integer Row_id, Integer Column_id){
        return Double.parseDouble(Table.get(Row_id).get(Column_id));
    }
    private void Amount_Two_Column (Integer Column_first_id, Integer Column_Second_id){
        int row = 1;
        while (row < Table.size()){
            int Sum = (Cell_String_To_Integer(row, Column_first_id)) + (Cell_String_To_Integer(row, Column_Second_id));
            String buffer = Integer.toString(Sum);
            Table.get(row).set(Column_first_id, buffer);
            row++;
        }
    }
    private   void Division_Column (Integer Column_dividend_id, Integer Column_divisor_id, Integer Column_Quotient_id){
        int row = 1;
        while (row < Table.size()) {
            Double Quotient = (Cell_String_To_Double(row, Column_dividend_id)) / (Cell_String_To_Double(row, Column_divisor_id));
            String str = String.format(Locale.US, "%.4f", Quotient);
            Table.get(row).set(Column_Quotient_id, str);
            row++;
        }
    }
    private String Cell_True_Negative (Integer Row_id, Integer Column_id){
        int row = 1;
        String TN;
        int int_TN = 0;
        while (row < Table.size()){
            if(row != Row_id){
                int i = 1;
                while(i < Value_of_TF + index_first_TF){
                    if(i != Column_id){
                        int buffer = Cell_String_To_Integer(row, i);
                        int_TN += buffer;
                    }
                    i++;
                }
            }
            row++;
        }
        TN = Integer.toString(int_TN);
        return TN;
    }
    private void True_Negative (){
        for (int i = 0; i < Value_of_TF; i++){
            Add_New_Column("True_Negative(" + i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int j = index_first_TF; j < index_first_TF + Value_of_TF; j++){
                String buffer = Cell_True_Negative(row, j);
                Table.get(row).set(Table.get(0).indexOf("True_Negative(0)") + j - 1, buffer);
            }
        }
    }
    private String Cell_False_Positive (Integer Row_id, Integer Column_id){
        int column = 1;
        String cell;
        int Sum = 0;
        while (column < index_first_TF + Value_of_TF){
            if(column != Column_id) {
                Sum += Cell_String_To_Integer(Row_id, column);
            }
            column++;
        }
        cell = ""+Sum;
        return cell;
    }
    private void False_Positive (){
        for(int i = 0; i < Value_of_TF; i++){
            Add_New_Column("False_Positive(" + i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int column = index_first_TF; column < index_first_TF + Value_of_TF; column++){
                String buffer = Cell_False_Positive(row, column);
                Table.get(row).set((Table.get(0).indexOf("False_Positive(0)") + column - 1), buffer);
            }
        }
    }
    private String Cell_False_Negative (Integer Row_id, Integer Column_id){
        int FN = 0;
        for (int row = 1; row < Table.size(); row++){
            if (row != Row_id){
                FN += Cell_String_To_Integer(row, Column_id);
            }
        }
        return "" + FN;
    }
    private void False_Negative (){
        for (int i = 0; i < Value_of_TF; i++){
            Add_New_Column("False_Negative("+ i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int column = index_first_TF; column < index_first_TF + Value_of_TF; column++){
                String buffer = Cell_False_Negative(row,column);
                Table.get(row).set((Table.get(0).indexOf("False_Negative(0)") + column - 1), buffer);
            }
        }
    }
    private void Precision (){
        for (int i = 0; i < Value_of_TF; i++){
            Add_New_Column("Precision(" + i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int column = index_first_TF; column < Value_of_TF + index_first_TF; column++){
                double FP = Cell_String_To_Double(row, Table.get(0).indexOf("False_Positive(0)") + column - 1);
                double TP = Cell_String_To_Double(row, column);
                double buffer = (TP / (TP + FP));
                String str = String.format(Locale.US,"%.4f", buffer);
                Table.get(row).set((Table.get(0).indexOf("Precision(0)") + column - 1), str);
            }
        }
    }
    private void Recall (){
        for (int i = 0; i < Value_of_TF; i++){
            Add_New_Column("Recall(" + i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int column = index_first_TF; column < Value_of_TF + index_first_TF; column++){
                double FN = Cell_String_To_Double(row, Table.get(0).indexOf("False_Negative(0)") + column - 1);
                double TP = Cell_String_To_Double(row, column);
                double buffer = (TP / (TP + FN));
                String str = String.format(Locale.US,"%.4f", buffer);
                Table.get(row).set((Table.get(0).indexOf("Recall(0)") + column - 1), str);
            }
        }
    }
    private void F_score(){
        for (int i = 0; i < Value_of_TF; i++){
            Add_New_Column("F_measure(" + i + ")");
        }
        for (int row = 1; row < Table.size(); row++){
            for (int column = index_first_TF; column< index_first_TF + Value_of_TF; column++){
                double recall = Cell_String_To_Double(row, (Table.get(0).indexOf("Recall(0)") + column - 1));
                double precision = Cell_String_To_Double(row, (Table.get(0).indexOf("Precision(0)") + column - 1));
                double buffer = 2 * (precision * recall)/(precision + recall);
                String str = String.format(Locale.US,"%.4f", buffer);
                Table.get(row).set(Table.get(0).indexOf("F_measure(0)") + column - 1, str);
            }
        }
    }
    private double Avg_F(){
        double Avg = 0;
        for (int row = 1; row < Table.size(); row++){
            for (int column = Table.get(0).indexOf("F_measure(0)"); column < Value_of_TF + Table.get(0).indexOf("F_measure(0)"); column++){
                double buffer = Cell_String_To_Double(row, column);
                if(Double.isNaN(buffer)){
                    Avg += 0;
                }
                else {
                    Avg += Cell_String_To_Double(row, column);
                }
            }
        }
        Avg /= (Table.size() - 1);
        return Avg;
    }
    private void Avg_F_in_row(){
        Add_New_Column("Avg_F_in_row");
        for (int row = 1; row < Table.size(); row++){
            double avg = 0;
            for (int column = Table.get(0).indexOf("F_measure(0)"); column < Value_of_TF + Table.get(0).indexOf("F_measure(0)"); column++){
                double buffer = Cell_String_To_Double(row, column);
                if(Double.isNaN(buffer)){
                    avg+= 0;
                } else {
                    avg +=Cell_String_To_Double(row, column);
                }
                double db = avg / Value_of_TF;
                String str = String.format(Locale.US,"%.4f", db);
                Table.get(row).set(Table.get(0).indexOf("Avg_F_in_row"), str);
            }
        }
    }
    private void Avg_F_in_column(){
        String str;
        for (int column = Table.get(0).indexOf("F_measure(0)"); column < Value_of_TF + Table.get(0).indexOf("F_measure(0)"); column++) {
            double avg = 0;
            for (int row = 1; row < Table.size(); row++) {
                double buffer = Cell_String_To_Double(row, column);
                if(Double.isNaN(buffer)){
                    avg+= 0;
                } else {
                    avg +=Cell_String_To_Double(row, column);
                }

            }
            double db = avg / (Table.size() - 1);
            str = String.format(Locale.US,"%.4f", db);
            System.out.println("Avg f для столбцов: " + str);
        }
    }
    private void max_F_in_row(){
        Add_New_Column("Max_F_in_row");
        System.out.println("Добавил");
        for (int row = 1; row < Table.size(); row++){
            double max = 0;
            double buffer;
            for (int column = Table.get(0).indexOf("F_measure(0)"); column < Value_of_TF + Table.get(0).indexOf("F_measure(0)"); column++){
                buffer = Cell_String_To_Double(row, column);

                if(buffer > max){
                    max = buffer;
                }
                String str = String.format(Locale.US,"%.4f", max);
                System.out.println(str);
                Table.get(row).set(Table.get(0).indexOf("Max_F_in_row"), str);
            }
        }
    }
    public static void message(){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, "Выполнилось");
    }
}