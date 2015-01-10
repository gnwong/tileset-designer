/*
 *  `Designer.java'
 *    ...
 *
 *  Copyright (C)  2015  George Wong
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/.
 */


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Dimension;


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Designer {

  private static JFrame frame;


  static PreviewPanel preview;
  static PreviewPanel selection;

  public static void main (String[] args) {
    createGUI();
    loadConfig();
  }


  private static void createGUI () {
    frame = new JFrame();
    frame.setTitle("Tileset Designer");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setMinimumSize(new Dimension(640,480));

    frame.setLayout(new BorderLayout());

    // Load images
    Images.loadImages();

    // Set up preview panel
    preview = new PreviewPanel(5, 5, true);
    JPanel previewFrame = new JPanel();
    previewFrame.setLayout(new GridBagLayout());
    previewFrame.add(preview); 
    previewFrame.setBorder(BorderFactory.createLoweredBevelBorder());
    frame.add(previewFrame, BorderLayout.CENTER);

    // Set up selection panel
    selection = new PreviewPanel(3, 10, false);
    selection.setParent(preview);
    preview.setParent(selection);
    frame.add(selection, BorderLayout.PAGE_END);

    // Set up debug stuff
    JPanel debugPanel = new JPanel();
    debugPanel.setLayout(new GridLayout(5,1));
    JButton clearbtn = new JButton("Clear Selected");
    clearbtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        preview.writeType = -1;
        preview.selectX = -1;
        preview.selectY = -1;
        preview.repaint();
      }
    });
    JButton skipbtn = new JButton("Skip Pixel");
    skipbtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        if (preview.skip == 0) {
          preview.skip = 1;
          preview.repaint();
        } else {
          preview.skip = 0;
          preview.repaint(); 
        }
      }
    });
    JButton reloadbtn = new JButton("Reload Image");
    reloadbtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        Images.loadImages();
        preview.repaint();
        selection.repaint();
      }
    });
    JButton savebtn = new JButton("Save");
    savebtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        saveConfig();
      }
    });
    JButton wmodebtn = new JButton("Mode: Base");
    wmodebtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        if (preview.writeMode == 0) {
          preview.writeMode = 1;
          wmodebtn.setText("Mode: Overlay");
        } else if (preview.writeMode == 1) {
          preview.writeMode = 0;
          wmodebtn.setText("Mode: Base");
        }
        
      }
    });
    JButton debugbtn = new JButton("Debug");
    debugbtn.addActionListener(new ActionListener () {
      public void actionPerformed (ActionEvent evt) {
        //preview.setSelectedTileType(selection.getTypeOfSelected());
      }
    });

    debugPanel.add(reloadbtn);
    debugPanel.add(clearbtn);
    debugPanel.add(skipbtn);
    debugPanel.add(wmodebtn);
    debugPanel.add(savebtn);
    frame.add(debugPanel, BorderLayout.LINE_END);


    frame.setVisible(true);
  }

  public static void loadConfig () {
    FileReader inputStream = null;

    try {
      inputStream = new FileReader("./config.txt");
      int c;
      for (int i=0; i<preview.horizontalTileCount; i++) {
        for (int j=0; j<preview.verticalTileCount; j++) {
          c = inputStream.read();
          //preview.map[i][j] = Character.getNumericValue(c);
          preview.map[i][j] = c - 36;
        }
      }
      for (int i=0; i<preview.horizontalTileCount; i++) {
        for (int j=0; j<preview.verticalTileCount; j++) {
          c = inputStream.read();
          //preview.overlay[i][j] = Character.getNumericValue(c);
          preview.overlay[i][j] = c - 36;
        }
      }
    } catch (Exception ex) {
    } finally {
      if (inputStream != null) {
        try { inputStream.close(); }
        catch (Exception ex) { }
      }
    }
  }

  public static void saveConfig () {
    FileWriter outputStream = null;

    try {
      outputStream = new FileWriter("./config.txt");
      for (int i=0; i<preview.horizontalTileCount; i++) {
        for (int j=0; j<preview.verticalTileCount; j++) {
          //outputStream.write(Integer.toString(preview.map[i][j]));
          outputStream.write(preview.map[i][j]+36);
        }
      }
      for (int i=0; i<preview.horizontalTileCount; i++) {
        for (int j=0; j<preview.verticalTileCount; j++) {
          //outputStream.write(Integer.toString(preview.overlay[i][j]));
          outputStream.write(preview.overlay[i][j]+36);
        }
      }
    } catch (Exception ex) {
    } finally {
      if (outputStream != null) {
        try { outputStream.close(); }
        catch (Exception ex) { }
      }
    }
  }

}

