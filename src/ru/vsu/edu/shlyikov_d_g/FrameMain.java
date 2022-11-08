package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.figures.*;
import ru.vsu.edu.shlyikov_d_g.parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
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

    private myPanel painter;

    private Figure figure;

    private int startX;
    private int startY;
    private int w = 8;
    private int h = 8;
    private int size;
    private int clickedX = -10;
    private int clickedY = -10;
    private int centerX = 0;
    private int centerY = 0;
    private int draggedX = 0;
    private int draggedY = 0;
    private double max = 0;
    double numberSize = 1;

    private void setFigure(Figure figure) {
        this.figure = figure;
    }

    private void parseFigure(){
        setFigure(new Figure(Parser.parsing(formulaInput.getText()), startX,startY, size, numberSize, max));
    }

    public static void goToLayout(JPanel jf, String name) {
        CardLayout cardLayout = (CardLayout) jf.getLayout();
        cardLayout.show(jf, name);
    }

    private int getW() {
        return this.w;
    }

    private int getH() {
        return this.h;
    }

    public FrameMain() {
        this.setTitle("task 3 kg");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painter = new myPanel(size);

        this.setSize(1920, 1080);
        painter.setSize(1650, 800);
        drawPanel.setSize(1650, 800);

        drawPanel.add(painter);

        this.setResizable(false);
        this.pack();

        drawButton.addActionListener(actionEvent -> {
            try {
                System.out.println(Arrays.toString(Parser.parsing(formulaInput.getText())));
                parseFigure();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setSize(int size){
        this.size = size;
    }

    class myPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {
        int size;

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
            Graphics2D gr;

            gr = (Graphics2D) g;

            DrawModule drawModule = new DrawModule(gr);

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


//            setFigure(new Tan(startX, startY, size));

            double temp;
            int k;
            max = 0;
            setFont(new Font("Arial", Font.BOLD, 12));
            for (y = centerY + getHeight()/2, x = centerX+getWidth()/2, temp = 0, k = 1;
                 y <= getHeight()*2 + Math.abs(centerY)*(y > 0 ? 2 : 1)|| x <= getWidth()*2 + Math.abs(centerX)*(x > 0 ? 2 : 1);
                 y += size, x += size, temp+=numberSize, k+=2) {
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
                    startX = (int)x;
                    startY = (int)y;
                }

                if (max < temp){
                    max = temp;
                }
            }
            System.out.println(max);

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

            if (startX != clickedX && startY != clickedY) {
                gr.setPaint(Color.BLUE);
                gr.fillOval(clickedX, clickedY, w, h);
            }

            gr.setPaint(Color.BLACK);
            gr.setStroke(new BasicStroke((float) 1));
            if (figure != null){
                for (int i = 0; i < figure.getCoords().getMatrix().size() - 1; i++) {
                    List<Double> list = figure.getCoords().getMatrix().get(i);
                    List<Double> list2 = figure.getCoords().getMatrix().get(i + 1);
                    int x0 = list.get(0).intValue();
                    int y0 = list.get(1).intValue();
                    int x1 = list2.get(0).intValue();
                    int y1 = list2.get(1).intValue();
//                    gr.drawOval(x0, y0, 1, 1);
                    drawModule.drawLine(x0,y0,x1,y1);
                }
            }
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            System.out.println(figure);
            System.out.println(size);
            try {
                Parser.parsing("ds");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (e.getWheelRotation()<0){
                System.out.println("UP");

                minusNumSize();
                if (figure != null) {
                    parseFigure();
                }
            }
            else{
                System.out.println("DOWN");

                plusNumSize();
                if (figure != null) {
                    parseFigure();
                }
            }
            System.out.println(numberSize);
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
                int mouseX = me.getX();
                int mouseY = me.getY();
                System.out.println("Dragging mouse at " + mouseX + ", " + mouseY);
                // Перетаскивание курсора мыши с точку с указанными координатами
                int dx = me.getX() - draggedX;
                int dy = me.getY() - draggedY;
                if (Math.abs(dx) < 100 && Math.abs(dy) < 100){
                    centerX += dx;
                    centerY += dy;
                    startX  +=  dx;
                    startY  += dy;
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
            System.out.printf("(%d,%d)\n",e.getX(), e.getY());
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

