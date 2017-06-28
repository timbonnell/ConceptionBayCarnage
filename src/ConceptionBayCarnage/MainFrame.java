package ConceptionBayCarnage;
import javax.swing.JFrame;


/** The MainFrame class is used to create the main frame and hold the main method
 * @author David Budgell, Tim Bonnell, K. Craig Webber
 */
public class MainFrame extends JFrame {

    
    /** creates a new MainFrame object
     * @param args arguments
     */
    public static void main(String[] args) {
        //Creates a Jpanel
        GraphicPane panel = new GraphicPane();
        //Create JFrame
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setTitle("Conception Bay Carnage");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        panel.setFocusable(true);
        frame.add(panel);
    } //End Main

    
} //End Class