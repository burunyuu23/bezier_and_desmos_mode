package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.figures.*;
import ru.vsu.edu.shlyikov_d_g.utils.ExpressionCommander;
import ru.vsu.edu.shlyikov_d_g.utils.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

public class FrameMain extends JFrame {
    private JPanel drawPanel;
    private JPanel mainPanel;
    private JTextField formulaInput;
    private JButton drawButton;
    private JComboBox chooseMethodBox;
    private JPanel desmosPanel;
    private JPanel cardsMethodsPanel;
    private JButton chooseMethodButton;
    private JPanel bezierCurvePanel;

    private Figure figure;
    private BezieCurve bezierPoints;

    private int startX;
    private int startY;
    private int size;
    private int centerX = 0;
    private int centerY = 0;
    private int draggedX = 0;
    private int draggedY = 0;
    private double max = 0;
    private double numberSize = -1;
    private List<ru.vsu.edu.shlyikov_d_g.utils.Point> pointsList = new ArrayList<>();

    private void setFigure(Figure figure) {
        this.figure = figure;
    }

    private void parseFigure() {
        switch (chooseMethodBox.getSelectedIndex()) {
            case 0 -> {
                if (!formulaInput.getText().isEmpty()) {
                    ExpressionCommander expressionCommander = new ExpressionCommander(formulaInput.getText());
                    setFigure(new Figure(expressionCommander, startX, startY, size, numberSize, max));
                }
            }
            case 1 -> {
                bezier();
                setFigure(new BezieCurve(pointsList, startX, startY));
            }
        }
    }

    private void refresh(){
        figure = null;
        bezierPoints = null;
        pointsList = new ArrayList<>();
    }

    private void bezier(){
//        List<Point> list = new ArrayList<>();
//        list.add(new Point(93, 239));
//        list.add(new Point(207, 150));
//        list.add(new Point(150, 350));
//        list.add(new Point(339, 249));
        bezierPoints = new BezieCurve(pointsList, startX, startY);
    }

    public static void goToLayout(JPanel jf, String name) {
        CardLayout cardLayout = (CardLayout) jf.getLayout();
        cardLayout.show(jf, name);
    }

    private int getW() {
        return 8;
    }

    private int getH() {
        return 8;
    }

