import java.util.ArrayList;

public class RecommendContents {
    private String[] codeList;
    private String[] expectScoreList;
    
	RecommendContents(int recomSize, Functions func, String userId, ArrayList<Integer> allContents, PersonList personlist){
		// codeList에 높은 점수 순으로 cultId 제공
		// android에서 mongId(==userId)를 1부터 제공하므로 0으로 만들어줌.
		func.getExpection(Integer.parseInt(userId) - 1, allContents, personlist.person);
		codeList = func.getCode();
		expectScoreList = func.getScore();
	}
	public String[] getCode(){
		return codeList;
	}
	public String[] getScore(){
		return expectScoreList;
	}
}
