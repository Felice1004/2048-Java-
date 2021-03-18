/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {

    static Square[][] squares;
    static int side = 4;

    public GameFrame(String name) {
        super(name);
        squares = new Square[side][side];

        squares[0][0] = new Square(0, 0, 60, 60, 50, 50);
        squares[0][1] = new Square(0, 1, 120, 60, 50, 50);
        squares[0][2] = new Square(0, 2, 180, 60, 50, 50);
        squares[0][3] = new Square(0, 3, 240, 60, 50, 50);

        squares[1][0] = new Square(1, 0, 60, 120, 50, 50);
        squares[1][1] = new Square(1, 1, 120, 120, 50, 50);
        squares[1][2] = new Square(1, 2, 180, 120, 50, 50);
        squares[1][3] = new Square(1, 3, 240, 120, 50, 50);

        squares[2][0] = new Square(2, 0, 60, 180, 50, 50);
        squares[2][1] = new Square(2, 1, 120, 180, 50, 50);
        squares[2][2] = new Square(2, 2, 180, 180, 50, 50);
        squares[2][3] = new Square(2, 3, 240, 180, 50, 50);

        squares[3][0] = new Square(3, 0, 60, 240, 50, 50);
        squares[3][1] = new Square(3, 1, 120, 240, 50, 50);
        squares[3][2] = new Square(3, 2, 180, 240, 50, 50);
        squares[3][3] = new Square(3, 3, 240, 240, 50, 50);

    }

    public static void main(String args[]) {
        GameFrame frame = new GameFrame("2048");

        //CREATE A GAMEPANEL
        GamePanel gamePanel = new GamePanel(squares);
        gamePanel.setRandomShowUpCell();

        //ADD COMPONENTS
        frame.add(gamePanel);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
//                System.out.println("你按了《" + c + "》鍵"); //測試用
                switch (c) {
                    case 38://上
                        if (gamePanel.isMovable(38)) {
                            gamePanel.upShiftMerge();
                        }
                        break;
                    case 40://下
                        if (gamePanel.isMovable(40)) {
                            gamePanel.downShiftMerge();
                        }
                        break;
                    case 37://左
                        if (gamePanel.isMovable(37)) {
                            gamePanel.leftShiftMerge();
                        }
                        break;
                    case 39://右
                        if (gamePanel.isMovable(39)) {
                            gamePanel.rightShiftMerge();
                        }
                        break;
                    case 32://空白建
//                        System.out.println("空白!");
                        for (Square[] ss : gamePanel.squares) {
                            for (Square s : ss) {
                                s.setVal(0);
                            }
                        }
                        gamePanel.setRandomShowUpCell();
                        gamePanel.repaint();
                        break;

                }
            }
        });

        //FRAME SETTING
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(470, 400);

    }

}
