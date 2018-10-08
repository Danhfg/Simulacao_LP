import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2003.12.22
 */
public class SimulatorView extends JFrame
{
	private static final long serialVersionUID = 1L;

	// Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private OceanView oceanView;
    
    // A map for storing colors for participants in the simulation
    private HashMap<Class<? extends Fish>,Color> colors;
    // A statistics object computing and storing simulation information
    private OceanStats stats;

    /**
     * Create a view of the given width and height.
     * @param height The simulation height.
     * @param width The simulation width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new OceanStats();
        colors = new HashMap<Class<? extends Fish>,Color>();

        setTitle("SimOcean");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        setLocation(100, 50);
        
        oceanView = new OceanView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(oceanView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    /**
     * Define a color to be used for a given class of fish.
     */
    public void setColor(Class<? extends Fish> fishClass, Color color)
    {
        colors.put(fishClass, color);
    }

    /**
     * @return The color to be used for a given class of fish.
     */
    private Color getColor(Class<? extends Fish> fishClass)
    {
        Color col = colors.get(fishClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the ocean.
     * @param step Which iteration step it is.
     * @param ocean The ocean whose status is to be displayed.
     */
    public void showStatus(int step, Ocean ocean)
    {
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        oceanView.preparePaint();
            
        for(int row = 0; row < ocean.getHeight(); row++) {
            for(int col = 0; col < ocean.getWidth(); col++) {
                Fish fish = ocean.getFishAt(row, col);
                if(fish != null) {
                    stats.incrementCount(fish.getClass());
                    oceanView.drawMark(col, row, getColor(fish.getClass()));
                }
                else {
                    oceanView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(ocean));
        oceanView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Ocean ocean)
    {
        return stats.isViable(ocean);
    }
    
    /**
     * Provide a graphical view of a rectangular ocean. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the ocean.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class OceanView extends JPanel
    {
		private static final long serialVersionUID = 1L;

		private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image oceanImage;

        /**
         * Create a new OceanView component.
         */
        public OceanView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }
        
        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                oceanImage = oceanView.createImage(size.width, size.height);
                g = oceanImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this ocean in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The ocean view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(oceanImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(oceanImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(oceanImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
