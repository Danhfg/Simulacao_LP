package projetosimulacao;

import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

/**
 * (Fill in description and author info here)
 */
public class Ocean
{
    
    private Fish[][] fishes;
    private Resource[][] resources;
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
        resources = new Resource[height][width];

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
    
    public Resource getResourceAt(int row, int col)
    {
        if (row < 0 || row >= height || col < 0 || col >= width)
            return null;
       
        
        return resources[row][col];
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
    
    public void placeResource(Resource resource, int rol, int col){
        resources[rol][col] = resource;
    }
    
    public void placeResource(Resource resource, Location loc){
        resources[loc.getRow()][loc.getCol()] = resource;
    }
   
    
    public void clear(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fishes[i][j] = null;
                resources[i][j] = null;
            }
        }
    }
    
    public void clear(Location location){
        
        fishes[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getFishAt(next.getRow(), next.getCol()) == null) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        Random rand = new Random();
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < height) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    
}
