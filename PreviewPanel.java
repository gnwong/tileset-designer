/*
 *  PreviewPanel.java
 *
 *
 */


import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Dimension;



import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class PreviewPanel extends JPanel {

  public int verticalTileCount, horizontalTileCount;
  public int pxSize;
  public int skip = 1;
  public int width, height;
  public int selectX, selectY;

  public int writeMode = 0; // 0 -> base; 1 -> overlay

  public int[][] map;
  public int[][] overlay;

  private boolean isPreview;
  private PreviewPanel parentPanel;

  public PreviewPanel (int vertical, int horizontal, boolean preview) {
    super();

    this.isPreview = preview;

    this.setBorder(BorderFactory.createLoweredBevelBorder());

    width = 48;
    height = 48;

    int counter = 0;
    verticalTileCount    = vertical;
    horizontalTileCount  = horizontal;
    map = new int[horizontal][vertical];
    overlay = new int[horizontal][vertical];
    for (int i=0; i<horizontal; i++) {
      for (int j=0; j<vertical; j++) {
        map[i][j] = counter;
        overlay[i][j] = -1;
        if (!this.isPreview) counter++;
      }
    }

    this.setPreferredSize (new Dimension((width+skip)*horizontalTileCount, (height+skip)*verticalTileCount));
    this.setMaximumSize   (new Dimension((width+skip)*horizontalTileCount, (height+skip)*verticalTileCount));
    this.setMinimumSize   (new Dimension((width+skip)*horizontalTileCount, (height+skip)*verticalTileCount));

    selectX = -1;
    selectY = -1;


    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed (MouseEvent e) {
        switch (e.getButton()) {
          // Left button
          case 1:
            selectX = getTile(e.getX(), e.getY())[0];
            selectY = getTile(e.getX(), e.getY())[1];
            if (!isPreview) {
              parentPanel.setSelectedTileType(map[selectX][selectY]);
            }
            break;
          // Middle button
          case 2:
            break;
          // End button
          case 3:
            break;
          default:
            break;
        }
        repaint();
      }
    });
  }

  public void setParent (PreviewPanel p) {
    this.parentPanel = p;
  }

  public void setSelectedTileType (int type) {
    try {
      if (writeMode == 0) {
        map[selectX][selectY] = type;  
      } else {
        overlay[selectX][selectY] = type;
      }
      repaint();
    } catch (Exception ex) {}
  }

  public int getTypeOfSelected () {
    return map[selectX][selectY];
  }

  @Override
  public void paint (Graphics g) {

    // Clear screen
    g.setColor(new Color(0xEEEEEE));
    g.drawRect(0,0,getWidth(),getHeight());    

    // Draw base
    for (int i=0; i<horizontalTileCount; i++) {
      for (int j=0; j<verticalTileCount; j++) {
        g.drawImage(Images.tile(map[i][j]), i*(width+skip), j*(height+skip), width, height, null);
      }
    } 

    // Draw overlay
    if (this.isPreview) {
      for (int i=0; i<horizontalTileCount; i++) {
        for (int j=0; j<verticalTileCount; j++) {
          if (overlay[i][j]>=0) {
            g.drawImage(Images.tile(overlay[i][j]), i*(width+skip), j*(height+skip), width, height, null);
          }
        }
      }
    }

    // Draw selection tile
    g.setColor(Color.black);
    g.drawRect(selectX*(width+skip)-1, selectY*(height+skip)-1, width+skip, height+skip);
  }

  public int[] getTile(int x, int y) {
    int[] tiles = new int[2];
    tiles[0] = x/(width+skip);
    tiles[1] = y/(height+skip);
    return tiles;
  }

}
