import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AK_SnakeGame extends JFrame {

public AK_SnakeGame(String playerName) {

initUI (playerName);
}

private void initUI(String playerName) {

add(new AK_GameBoard(playerName));

setResizable(true);
pack();

setTitle("The Snake Game (C) Apurva_Kedar");
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

public static void main (String[] args) {
    //call login form
    //if it returns true(username and pas is correct) run the following code
     
System.out.println("GAME STARTED");
SwingUtilities.invokeLater(new Runnable(){
    public void run(){
 JFrame ex = new AK_LoginForm();  
 ex.setVisible(true);
}});
//EventQueue.invokeLater(() -> {
//JFrame ex = new AK_SnakeGame();
//ex.setVisible(true);
//});
}
}

