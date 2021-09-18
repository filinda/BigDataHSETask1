import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class ChartUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public ChartUI(String applicationTitle, String chartTitle, Double[] ax, Double[] ay, Double[] maxx, Double[] maxy, Double[] minx, Double[] miny) {
        super(applicationTitle);
        // This will create the dataset
        XYDataset dataset = createDataset(ax, ay, maxx, maxy, minx, miny);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }

    /**
     * Creates a sample dataset
     */
    private  XYDataset createDataset(Double[] ax, Double[] ay,Double[] maxx, Double[] maxy,Double[] minx, Double[] miny) {
    	
    	XYSeries series1 = new XYSeries("avg");
    	for(int i=0; i< ax.length; i++) {
    		series1.add(ax[i], ay[i]);
    	}
    	
    	XYSeries series2 = new XYSeries("max");
    	for(int i=0; i< maxx.length; i++) {
    		series2.add(maxx[i], maxy[i]);
    	}
    	
    	XYSeries series3 = new XYSeries("min");
    	for(int i=0; i< minx.length; i++) {
    		series3.add(minx[i], miny[i]);
    	}
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        
        return dataset;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createChart(XYDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createXYLineChart(
            title,                  // chart title
            "",                // data
            "",                   // include legend
            dataset
        );

        
        return chart;

    }
}