/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.Timer;
import static main.GameFrame.side;
import static main.GameFrame.squares;

public class GamePanel extends JPanel {

    int speed = 8;

    enum STATUS {
        PREEXE,
        RUNNING,
        FINISH,
    }
    boolean winNotified = false;
    int Rdrow, Rdcol, score = 0;
    int side = 4;
    Square[][] squares;
    Square[][] storeSquares = new Square[side][side];
    Square[][] ssft = storeSquares;
    Square[][] squaresBg;
    ArrayList<Timer> timerList;
    ArrayList<Square> squaresAnima;
    STATUS status = STATUS.PREEXE;
    Graphics g;

    public GamePanel(Square[][] s) {
        timerList = new ArrayList<Timer>();
        squaresAnima = new ArrayList<Square>();

        setBackground(new Color(255, 249, 247));
//        setBackground(null);
        squares = s;

        squaresBg = new Square[side][side];
        squaresBg[0][0] = new Square(0, 0, 60, 60, 50, 50);
        squaresBg[0][1] = new Square(0, 1, 120, 60, 50, 50);
        squaresBg[0][2] = new Square(0, 2, 180, 60, 50, 50);
        squaresBg[0][3] = new Square(0, 3, 240, 60, 50, 50);

        squaresBg[1][0] = new Square(1, 0, 60, 120, 50, 50);
        squaresBg[1][1] = new Square(1, 1, 120, 120, 50, 50);
        squaresBg[1][2] = new Square(1, 2, 180, 120, 50, 50);
        squaresBg[1][3] = new Square(1, 3, 240, 120, 50, 50);

        squaresBg[2][0] = new Square(2, 0, 60, 180, 50, 50);
        squaresBg[2][1] = new Square(2, 1, 120, 180, 50, 50);
        squaresBg[2][2] = new Square(2, 2, 180, 180, 50, 50);
        squaresBg[2][3] = new Square(2, 3, 240, 180, 50, 50);

        squaresBg[3][0] = new Square(3, 0, 60, 240, 50, 50);
        squaresBg[3][1] = new Square(3, 1, 120, 240, 50, 50);
        squaresBg[3][2] = new Square(3, 2, 180, 240, 50, 50);
        squaresBg[3][3] = new Square(3, 3, 240, 240, 50, 50);

    }

