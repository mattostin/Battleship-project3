
import java.util.*;
import java.util.Random;

public class ImpossibleBot extends Bot {

    public int tauntCounter;

    private Random rand = new Random();

    public ImpossibleBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);

        Thread impossiblebotCode = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("FAILURE IN BOT SLEEP");
                    return;
                }

                if (this.turn && rand.nextInt(14) < 4){
                    tauntCounter++;
                    System.out.println("Ship Malfunctioning");
                    simulateMove();
                    this.turn = !this.turn;
                }

                // if (this.turn && shouldTaunt()){
                //     tauntCounter++;
                //     System.out.println("#" + tauntCounter);
                // }
                else if (this.turn) {
                    //System.out.println("SUPPOSED TO SIMULATE");
                    //line below this is the actual code, trying to make it shoot a bunch
                    // simulateMove();
                    //works but sometimes shoots 2-3 times other times it shoots like ten times.
                    for (int i = 0; i<15; i++){
                        simulateMove();
                        this.turn = true;
                        this.fired = false;
                        //debugging
                        System.out.println("Impossible Bot Fired");
                    }
                }
            }
        });
    
        impossiblebotCode.start();
    }


    //  public void simulateMove() {
    //     while (true) {
    //         for (int i = 0; i < 10; i++){
    //             int a = rand.nextInt(this.OwnBoard.boardSize[0]);
    //             int b = rand.nextInt(this.OwnBoard.boardSize[1]);
    //             Tile target = OppBoard.getTile(a, b);
    //             //boolean breaker = this.fire(target);
    //             if (target.hasBoat()){
    //                 i--;
    //             }
    //         }
    //             // if (breaker) {
    //             //     if (!target.hasBoat()) {
    //         this.fired = true;
    //         this.turn = false;
    //         break;
    //     }
    // }
    
        //totalTilesHit is for what the bot hits,
        //totalTimesBeenHit is for what is hit by the bot.
    public double totalTimesBotHits, totalTimesBeenHit;
    

    //sees what the bot has hit on the players board.
    private ArrayList<Tile> getHitTiles() {
        ArrayList<Tile> hitTiles = new ArrayList<>();
        for (int r = 0; r < this.OppBoard.boardSize[0]; r++) {
            for (int c = 0; c < this.OppBoard.boardSize[1]; c++) {
                Tile tile = OppBoard.getTile(r, c);
                if (tile.hasBoat() && tile.beenHit()) {
                    totalTimesBotHits++;
                    hitTiles.add(tile);
                }
            }
        }
        return hitTiles;
    }
    //sees what is hit by the player on the bots side.
    private ArrayList<Tile> ownHitTiles() {
        ArrayList<Tile> hitTiles = new ArrayList<>();
        for (int r = 0; r < this.OwnBoard.boardSize[0]; r++) {
            for (int c = 0; c < this.OwnBoard.boardSize[1]; c++) {
                Tile tile = OwnBoard.getTile(r, c);
                if (tile.hasBoat() && tile.beenHit()) {
                    totalTimesBeenHit++;
                    hitTiles.add(tile);
                }
            }
        }
        return hitTiles;
    }


    //make it so that if the bot has been hit significantly less than the player
    //starts taunting


    ArrayList<Tile> hitTiles = new ArrayList<>();

    //is not working
    public boolean shouldTaunt(){
        if (totalTimesBotHits/5 <= totalTimesBeenHit){
            return true;
        }
        else{
            return false;
        }
        

    }

    @Override
    public void simulateMove() {
        while (true) {
            int a = rand.nextInt(this.OwnBoard.boardSize[0]);
            int b = rand.nextInt(this.OwnBoard.boardSize[1]);
            Tile target = OppBoard.getTile(a, b);
            boolean breaker = this.fire(target);
            if (breaker) {
                if (!target.hasBoat()) {
                    this.fired = true;
                    this.turn = false;
                    break;
                }
            }
        }
    }
            
}

    
