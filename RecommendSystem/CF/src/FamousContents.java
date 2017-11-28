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
			// ���� �������� ������ ��
			int index = this.cultId.indexOf(cultId);
			if (index == -1) {
				// cultId�� ù ������ ��
				this.cultId.add(cultId);
				this.rate.add(rate);
				this.count.add(1);
			} else {
				// cultId�� �̹� ������ ��
				this.rate.set(index, this.rate.get(index) + rate);
				this.count.set(index, this.count.get(index) + 1);
			}
		}
	}

	public void getFamousContents(int size) {
		famousCodeList = new String[cultId.size()];
		famousScoreList = new String[cultId.size()];		
		// �ش� contents�� ���� ������� ���ϱ�
		ArrayList<Integer> indexList = new ArrayList();
		float val;
		// size��ŭ�� ����� ����. ��, cultId 
		if(size>cultId.size())
			size = cultId.size();
		for (int i = 0; i < size; i++) {
			float max = 0;
			int max_j = 0;
			for (int j = 0; j < cultId.size(); j++) {
				val = this.rate.get(j) / this.count.get(j);
				// indexList�� ���Ե��� ���� j �� val�� �ִ��� �������� �ϴ� j
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