    public void setSquares(Square[][] s) {
        this.squares = s;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;

        //draw background
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                squaresBg[i][j].draw(g);
            }
        }

        //draw front squares
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (squares[i][j] != null) {
                    squares[i][j].draw(g);
                }

            }
        }
        //draw Animation
        if (!squaresAnima.isEmpty()) {
            for (Square s : squaresAnima) {
                if (s != null && s.val != 0) {
                    s.draw(g);
                }
            }
        }
        this.squaresAnima.removeAll(squaresAnima);

        g.drawString("Score", 360, 160);

        if (status == STATUS.FINISH) {
            calculateScore();
            g.drawString(Integer.toString(score - 2), 372, 180);
        } else {
            if (score == 0) {
                g.drawString(Integer.toString(0), 372, 180);
            } else {
                g.drawString(Integer.toString(score - 2), 372, 180);
            }
        }

    }

    public boolean isMovable(int direction) {
        boolean movable = false;
        clearStoreSquares();
        switch (direction) {
            case 38://上
                addSquaresToStoreSquaresUP();
                for (int i = 0; i < side - 1; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null && storeSquares[i + 1][j] != null) {
                            if (storeSquares[i][j].getVal() == storeSquares[i + 1][j].getVal()) {
                                return true;
                            }
                        }
                        if (storeSquares[i][j] != null && storeSquares[i][j].getVal() != squares[i][j].getVal()) {
                            return true;
                        }
                    }
                }
                break;
            case 40://下
                addSquaresToStoreSquaresDOWN();
                for (int i = 3; i > 0; i--) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null && storeSquares[i - 1][j] != null) {
                            if (storeSquares[i][j].getVal() == storeSquares[i - 1][j].getVal()) {
                                return true;
                            }
                        }
                        if (storeSquares[i][j] != null && storeSquares[i][j].getVal() != squares[i][j].getVal()) {
                            return true;
                        }
                    }
                }
                break;
            case 37://左
                addSquaresToStoreSquaresLEFT();
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side - 1; j++) {
                        if (storeSquares[i][j] != null && storeSquares[i][j + 1] != null) {
                            if (storeSquares[i][j].getVal() == storeSquares[i][j + 1].getVal()) {
                                return true;
                            }
                        }
                        if (storeSquares[i][j] != null && storeSquares[i][j].getVal() != squares[i][j].getVal()) {
                            return true;
                        }
                    }
                }
                break;
            case 39://右
                addSquaresToStoreSquaresRIGHT();
                for (int i = 0; i < side; i++) {
                    for (int j = 3; j > 0; j-- ) {
                        if (storeSquares[i][j] != null && storeSquares[i][j-1] != null) {
                            if (storeSquares[i][j].getVal() == storeSquares[i][j - 1].getVal()) {
                                return true;
                            }
                        }
                        if (storeSquares[i][j] != null && storeSquares[i][j].getVal() != squares[i][j].getVal()) {
                            return true;
                        }
                    }
                }
                break;

        }

        return movable;
    }

    private boolean hasWon() {
        boolean win = false;
        if (!this.winNotified) {
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) {
                    if (squares[i][j].getVal() == 2048) {
                        JOptionPane.showMessageDialog(null, "You Win!");
                        this.winNotified = true;
                        return true;
                    }
                }
            }
        }
        return win;

    }

    private void calculateScore() {
        int sum = 0;
        for (Square[] ss : squares) {
            for (Square s : ss) {
                sum += s.val;
            }
        }
        score = sum;
    }

    private void refresh() {
        for (int i = side - 1; i >= 0; i--) {
            for (int j = side - 1; j >= 0; j--) {
                squares[i][j].setMerged(false);
            }
        }
    }

    private void setRandomRowCol() {
        this.Rdrow = (int) (Math.random() * side);
        this.Rdcol = (int) (Math.random() * side);
    }

    void setRandomShowUpCell() {
//        System.out.println("I am called :)");
        boolean isSet = false;
        while (!isSet) {
            setRandomRowCol();
            if (this.squares[Rdrow][Rdcol].getVal() == 0) {
                this.squares[Rdrow][Rdcol].setVal(2);
                isSet = true;
            }
        }

    }

    private void clearStoreSquares() {
//        System.out.println("又清空啦~~~");
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                storeSquares[i][j] = null;
            }
        }
    }

    private void addSquaresToStoreSquaresLEFT() {
        for (int i = 0; i < side; i++) {
            int st = 0;
            for (int j = 0; j < side; j++) {
                if (squares[i][j].getVal() != 0) {//自己非0
//                    System.out.println("被加在 " + i + "," + st);
                    Square addedSquare = new Square(i, j, squares[i][j].x, squares[i][j].y, squares[i][j].height, squares[i][j].width);
                    addedSquare.destX = squares[i][st].x;
                    addedSquare.setVal(squares[i][j].val);
                    storeSquares[i][st] = addedSquare;
                    st++;
                }
            }//end of double loop(2)
        }//end of double loop(1)
    }

    public void leftShiftMerge() {
        status = STATUS.PREEXE;
        clearStoreSquares();
        //把方塊加進去storeSquares
        addSquaresToStoreSquaresLEFT();

        status = STATUS.RUNNING;
        // ->使用timer來點轉場動畫->然後用storeSquares把squares大改寫
        Timer firstAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            squares[i][j] = (Square) storeSquares[i][j].clone();
                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }

                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].x > ssc[i][j].destX) {
                                ssc[i][j].x -= 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].x == ssc[i][j].destX) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }
                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    repaint();
                    setPosition(e);
                } else {
                    repaint();
                    repaint();
                }
            }
        });
        firstAnimationTimer.start();

        //->使用timer來進行合併和合併動畫****
        Timer secondAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] squareCopy = squares.clone();

            private void clearStoreSquares() {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        storeSquares[i][j] = null;
                    }
                }
            }

            private void addSquaresToStoreSquares() {
                for (int i = 0; i < side; i++) {//loop Rdrow
                    int st = 0;
                    for (int j = 0; j < side; j++) {
                        if (squares[i][j].getVal() != 0) {//自己非0
                            Square addedSquare = new Square(i, j, squares[i][j].x, squares[i][j].y, squares[i][j].height, squares[i][j].width);
                            addedSquare.destX = squares[i][st].x;
                            addedSquare.setVal(squares[i][j].val);
                            storeSquares[i][st] = addedSquare;
                            st++;
                        }
                    }//end of double loop(2)
                }//end of double loop(1)

            }

            private void merge() {  //合併!

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (j != side - 1) {
                            if (squares[i][j].getVal() == squares[i][j + 1].getVal() && !squares[i][j].isMerged) {
                                squares[i][j].setVal(squares[i][j].getVal() + squares[i][j + 1].getVal());
                                squares[i][j + 1].setVal(0);
                                squares[i][j].setMerged(true);
                            }
                        }
                    }
                }
                refresh();
                //合併結束
            }

            private void stop(ActionEvent e) {
                clearStoreSquares();
                addSquaresToStoreSquares();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstAnimationTimer.isRunning()) {
                    //合併動畫

                    //正式合併
                    merge();
                    repaint();
                    stop(e);
                }

            }//end of actionperformed

        });
        secondAnimationTimer.start();

        //合併之後的再次排列
        Timer thirdAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {

                //用storeSquares把squares大改寫
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            squares[i][j] = storeSquares[i][j];
                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }
                    }
                }

                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                ((Timer) e.getSource()).stop();

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstAnimationTimer.isRunning() && !secondAnimationTimer.isRunning()) {
                    //Merge完重新排好//也需要移動動畫
                    ssc = storeSquares.clone();
                    for (int i = 0; i < side; i++) {
                        for (int j = 0; j < side; j++) {
                            if (ssc[i][j] != null) {
                                if (ssc[i][j].x > ssc[i][j].destX) {
                                    ssc[i][j].x -= 10;
                                    squaresAnima.add(ssc[i][j]);
                                } else if (ssc[i][j].x == ssc[i][j].destX) {
                                    squares[i][j] = (Square) ssc[i][j].clone();
                                }
                            }
                        }
                    }

                    for (int i = 0; i < side; i++) {
                        for (int j = 0; j < side; j++) {
                            for (Square a : squaresAnima) {
                                if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                    Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                    zeroSquare.setVal(0);
                                    squares[i][j] = zeroSquare;
                                    break;
                                }
                            }
                        }
                    }

                    //開始動畫或停止timer
                    if (squaresAnima.isEmpty()) {
                        setRandomShowUpCell();
//                        System.out.println("隨機方塊是! row: " + Rdrow + "col: " + Rdcol);
                        repaint();
                        setPosition(e);
                    } else {
                        repaint();
                        repaint();
                    }

                }//end of if

            }
        });
        thirdAnimationTimer.start();

        //setRandomShowUp Animation Timer
        Timer fourthAnimationTimer = new Timer(5, new ActionListener() {
            Square showUpSquare = new Square(Rdrow, Rdcol, 0, 0, 0, 0);

            private void stop(ActionEvent e) {
                hasWon();
                status = STATUS.FINISH;
                squares[Rdrow][Rdcol].setVal(2);
                repaint();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!thirdAnimationTimer.isRunning()) {
                    showUpSquare.row = Rdrow;
                    showUpSquare.col = Rdcol;
                    showUpSquare.x = (Rdcol + 1) * 60;
                    showUpSquare.y = (Rdrow + 1) * 60;

                    showUpSquare.setVal(2);

                    showUpSquare.height += 2;
                    showUpSquare.width += 2;
                    if (showUpSquare.width < 50) {
                        squaresAnima.add(showUpSquare);
                    }
                    if (squaresAnima.isEmpty()) {
                        stop(e);
                    } else {
                        repaint();
                        repaint();
                    }

                }

            }

        });
        fourthAnimationTimer.start();
    }//end of left shift

    private void addSquaresToStoreSquaresRIGHT() {
        for (int i = 0; i < side; i++) {
            int st = 3;
            for (int j = 3; j >= 0; j--) {
                if (squares[i][j].getVal() != 0) {//自己非0
//                    System.out.println("被加在 " + i + "," + st);
                    Square addedSquare = new Square(i, j, squares[i][j].x, squares[i][j].y, squares[i][j].height, squares[i][j].width);
                    addedSquare.destX = squares[i][st].x;
                    addedSquare.setVal(squares[i][j].val);
                    storeSquares[i][st] = addedSquare;
                    st--;
                }
            }//end of LOOP
        }//end of LOOP

    }

    public void rightShiftMerge() {
        status = STATUS.PREEXE;

        clearStoreSquares();
        addSquaresToStoreSquaresRIGHT();
        status = STATUS.RUNNING;
        Timer firstAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            squares[i][j] = (Square) storeSquares[i][j].clone();
                            //新square
                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }

                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].x < ssc[i][j].destX) {
                                ssc[i][j].x += 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].x == ssc[i][j].destX) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                //如果方塊有被加到anima裡面 就讓它不見
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }

                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    repaint();
                    setPosition(e);
                } else {
                    repaint();
                    repaint();
                }
            }
        });
        firstAnimationTimer.start();

        Timer secondAnimationTimer = new Timer(speed, new ActionListener() {
//            Square[][] squareCopy = squares.clone();

            private void clearStoreSquares() {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        storeSquares[i][j] = null;
                    }
                }
            }

            private void addSquaresToStoreSquares() {
                addSquaresToStoreSquaresRIGHT();

            }

            private void merge() {  //合併!

                for (int i = 0; i < side; i++) {
                    for (int j = 3; j > 0; j--) {
                        if (squares[i][j].getVal() == squares[i][j - 1].getVal() && !squares[i][j].isMerged) {
                            squares[i][j].setVal(squares[i][j].getVal() + squares[i][j - 1].getVal());
                            squares[i][j - 1].setVal(0);
                            squares[i][j].setMerged(true);
                        }
                    }
                }
                refresh();
                //合併結束
            }

            private void stop(ActionEvent e) {
                clearStoreSquares();
                addSquaresToStoreSquares();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstAnimationTimer.isRunning()) {

                    merge();
                    repaint();
                    stop(e);
                }

            }//end of actionperformed

        });
        secondAnimationTimer.start();

        Timer thirdAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            squares[i][j] = (Square) storeSquares[i][j].clone();
                            //新square
                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }

                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].x < ssc[i][j].destX) {
                                ssc[i][j].x += 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].x == ssc[i][j].destX) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                //如果方塊有被加到anima裡面 就讓它不見
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }

                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    setRandomShowUpCell();
                    repaint();
                    setPosition(e);
                } else {
                    repaint();
                    repaint();
                }
            }
        });
        thirdAnimationTimer.start();

        Timer fourthAnimationTimer = new Timer(5, new ActionListener() {
            Square showUpSquare = new Square(Rdrow, Rdcol, Rdcol * 60, Rdrow * 60, 0, 0);

            private void stop(ActionEvent e) {
                hasWon();
                status = STATUS.FINISH;
                squares[Rdrow][Rdcol].setVal(2);
                repaint();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!thirdAnimationTimer.isRunning()) {
                    showUpSquare.row = Rdrow;
                    showUpSquare.col = Rdcol;
                    showUpSquare.x = (Rdcol + 1) * 60;
                    showUpSquare.y = (Rdrow + 1) * 60;
//                    System.out.println("隨機方塊是! row: " + Rdrow + "col: " + Rdcol);
//                    System.out.println(showUpSquare);
                    showUpSquare.setVal(2);
                    showUpSquare.height += 2;
                    showUpSquare.width += 2;
                    if (showUpSquare.width < 50) {
                        squaresAnima.add(showUpSquare);
                    }
                    if (squaresAnima.isEmpty()) {
                        stop(e);
                    } else {
                        repaint();
                        repaint();
                    }

                }

            }
        });
        fourthAnimationTimer.start();

    }

    private void addSquaresToStoreSquaresUP() {
        for (int j = 0; j < side; j++) {
            int st = 0;
            for (int i = 0; i < side; i++) {
                if (squares[i][j].getVal() != 0) {//自己非0
//                    System.out.println("被加在 " + i + "," + st);
                    Square addedSquare = new Square(i, j, squares[i][j].x, squares[i][j].y, squares[i][j].height, squares[i][j].width);
                    addedSquare.destY = squares[st][j].y;
                    addedSquare.setVal(squares[i][j].val);
                    storeSquares[st][j] = addedSquare;
                    st++;
                }
            }//end of LOOP
        }//end of LOOP
    }

    public void upShiftMerge() {
        status = STATUS.PREEXE;
        clearStoreSquares();

        //把方塊加進去storeSquares
        addSquaresToStoreSquaresUP();

        status = STATUS.RUNNING;
        // ->使用timer來點轉場動畫->然後用storeSquares把squares大改寫
        Timer firstAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            Square addedSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            addedSquare.setVal(storeSquares[i][j].val);
                            squares[i][j] = (Square) addedSquare.clone();

                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }
                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        System.out.println(squares[i][j]);
                    }
                }
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].y > ssc[i][j].destY) {
                                ssc[i][j].y -= 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].y == ssc[i][j].destY) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }
                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    System.out.println("anima empty");
                    repaint();
                    setPosition(e);
                } else {
                    System.out.println("repaint");

                    repaint();
                    repaint();
                }
            }
        });
        firstAnimationTimer.start();

        //->使用timer來進行合併和合併動畫****
        Timer secondAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] squareCopy = squares.clone();

            private void clearStoreSquares() {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        storeSquares[i][j] = null;
                    }
                }
            }

            private void merge() {  //合併!

                for (int j = 0; j < side; j++) {
                    for (int i = 0; i < side - 1; i++) {
                        if (squares[i][j].getVal() == squares[i + 1][j].getVal() && !squares[i][j].isMerged) {
                            squares[i][j].setVal(squares[i][j].getVal() + squares[i + 1][j].getVal());
                            squares[i + 1][j].setVal(0);
                            squares[i][j].setMerged(true);
                        }

                    }
                }
                refresh();
                //合併結束
            }

            private void stop(ActionEvent e) {
                clearStoreSquares();
                addSquaresToStoreSquaresUP();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstAnimationTimer.isRunning()) {
                    //合併動畫

                    //正式合併
                    merge();
                    repaint();
                    stop(e);
                }

            }//end of actionperformed

        });
        secondAnimationTimer.start();

        //合併之後的再次排列
        Timer thirdAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            Square addedSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            addedSquare.setVal(storeSquares[i][j].val);
                            squares[i][j] = (Square) addedSquare.clone();

                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }
                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {

                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].y > ssc[i][j].destY) {
                                ssc[i][j].y -= 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].y == ssc[i][j].destY) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }
                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    setRandomShowUpCell();
                    repaint();
                    setPosition(e);
                } else {
                    repaint();
                    repaint();
                }
            }
        });
        thirdAnimationTimer.start();

        //setRandomShowUp Animation Timer
        Timer fourthAnimationTimer = new Timer(5, new ActionListener() {
            Square showUpSquare = new Square(Rdrow, Rdcol, Rdcol * 60, Rdrow * 60, 0, 0);

            private void stop(ActionEvent e) {
                hasWon();
                status = STATUS.FINISH;
                squares[Rdrow][Rdcol].setVal(2);
                repaint();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!thirdAnimationTimer.isRunning()) {
                    showUpSquare.row = Rdrow;
                    showUpSquare.col = Rdcol;
                    showUpSquare.x = (Rdcol + 1) * 60;
                    showUpSquare.y = (Rdrow + 1) * 60;
//                    System.out.println("隨機方塊是! row: " + Rdrow + "col: " + Rdcol);
//                    System.out.println(showUpSquare);
                    showUpSquare.setVal(2);
                    showUpSquare.height += 2;
                    showUpSquare.width += 2;
                    if (showUpSquare.width < 50) {
                        squaresAnima.add(showUpSquare);
                    }
                    if (squaresAnima.isEmpty()) {
                        stop(e);
                    } else {
                        repaint();
                        repaint();
                    }

                }

            }
        });
        fourthAnimationTimer.start();

    }

    private void addSquaresToStoreSquaresDOWN() {
        for (int j = 0; j < side; j++) {
            int st = 3;
            for (int i = 3; i >= 0; i--) {
                if (squares[i][j].getVal() != 0) {//自己非0
//                    System.out.println("被加在 " + i + "," + st);
                    Square addedSquare = new Square(i, j, squares[i][j].x, squares[i][j].y, squares[i][j].height, squares[i][j].width);
                    addedSquare.destY = squares[st][j].y;
                    addedSquare.setVal(squares[i][j].val);
                    storeSquares[st][j] = addedSquare;
                    st--;
                }
            }//end of LOOP
        }//end of LOOP
    }

    public void downShiftMerge() {
        status = STATUS.PREEXE;
        clearStoreSquares();

        //把方塊加進去storeSquares
        addSquaresToStoreSquaresDOWN();

        status = STATUS.RUNNING;
        // ->使用timer來點轉場動畫->然後用storeSquares把squares大改寫
        Timer firstAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            Square addedSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            addedSquare.setVal(storeSquares[i][j].val);
                            squares[i][j] = (Square) addedSquare.clone();

                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }
                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].y < ssc[i][j].destY) {
                                ssc[i][j].y += 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].y == ssc[i][j].destY) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }
                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    System.out.println("anima empty");
                    repaint();
                    setPosition(e);
                } else {
                    System.out.println("repaint");

                    repaint();
                    repaint();
                }
            }
        });
        firstAnimationTimer.start();

        //->使用timer來進行合併和合併動畫****
        Timer secondAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] squareCopy = squares.clone();

            private void clearStoreSquares() {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        storeSquares[i][j] = null;
                    }
                }
            }

            private void merge() {  //合併!

                for (int j = 0; j < side; j++) {
                    for (int i = 3; i > 0; i--) {
                        if (squares[i][j].getVal() == squares[i - 1][j].getVal() && !squares[i][j].isMerged) {
                            squares[i][j].setVal(squares[i][j].getVal() + squares[i - 1][j].getVal());
                            squares[i - 1][j].setVal(0);
                            squares[i][j].setMerged(true);
                        }

                    }
                }
                refresh();
                //合併結束
            }

            private void stop(ActionEvent e) {
                clearStoreSquares();
                addSquaresToStoreSquaresDOWN();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!firstAnimationTimer.isRunning()) {
                    //合併動畫

                    //正式合併
                    merge();
                    repaint();
                    stop(e);
                }

            }//end of actionperformed

        });
        secondAnimationTimer.start();

        //合併之後的再次排列
        Timer thirdAnimationTimer = new Timer(speed, new ActionListener() {
            Square[][] ssc = storeSquares.clone();

            void setPosition(ActionEvent e) {
                //用storeSquares把squares大改寫
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (storeSquares[i][j] != null) {
                            Square addedSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            addedSquare.setVal(storeSquares[i][j].val);
                            squares[i][j] = (Square) addedSquare.clone();

                        } else {
                            Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                            zeroSquare.setVal(0);
                            squares[i][j] = (Square) zeroSquare.clone();
                        }

                    }
                }
                stop(e);

            }//end of set position

            public void stop(ActionEvent e) {

                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        if (ssc[i][j] != null) {
                            if (ssc[i][j].y < ssc[i][j].destY) {
                                ssc[i][j].y += 10;
                                squaresAnima.add(ssc[i][j]);
                            } else if (ssc[i][j].y == ssc[i][j].destY) {
                                squares[i][j] = (Square) ssc[i][j].clone();
                            }
                        }
                    }
                }

                for (int i = 0; i < side; i++) {
                    for (int j = 0; j < side; j++) {
                        for (Square a : squaresAnima) {
                            if (a.row == squares[i][j].row && a.col == squares[i][j].col) {
                                Square zeroSquare = new Square(i, j, (j + 1) * 60, (i + 1) * 60, 50, 50);
                                zeroSquare.setVal(0);
                                squares[i][j] = zeroSquare;
                                break;
                            }
                        }
                    }
                }
                //開始動畫或停止timer
                if (squaresAnima.isEmpty()) {
                    setRandomShowUpCell();
                    repaint();
                    setPosition(e);
                } else {
                    repaint();
                    repaint();
                }
            }
        });
        thirdAnimationTimer.start();

        //setRandomShowUp Animation Timer
        Timer fourthAnimationTimer = new Timer(5, new ActionListener() {
            Square showUpSquare = new Square(Rdrow, Rdcol, Rdcol * 60, Rdrow * 60, 0, 0);

            private void stop(ActionEvent e) {
                hasWon();
                status = STATUS.FINISH;
                squares[Rdrow][Rdcol].setVal(2);
                repaint();
                ((Timer) e.getSource()).stop();
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!thirdAnimationTimer.isRunning()) {
                    showUpSquare.row = Rdrow;
                    showUpSquare.col = Rdcol;
                    showUpSquare.x = (Rdcol + 1) * 60;
                    showUpSquare.y = (Rdrow + 1) * 60;
//                    System.out.println("隨機方塊是! row: " + Rdrow + "col: " + Rdcol);
//                    System.out.println(showUpSquare);
                    showUpSquare.setVal(2);
                    showUpSquare.height += 2;
                    showUpSquare.width += 2;
                    if (showUpSquare.width < 50) {
                        squaresAnima.add(showUpSquare);
                    }
                    if (squaresAnima.isEmpty()) {
                        stop(e);
                    } else {
                        repaint();
                        repaint();
                    }

                }

            }
        });
        fourthAnimationTimer.start();
    }

}
