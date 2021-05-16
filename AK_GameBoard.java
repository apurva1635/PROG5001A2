import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;



public class AK_GameBoard extends JPanel implements ActionListener {    
    int xcells = 90;    
    int ycells = 90;
    int szcell = 10; 
   private final int PIXEL_SIZE = 10;
   private final int PIXELS = 900;
   private final int POSITION = 29;
   private final int TIME_DELAY = 140;

   


   private Image body_part;
   private Image prey;
   private Image snake_head;
   private JToolBar toolBar;
  
   private PointArray pointArray = new PointArray();
   private final List<Point> pointArrayList = pointArray.getPointArrayList();
   private int pixels;
   private int x_prey;
   private int y_prey;

   private boolean leftDir = false;
   private boolean rightDir = true;
   private boolean upDir = false;
   private boolean downDir = false;
   private boolean isGameOn = true;
   private boolean isSpacePressed = true;
   private Timer timer;
   private JLabel currPlayerScorelabel = new JLabel("CURRENT PLAYER SCORE:");

  
    /**
    *This is a default constructor to create game board with background 
    *colour as black and dimensions as 300px * 300px
    */
    public AK_GameBoard() {
        drawBoard(); 
        
    }
  private void drawBoard() {

//pointArray.add(new Point(-100,-100));
addKeyListener(new UserInputAdapter());
setBackground(Color.black);
setFocusable(true);
String highScore = getHighScore();
setPreferredSize(new Dimension(xcells * szcell, ycells * szcell));
setLayout(new BorderLayout());

toolBar = new JToolBar("");
//toolBar.setRollover(true);
toolBar.setOrientation(SwingConstants.VERTICAL);
toolBar.setBackground(Color.white);
JPanel p = new JPanel();
p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
JLabel label = new JLabel("TOP PLAYER SCORE:" + highScore);
label.setBorder(border);
p.add(label);

currPlayerScorelabel = new JLabel("CURRENT PLAYER SCORE");
currPlayerScorelabel.setBorder(border);
p.add(currPlayerScorelabel);
p.setBackground(Color.white);
JButton quitBtn = new JButton("Quit");
BufferedImage myPicture = null;
try {
myPicture = ImageIO.read(new File("snake_head.png"));
} catch (Exception e) {
   e.printStackTrace();
}
JLabel picLabel = new JLabel(new ImageIcon(myPicture));
p.add(picLabel);
label = new JLabel();
label.setText("<html>PROG5001: 2021 <br/>Apurva Kedar</html>");
label.setBorder(border);
p.add(label);
quitBtn.setBackground(Color.blue);
quitBtn.addActionListener(new QuitListener());
p.add(quitBtn);
toolBar.add(p);
add(toolBar, BorderLayout.EAST);
showIcons();
startGame();
}

private void showIcons() {

ImageIcon bpartImage = new ImageIcon("body_part.png");
body_part = bpartImage.getImage();
body_part = body_part.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
ImageIcon preyImage = new ImageIcon("prey.png");
prey = preyImage.getImage();
prey = prey.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
ImageIcon headImage = new ImageIcon("snake_head.png");
snake_head = headImage.getImage();
snake_head = snake_head.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
}

private void startGame() {

pixels = 3;

for (int i = 0; i<pixels; i++) {

pointArrayList.add(new Point(50 - i *10, 50));
}

showPrey();

timer = new Timer(TIME_DELAY, this);
timer.start();
}


@Override
public void paintComponent(Graphics g) {
super.paintComponent(g);

doDrawing(g);
}

private void doDrawing(Graphics g) {

if (isGameOn) {

g.drawImage(prey, x_prey, y_prey, this);
Point tempPoint = null;
int tempPointX;
int tempPointY;
for (int i = 0; i < pixels; i++) {
tempPoint = pointArrayList.get(i);
tempPointX = new Double(tempPoint.getX()).intValue();
tempPointY = new Double(tempPoint.getY()).intValue();
if (i == 0) {
g.drawImage(snake_head, tempPointX, tempPointY, this);
} else {
g.drawImage(body_part, tempPointX, tempPointY, this);
}
}

Toolkit.getDefaultToolkit().sync();

} else {

gameOver(g);
}
}

private void isPreyHit() {
Point tempPoint = pointArrayList.get(0);
int tempPointX = new Double(tempPoint.getX()).intValue();
int tempPointY = new Double(tempPoint.getY()).intValue();
if ((tempPointX == x_prey) &&(tempPointY == y_prey)) {
   pixels++;
pointArrayList.add(new Point());
showPrey();
}
}

private void showPrey() {

int r = (int) (Math.random() * POSITION);
x_prey = ((r * PIXEL_SIZE));

r = (int) (Math.random() * POSITION);
y_prey = ((r * PIXEL_SIZE));
}

private void gameOver(Graphics g) {

String msg = "Game Over";
Font small = new Font("Calibri", Font.BOLD, 32);
FontMetrics metr = getFontMetrics(small);

g.setColor(Color.white);
g.setFont(small);
g.drawString(msg, (xcells * szcell - metr.stringWidth(msg)) / 2, ycells * szcell / 2);
System.out.println(pixels);
writeScoreToFile();
}

private void writeScoreToFile() {
try{
File myObj = new File("player_score.txt");
if (!myObj.exists()) {
//System.out.println("File created: " + myObj.getName());
myObj.createNewFile();
}
FileWriter myWriter = new FileWriter("player_score.txt", true);
myWriter.write("Player Name" + "," + (pixels - 3) + "\n");
myWriter.close();
System.out.println("An error occurred.");
}catch(Exception e){
e.printStackTrace();
}
}
private void moveSnake() {

 System.out.println("-------1---------pointArrayList:"+pointArrayList);
Point tempPoint = null;

for (int i = pixels; i > 0; i--) {
if ((i - 2) != -1) {
pointArrayList.set(i - 1, pointArrayList.get(i -2));
}
}

tempPoint = pointArrayList.get(0);
int tempPointX = new Double(tempPoint.getX()).intValue();
int tempPointY = new Double(tempPoint.getY()).intValue();

if (leftDir) {
tempPointX -= PIXEL_SIZE;
}

if (rightDir) {
tempPointX += PIXEL_SIZE;
}

if (upDir) {
tempPointY -= PIXEL_SIZE;
}

if (downDir) {
tempPointY += PIXEL_SIZE;
//System.out.println("--------2---------tempPointY:"+tempPointY);
//System.out.println("--------2--------tempPointX:"+tempPointX);
}
pointArrayList.set(0, new Point(tempPointX, tempPointY));
//System.out.println("***********************************");
System.out.println("-------3---------pointArrayList:"+pointArrayList);
//System.out.println("***********************************");
}

private void checkCollision() {

Point tempPointFirst = pointArrayList.get(0);
Point tempPoint = null;
int tempPointX;
int tempPointY;
int tempPointXFirst = new Double(tempPointFirst.getX()).intValue();
int tempPointYFirst = new Double(tempPointFirst.getY()).intValue();
for (int i = pixels; i > 0; i--) {
tempPoint = pointArrayList.get(i-1);
tempPointX = new Double(tempPoint.getX()).intValue();
tempPointY = new Double(tempPoint.getY()).intValue();
if ((i > 4) && (tempPointXFirst == tempPointX) && (tempPointYFirst == tempPointY)) {
isGameOn = false;
}


if (tempPointYFirst >= ycells * szcell) {
   isGameOn = false;
}

if (tempPointYFirst < 0) {
   isGameOn = false;
}

if (tempPointXFirst >= ycells * szcell) {
   isGameOn = false;
}

if (tempPointXFirst < 0) {
   isGameOn = false;

}

if (isGameOn) {
    timer.stop();
}
}

}
@Override
public void actionPerformed(ActionEvent e) {

//System.out.println(""-----in actionPerformed : :"+isSpacePressed);
currPlayerScorelabel.setText("Current Player Score:"+ (pixels - 3));
if (!isSpacePressed && isGameOn) {
isPreyHit();
checkCollision();
moveSnake();

} else {

}
repaint();

}

private String getHighScore() {
BufferedReader reader = null;
String tempValue = "0";
try {
String filePath = "player_score.txt";
HashMap<String, String> map = new HashMap<String, String>();
List keyList = new ArrayList();
String line;
reader = new BufferedReader (new FileReader(filePath));

while ((line = reader.readLine()) !=null) {
String[] parts = line.split(",", 2);
if (parts.length >= 2) {
String key = parts[0];
String value = parts[1];
if (keyList.contains(key)) {
if (new Integer((String) map.get(key)) < new Integer(value)) {
map.put(key, value);
}
}else{
  map.put(key, value);
  keyList.add(key);
}
}
}
for (String key : map.keySet()) {
if (new Integer(tempValue) < new Integer((String) map.get(key))){
    tempValue = (String)map.get(key);
}
}
reader.close();
} catch (Exception e) {
  e.printStackTrace();
}
return tempValue;
}

private class QuitListener implements ActionListener {
@Override
public void actionPerformed(ActionEvent e) {
writeScoreToFile();
System.exit(0);
}
}

private class UserInputAdapter extends KeyAdapter {

@Override
public void keyPressed(KeyEvent e) {

int key = e.getKeyCode();
System.out.println("key = "+key);
System.out.println("KeyEvent.VK_LEFT = "+KeyEvent.VK_LEFT);
System.out.println("KeyEvent.VK_RIGHT = "+KeyEvent.VK_RIGHT);
System.out.println("KeyEvent.VK_DOWN = "+KeyEvent.VK_DOWN);
System.out.println("KeyEvent.VK_UP = "+KeyEvent.VK_UP);
System.out.println("KeyEvent.VK_A = "+KeyEvent.VK_A);
System.out.println("KeyEvent.VK_D = "+KeyEvent.VK_D);
System.out.println("KeyEvent.VK_S = "+KeyEvent.VK_S);
System.out.println("KeyEvent.VK_W = "+KeyEvent.VK_W);
if ((key == KeyEvent.VK_LEFT) && (!rightDir)) {
leftDir = true;
upDir = false;
downDir = false;
}

if ((key == KeyEvent.VK_RIGHT) && (!leftDir)) {
rightDir = true;
upDir = false;
downDir = false;
}

if ((key == KeyEvent.VK_DOWN) && (!downDir)) {
upDir = true;
rightDir = false;
leftDir = false;
}

if ((key == KeyEvent.VK_UP) && (!upDir)) {
downDir = true;
rightDir = false;
leftDir = false;
}
if (key == KeyEvent.VK_SPACE) {
//System.out.println("------------isSpacePressed:"+isSpacePressed);
if (isSpacePressed) {
   isSpacePressed = false;
} else {
   isSpacePressed = true;
}
// System.out.println("-----------isSpacePressed:"+isSpacePressed):
}
}
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
