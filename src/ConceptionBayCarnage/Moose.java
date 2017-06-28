package ConceptionBayCarnage;
import javax.swing.ImageIcon;


/** The Moose class is used to create moose objects
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class Moose extends GameEntity implements Moveable{
    private int moveX, moveY;
    
    
    /** creates a new moose object
     * @param x location on x-axis
     * @param y location on y-axis
     * @param radius value of radius in pixels
     * @param picLocation location of image file
     * @param colResult type of colResult
     */
    public Moose(int x, int y, int radius, ImageIcon picLocation, CollisionResult colResult) {
        super(x, y, radius, picLocation, colResult);
        this.setMoveX(0);
        this.setMoveY(0);
    } //End Moose
    
    
    /** creates a new moving moose object
     * @param x location on x-axis
     * @param y location on y-axis
     * @param radius value of radius in pixels
     * @param picLocation location of image file
     * @param colResult type of colResult
     * @param moveX move value on x-axis
     * @param moveY move value on y-axis
     */
    public Moose(int x, int y, int radius, ImageIcon picLocation, CollisionResult colResult,int moveX, int moveY) {
        super(x, y, radius, picLocation, colResult);
        this.setMoveX(moveX);
        this.setMoveY(moveY);
    } //End Moose

    
    /** moves the GameEntity
     */
    @Override
    public void move() {
        //System.out.println("Attempting to move moose");
        this.setX(this.getX() + this.moveX);
        this.setY(this.getY() + this.moveY);
    } //End move

    
    /** gets the value of moveX
     * @return moveX -  move value on x-axis
     */
    public int getMoveX() {
        return moveX;
    } //End getMoveX

    
    /** sets the value of moveX
     * @param moveX move value on x-axis
     */
    private void setMoveX(int moveX) {
        this.moveX = moveX;
    } //End setMoveX

    
    /** gets the value of moveY
     * @return moveY - move value on y-axis
     */
    public int getMoveY() {
        return moveY;
    } //End getMoveY

    
    /** sets the value of moveY
     * @param moveY move value on y-axis
     */
    private void setMoveY(int moveY) {
        this.moveY = moveY;
    } //End setMoveY
    
    
} //End class