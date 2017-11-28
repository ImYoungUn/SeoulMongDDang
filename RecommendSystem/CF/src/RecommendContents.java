import java.util.ArrayList;

public class RecommendContents {
    private String[] codeList;
    private String[] expectScoreList;
    
	RecommendContents(int recomSize, Functions func, String userId, ArrayList<Integer> allContents, PersonList personlist){
		// codeList�� ���� ���� ������ cultId ����
		// android���� mongId(==userId)�� 1���� �����ϹǷ� 0���� �������.
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
