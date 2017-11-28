import java.util.ArrayList;
import java.util.Comparator;

public class FamousContents {
	private ArrayList<Integer> cultId;
	private ArrayList<Float> rate;
	private ArrayList<Integer> count;
	private String[] famousCodeList;
	private String[] famousScoreList;

	FamousContents() {
		cultId = new ArrayList();
		rate = new ArrayList();
		count = new ArrayList();
	}

	public void contentsSave(ArrayList<Integer> allContents, int cultId, float rate) {
		if (allContents.contains(cultId)) {
			// 현재 컨텐츠가 존재할 때
			int index = this.cultId.indexOf(cultId);
			if (index == -1) {
				// cultId에 첫 저장일 때
				this.cultId.add(cultId);
				this.rate.add(rate);
				this.count.add(1);
			} else {
				// cultId에 이미 존재할 때
				this.rate.set(index, this.rate.get(index) + rate);
				this.count.set(index, this.count.get(index) + 1);
			}
		}
	}

	public void getFamousContents(int size) {
		famousCodeList = new String[cultId.size()];
		famousScoreList = new String[cultId.size()];		
		// 해당 contents에 대한 평균점수 구하기
		ArrayList<Integer> indexList = new ArrayList();
		float val;
		// size만큼의 결과를 얻음. 단, cultId 
		if(size>cultId.size())
			size = cultId.size();
		for (int i = 0; i < size; i++) {
			float max = 0;
			int max_j = 0;
			for (int j = 0; j < cultId.size(); j++) {
				val = this.rate.get(j) / this.count.get(j);
				// indexList에 포함되지 않은 j 중 val이 최댓값을 가지도록 하는 j
				if (val > max && !indexList.contains(j)) {
					max = val;
					max_j = j;
				}
			}
			indexList.add(max_j);
			if (cultId.size() != 0) {
				famousCodeList[i] = cultId.get(max_j).toString();
				famousScoreList[i] = Float.toString(max);
			} else {
				famousCodeList[i] = "NULL";
				famousScoreList[i] = "NULL";
			}
		}
	}

	public String[] getCode() {
		return famousCodeList;
	}

	public String[] getScore() {
		return famousScoreList;
	}
}
