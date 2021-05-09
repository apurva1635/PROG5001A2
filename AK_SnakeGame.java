import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AK_SnakeGame extends JFrame {

    public AK_SnakeGame(String gameTille) {        
        add(new AK_GameBoard());
        setTitle(gameTille);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();        
    }
    
    public static void main(String[] args) {        
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AK_LoginForm().setVisible(true);
            }
        });
    }
}
