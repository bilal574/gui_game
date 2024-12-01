package org.example;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//
public class TicTacToe implements Serializable {
    private static final long serialVersionUID = 1L;
    StartGamePanel startGamePanel ;
    int boardWidth = 600;
    int boardHeight = 650; //50px for the text panel on top

    transient JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {



    }

    void onStart()
    {
        startGamePanel = new StartGamePanel();
        //JFrame
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setFocusable(false);
        frame.setSize(600,650);
        frame.setLocation(700,150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





        JPanel startGamePanell = startGamePanel.createPanel();

        frame.add(startGamePanell);


        frame.setVisible(true);
    }

    void startGame()
    {

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocation(700,150);

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }

                    }
                });
            }
        }
    }
    void checkWinner() {
        //horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //diagonally
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //anti-diagonally
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }



    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
    }

    private String winner;
    private LocalDateTime winningTime;

    // ... Existing code ...

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        winner = currentPlayer;
        winningTime = LocalDateTime.now();
        textLabel.setText(currentPlayer + " is the winner!");

        // Save winner information to text file
        saveWinnerToTxt();
        sendWinnerToServer(winner);
    }

    private void saveWinnerToTxt() {
        String fileName = "winners_list.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Append winner information to the text file
            writer.write(winner + "," + winningTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            System.out.println("Winner's information has been saved to the text file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendWinnerToServer(String winnerName) {
        try (Socket socket = new Socket("localhost", 4999);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream())) {
            printWriter.println(winnerName);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}