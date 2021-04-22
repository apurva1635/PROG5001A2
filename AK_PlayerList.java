/**
 * Write a description of class AK_Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

public class AK_PlayerList
{
    // list of player objects
    private ArrayList<AK_Player> playerList;
 
    /**
     * Constructor for objects of class AK_Player
     */
    public AK_PlayerList() {
        // initialise the player list
        playerList = new ArrayList<>();
    }

    /**
     * Method to add a Player
     * @params name, password
     * @return none
     */
    public void add(String name, String password) {
        AK_Player player = new AK_Player(name, password);
        playerList.add(player);
    }

    /**
     * Method to sort PlayerList
     * @params direction (A - Acsending order, D - Descending order)
     * @return none
     */
    public void sort(char direction) {
        //modify this method to makes it works as expected
        if (direction == 'A') {
            //sort the list in ascending order
            Collections.sort(playerList);
        } else if (direction == 'D')        
        {
            //sort the list in descending order
            Collections.sort(playerList, Collections.reverseOrder());
        }
    }

    /**
     * Method to match a player in the list with a given name and password
     * @param name, password
     * @return true if matched and false if not
     */
    public boolean matchPlayer(String name, String password) {
        boolean match;
        match = false;
        
        Iterator<AK_Player> iter = playerList.iterator();
        while (iter.hasNext()) {
            AK_Player player = iter.next();
            if ((player.name.compareTo(name)==0) && (player.password.compareTo(password)==0)) {
               match = true;
               break;
            }
        }        
        
        return match;
    }
    
    /**
     * Method to convert the PlayerList to a string
     * @params none
     * @return a string that represents all players in the list
     */
    @Override
    public String toString() {
        //modify this method to makes it works as expected
        String s = "";
        /*
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
                s = s + player.toString() + "\n";
        }
        */
        Iterator<AK_Player> iter = playerList.iterator();
        while (iter.hasNext()) {
            AK_Player player = iter.next();
            s = s + player.toString() + "\n";
        }
        return s;
    }
    
    /**
     * This private class for storing player's details 
     */
    class AK_Player implements Comparable<AK_Player> {
        public String name;
        public String password;
        
        public AK_Player(String name, String password) {
            this.name = name;
            this.password = password;
        }
        
        @Override
        public String toString() {
            return name + "[pass=" + password + "]";
        }
 
        @Override
        public int compareTo(AK_Player p) {
            return this.name.compareTo(p.name);
        }
    }
}
