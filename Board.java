public class Board {
    public Tile[][] tiles;

    public Board () {
        tiles = new Tile[10][10];    
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new Tile (i, j);
            }
        }
    }

    public void SetShips (Ship[] ships) {
        // patrol boat      
    }

    public Tile getTile (int x, int y) {
        return tiles[x][y];
    } 

    public void hit (int x, int y) {
        tiles[x][y].hit();
    } 
}
