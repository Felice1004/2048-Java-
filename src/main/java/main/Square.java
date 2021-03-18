/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;

public class Square extends Rectangle {

    boolean isMerged;
    int val;
    int row, col;
    int destX,destY= 60;
   
    int x, y, height, width;
    Font font;
//    Square destSquare = null;
    HashMap<Integer, Color> ColorMap;

    public Square(int i, int j, int x, int y, int height, int width) {
        this.row = i;
        this.col = j;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.val = 0;
        this.font = new Font("Gen Jyuu Gothic Medium", Font.PLAIN, 13);
        this.isMerged = false;

        ColorMap = new HashMap<Integer, Color>();
        ColorMap.put(0, new Color(236, 226, 228));
        ColorMap.put(2, new Color(250, 231, 231));
        ColorMap.put(4, new Color(245, 207, 207));
        ColorMap.put(8, new Color(235, 157, 157));
        ColorMap.put(16, new Color(228, 120, 120));
        ColorMap.put(32, new Color(215, 57, 57));
        ColorMap.put(64, new Color(175, 35, 35));
        ColorMap.put(128, new Color(255, 196, 155));
        ColorMap.put(256, new Color(255, 163, 101));
        ColorMap.put(512, new Color(173, 58, 88));
        ColorMap.put(1024, new Color(124, 79, 90));

    }

    public void setVal(int i) {
        this.val = i;
    }

    public int getVal() {
        return val;
    }

    public void setMerged(boolean b) {
        this.isMerged = b;
    }

    public void draw(Graphics g) {
        if (ColorMap.get(this.val) != null) {
            g.setColor(ColorMap.get(this.val));
        } else {
            g.setColor(new Color(179, 0, 51));
        }

        g.fillRect(x, y, height, width);
        g.setColor(Color.black);
        if (val != 0) {
            g.setFont(this.font);
            g.drawString(String.valueOf(val), x + 13, y + 30);
        }

    }

    @Override
    public String toString() {
        return "square( " + row + "," + col + " )    - >    [x: " + x + ", y: " + y + "] " + " val: " + val;
    }
}
