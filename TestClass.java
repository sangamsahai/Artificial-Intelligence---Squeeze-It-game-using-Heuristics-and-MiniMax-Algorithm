
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class TestClass {

    public static void main(String[] args) throws Exception {

        

        //Setting the initial board configuration
        char[][] startingArray = new char[8][8];
        startingArray[1][0] = 'w';
        startingArray[0][1] = 'w';
        startingArray[1][2] = 'w';
        startingArray[0][3] = 'w';
        startingArray[0][4] = 'w';
        startingArray[0][5] = 'w';
        startingArray[0][6] = 'w';
        startingArray[0][7] = 'w';

        startingArray[7][0] = 'b';
        startingArray[7][1] = 'b';
        startingArray[7][2] = 'b';
        startingArray[7][3] = 'b';
        startingArray[7][4] = 'b';
        startingArray[7][5] = 'b';
        startingArray[7][6] = 'b';
        startingArray[7][7] = 'b';
        
        BoardState bs3 = new BoardState();
        bs3.printConfig(startingArray);
        
        MiniMax mm = new MiniMax();
        mm.startingConfig.configuration = startingArray;  //Set the initial configuration for minimax

        char[][] nxtMve = mm.getTheMiniMaxNextMove();        //Get next move
        System.out.println("\n");
        bs3.printConfig(nxtMve);                             //Print boardState for this move

    }

}
