import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chalk extends JPanel implements ActionListener {

    private int animaX = 0, animaY = 0, angle = 190;
    boolean isCross, isPainted = false;
    private Timer timer;

    public Chalk(int x, int y, boolean isCross) {
        this.setBounds(x, y, 300,300);
        this.setOpaque(false);
        this.setLayout(new OverlayLayout(this));
        this.isCross = isCross;
        timer = new Timer((isCross) ? 30 : 15, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paint(g2D);

        g2D.setPaint(Color.white);

        if (!isPainted && isCross) {
            g2D.setStroke(new BasicStroke(26));
            g2D.drawArc(animaX + 15, animaY + 15, 1, 1, 45, 180);
            g2D.setStroke(new BasicStroke(27));
            g2D.drawLine(animaX + 30, animaY + 30, animaX + 90,animaY + 90);
        } else if (!isPainted) {
            g2D.setStroke(new BasicStroke(26));
            int radius = 43; // set the radius of the circle
            int centerX = animaX + 50; // set the center x-coordinate of the circle
            int centerY = animaY + 50; // set the center y-coordinate of the circle
            double radians = Math.toRadians(angle);
            int x = (int) (centerX + radius * Math.sin(radians));
            int y = (int) (centerY + radius * Math.cos(radians));
            g2D.drawArc(x, y, 1, 1, 45, 180);
            g2D.setStroke(new BasicStroke(27));
            g2D.drawLine(x + 15, y + 15, x + 75,y + 75);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isCross && animaX < 75 && animaY == animaX) {
            animaX+=2;
            animaY+=2;
            repaint();
        } else if (isCross && animaY > -5 && animaX > 75) {
            timer.setDelay(10);
            timer.start();
            animaY-=2;
            repaint();
        } else if (isCross && animaX > 0 && animaY < 75) {
            timer.setDelay(30);
            timer.start();
            if (animaX == 46) {
                animaX--;
            }
            animaX-=2;
            animaY+=2;
            repaint();
        } else {
            if (isCross) {
                isPainted = true;
                repaint();
                timer.stop();
            }
        }
        if (!isCross && angle < 570) {
            angle+=2;
            repaint();
        } else {
            if (!isCross) {
                isPainted = true;
                repaint();
                timer.stop();
            }
        }
    }
}
