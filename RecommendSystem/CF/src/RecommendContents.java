import java.util.ArrayList;

public class RecommendContents {
    private String[] codeList;
    private String[] expectScoreList;
    
	RecommendContents(int recomSize, Functions func, String userId, ArrayList<Integer> allContents, PersonList personlist){
		//처음유저는 mongId를 1로 만들어서 추천해준다.(임시로)@@@@@@@@@@@@@@@@@@
		int uI = Integer.parseInt(userId) - 1;
		//uI가 점수매긴 정보가 하나도 없을 때-> Function.save에서 person의 개수를 안늘림!!
		if(personlist.person.size()<uI || personlist.person.get(uI).cultId.size()==0)
			uI=0;
		// codeList에 높은 점수 순으로 cultId 제공
		// android에서 mongId(==userId)를 1부터 제공하므로 0으로 만들어줌.
		func.getExpection(uI, allContents, personlist.person);
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
