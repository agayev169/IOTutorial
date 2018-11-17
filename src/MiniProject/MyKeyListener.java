package MiniProject;

import Supervised.SaveRestoreObjFromFile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MyKeyListener implements KeyListener {
    private Panel panel; // Main panel

    public MyKeyListener(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'm' || keyEvent.getKeyChar() == 'M') {
            // Change the mode to the next, chosen object to null and repaint the panel
            panel.nextMode();
            panel.setChosenByIndex(-1);
            panel.repaint();
        } else if (keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'S') {
            // Change the shape to be drawn
            panel.nextShape();
        } else if (keyEvent.getKeyChar() == 'c' || keyEvent.getKeyChar() == 'C') {
            // Make the ColorPicker be visible or invisible depending on its previous state
            if (panel.getColorPicker().isVisible()) {
                panel.getColorPicker().setVisible(false);
            } else {
                panel.getColorPicker().setVisible(true);
            }
        } else if (keyEvent.getKeyChar() == 'z' || keyEvent.getKeyChar() == 'Z') {
            // Save the project with project name
            try {
                SaveRestoreObjFromFile.saveToFile("project", panel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (keyEvent.getKeyChar() == 'l' || keyEvent.getKeyChar() == 'L') {
            // Load the project
            try {
                Panel loadedPanel = (Panel) SaveRestoreObjFromFile.restoreFromFile("project");
                panel.setObjects(loadedPanel.getObjects());

                // Set indexes to -1 so that there is no problem with moving and stroking
                panel.setToMove(-1);
                panel.setOnIndex(-1);
                panel.repaint();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void keyTyped(KeyEvent keyEvent) {}
}
