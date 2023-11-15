public class Board {
    public Tile[][] tiles;

    public Board () {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new Tile (i, j);
            }
        }
    }

    public void SetShips (Ship[] ships) {
        // patrol boat    
        
    }
}
