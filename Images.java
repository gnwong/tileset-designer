/*
 *    Images.java
 *
 *    Created by George Wong on 19 May 2014
 *    Copyright (c) 2014 George Wong. All rights reserved.
 */


import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;


public class Images {

  public static String tilesetFilename = "";
  private static BufferedImage[] tileset;

  public static int width, height;

  public static void loadImages () {
    tilesetFilename  = "sample48.png";
    width = 48;
    height = 48;

    BufferedImage bigTileset;
    try {
      bigTileset   = ImageIO.read(new File(tilesetFilename));  
    } catch (Exception ex) {
      return;
    }
    
    int rows = 5;
    int cols = 10;
    tileset = new BufferedImage[rows * cols];
    

    for (int i=0; i<cols; i++) {
      for (int j=0; j<rows; j++) {
        tileset[(i*rows) + j] = bigTileset.getSubimage(1+(i*width), 1+(j*height),width-1,height-1);
      }
    }
  }


  public static Image tile(int x) {
    return tileset[x];
  }



}


