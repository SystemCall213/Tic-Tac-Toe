import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Cross extends JPanel implements ActionListener{

    Timer timer;
    boolean firstPainted = false, stop = false;
    private int x1 = 20, y1 = 20, x2 = 80, y2 = 20;
    Cross(int x, int y) {
        this.setBounds(x, y, 100, 100);
        this.setBackground(Main.panel_colors[(int)(Math.random()*12)]);
        this.setLayout(null);
        timer = new Timer(30, this);
        timer.start();
    }



    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paint(g2D);

        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(15));
        g2D.drawLine(20, 20, x1 + 2, y1 + 2);
        if (y2 > 20) g2D.drawLine(80, 20, x2, y2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x1 < 80 && !firstPainted) {
            x1 += 2;
            y1 += 2;
            repaint();
            if (stop) {
                timer.setDelay(30);
                timer.start();
                stop = false;
            }
            if (x1 == 78) {
                stop = true;
            }
        } else if (x2 > 20 && firstPainted) {
            x2 -= 2;
            y2 += 2;
            repaint();
        } else if (x1 > 77) {
            firstPainted = true;
        } else if (y2 > 77) {
            timer.stop();
        }
        if (stop) {
            timer.setDelay(1100);
            timer.start();
        }

    }
}