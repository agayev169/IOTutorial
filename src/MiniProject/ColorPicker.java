package MiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorPicker extends JPanel implements MouseListener {
    Color color;
    ColorPicker (Color color) {
        super();
        this.color = color = Color.RED;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < 200; y += 10) {
            for (int x = 0; x < 200; x += 10) {
                g.setColor(new Color(y * 255 / 200, x * 255 / 200, x * 255 / 200));
                g.fillRect(x, y, 10, 10);
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(color.getGreen() * 200 / 255, color.getRed() * 200 / 255, 10, 10);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        color = new Color(mouseEvent.getY() * 255 / 200,
                mouseEvent.getX() * 255 / 200,
                mouseEvent.getX() * 255 / 200);
        Panel.setColor(color);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
