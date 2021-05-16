import java.awt.EventQueue;
import javax.swing.JFrame;

public class AK_SnakeGame extends JFrame {

public AK_SnakeGame() {

initUI ();
}

private void initUI() {

add(new AK_GameBoard());

setResizable(true);
pack();

setTitle("My Snake Game");
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

public static void main (String[] args) {
System.out.println("GAME STARTED");
EventQueue.invokeLater(() -> {
JFrame ex = new AK_SnakeGame();
ex.setVisible(true);
});
}
}

