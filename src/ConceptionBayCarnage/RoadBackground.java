package ConceptionBayCarnage;


/** The RoadBackground class is used to create the background
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class RoadBackground {
    int x;
    
    
    /** creates a new background object
     * @param x location on x-axis
     */
    public RoadBackground(int x){
        this.x = x;
    } //End RoadBackground
  
    
    /** gets value of x
     * @return x - location on x-axis
     */
    public int getX() {
        return x;
    } //End getX

    
    /** sets the value of x
     * @param x location on x-axis
     */
    public void setX(int x) {
        this.x = x;
    } //End setX
    
    
} //End Class