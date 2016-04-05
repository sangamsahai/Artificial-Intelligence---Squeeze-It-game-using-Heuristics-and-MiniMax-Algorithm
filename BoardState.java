
import static java.lang.Math.abs;

public class BoardState {

    public int ROWS = GlobalParameters.ROWS;
    public int COLUMNS = GlobalParameters.COLUMNS;

    //we calculate the game value with respect to myColor('b' / 'w')
    //so we calculate the (myColor's score - otherColor's score) as the game score
    public char myColor = GlobalParameters.myColor;
    public char otherColor = GlobalParameters.otherColor;

    public int actualScore = 0;//This will hold the actual score of the board 
    public int heuristicScore = 0;// This will hold the heuristic considering the board configuration

    //Default is * for every location in the board
    //Black =  'b'
    //White =  'w'
    public char[][] configuration = new char[ROWS][COLUMNS];

    //Gives you the output board state(with actual score) for a given move(actual squeeze it pattern weights a lot heavier than heuristic score !!!)
    //In case there is no pattern formed with this move , then it will give the output state with its Heuristic score
    public BoardState getOutputStateForGivenMove(char piece, CoOrdinate source, CoOrdinate destination) throws Exception {

        if (checkSqueezePatternLeft(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);   //copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position
            //clearing  values to the right - remove pieces from the pattern
            CoOrdinate tempRight = getRight(destination);
            while (tempRight != null && configuration_temp[tempRight.x][tempRight.y] != piece) {
                configuration_temp[tempRight.x][tempRight.y] = Character.MIN_VALUE;;
                tempRight = getRight(tempRight);
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise
            return bs;
        }//end of if

        if (checkSqueezePatternRight(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);  //copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position
            //clearing  values to the left - remove pieces from the pattern
            CoOrdinate tempLeft = getLeft(destination);
            while (tempLeft != null && configuration_temp[tempLeft.x][tempLeft.y] != piece) {
                configuration_temp[tempLeft.x][tempLeft.y] = Character.MIN_VALUE;;
                tempLeft = getLeft(tempLeft);
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise
            return bs;
        }//end of if

        if (checkSqueezePatternUp(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);  //copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position
            //clearing  values to the bottom
            CoOrdinate tempDown = getDown(destination);
            while (tempDown != null && configuration_temp[tempDown.x][tempDown.y] != piece) {
                configuration_temp[tempDown.x][tempDown.y] = Character.MIN_VALUE;;
                tempDown = getDown(tempDown);
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise
            return bs;
        }//end of if

        if (checkSqueezePatternDown(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);  //copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position
            //clearing  values to the up
            CoOrdinate tempUp = getUp(destination);
            while (tempUp != null && configuration_temp[tempUp.x][tempUp.y] != piece) {
                configuration_temp[tempUp.x][tempUp.y] = Character.MIN_VALUE;;
                tempUp = getUp(tempUp);
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise
            return bs;
        }//end of if

        if (checkSqueezePatternMiddle(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);//copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position

            //clearing  values to the left - remove pieces from the pattern
            CoOrdinate tempLeft = getLeft(destination);
            while (tempLeft != null && configuration_temp[tempLeft.x][tempLeft.y] == piece) {
                tempLeft = getLeft(tempLeft);
            }
            if (tempLeft != null) {
                configuration_temp[tempLeft.x][tempLeft.y] = Character.MIN_VALUE;
            }

            //clearing  values to the right - remove pieces from the pattern
            CoOrdinate tempRight = getRight(destination);
            while (tempRight != null && configuration_temp[tempRight.x][tempRight.y] == piece) {
                tempRight = getRight(tempRight);
            }
            if (tempRight != null) {
                configuration_temp[tempRight.x][tempRight.y] = Character.MIN_VALUE;
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise

            return bs;
        }//end of if

        if (checkSqueezePatternMiddleVertical(piece, destination) == true) {
            char[][] configuration_temp = makeCopyOfArray(configuration);//copy the current game configuration  - CLONING!! !!
            configuration_temp[destination.x][destination.y] = piece;//filling new position
            configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position

            //clearing  values towards up
            CoOrdinate tempUp = getUp(destination);
            while (tempUp != null && configuration_temp[tempUp.x][tempUp.y] == piece) {
                tempUp = getUp(tempUp);
            }
            if (tempUp != null) {
                configuration_temp[tempUp.x][tempUp.y] = Character.MIN_VALUE;
            }

            //clearing  values towards down
            CoOrdinate tempDown = getDown(destination);
            while (tempDown != null && configuration_temp[tempDown.x][tempDown.y] == piece) {
                tempDown = getDown(tempDown);
            }
            if (tempDown != null) {
                configuration_temp[tempDown.x][tempDown.y] = Character.MIN_VALUE;
            }

            BoardState bs = new BoardState();
            bs.configuration = configuration_temp;
            if (piece == myColor) {
                bs.actualScore = 1000;
            } else {
                bs.actualScore = -1000;
            }//+1000 if myColor made the pattern and -1000 otherwise

            return bs;
        }//end of if

        //IF NO PATTERN IS MADE BY THIS MOVE
        char[][] configuration_temp = makeCopyOfArray(configuration); //copy the current game configuration  - CLONING!! !! 
        configuration_temp[destination.x][destination.y] = piece;//filling new position
        configuration_temp[source.x][source.y] = Character.MIN_VALUE;//clearing old position
        BoardState bs = new BoardState();
        bs.configuration = makeCopyOfArray(configuration_temp);
        bs.heuristicScore = bs.getHeuristicScoreForThisConfiguration();//setting heuristic score of this resultant state

        return bs;

    }//end of function

    //This will return YES if the squeeze pattern(middle) is formed when the  piece moves to destination 
    public boolean checkSqueezePatternMiddle(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search right
        boolean right_result = false;
        CoOrdinate tempRight = getRight(destination);

        while (tempRight != null) {
            if (configuration[tempRight.x][tempRight.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempRight.x][tempRight.y] == otherPiece) {
                right_result = true;
                break;
            }//saw the other  piece
            tempRight = getRight(tempRight);
        }

        //search left
        boolean left_result = false;
        CoOrdinate tempLeft = getLeft(destination);

        while (tempLeft != null) {
            if (configuration[tempLeft.x][tempLeft.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempLeft.x][tempLeft.y] == otherPiece) {
                left_result = true;
                break;
            }//saw a other piece
            tempLeft = getLeft(tempLeft);
        }

        return right_result && left_result;
    }//end of function

    //This will return YES if the squeeze pattern(middle) is formed when the  piece moves to destination(middle - vertical) 
    public boolean checkSqueezePatternMiddleVertical(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search up
        boolean up_result = false;
        CoOrdinate tempUp = getUp(destination);

        while (tempUp != null) {
            if (configuration[tempUp.x][tempUp.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempUp.x][tempUp.y] == otherPiece) {
                up_result = true;
                break;
            }//saw the other  piece
            tempUp = getUp(tempUp);
        }

        //search down
        boolean down_result = false;
        CoOrdinate tempDown = getDown(destination);

        while (tempDown != null) {
            if (configuration[tempDown.x][tempDown.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempDown.x][tempDown.y] == otherPiece) {
                down_result = true;
                break;
            }//saw a other piece
            tempDown = getDown(tempDown);
        }

        return up_result && down_result;
    }//end of function

    //This will return YES if the squeeze pattern(right) is formed when the piece moves to destination(right) and pattern forms in left 
    public boolean checkSqueezePatternRight(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search left
        boolean left_result = false;
        CoOrdinate tempLeft = getLeft(destination);
        boolean seen_a_otherPiece = false;

        while (tempLeft != null) {
            if (configuration[tempLeft.x][tempLeft.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempLeft.x][tempLeft.y] == piece && !seen_a_otherPiece) {
                break;
            }//saw a piece without seeing other piece
            if (configuration[tempLeft.x][tempLeft.y] == otherPiece) {
                seen_a_otherPiece = true;
            }//saw a otherPiece
            if (configuration[tempLeft.x][tempLeft.y] == piece && seen_a_otherPiece) {
                left_result = true;
                break;
            }//saw a piece after otherPiece
            tempLeft = getLeft(tempLeft);
        }

        return left_result;
    }//end of function

    //This will return YES if the squeeze pattern(up) is formed when the piece moves to destination(up) and pattern forms in down 
    public boolean checkSqueezePatternUp(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search down
        boolean down_result = false;
        CoOrdinate tempDown = getDown(destination);
        boolean seen_a_otherPiece = false;

        while (tempDown != null) {
            if (configuration[tempDown.x][tempDown.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempDown.x][tempDown.y] == piece && !seen_a_otherPiece) {
                break;
            }//saw a piece without seeing other piece
            if (configuration[tempDown.x][tempDown.y] == otherPiece) {
                seen_a_otherPiece = true;
            }//saw a otherPiece
            if (configuration[tempDown.x][tempDown.y] == piece && seen_a_otherPiece) {
                down_result = true;
                break;
            }//saw a piece after otherPiece
            tempDown = getDown(tempDown);
        }

        return down_result;
    }//end of function

    //This will return YES if the squeeze pattern(down) is formed when the piece moves to destination(down) and pattern forms in up 
    public boolean checkSqueezePatternDown(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search up
        boolean up_result = false;
        CoOrdinate tempUp = getUp(destination);
        boolean seen_a_otherPiece = false;

        while (tempUp != null) {
            if (configuration[tempUp.x][tempUp.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempUp.x][tempUp.y] == piece && !seen_a_otherPiece) {
                break;
            }//saw a piece without seeing other piece
            if (configuration[tempUp.x][tempUp.y] == otherPiece) {
                seen_a_otherPiece = true;
            }//saw a otherPiece
            if (configuration[tempUp.x][tempUp.y] == piece && seen_a_otherPiece) {
                up_result = true;
                break;
            }//saw a piece after otherPiece
            tempUp = getUp(tempUp);
        }

        return up_result;
    }//end of function

    //This will return YES if the squeeze pattern(left) is formed when the piece moves to destination(left) and pattern forms in right  
    public boolean checkSqueezePatternLeft(char piece, CoOrdinate destination) throws Exception {
        char otherPiece;
        if (piece == 'b') {
            otherPiece = 'w';
        } else {
            otherPiece = 'b';
        }

        //search right
        boolean right_result = false;
        CoOrdinate tempRight = getRight(destination);
        boolean seen_a_otherPiece = false;

        while (tempRight != null) {
            if (configuration[tempRight.x][tempRight.y] == Character.MIN_VALUE) {
                break;
            }//saw an empty spot
            if (configuration[tempRight.x][tempRight.y] == piece && !seen_a_otherPiece) {
                break;
            }//saw a piece without seeing other piece
            if (configuration[tempRight.x][tempRight.y] == otherPiece) {
                seen_a_otherPiece = true;
            }//saw a otherPiece
            if (configuration[tempRight.x][tempRight.y] == piece && seen_a_otherPiece) {
                right_result = true;
                break;
            }//saw a piece after otherPiece
            tempRight = getRight(tempRight);
        }

        return right_result;
    }//end of function

    //This function returns true of any kind of pattern is formed	
    public boolean checkSqueezePattern(char piece, CoOrdinate destination) throws Exception {
        return (checkSqueezePatternLeft(piece, destination) || checkSqueezePatternRight(piece, destination)
                || checkSqueezePatternMiddle(piece, destination) || checkSqueezePatternMiddleVertical(piece, destination)
                || checkSqueezePatternDown(piece, destination) || checkSqueezePatternUp(piece, destination));
    }//end of function

    //Returns the right CoOrdinate
    public CoOrdinate getRight(CoOrdinate source) throws Exception {
        if (source.y < COLUMNS - 1) {
            return new CoOrdinate(source.x, source.y + 1);
        } else {
            return null;
        }
    }

    //Returns the left CoOrdinate
    public CoOrdinate getLeft(CoOrdinate source) throws Exception {
        if (source.y > 0) {
            return new CoOrdinate(source.x, source.y - 1);
        } else {
            return null;
        }
    }

    //Returns the up CoOrdinate
    public CoOrdinate getUp(CoOrdinate source) throws Exception {
        if (source.x > 0) {
            return new CoOrdinate(source.x - 1, source.y);
        } else {
            return null;
        }
    }

    //Returns the Down CoOrdinate
    public CoOrdinate getDown(CoOrdinate source) throws Exception {
        if (source.x < ROWS - 1) {
            return new CoOrdinate(source.x + 1, source.y);
        } else {
            return null;
        }
    }

    public void printConfig(char[][] config) throws Exception {
        for (int i = 0; i < ROWS; i++) {
            String rowString = "";
            for (int j = 0; j < COLUMNS; j++) {
                if (config[i][j] != 'w' && config[i][j] != 'b') {
                    rowString = rowString + '*';
                } else {
                    rowString = rowString + config[i][j];
                }
            }

            System.out.println(rowString);
        }
    }//end of function

    public int getHeuristicScoreForThisConfiguration() throws Exception {
        int myColorSCore = 0;
        int otherColorScore = 0;
        for (int i = 0; i < ROWS; i++) {

            for (int j = 0; j < COLUMNS; j++) {
                int scoreForThisPiece = 0;
                char piece = configuration[i][j];
                if (piece != Character.MIN_VALUE) {
                    //Moving Up
                    CoOrdinate tempUp = getUp(new CoOrdinate(i, j));

                    while (tempUp != null && configuration[tempUp.x][tempUp.y] == Character.MIN_VALUE) {
                        if (checkSqueezePattern(piece, tempUp)) {
                            scoreForThisPiece = scoreForThisPiece + (10);
                        }
                        tempUp = getUp(tempUp);
                    }
                    //Moving Down
                    CoOrdinate tempDown = getDown(new CoOrdinate(i, j));
                    while (tempDown != null && configuration[tempDown.x][tempDown.y] == Character.MIN_VALUE) {
                        if (checkSqueezePattern(piece, tempDown)) {
                            scoreForThisPiece = scoreForThisPiece + (10);
                        }
                        tempDown = getDown(tempDown);
                    }

                    //Moving Right
                    CoOrdinate tempRight = getRight(new CoOrdinate(i, j));
                    while (tempRight != null && configuration[tempRight.x][tempRight.y] == Character.MIN_VALUE) {
                        if (checkSqueezePattern(piece, tempRight)) {
                            scoreForThisPiece = scoreForThisPiece + (10);
                        }
                        tempRight = getRight(tempRight);
                    }

                    //Moving Left
                    CoOrdinate tempLeft = getLeft(new CoOrdinate(i, j));
                    while (tempLeft != null && configuration[tempLeft.x][tempLeft.y] == Character.MIN_VALUE) {
                        if (checkSqueezePattern(piece, tempLeft)) {
                            scoreForThisPiece = scoreForThisPiece + (10);
                        }
                        tempLeft = getLeft(tempLeft);
                    }

                    if (piece == myColor) {
                        myColorSCore = myColorSCore + scoreForThisPiece;
                    } else {
                        otherColorScore = otherColorScore + scoreForThisPiece;
                    }

                }//end of main if
            }//end of j loop

        }//end of i loop

        return (myColorSCore - otherColorScore);
    }//end of function

    public char[][] makeCopyOfArray(char[][] inp) throws Exception {
        char[][] returnArray = new char[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {

            for (int j = 0; j < COLUMNS; j++) {
                returnArray[i][j] = inp[i][j];
            }
        }
        return returnArray;
    }//end of function

}//end of class
