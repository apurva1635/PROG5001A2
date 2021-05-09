import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class AK_GameBoard extends JPanel implements ActionListener {    
    int xcells = 30;    
    int ycells = 30;
    int szcell = 10; 
private final int B_WIDTH = 900;
private final int B_HEIGHT = 900;
private final int PIXEL_SIZE = 5;
private final int PIXELS = 100;
private final int POSITION = 30;

private final int xPix[] = new int[PIXELS];
private final int yPix[] = new int[PIXELS];
private int pixels;
private int x_prey;
private int y_prey;
private Image body_part;
private Image prey;
private Image snake_head;
private JToolBar toolBar;
  
    /**
    *This is a default constructor to create game board with background 
    *colour as black and dimensions as 300px * 300px
    */
    public AK_GameBoard() {
        drawBoard(); 
        
    }
    private void drawBoard() {

setLayout(new BorderLayout());
setBackground(Color.black);
setFocusable(true);
setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
toolBar = new JToolBar("");
toolBar.setOrientation(SwingConstants.VERTICAL);
toolBar.setBackground(Color.white);
JPanel p = new JPanel();
p.setLayout(new BoxLayout(p, BoxLayout. Y_AXIS));
Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
JLabel label = new JLabel("TOP PLAYER SCORE");
label.setBorder(border);
p.add(label);

label = new JLabel("CURRENT PLAYER SCORE:");
label.setBorder(border);
p.add(label);
p.setBackground(Color.white);
JButton quitBtn = new JButton("QUIT");
BufferedImage myPicture = null;
try{
myPicture = ImageIO.read(new File("snake_head.png"));
}catch (Exception e) {
    e.printStackTrace();
}
JLabel picLabel = new JLabel(new ImageIcon(myPicture));
p.add(picLabel);
label = new JLabel();
label.setText("<html>PROG5001: 2021 <br/>Apurva Kedar</html>");
label.setBorder(border);
p.add(label);

quitBtn.setBackground(Color.blue);
p.add(quitBtn);
toolBar.add(p);

add(toolBar, BorderLayout.EAST);
showIcons();
startGame();
}
private void showIcons() {
ImageIcon bpartImage = new ImageIcon("body_part.png");
body_part = bpartImage.getImage();

ImageIcon preyImage = new ImageIcon("prey.png");
prey = preyImage.getImage();

ImageIcon headImage = new ImageIcon("snake_head.png");
snake_head = headImage.getImage();
}

private void startGame() {

pixels = 3;

for (int initialPix = 0; initialPix < pixels; initialPix++) {
      xPix[initialPix] = 150 - initialPix * 10;
      yPix[initialPix] =150;
}

showApple();

}

@Override
public void paintComponent(Graphics g) {
super.paintComponent(g);
doDrawing(g);
}

private void doDrawing(Graphics g) {

g.drawImage(prey, x_prey, y_prey, this);

for (int pix = 0; pix < pixels; pix++) {
if (pix == 0) {
    g.drawImage(snake_head, xPix[pix], yPix[pix], this);
} else { 
         g.drawImage(body_part, xPix[pix], yPix[pix], this);
    }
}

Toolkit.getDefaultToolkit().sync();

}



private void checkprey() {

if ((xPix[0] == x_prey) && (yPix[0] == y_prey)) {
pixels++;
showApple();
}
}


private void showApple() {

int random = (int) (Math.random() * POSITION);
x_prey = ((random * PIXEL_SIZE));

random = (int) (Math.random() * POSITION);
y_prey = ((random * PIXEL_SIZE));
}

@Override
public void actionPerformed(ActionEvent e) {

checkprey();
repaint();
}

/**
    *This is a custom constructor to create game board with background 
    *colour and dimensions as entered by user.This constructor has 3 parameters 
    *x dimension, y dimension,background color.
    */
    public AK_GameBoard(int xwidth,int ywidth,Color color) {
        setPreferredSize(new Dimension(xwidth,ywidth));        
        setBackground(color);
        setFocusable(true);        
    }
    
    //This method is used to make initialisation such as loading images.
    public void initGame(){}
    //This method is used to display Prey image at random locations.
    public void showRandomPrey(){}
    /**
     * This method is used to check if snake hits the boundaries of the board.
     *
     * @return This returns true if snake hits the boundaries of the board.
     *
     */
    public boolean isGameOver(){
        return false;
    }
    /**
     * This method is used to check if snake head touches the Prey image.
     *
     * @return This returns true if snake head touches the Prey image.
     *
     */
    public boolean isPreyTouched(){
        return false;
    }
}
