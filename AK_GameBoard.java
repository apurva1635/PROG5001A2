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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;

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



public class AK_GameBoard extends JPanel implements ActionListener,MouseListener {    
    int xcells = 90;    
    int ycells = 90;
    int szcell = 10; 
   private final int PIXEL_SIZE = 10;
   private final int PIXELS = 900;
   private final int POSITION = 29;
   private final int TIME_DELAY = 100;

   


   private Image body_part;
   private Image prey;
   private Image snake_head;
   private JToolBar toolBar;
  
   private PointArray pointArray = new PointArray();
   private final List<Point> pointArrayList = pointArray.getPointArrayList();
   private final List<Point> pointArrayList2 = pointArray.getPointArrayList();
   private int pixels;
   private int pixels2;
   private int x_prey;
   private int y_prey;

   private boolean leftDir = false;
   private boolean rightDir = true;
   private boolean upDir = false;
   private boolean downDir = false;
   private boolean leftDir2 = false;
   private boolean rightDir2 = true;
   private boolean upDir2 = false;
   private boolean downDir2 = false;
   private boolean isGameOn = true;
   private boolean isSpacePressed = true;
   private Timer timer;
   private JLabel currPlayerScorelabel = new JLabel("CURRENT PLAYER SCORE:");
   private String playerName = "";
   private List<Point> preyList = new ArrayList<Point>();
   
  
    /**
    *This is a default constructor to create game board with background 
    *colour as black and dimensions as 300px * 300px
    */
    public AK_GameBoard(String playerName) {
        this.playerName = playerName;
        drawBoard(); 
        addMouseListener(this);
    }
    public void mouseClicked(MouseEvent e){
     if (isSpacePressed) {
     isSpacePressed = false;
     } else {
     isSpacePressed = true;
    }
    }
      public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
          public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
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
myPicture = ImageIO.read(new File("snake_game.png"));
//myPicture = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
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

static final Random rand = new Random();
void addPrey(){
int r = (int) (Math.random() * POSITION);
int x = ((r * PIXEL_SIZE));

r = (int) (Math.random() * POSITION);
int y = ((r * PIXEL_SIZE));
if(preyList.size()<2){
    while(true){
    Point p = new Point(x,y);
    if(pointArrayList.contains(p)||pointArrayList2.contains(p) || preyList.contains(p)){
    continue;
    }
    preyList.add(p);
    break;
    }
}
System.out.println(preyList.size());
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
pixels2 = 3;

for (int i = 0; i<pixels2; i++) {

pointArrayList2.add(new Point(80 - i *10, 80));
}

//showPrey();
do 
    addPrey();
while(preyList.isEmpty());
timer = new Timer(TIME_DELAY, this);
timer.start();
}


@Override
public void paintComponent(Graphics g) {
super.paintComponent(g);

doDrawing(g);
doDrawing2(g);
}

private void doDrawing(Graphics g) {

if (isGameOn) {

//g.drawImage(prey, x_prey, y_prey, this);
for(Point p:preyList){
    int x = new Double(p.getX()).intValue();
    int y = new Double(p.getY()).intValue();
    g.drawImage(prey,x,y,this);
}
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

private void doDrawing2(Graphics g) {

if (isGameOn) {

//g.drawImage(prey, x_prey, y_prey, this);
for(Point p:preyList){
    int x = new Double(p.getX()).intValue();
    int y = new Double(p.getY()).intValue();
    g.drawImage(prey,x,y,this);
}
Point tempPoint = null;
int tempPointX;
int tempPointY;
for (int i = 0; i < pixels2; i++) {
tempPoint = pointArrayList2.get(i);
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
for(int i = 0; i<preyList.size(); i++){
    Point p = preyList.get(i);
    int x = new Double(p.getX()).intValue();
    int y = new Double(p.getY()).intValue();
    if ((tempPointX == x) &&(tempPointY == y)) {
        pixels++;
        pointArrayList.add(new Point());
        //showPrey();
        preyList.remove(new Point(x,y));
    }
}

}
private void isPreyHit2() {
Point tempPoint = pointArrayList2.get(0);
int tempPointX = new Double(tempPoint.getX()).intValue();
int tempPointY = new Double(tempPoint.getY()).intValue();
for(int i = 0; i<preyList.size(); i++){
    Point p = preyList.get(i);
    int x = new Double(p.getX()).intValue();
    int y = new Double(p.getY()).intValue();
    if ((tempPointX == x) &&(tempPointY == y)) {
        pixels2++;
        pointArrayList2.add(new Point());
        //showPrey();
        preyList.remove(new Point(x,y));
    }
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
myWriter.write(playerName + "," + (pixels - 3) + "\n");
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

private void moveSnake2() {

 System.out.println("-------1---------pointArrayList:"+pointArrayList);
Point tempPoint = null;

for (int i = pixels2; i > 0; i--) {
if ((i - 2) != -1) {
pointArrayList2.set(i - 1, pointArrayList2.get(i -2));
}
}

tempPoint = pointArrayList2.get(0);
int tempPointX = new Double(tempPoint.getX()).intValue();
int tempPointY = new Double(tempPoint.getY()).intValue();

if (leftDir2) {
tempPointX -= PIXEL_SIZE;
}

if (rightDir2) {
tempPointX += PIXEL_SIZE;
}

if (upDir2) {
tempPointY -= PIXEL_SIZE;
}

if (downDir2) {
tempPointY += PIXEL_SIZE;
//System.out.println("--------2---------tempPointY:"+tempPointY);
//System.out.println("--------2--------tempPointX:"+tempPointX);
}
pointArrayList2.set(0, new Point(tempPointX, tempPointY));
//System.out.println("***********************************");
System.out.println("-------3---------pointArrayList:"+pointArrayList2);
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

if (tempPointXFirst >= xcells * szcell) {
   isGameOn = false;
}

if (tempPointXFirst < 0) {
   isGameOn = false;

}

if (!isGameOn) {
    timer.stop();
}
}

}

private void checkCollision2() {

Point tempPointFirst = pointArrayList2.get(0);
Point tempPoint = null;
int tempPointX;
int tempPointY;
int tempPointXFirst = new Double(tempPointFirst.getX()).intValue();
int tempPointYFirst = new Double(tempPointFirst.getY()).intValue();
for (int i = pixels2; i > 0; i--) {
tempPoint = pointArrayList2.get(i-1);
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

if (tempPointXFirst >= xcells * szcell) {
   isGameOn = false;
}

if (tempPointXFirst < 0) {
   isGameOn = false;

}

if (!isGameOn) {
    timer.stop();
}
}

}

@Override
public void actionPerformed(ActionEvent e) {

System.out.println("-----in actionPerformed : :"+isSpacePressed);
currPlayerScorelabel.setText("Current Player Score:"+ (pixels - 3));
if (!isSpacePressed && isGameOn) {
    //System.out.println("0-----in actionPerformed : :"+isSpacePressed);
isPreyHit();
isPreyHit2();
checkCollision();
checkCollision2();
moveSnake();
moveSnake2();
addPrey();
//System.out.println("11-----in actionPerformed : :"+isSpacePressed);
} else {

}
//System.out.println("1-----in actionPerformed : :"+isSpacePressed);
try{
    //revalidate();
repaint();
}catch(Exception ex){
    //System.out.println("2-----EXCEPTION : :"+ex);
ex.printStackTrace();
}
//System.out.println("2-----in actionPerformed : :"+isSpacePressed);
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

if ((key == KeyEvent.VK_UP) && (!downDir)) {
upDir = true;
rightDir = false;
leftDir = false;
}

if ((key == KeyEvent.VK_DOWN) && (!upDir)) {
downDir = true;
rightDir = false;
leftDir = false;
}

if ((key == KeyEvent.VK_A) && (!rightDir2)) {
leftDir2 = true;
upDir2 = false;
downDir2 = false;
}

if ((key == KeyEvent.VK_D) && (!leftDir2)) {
rightDir2 = true;
upDir2 = false;
downDir2 = false;
}

if ((key == KeyEvent.VK_W) && (!downDir2)) {
upDir2 = true;
rightDir2 = false;
leftDir2 = false;
}

if ((key == KeyEvent.VK_Z) && (!upDir2)) {
downDir2 = true;
rightDir2 = false;
leftDir2 = false;
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
