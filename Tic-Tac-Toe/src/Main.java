import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Main {
    private static final int num_of_panels = 9;
    private static int move = 0;
    protected static final Color[] panel_colors = {new Color(185,156,40), new Color(50, 168, 82), new Color(42, 169, 176),
            new Color(68, 73, 145), new Color(150, 54, 125), new Color(181, 85, 91),
            new Color(156, 83, 6), new Color(192, 204, 27), new Color(86, 110, 77),
            new Color(33, 222, 140), new Color(0, 181, 209), new Color(99, 60, 207)};

    protected static PanelListener listener = new PanelListener();
    protected static JLayeredPane gameBoard = new JLayeredPane();
    protected static ArrayList<JPanel> panels = new ArrayList<JPanel>(num_of_panels);
    protected static JFrame frame = new JFrame();
    protected static JLayeredPane layers = new JLayeredPane();
    protected static JPanel jPanel = new JPanel();

    public static void main(String[] args) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setSize(1080,720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        layers.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        gameBoard.setBounds(frame.getWidth()/2 - 150, frame.getHeight()/2 - 150, 300, 300);
        gameBoard.setBackground(Color.green);
        gameBoard.setLayout(null);

        int xBox = 0;
        int yBox = 0;

        for (int i = 0; i < num_of_panels; i++) {
            panels.add(i, new JPanel());
            panels.get(i).setBackground(panel_colors[(int)(Math.random()*12)]);
            int size = 100;
            panels.get(i).setBounds(xBox, yBox, size, size);
            panels.get(i).setLayout(null);
            panels.get(i).addMouseListener(listener);
            if (xBox == size*2) {
                yBox+=size;
                xBox-=size*2;
            } else {
                xBox+=size;
            }
            gameBoard.add(panels.get(i));
        }
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameBoard.setLocation(frame.getSize().width/2 - 150, frame.getSize().height/2 - 150);
                if (frame.getSize().width < gameBoard.getWidth() + 60) {
                    frame.setSize(gameBoard.getWidth() + 60, frame.getSize().height);
                }
                if (frame.getSize().height < gameBoard.getHeight() + 110) {
                    frame.setSize(frame.getSize().width, gameBoard.getHeight() + 110);
                }
            }
        });

        layers.add(gameBoard, JLayeredPane.DEFAULT_LAYER);
        frame.add(layers);
        frame.setVisible(true);
    }

    private static class PanelListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent event) {
            Object source = event.getSource();
            if (source instanceof JPanel panelPressed) {
                frame.setEnabled(false);

                int delayInMillis = 3000;
                Timer timer = new Timer(delayInMillis, e -> {
                    frame.setEnabled(true);
                });
                timer.setRepeats(false);
                timer.start();
                Chalk chalk;
                Point inner = panelPressed.getLocation();
                Point outer = gameBoard.getLocation();
                if (move % 2 == 0) {
                    Cross cross = new Cross(panelPressed.getX(), panelPressed.getY());
                    chalk = new Chalk(inner.x + outer.x, inner.y + outer.y, true);
                    layers.add(chalk, JLayeredPane.PALETTE_LAYER);
                    gameBoard.remove(panelPressed);
                    gameBoard.add(cross);
                    gameBoard.revalidate();
                    gameBoard.getComponentAt(panelPressed.getX(), panelPressed.getY()).repaint();
                    panels.get(panels.indexOf(panelPressed)).remove(panelPressed);
                    panels.set(panels.indexOf(panelPressed), cross);
                    move++;
                    panelPressed = new Cross(0,0);
                } else {
                    Nought nought = new Nought(panelPressed.getX(), panelPressed.getY());
                    chalk = new Chalk(inner.x + outer.x, inner.y + outer.y, false);
                    layers.add(chalk, JLayeredPane.PALETTE_LAYER);
                    gameBoard.remove(panelPressed);
                    gameBoard.add(nought);
                    gameBoard.revalidate();
                    gameBoard.getComponentAt(panelPressed.getX(), panelPressed.getY()).repaint();
                    panels.get(panels.indexOf(panelPressed)).remove(panelPressed);
                    panels.set(panels.indexOf(panelPressed), nought);
                    move++;
                    panelPressed = new Nought(0,0);
                }

                if (move < num_of_panels) {
                    for (int i = 0; i < 3; i++) {
                        if (((panels.get(i*3).getClass().equals(panels.get(i*3+1).getClass()) && panels.get(i*3+1).getClass().equals(panels.get(i*3+2).getClass()) && !panels.get(i*3).getClass().equals(jPanel.getClass()))
                                || (panels.get(i).getClass().equals(panels.get(i+3).getClass()) && panels.get(i+3).getClass().equals(panels.get(i+6).getClass()) && !panels.get(i).getClass().equals(jPanel.getClass()))
                                || (panels.get(Math.abs(i-2)).getClass().equals(panels.get(4).getClass()) && panels.get(4).getClass().equals(panels.get(i+6).getClass()) && !panels.get(Math.abs(i-2)).getClass().equals(jPanel.getClass()) && i != 1))) {

                            JLabel text = new JLabel();
                            text.setText(panelPressed.getClass().getSimpleName() + "'s won!!!");
                            text.setSize(100,100);

                            JFrame endGame = new JFrame();
                            endGame.setSize(400, 300);
                            endGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            endGame.getContentPane().setBackground((panel_colors[(int)(Math.random()*12)]));
                            endGame.add(text);
                            endGame.setLocationRelativeTo(null);
                            endGame.setLayout(new GridBagLayout());
                            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            endGame.setVisible(true);
                        }
                    }
                }
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