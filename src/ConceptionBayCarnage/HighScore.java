package ConceptionBayCarnage;
import java.io.Serializable;


/** The HighScore class is used to store the details of a highScore comprised by 
 * a String and an integer. This class is serializable and comparable to maintain
 * the consistency of game leaderboards.
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class HighScore implements Comparable<HighScore>, Serializable{
    private int recordedScore;
    private String recordedName;
    
    
    /** creates a new HighScore object
     * @param recordedScore player's score
     * @param recordedName player's name
     */
    public HighScore(int recordedScore, String recordedName){
        this.setRecordedScore(recordedScore);
        this.setRecordedName(recordedName);
    } //End HighScore
    
    
    /** compares player's score to a HighScore value
     * @param y HighScore object
     * @return int - -1, 0, or 1
     */
    @Override
    public int compareTo(HighScore y){
        int otherScore = y.getRecordedScore();
        if (this.getRecordedScore() > otherScore){
            return -1;
        } else if (this.getRecordedScore() == otherScore){
            return 0;
        } else {
            return 1;
        } //End if
    } //End compareTo
    
    
    /** gets the value of the recordedScore
     * @return recordedScore - value of the player's score as an int
     */
    public int getRecordedScore() {
        return recordedScore;
    } //End getRecordedScore

    
    /** sets the value of recordedScore
     * @param recordedScore value of the player's score as an int
     */
    public void setRecordedScore(int recordedScore) {
        this.recordedScore = recordedScore;
    } //End setRecordedScore

    
    /** gets the value of recordedName
     * @return recordedName - player's name
     */
    public String getRecordedName() {
        return recordedName;
    } //End getRecordedName

    
    /** sets the value of recordedName
     * @param recordedName player's name
     */
    public void setRecordedName(String recordedName) {
        this.recordedName = recordedName;
    } //End setRecordedName
  
    
    /** displays names in score board
     * @return string (this.getRecordedScore() + " " + this.getRecordedName())
     */
    public String toString(){
        return this.getRecordedScore() + " " + this.getRecordedName();
    }
    
    
} //End Class