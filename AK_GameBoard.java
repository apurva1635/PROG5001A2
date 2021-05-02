import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class AK_GameBoard extends JPanel {    
    int xcells = 30;    
    int ycells = 30;
    int szcell = 10; 
    /**
    *This is a default constructor to create game board with background 
    *colour as black and dimensions as 300px * 300px
    */
    public AK_GameBoard() {
        setPreferredSize(new Dimension(xcells * szcell, ycells * szcell));        
        setBackground(Color.black);
        setFocusable(true);        
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
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
