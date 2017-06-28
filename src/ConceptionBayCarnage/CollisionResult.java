package ConceptionBayCarnage;

/** The CollisionResult class is used to hold information regarding changes to be 
* made to health, score, and determining if the object persists after a collision
* has occurred.
* @author David Budgell, Tim Bonnell, K. Craig Webber
*/
public class CollisionResult {
    //Declare Attributes
    int score, health;
    boolean persist;

    
    /**Creates a new CollisionResult Object which represents the actions to be taken
     * upon collision with this object in a game of ConceptionBayCarnage
    * @param score The value to be added to the players score
    * @param health The value to be added or subtracted to the players health
    * @param persist The value that determines if the object persists after collision
    */
    public CollisionResult(int score, int health, boolean persist) {
        this.setScore(score);
        this.setHealth(health);
        this.setPersist(persist);
    } //End CollisionResult
    
    
    /** Gets the value of score
     * @return score - The value to be added to the players score
     */
    public int getScore() {
        return score;
    } //End getScore

    
    /** Sets the value of score
     * @param score The value to be added to the players score
     */
    public void setScore(int score) {
        this.score = score;
    } //End setScore

    
    /** Gets the value of health
     * @return health - The value to be added or subtracted to the players health
     */
    public int getHealth() {
        return health;
    } //End getHealth

    
    /** Sets the value of health
     * @param health The value to be added or subtracted to the players health
     */
    public void setHealth(int health) {
        this.health = health;
    } //End setHealth

    
    /** Gets the value of persist
     * @return persist - The value that determines if the object persists after collision
     */
    public boolean isPersist() {
        return persist;
    } //End isPersist

    
    /** Sets the value of persist
     * @param persist The value that determines if the object persists after collision
     */
    public void setPersist(boolean persist) {
        this.persist = persist;
    } //End setPersist
    
    
} //End Class