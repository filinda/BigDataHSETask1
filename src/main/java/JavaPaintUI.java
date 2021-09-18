import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class JavaPaintUI extends JFrame {

    private int tool = 1;
    int currentX, currentY, oldX, oldY;

    public JavaPaintUI() {
        initComponents();
    }

    private void initComponents() {
        // we want a custom Panel2, not a generic JPanel!
        jPanel2 = new Panel2();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
       

        // add the component to the frame to see it!
        this.setContentPane(jPanel2);
        // be nice to testers..
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }// </editor-fold>

    // Variables declaration - do not modify
    Panel2 jPanel2;
    // End of variables declaration
    
    // This class name is very confusing, since it is also used as the
    // name of an attribute!
    //class jPanel2 extends JPanel {
    class Panel2 extends JPanel {
    	
    	Integer[] graph1 = new Integer[1];
    	Integer[] graph2 = new Integer[1];
        long[] timings;
    	
        Panel2() {
            // set a preferred size for the custom panel.
        	graph1[0] = 0;
        	graph2[0] = 0;
            setPreferredSize(new Dimension(700,500));
        }
        
        public void setGraph(Integer[] graph, Integer[] graph2, long[] timings) {
        	this.graph1 = graph;
        	this.graph2 = graph2;
        	this.timings = timings;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            for(int i=0; i<graph1.length-1; i++) {
            	g.drawLine(i, graph1[i]/5, i+1, graph1[i+1]/5);
            }
            g.setColor(Color.BLUE);
            for(int i=0; i<graph2.length-1; i++) {
            	g.drawLine(i, graph2[i]/5, i+1, graph2[i+1]/5);
            }
            
        }
    }
}