    public FrameMain() {
        this.setTitle("task 3 kg");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myPanel painter = new myPanel(size);

        this.setSize(1920, 1080);
        painter.setSize(1650, 800);
        drawPanel.setSize(1650, 800);

        drawPanel.add(painter);

        cardsMethodsPanel.add(desmosPanel, "desmosPanel");
        cardsMethodsPanel.add(bezierCurvePanel, "bezierCurvePanel");

        this.setResizable(false);
        this.pack();

        drawButton.addActionListener(actionEvent -> {
            try {
                parseFigure();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        chooseMethodButton.addActionListener(actionEvent -> {
            switch (chooseMethodBox.getSelectedIndex()) {
                case 0 -> {
                    refresh();
                    goToLayout(cardsMethodsPanel, "desmosPanel");
                }
                case 1 -> {
                    refresh();
                    goToLayout(cardsMethodsPanel, "bezierCurvePanel");
                }
            }
        });
    }

    private void setSize(int size){
        this.size = size;
    }

    class myPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {
        int size;
        public static DrawModule drawModule;
        public static Graphics2D gr;

        myPanel(int size) {
            this.size = size;
            this.setBorder(BorderFactory.createLineBorder(Color.red));
            this.setBackground(Color.white);
            this.addMouseWheelListener(this);
            this.addMouseMotionListener(this);
            this.addMouseListener(this);
        }

        public Dimension getPreferredSize() {
            return new Dimension(390, 190);
        }

        public void doDrawing(Graphics g) {
            double y;
            double x;

            gr = (Graphics2D) g;

            drawModule = new DrawModule(gr, getHeight(), 0);

            gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            size = getHeight() / 10;
            FrameMain.this.setSize(size);

            // Делаем белый фон
            Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
            gr.setPaint(Color.white);
            gr.fill(rect);
            gr.draw(rect);

            // Рисуем сетку
            gr.setStroke(new BasicStroke((float) 0.4));

            double temp;
            int k;
            max = 0;
            setFont(new Font("Arial", Font.BOLD, 12));
            max = getWidth() + Math.abs(centerX) * (centerX > 0 ? 2 : 0);
            for (y = centerY + getHeight() / 2.0, x = centerX + getWidth() / 2.0, temp = 0, k = 1;
                 y <= getHeight() + Math.abs(centerY) * (centerY > 0 ? 2 : 0) || x <= getWidth() + Math.abs(centerX) * (centerX > 0 ? 2 : 0);
                 y += size, x += size, temp += numberSize, k += 2) {
                gr.setPaint(Color.LIGHT_GRAY);
                gr.draw(new Line2D.Double(0, y, this.getWidth(), y));
                gr.draw(new Line2D.Double(x, 0, x, this.getHeight()));

                gr.draw(new Line2D.Double(0, y - k * size, this.getWidth(), y - k * size));
                gr.draw(new Line2D.Double(x - k * size, 0, x - k * size, this.getHeight()));

                gr.setPaint(Color.BLACK);
                //x
                gr.drawString(String.valueOf(temp), (int) (x + size * 0.1), size / 6);
                gr.drawString(String.valueOf(temp - k * numberSize), (int) (x - k * size + size * 0.1), size / 6);

                //y
                gr.drawString(String.valueOf(temp), size / 16, (int) (y + size * 0.2));
                gr.drawString(String.valueOf(temp - k * numberSize), size / 16, (int) (y - k * size + size * 0.2));

                if (draggedX == 0 && draggedY == 0 && temp == 0) {
                    startX = (int) x;
                    startY = (int) y;
                }
            }

            // Рисуем оси
            gr.setPaint(Color.GREEN);
            gr.setStroke(new BasicStroke((float) 2));
            gr.draw(new Line2D.Double(startX, 0, startX, this.getHeight()));
            gr.draw(new Line2D.Double(0, startY, this.getWidth(), startY));

            int w, h;
            w = getW();
            h = getH();
            gr.setPaint(Color.RED);
            gr.fillOval(startX - w / 2, startY - h / 2, w, h);

            int clickedX = -10;
            int clickedY = -10;
            if (startX != clickedX && startY != clickedY) {
                gr.setPaint(Color.BLUE);
                gr.fillOval(clickedX, clickedY, w, h);
            }

            gr.setPaint(Color.BLACK);
            gr.setStroke(new BasicStroke((float) 1));
            if (figure != null){
                figure.draw(drawModule);
                }
            if (bezierPoints != null){
                bezierPoints.drawBezier(drawModule);
            }
//                System.out.println("DONE!");
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            System.out.println(figure);
//            try {
//                System.out.println(size);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
            if (e.getWheelRotation()<0){
                minusNumSize();
            }
            else{
//                System.out.println("DOWN");
                plusNumSize();
            }
            if (figure != null) {
                parseFigure();
            }
//            System.out.println(numberSize);
        }

        private void plusNumSize(){
            if (numberSize > 1){
                numberSize++;
            }
            else{
                numberSize*=2;
            }
        }

        private void minusNumSize() {
            if (numberSize > 1) {
                numberSize--;
            } else {
                numberSize /= 2;
            }
        }

            @Override
            public void mouseDragged(MouseEvent me) {
                // сохранить координаты
//                int mouseX = me.getX();
//                int mouseY = me.getY();
//                System.out.println("Dragging mouse at " + mouseX + ", " + mouseY);
                // Перетаскивание курсора мыши с точку с указанными координатами
                if (me.getModifiersEx() == InputEvent.BUTTON2_DOWN_MASK) {
                    int dx = me.getX() - draggedX;
                    int dy = me.getY() - draggedY;
                    if (Math.abs(dx) < 50 && Math.abs(dy) < 50) {
                        centerX += dx;
                        centerY += dy;
                        startX += dx;
                        startY += dy;
                    }
//                centerX -= (startX - draggedX + startX - me.getX())/50;
//                centerY -= (startY - draggedY + startY - me.getY())/50;
//                startX -= (startX - draggedX + startX - me.getX())/50;
//                startY -= (startY - draggedY + startY - me.getY())/50;
                    draggedX = me.getX();
                    draggedY = me.getY();
                    parseFigure();
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent me) {
            }

        public boolean shouldHandleHere(MouseWheelEvent e) {
            return (e.getModifiersEx()) != 0;
        }

            @Override
            public void paintComponent (Graphics g){
                super.paintComponent(g);
                doDrawing(g);
            }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (chooseMethodBox.getSelectedIndex() == 1) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    pointsList.add(new ru.vsu.edu.shlyikov_d_g.utils.Point(e.getX() - startX, e.getY() - startY));
//                    System.out.printf("(%d,%d)\n", e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    for (int i = 0; i < pointsList.size(); i++) {
                        Point p = pointsList.get(i);
                        if (Math.abs(p.getX() + startX - e.getX()) <= 20 && Math.abs(p.getY() + startY - e.getY()) <= 20) {
                            pointsList.remove(i);
                        }
                    }
                }
                parseFigure();
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    }

