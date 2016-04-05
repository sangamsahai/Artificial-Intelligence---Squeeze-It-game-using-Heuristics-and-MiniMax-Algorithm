
import java.util.ArrayList;

public class TreeNode {

	public BoardState boardState;
	public ArrayList<TreeNode> childrenList=new ArrayList<TreeNode>();
	public int distanceFromRoot;
	public String minOrMax="";
	
	public int minMaxScore=0;
	public char[][] moveToChoose=null;
	
	
}//end of class
