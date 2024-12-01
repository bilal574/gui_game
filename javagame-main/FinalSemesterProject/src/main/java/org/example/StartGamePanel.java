package org.example;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StartGamePanel extends Thread{

    TicTacToe ticTacToe;
    public  void gameassign(TicTacToe ticTacToe){
        this.ticTacToe = ticTacToe;
    }

    public  void run()
    {
        JFrame scoresFrame = new JFrame("Scores");
        scoresFrame.setSize(400, 400);
        scoresFrame.setLocationRelativeTo(null);

        JTextArea scoresTextArea = new JTextArea();
        scoresTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(scoresTextArea);

        try (BufferedReader reader = new BufferedReader(new FileReader("winners_list.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoresTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoresFrame.add(scrollPane);
        scoresFrame.setVisible(true);
    }

    public JPanel createPanel()
    {

        JLabel lTicTacToe = new JLabel("Tic Tac Toe");
        lTicTacToe.setFont(new Font("Arial",Font.ITALIC,30));
        lTicTacToe.setForeground(Color.WHITE);
        lTicTacToe.setLocation(400,30);




        //JButton start game
        JButton startGame = new JButton("Start Game");
        startGame.setFont(new Font("Arial",Font.PLAIN,25));
        startGame.setFocusable(false);
        startGame.setBackground(Color.yellow);
        startGame.setSize(200,70);
        startGame.setLocation(200,150);





        //JButton scores
        JButton scores = new JButton("Scores");
        scores.setFocusable(false);
        scores.setFont(new Font("Arial",Font.PLAIN, 25));
        scores.setBackground(Color.yellow);
        scores.setSize(200,70);
        scores.setLocation(200,250);


        //JButton exit
        JButton exit = new JButton("Exit");
        exit.setFocusable(false);
        exit.setFont(new Font("Arial",Font.PLAIN,25));
        exit.setBackground(Color.yellow);
        exit.setSize(200,70);
        exit.setLocation(200,350);






        //Panel 1
       // Border panel1Border = BorderFactory.createLineBorder(Color.white, 5);
        JPanel panel_1 = new JPanel();
        panel_1.setSize(600,650);
        panel_1.setBackground(Color.black);
        //panel_1.setBorder(panel1Border);



        //adding to the panel
        panel_1.add(lTicTacToe);
        panel_1.add(startGame);
        panel_1.add(scores);
        panel_1.add(exit);
        panel_1.setLayout(null);

//        startGame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//
//               // panel_1.setEnabled(false);
//                StartGamePanel startGamePanel = new StartGamePanel();
//                startGamePanel.createPanel();
//                //panel_1.setEnabled(false);
//            }
//        });

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                // Remove the panel from its parent container
//                Container parent = panel_1.getParent();
//                if (parent != null) {
//                    parent.remove(panel_1);
//                    parent.revalidate();
//                    parent.repaint();
//                }

                TicTacToe ticTacToe = new TicTacToe();
                ticTacToe.startGame();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });


        ///////////////
        scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                run();
            }
        });


        /////


        return panel_1;

    }
}
