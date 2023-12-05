
public class Campaign {
    
    public static Player[] level1 () {
        int[] size = {2, 2};
        String[] names = {"Patrol Boat"};
        int[] sizes = {2};
        Board a = new Board (size);
        Board b = new Board (size);
        Player Player1 = new Player (true, a, b, names, sizes);
        Player Player2 = new Player (false, b, a, names, sizes);

        Player[] ans = {Player1, Player2};
        return ans;
    }

    public static Player[] level2 () {
        int[] size = {5,5};
        String[] names = {"Patrol Boat","Destroyer"};
        int[] sizes = {2,3};
        Board a = new Board (size);
        Board b = new Board(size);

        Player Player1 = new Player(true, a, b, names, sizes);
        Player Player2 = new Player(false, a, b, names, sizes);




        Player[] ans = {new Player()};
        return ans;
    }
}