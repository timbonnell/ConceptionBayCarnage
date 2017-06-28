package ConceptionBayCarnage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/** The HighScoreHandler class is used is used to generate and maintain
 * the consistency of a five member leaderboard. 
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class HighScoreHandler implements Serializable{
    private ArrayList<HighScore> listedScores;
    
    
    /** creates a new HighScoreHandler object
     * @param autoGenerate - Whether to auto-generate a default leaderboard. 
     * Set to true it will generate five elements in the leader board
     */
    public HighScoreHandler(boolean autoGenerate){
        this.listedScores = new ArrayList<HighScore>();
        if (autoGenerate){
            this.listedScores.add(new HighScore(10000, "abc"));
            this.listedScores.add(new HighScore(9000, "def"));
            this.listedScores.add(new HighScore(8000, "ghi"));
            this.listedScores.add(new HighScore(7000, "jkl"));
            this.listedScores.add(new HighScore(6000, "mno"));
            Collections.sort(this.listedScores);
        }
    }

    
    /** Returns the entire ArrayList comprising the leaderboards
     * @return listedScores - ArrayList 
     */
    public ArrayList<HighScore> getListedScores() {
        return listedScores;
    }

    
    /** sets the value of listedScore
     * @param listedScores values of high scores
     */
    public void setListedScores(ArrayList<HighScore> listedScores) {
        this.listedScores = listedScores;
    }
    
    
    /** 
     * Returns whether a given integer value is greater than the score of any 
     * element in the leaderboard.
     * @param aScore int - player's score
     * @return isHighScore - boolean value representing whether parameter 'aScore' 
     * is greater than the value fo any member in the leaderboard
     */
    public boolean isHighScore(int aScore){
        boolean isHighScore = false;
        if (this.listedScores.size() < 5){ // Score list has too few values
            isHighScore = true;
        } else {
            // if greater than the smallest element in the list (Position 4)
            if (aScore > this.listedScores.get(4).getRecordedScore()){
                isHighScore = true;
            } //End if
        } //End else
        return isHighScore;
    } //End isHighScore
    
    
    /** inserts a new high score into high scores
     * @param aScore player's score
     * @param aName player's name
     */
    public void insertHighScore(int aScore, String aName){
        if (this.isHighScore(aScore) ){ // Double-checks that the value is actually a high score
            this.listedScores.remove(4);
            this.listedScores.add(new HighScore(aScore, aName));
            System.out.println("Pre-sort");
            System.out.println(this);
            Collections.sort(this.listedScores);
            System.out.println("Post-sort");
            System.out.println(this);
        } //End if
    } //End insertHighScore
    
    
    /** Returns the String representation of all HighScore elements, broken up line by line.
     * @return returnString - String representation of HighScore elements on multiple lines
     */
    public String toString(){
        String returnString = new String();
        for (HighScore aScore: listedScores){
            returnString = returnString + aScore + System.getProperty("line.separator");
        } //End for
        return returnString;
    } //End toString
    
    
} //End Class