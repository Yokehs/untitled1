package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public final class GUI extends Component {
    private final int width_btn = 130;
    private final int height_ot = 10;
    private final int width_ot = 20;
    private final int w = 100;
    private final int h = 20;
    private final int width_frame = width_ot * 4 + width_btn * 3 + 16;
    private final int height_frame = 119;
    private String Buffer_in1, Buffer_in2, Buffer_out;
    private boolean save_first_str;
    JFrame jFrame;
    JPanel jPanel_main;
    JButton btn_Filter, btn_Add, btn_Scorer;
    JLabel jLabel;
    private void config_action_panel(JPanel Name_Panel){
        jPanel_main.setVisible(false);
        Name_Panel.setLayout(null);
        jFrame.add(Name_Panel);
        JButton btn_cancel = new JButton("Отмена");
        btn_cancel.setBounds(width_ot * 18,height_ot * 5 + 4, w, h);
        btn_cancel.addActionListener(e -> Cancel_btn(Name_Panel));
        Name_Panel.setSize(width_frame, height_frame);
        Name_Panel.setVisible(true);
        Name_Panel.add(btn_cancel);
    }
    private void config_Frame(JFrame name_frame){
        name_frame.setSize(width_frame, height_frame);
        name_frame.setResizable(false);
        name_frame.setLayout(null);
        name_frame.setLocationRelativeTo(null);
    }
    private void Cancel_btn(JPanel name_closing_panel){
        name_closing_panel.setVisible(false);
        jPanel_main.setVisible(true);
    }
    private void action_btn_filter(){
        JPanel panel_filter = new JPanel();
        config_action_panel(panel_filter);
        JCheckBox checkBox = new JCheckBox("Оставить первую строку");
        JTextField Value_row = new JTextField("Кол-во строк");
        JTextField text = new JTextField("Паттерн");
        JLabel text_panel = new JLabel("Введите паттерн:");
        JLabel chose_folder_panel = new JLabel("Выберите файлы:");
        JButton btn_run = new JButton("Выполнить");
        JButton btn_in = new JButton("Ввод");
        JButton btn_out = new JButton("Вывод");
        checkBox.addActionListener(e -> {
            if(checkBox.isSelected()){
                save_first_str = true;
            }
        });
        btn_run.addActionListener(e -> {
            if(Buffer_out.equals("") || Buffer_in1.equals(""))
            {
                JOptionPane.showMessageDialog(jFrame, "Выберите файл");
            }else if(Buffer_in1.equals(Buffer_out)){
                JOptionPane.showMessageDialog(jFrame, "Загружаете один и тот же файл");
            }else if(Value_row.getText().equals("") || Integer.parseInt(Value_row.getText()) < 1) {
                JOptionPane.showMessageDialog(jFrame, "Укажите количество строк");
            }else if(Buffer_out.equals("Кол-во строк") || Buffer_in1.equals("Кол-во строк")) {
                JOptionPane.showMessageDialog(jFrame, "Укажите количество строк");
            }else if(Value_row.getText().equals("Кол-во строк")){
                JOptionPane.showMessageDialog(jFrame, "Укажите количество строк");
            }else {
                try {
                    //new Filter(save_first_str, text.getText(), Integer.parseInt(Value_row.getText()), Buffer_in1, Buffer_out);
                    Filter filter = new Filter();
                    filter.filter(save_first_str, text.getText(), Integer.parseInt(Value_row.getText()), Buffer_in1, Buffer_out);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(jFrame, "Что-то пошло не так :(");
                }
            }
        });
        btn_in.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Ввод");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_in1 = "" + fileChooser.getSelectedFile();
            }
        });
        btn_out.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Вывод");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_out = "" + fileChooser.getSelectedFile();
            }
        });
        checkBox.setBounds(width_ot,height_ot * 5 + 4, w * 2, h);
        Value_row.setBounds((width_ot-1) * 13, height_ot * 3 + 1, w, h);
        text.setBounds((width_ot-1) * 7, height_ot * 3 + 1, w, h);
        text_panel.setBounds(width_ot, height_ot * 3, w , h);
        chose_folder_panel.setBounds(width_ot - 5, height_ot - 5, w + 7, h);
        btn_run.setBounds((width_ot-1) * 13,height_ot * 5 + 4, w, h);
        btn_in.setBounds((width_ot-1) * 7, height_ot - 5, w, h);
        btn_out.setBounds((width_ot-1) * 13, height_ot - 5, w, h);
        panel_filter.setSize(width_frame, height_frame);
        panel_filter.add(text_panel);
        panel_filter.add(chose_folder_panel);
        panel_filter.add(text);
        panel_filter.add(Value_row);
        panel_filter.add(btn_in);
        panel_filter.add(btn_run);
        panel_filter.add(btn_out);
        panel_filter.add(checkBox);
    }
    private void action_btn_add(){
        JPanel panel_add = new JPanel();
        config_action_panel(panel_add);
        JLabel label_Delimiter = new JLabel("Разделитель:");
        JLabel label_Foreders = new JLabel("Директории:");
        JTextField Delimiter_text = new JTextField(",");
        JButton btn_in1 = new JButton("Ввод 1");
        JButton btn_in2 = new JButton("Ввод 2");
        JButton btn_out1 = new JButton("Вывод");
        JButton btn_run = new JButton("Выполнить");
        btn_in1.addActionListener(e -> {
            JFileChooser fileChooser_in1 = new JFileChooser();
            int ret = fileChooser_in1.showDialog(null,"Ввод 1");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_in1 = "" + fileChooser_in1.getSelectedFile();
            }
        });
        btn_in2.addActionListener(e -> {
            JFileChooser fileChooser_in2 = new JFileChooser();
            int ret = fileChooser_in2.showDialog(null,"Ввод 2");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_in2 = "" + fileChooser_in2.getSelectedFile();
            }
        });
        btn_out1.addActionListener(e -> {
            JFileChooser fileChooser_out = new JFileChooser();
            int ret = fileChooser_out.showDialog(null,"Вывод");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_out = "" + fileChooser_out.getSelectedFile();
            }
        });
        btn_run.addActionListener(e -> {
            try {
                //new Add(Delimiter_text.getText(), Buffer_in1, Buffer_in2, Buffer_out);
                Add add = new Add();
                add.add(Delimiter_text.getText(), Buffer_in1, Buffer_in2, Buffer_out);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        label_Delimiter.setBounds(width_ot - 5, height_ot - 5, w + 7, h);
        label_Foreders.setBounds(width_ot - 5, height_ot * 3, w + 7, h);
        Delimiter_text.setBounds((width_ot - 1) * 7, height_ot - 5, w, h);
        btn_run.setBounds((width_ot - 1) * 13,height_ot * 5 + 4, w, h);
        btn_in1.setBounds((width_ot - 1) * 7, height_ot * 3, w, h);
        btn_in2.setBounds((width_ot - 1) * 13, height_ot * 3, w, h);
        btn_out1.setBounds(width_ot * 18, height_ot * 3, w, h);
        panel_add.add(label_Delimiter);
        panel_add.add(label_Foreders);
        panel_add.add(Delimiter_text);
        panel_add.add(btn_run);
        panel_add.add(btn_in1);
        panel_add.add(btn_in2);
        panel_add.add(btn_out1);
    }
    private void action_btn_scorer(){
        JPanel panel_scorer = new JPanel();
        config_action_panel(panel_scorer);
        JTextField Delimiter_text = new JTextField("Разделитель");
        JTextField Value_Of_TF = new JTextField("Количество цф");
        JTextField first_index_CF = new JTextField("Первый id цф");
        JButton btn_run = new JButton("Выполнить");
        JButton btn_in = new JButton("Ввод");
        JButton btn_out = new JButton("Вывод");

        btn_in.addActionListener(e -> {
            JFileChooser fileChooser_in1 = new JFileChooser();
            int ret = fileChooser_in1.showDialog(null,"Ввод 1");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_in1 = "" + fileChooser_in1.getSelectedFile();
            }
        });
        btn_out.addActionListener(e -> {
            JFileChooser fileChooser_out = new JFileChooser();
            int ret = fileChooser_out.showDialog(null,"Вывод");
            if(ret == JFileChooser.APPROVE_OPTION){
                this.Buffer_out = "" + fileChooser_out.getSelectedFile();
            }
        });
        btn_run.addActionListener(e -> {
            try {
                Scorer scorer = new Scorer();
                scorer.scorer(Delimiter_text.getText(), Integer.parseInt(Value_Of_TF.getText()), Integer.parseInt(first_index_CF.getText()), Buffer_in1, Buffer_out);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
        first_index_CF.setBounds((width_ot-1) * 7,height_ot * 3, w, h);
        Value_Of_TF.setBounds((width_ot-1), height_ot * 3, w, h);
        Delimiter_text.setBounds((width_ot-1) * 13, height_ot - 5, w, h);
        btn_run.setBounds((width_ot-1) * 13,height_ot * 5 + 4, w, h);
        btn_in.setBounds((width_ot-1), height_ot - 5, w, h);
        btn_out.setBounds((width_ot-1) * 7, height_ot - 5, w, h);

        panel_scorer.add(first_index_CF);
        panel_scorer.add(Value_Of_TF);
        panel_scorer.add(Delimiter_text);
        panel_scorer.add(btn_run);
        panel_scorer.add(btn_in);
        panel_scorer.add(btn_out);
    }
    public void gui(){
        jFrame = new JFrame();
        jPanel_main = new JPanel();
        jPanel_main.setLayout(null);
        jPanel_main.setBounds(0,0,width_frame,height_frame);
        btn_Filter = new JButton("Фильтр");
        /*height, y- высота
         width, x- ширина
        */
        int height_btn = 60;
        btn_Filter.setBounds(width_ot, height_ot, width_btn, height_btn);
        btn_Filter.addActionListener(e -> action_btn_filter());
        btn_Add = new JButton("Объединение");
        btn_Add.setBounds(width_ot * 2 + width_btn, height_ot, width_btn, height_btn);
        btn_Add.addActionListener(e -> action_btn_add());
        btn_Scorer = new JButton("Подсчет");
        btn_Scorer.setBounds(width_ot * 3 + width_btn * 2, height_ot, width_btn, height_btn);
        btn_Scorer.addActionListener(e -> action_btn_scorer());
        jLabel = new JLabel("Выберите действие:");
        jLabel.setVisible(true);
        jPanel_main.setSize(465,200);
        jPanel_main.add(jLabel);
        jPanel_main.add(btn_Filter);
        jPanel_main.add(btn_Add);
        jPanel_main.add(btn_Scorer);
        jFrame.add(jPanel_main);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        config_Frame(jFrame);
        jFrame.setVisible(true);
    }
}