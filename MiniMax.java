
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MiniMax {

    public BoardState startingConfig = new BoardState();
    public int ROWS = GlobalParameters.ROWS;
    public int COLUMNS = GlobalParameters.COLUMNS;
    public TreeNode root = new TreeNode();

    public void poppulateTree() throws Exception {

        root.boardState = startingConfig;
        root.distanceFromRoot = 0;

        root.minOrMax = "max";   //SETTING MIN OR MAX !!

        HashMap<String, String> hm = new HashMap<String, String>();

        Queue<TreeNode> queue = new LinkedList<TreeNode>();

        queue.add(root);  //ADD ROOT TO THE QUEUE

        int desiredLevel = GlobalParameters.LEVEL_OF_LOOK_AHEAD;  //Root is at level 0

        while (queue.isEmpty() == false) {
            TreeNode node_front = queue.remove();//  REMOVING --  the front of Queue
            BoardState front = node_front.boardState;
            char[][] config_temp = front.configuration;

            hm.put(getStringRepresentationOfConfiguration(config_temp), "");//put this confirguration in Hash Map

            ////////   Exploring all possible moves for this top of queue(config_temp) !
            ArrayList<TreeNode> childrenList = new ArrayList<TreeNode>();//This will hold the list of children

            if (front.actualScore != 0) {
                continue;
            }//Dont expand this node further as we have the exact score !!
            if (node_front.distanceFromRoot >= desiredLevel) {
                continue;
            }//Dont expand the tree beyond desired level

            char peiceWHichCanBeMoved;
            if (node_front.minOrMax == "max") {
                peiceWHichCanBeMoved = GlobalParameters.myColor;
            } else {
                peiceWHichCanBeMoved = GlobalParameters.otherColor;
            }

            for (int i = 0; i < ROWS; i++) {

                for (int j = 0; j < COLUMNS; j++) {

                    char piece = config_temp[i][j];
                    if (piece != Character.MIN_VALUE && piece == peiceWHichCanBeMoved)//If this location has the piece which is to be moved
                    {
                        //Moving Up
                        CoOrdinate tempUp = getUp(new CoOrdinate(i, j));
                        while (tempUp != null && config_temp[tempUp.x][tempUp.y] == Character.MIN_VALUE)//piece only moves to empty space
                        {
                            BoardState bs = front.getOutputStateForGivenMove(piece, new CoOrdinate(i, j), new CoOrdinate(tempUp.x, tempUp.y));
                            TreeNode temp_node = new TreeNode();
                            temp_node.boardState = bs;
                            temp_node.distanceFromRoot = node_front.distanceFromRoot + 1;

                            if (node_front.minOrMax.equals("max")) {
                                temp_node.minOrMax = "min";
                            } else {
                                temp_node.minOrMax = "max";
                            }

                            if (!hm.containsKey(getStringRepresentationOfConfiguration(temp_node.boardState.configuration))) {
                                queue.add(temp_node);
                                childrenList.add(temp_node);
                            }

                            tempUp = getUp(tempUp);
                        }
                        //Moving Down
                        CoOrdinate tempDown = getDown(new CoOrdinate(i, j));
                        while (tempDown != null && config_temp[tempDown.x][tempDown.y] == Character.MIN_VALUE)//piece only moves to empty space
                        {
                            BoardState bs = front.getOutputStateForGivenMove(piece, new CoOrdinate(i, j), new CoOrdinate(tempDown.x, tempDown.y));
                            TreeNode temp_node = new TreeNode();
                            temp_node.boardState = bs;
                            temp_node.distanceFromRoot = node_front.distanceFromRoot + 1;

                            if (node_front.minOrMax.equals("max")) {
                                temp_node.minOrMax = "min";
                            } else {
                                temp_node.minOrMax = "max";
                            }

                            if (!hm.containsKey(getStringRepresentationOfConfiguration(temp_node.boardState.configuration))) {
                                queue.add(temp_node);
                                childrenList.add(temp_node);
                            }

                            tempDown = getDown(tempDown);
                        }

                        //Moving Right
                        CoOrdinate tempRight = getRight(new CoOrdinate(i, j));
                        while (tempRight != null && config_temp[tempRight.x][tempRight.y] == Character.MIN_VALUE)//piece only moves to empty space
                        {
                            BoardState bs = front.getOutputStateForGivenMove(piece, new CoOrdinate(i, j), new CoOrdinate(tempRight.x, tempRight.y));
                            TreeNode temp_node = new TreeNode();
                            temp_node.boardState = bs;
                            temp_node.distanceFromRoot = node_front.distanceFromRoot + 1;

                            if (node_front.minOrMax.equals("max")) {
                                temp_node.minOrMax = "min";
                            } else {
                                temp_node.minOrMax = "max";
                            }

                            if (!hm.containsKey(getStringRepresentationOfConfiguration(temp_node.boardState.configuration))) {
                                queue.add(temp_node);
                                childrenList.add(temp_node);
                            }

                            tempRight = getRight(tempRight);
                        }

                        //Moving Left
                        CoOrdinate tempLeft = getLeft(new CoOrdinate(i, j));
                        while (tempLeft != null && config_temp[tempLeft.x][tempLeft.y] == Character.MIN_VALUE)//piece only moves to empty space
                        {
                            BoardState bs = front.getOutputStateForGivenMove(piece, new CoOrdinate(i, j), new CoOrdinate(tempLeft.x, tempLeft.y));
                            TreeNode temp_node = new TreeNode();
                            temp_node.boardState = bs;
                            temp_node.distanceFromRoot = node_front.distanceFromRoot + 1;

                            if (node_front.minOrMax.equals("max")) {
                                temp_node.minOrMax = "min";
                            } else {
                                temp_node.minOrMax = "max";
                            }

                            if (!hm.containsKey(getStringRepresentationOfConfiguration(temp_node.boardState.configuration))) {
                                queue.add(temp_node);
                                childrenList.add(temp_node);
                            }

                            tempLeft = getLeft(tempLeft);
                        }
                    }//end of main if - if there is a piece in this cell
                }//end of j loop

            }//end of i loop

            node_front.childrenList = childrenList;  //adding child list to parent

        }//end of while

    }//end of function

    public TreeNode getTheMiniMaxScore(TreeNode tempRoot) throws Exception {

        if (tempRoot.childrenList.size() == 0)//for leaf nodes !
        {
            tempRoot.minMaxScore = tempRoot.boardState.actualScore + tempRoot.boardState.heuristicScore;
            return tempRoot;
        }

        ArrayList<TreeNode> childrenList = tempRoot.childrenList;

        //if tempRoot is MAX
        int minValue = Integer.MIN_VALUE;
        if (tempRoot.minOrMax.equals("max")) {
            TreeNode treeNodeTemp = new TreeNode();
            for (int i = 0; i < childrenList.size(); i++) {
                int val = getTheMiniMaxScore(childrenList.get(i)).minMaxScore;
                if (val > minValue) {
                    minValue = val;
                    treeNodeTemp.minMaxScore = val;
                    treeNodeTemp.moveToChoose = childrenList.get(i).boardState.configuration;
                }
            }
            return treeNodeTemp;
        }

        //if tempRoot is MIN
        int maxValue = Integer.MAX_VALUE;
        if (tempRoot.minOrMax.equals("min")) {
            TreeNode treeNodeTemp = new TreeNode();
            for (int i = 0; i < childrenList.size(); i++) {
                int val = getTheMiniMaxScore(childrenList.get(i)).minMaxScore;
                if (val < maxValue) {
                    maxValue = val;
                    treeNodeTemp.minMaxScore = val;
                    treeNodeTemp.moveToChoose = childrenList.get(i).boardState.configuration;
                }
            }
            return treeNodeTemp;
        }

        return null;
    }//end of function

    public char[][] getTheMiniMaxNextMove() throws Exception {
        try {
            this.poppulateTree();
            return getTheMiniMaxScore(root).moveToChoose;
        } catch (Exception e) {
            return getAlternativeMove();
        }
    }//end of function

    public char[][] getAlternativeMove() throws Exception {
        char[][] currentConfig = this.startingConfig.configuration;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (currentConfig[i][j] == GlobalParameters.myColor) {
                    //Moving Up
                    CoOrdinate tempUp = getUp(new CoOrdinate(i, j));
                    if (tempUp != null && currentConfig[tempUp.x][tempUp.y] == Character.MIN_VALUE) {
                        currentConfig[i][j] = Character.MIN_VALUE;
                        currentConfig[tempUp.x][tempUp.y] = GlobalParameters.myColor;
                        return currentConfig;
                    }
                    //Moving Down
                    CoOrdinate tempDown = getDown(new CoOrdinate(i, j));
                    if (tempDown != null && currentConfig[tempDown.x][tempDown.y] == Character.MIN_VALUE) {
                        currentConfig[i][j] = Character.MIN_VALUE;
                        currentConfig[tempDown.x][tempDown.y] = GlobalParameters.myColor;
                        return currentConfig;
                    }
                    //Moving Left
                    CoOrdinate tempLeft = getLeft(new CoOrdinate(i, j));
                    if (tempLeft != null && currentConfig[tempLeft.x][tempLeft.y] == Character.MIN_VALUE) {
                        currentConfig[i][j] = Character.MIN_VALUE;
                        currentConfig[tempLeft.x][tempLeft.y] = GlobalParameters.myColor;
                        return currentConfig;
                    }
                    //Moving Right
                    CoOrdinate tempRight = getRight(new CoOrdinate(i, j));
                    if (tempRight != null && currentConfig[tempRight.x][tempRight.y] == Character.MIN_VALUE) {
                        currentConfig[i][j] = Character.MIN_VALUE;
                        currentConfig[tempRight.x][tempRight.y] = GlobalParameters.myColor;
                        return currentConfig;
                    }
                }//end of outer if
            }
        }//end of outer for

        return currentConfig;

    }//end of function

    public String getStringRepresentationOfConfiguration(char[][] inp) throws Exception {
        String retString = "";
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                //encoding space to !
                if (inp[i][j] == Character.MIN_VALUE) {
                    retString += "!";
                } else {
                    retString += "" + inp[i][j];
                }
            }
        }
        return retString;
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
                rowString = rowString + config[i][j];
            }
            System.out.println(rowString);
        }
    }//end of function

}//end of class
