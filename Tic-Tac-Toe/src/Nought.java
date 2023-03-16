import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nought extends JPanel implements ActionListener {

    Timer timer;
    private int arcStart= 90, arcEnd = 5;

    Nought(int x, int y) {
        this.setBounds(x, y, 100, 100);
        this.setBackground(Main.panel_colors[(int)(Math.random()*12)]);
        this.setLayout(null);
        timer = new Timer(32, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paint(g2D);

        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(10));
        g2D.drawArc(12, 12, 75, 75, 100, arcEnd);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (arcEnd < 360) {
            arcEnd+=5;
            repaint();
        } else {
            timer.stop();
        }
    }
}
