package MiniProject;

import Supervised.SaveRestoreObjFromFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Panel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Serializable {
    private final int WIDTH;
    private final int HEIGHT;

    /* Modes:
    0 - Moving
    1 - Creating
    1 - Delete
    */
    private int mode = 0;
    private final int MOVING = 0;
    private final int CREATING = 1;
    private final int DELETE = 2;

    /* Shapes:
    0 - Rectangle
    1 - Circle
    */
    private int shape = 0;
    private final int RECTANGLE = 0;
    private final int CIRCLE = 1;
    private static Color color;

    private int toMove = -1; // Object to move
    private int onIndex = -1;

    private int prevX = -1;
    private int prevY = -1;

    private ArrayList<Shape> objects = new ArrayList<>();
    private JFrame colorPicker;

    Panel(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        colorPicker = new JFrame("Color Picker");
        colorPicker.setSize(200, 200);
        colorPicker.setResizable(false);
        colorPicker.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ColorPicker panel = new ColorPicker(color);
        colorPicker.add(panel);
        colorPicker.addMouseListener(panel);
    }

    public void setObjects(ArrayList<Shape> objects) {
        this.objects = objects;
    }

    public static void main(String[] args) {
        Panel panel = new Panel(640, 480);
        panel.objects.add(new Rectangle(50, 50, 50, 50, Color.red));

        JFrame jf = new JFrame("Le Paint");
        jf.setSize(panel.WIDTH, panel.HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setVisible(true);

        jf.add(panel);
        jf.addKeyListener(panel);
        jf.addMouseListener(panel);
        jf.addMouseMotionListener(panel);
//        panel.paintComponent(panel.getGraphics());
        panel.repaint();
    }

    public static void setColor(Color color) {
        Panel.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        repaint();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).show(g);
        }

        if (onIndex != -1 && onIndex < objects.size()) {
            objects.get(onIndex).stroke(g);
        }

        g.setColor(Color.BLACK);
        if (mode == 0) {
            g.drawString("Moving", 10, 20);
        } else if (mode == 1) {
            g.drawString("Creating", 10, 20);
        } else if (mode == 2) {
            g.drawString("Delete", 10, 20);
        }
    }

    private int getObjectAt(int x, int y) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            if (objects.get(i).isInside(x, y)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'm' || keyEvent.getKeyChar() == 'M') {
            mode = (mode + 1) % 3;
            repaint();
        } else if (keyEvent.getKeyChar() == 's' || keyEvent.getKeyChar() == 'S') {
            shape = (shape + 1) % 2;
        } else if (keyEvent.getKeyChar() == 'c' || keyEvent.getKeyChar() == 'C') {
            if (colorPicker.isVisible()) {
                colorPicker.setVisible(false);
            } else {
                colorPicker.setVisible(true);
            }
        } else if (keyEvent.getKeyChar() == 'z' || keyEvent.getKeyChar() == 'Z') {
            try {
                SaveRestoreObjFromFile.saveToFile("project", this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (keyEvent.getKeyChar() == 'x' || keyEvent.getKeyChar() == 'X') {
            try {
                Panel panel = (Panel) SaveRestoreObjFromFile.restoreFromFile("project");
                this.setObjects(panel.objects);

                toMove = -1;
                onIndex = -1;
                repaint();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        toMove = -1;
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        prevX = mouseEvent.getX();
        prevY = mouseEvent.getY();
        if (mode == MOVING) {
            toMove = getObjectAt(prevX, prevY);
        } else if (mode == CREATING) {
            if (shape == RECTANGLE) {
                objects.add(new Rectangle(mouseEvent.getX(), mouseEvent.getY(), 0, 0));
            } else if (shape == CIRCLE) {
                objects.add(new Circle(mouseEvent.getX(), mouseEvent.getY(), 0));
            }
        } else if (mode == DELETE) {
            int toRemove = getObjectAt(prevX, prevY);
            if (toRemove != -1) {
                objects.remove(toRemove);
            }
            onIndex = -1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (mode == MOVING) {
            if (toMove != -1 && toMove < objects.size()) {
                Shape obj = objects.get(toMove);
                obj.move(mouseEvent.getX() - prevX, mouseEvent.getY() - prevY);
                objects.set(toMove, obj);
            }
            prevX = mouseEvent.getX();
            prevY = mouseEvent.getY();
        } else if (mode == CREATING) {
            int width = mouseEvent.getX() - prevX;
            int height = mouseEvent.getY() - prevY;
            if (shape == RECTANGLE) {
                if (width >= 0 && height >= 0) {
                    objects.set(objects.size() - 1, new Rectangle(
                            prevX, prevY,
                            width, height, color
                    ));
                } else if (width >= 0) {
                    objects.set(objects.size() - 1, new Rectangle(
                            prevX, prevY + height,
                            width, -height, color
                    ));
                } else if (height >= 0) {
                    objects.set(objects.size() - 1, new Rectangle(
                            prevX + width, prevY,
                            -width, height, color
                    ));
                } else {
                    objects.set(objects.size() - 1, new Rectangle(
                            prevX + width, prevY + height,
                            -width, -height, color
                    ));
                }
            } else {
                if (width >= 0) {
                    objects.set(objects.size() - 1, new Circle(
                            prevX, prevY,
                            width, color
                    ));
                } else {
                    objects.set(objects.size() - 1, new Circle(
                            prevX, prevY,
                            -width, color
                    ));
                }
            }

        }

//        paintComponent(getGraphics());
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        onIndex = getObjectAt(mouseEvent.getX(), mouseEvent.getY());
//        paintComponent(getGraphics());
        repaint();
    }
}
