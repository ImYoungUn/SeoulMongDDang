import java.util.ArrayList;
import java.util.Collections;

public class Functions {
	private ArrayList<Integer> codeListArr = new ArrayList();
	private ArrayList<Float> expectScoreListArr = new ArrayList();
	private String[] codeList;
	private String[] expectScoreList;

	public void save(int userId, ArrayList<String> rate, ArrayList<Person> person, ArrayList<Integer> allContents,
			FamousContents contents) {
		// person에 정보 저장 -> 문화Id,해당 점수 + 관람한 문화ID (해당 문화 id는 제외된 후 추천됨)
		// 3.0:1923:0 3.5:123:0 5.0:11:1 1.0:1123:0정보 들어옴.
		// userId는 0,3,2,4,199,332,2,4가 될 수 있으므로 사이에 new person을 차례차례넣어야 함.
		for (int r = 0; r < rate.size(); r++) {
			String[] sep = null;
			sep = rate.get(r).split(":");

			// first save person인지 check;
			while (userId >= person.size()) {
				// if the userId is the newest...
				person.add(new Person());
			}
			// find Person who has a same userId...
			// f번째 person의 rate에 점수를 더함
			person.get(userId).rate.add(Float.parseFloat(sep[0]));
			person.get(userId).cultId.add(Integer.parseInt(sep[1]));
			// 한번 본 작품이면
			if (sep[2].compareTo("1") == 0) {
				person.get(userId).watchedCultId.add(Integer.parseInt(sep[1]));
			}
			contents.contentsSave(allContents, Integer.parseInt(sep[1]), Float.parseFloat(sep[0]));
		}
	}

	public void getExpection(int userId, ArrayList<Integer> allContents, ArrayList<Person> person) {
		// 모든 컨텐츠들에 대해 (그 컨텐츠를 평가내린 사람이 있따면) 예측 결과를 제공하는 함수
		// 우선은, 예측 점수는 제공하지 않고 컨텐츠 id만 제공함.
		ArrayList<String> result = new ArrayList();
		int count;
		float[] simPerson = new float[person.size()];
		float expectScore[] = new float[allContents.size()];

		EQ1 eq1 = new EQ1();
		EQ2 eq2 = new EQ2();
		// int userSize = person.get(userId).cultId.size();
		for (int i = 0; i < person.size(); i++) {
			// 우선은 모두 simPerson에 저장@@@@@@@@@@@@@@@@@@@@@@@
			if (userId != i)
				simPerson[i] = eq1.getSim(userId, i, person);
			else
				simPerson[userId] = -99;
			// System.out.println("유사도 _i : " + i + ", " + simPerson[i]);
		}

		for (int i = 0; i < allContents.size(); i++) {
			// user가 평가내린적이 없는 컨텐츠라면,
			if (!person.get(userId).cultId.contains(allContents.get(i))) {
				codeListArr.add(allContents.get(i));
				expectScoreListArr.add(eq2.expect(allContents.get(i), userId, simPerson, person));
			}
		}

		codeList = new String[codeListArr.size()];
		expectScoreList = new String[expectScoreListArr.size()];

		for (int j = 0; j < codeList.length; j++) {
			float max = 0;
			int max_i = 0;
			for (int i = 0; i < expectScoreListArr.size(); i++) {
				if (expectScoreListArr.get(i) > max) {
					max_i = i;
					max = expectScoreListArr.get(i);
				}
			}
			codeList[j] = codeListArr.get(max_i).toString();
			expectScoreList[j] = Float.toString(expectScoreListArr.get(max_i));
			codeListArr.remove(max_i);
			expectScoreListArr.remove(max_i);
		}
	}
	public String[] getCode(){
		return codeList;
	}
	public String[] getScore(){
		return expectScoreList;
	}
}
