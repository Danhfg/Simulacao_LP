package projetosimulacao;

import java.util.Random;

/**
 * (Fill in description and author info here)
 */
public class Ocean
{
    
    private Fish[][] fishes;
    private Seaweed[][] seaweeds;
    private int height;
    private int width;
    
    /**
     * Represent an ocean of the given dimensions.
     * @param height The height of the ocean.
     * @param width The width of the ocean.
     */
    public Ocean(int height, int width)
    {
        this.height = height;
        this.width = width;
        
        fishes = new Fish[height][width];
        seaweeds = new Seaweed[height][width];
        
        Random rng = new Random();
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                seaweeds[i][j] = new Seaweed(rng.nextInt(11));
                //System.out.print(seaweeds[i][j].getAmount() + " ");
            }
            //System.out.println("");
        }
        
        //fishes[1][1] = new Shark();
        
        // some code needs to go here
        
        
    }
    
    /**
     * Return the fish at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The fish at the given location, or null if there is none.
     */
    public Fish getFishAt(int row, int col)
    {
        if (row < 0 || row >= height || col < 0 || col >= width)
            return null;
        
        return fishes[row][col];
    }
    
    public Seaweed getSeaweedAt(int row, int col)
    {
        if (row < 0 || row >= height || col < 0 || col >= width)
            return null;
       
        
        return seaweeds[row][col];
    }
    
    /**
     * @return The height of the ocean.
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * @return The width of the ocean.
     */
    public int getWidth()
    {
        return width;
    }
    
    public void placeFish(Fish fish, int rol, int col){
        fishes[rol][col] = fish;
    }
    
    public void placeFish(Fish fish, Location loc){
        fishes[loc.getRow()][loc.getCol()] = fish;
    }
    
    public void placeSeaweed(Seaweed seaweed, int rol, int col){
        seaweeds[rol][col] = seaweed;
    }
    
    public void clear(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fishes[i][j] = null;
                seaweeds[i][j] = null;
            }
        }
    }
}
