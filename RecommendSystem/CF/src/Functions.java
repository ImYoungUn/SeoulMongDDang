import java.util.ArrayList;
import java.util.Collections;

public class Functions {
	private ArrayList<Integer> codeListArr = new ArrayList();
	private ArrayList<Float> expectScoreListArr = new ArrayList();
	private String[] codeList;
	private String[] expectScoreList;

	public void save(int userId, ArrayList<String> rate, ArrayList<Person> person, ArrayList<Integer> allContents,
			FamousContents contents) {
		// person�� ���� ���� -> ��ȭId,�ش� ���� + ������ ��ȭID (�ش� ��ȭ id�� ���ܵ� �� ��õ��)
		// 3.0:1923:0 3.5:123:0 5.0:11:1 1.0:1123:0���� ����.
		// userId�� 0,3,2,4,199,332,2,4�� �� �� �����Ƿ� ���̿� new person�� �������ʳ־�� ��.
		for (int r = 0; r < rate.size(); r++) {
			String[] sep = null;
			sep = rate.get(r).split(":");

			// first save person���� check;
			while (userId >= person.size()) {
				// if the userId is the newest...
				person.add(new Person());
			}
			// find Person who has a same userId...
			// f��° person�� rate�� ������ ����
			person.get(userId).rate.add(Float.parseFloat(sep[0]));
			person.get(userId).cultId.add(Integer.parseInt(sep[1]));
			// �ѹ� �� ��ǰ�̸�
			if (sep[2].compareTo("1") == 0) {
				person.get(userId).watchedCultId.add(Integer.parseInt(sep[1]));
			}
			contents.contentsSave(allContents, Integer.parseInt(sep[1]), Float.parseFloat(sep[0]));
		}
	}

	public void getExpection(int userId, ArrayList<Integer> allContents, ArrayList<Person> person) {
		// ��� �������鿡 ���� (�� �������� �򰡳��� ����� �ֵ���) ���� ����� �����ϴ� �Լ�
		// �켱��, ���� ������ �������� �ʰ� ������ id�� ������.
		ArrayList<String> result = new ArrayList();
		int count;
		float[] simPerson = new float[person.size()];
		float expectScore[] = new float[allContents.size()];

		EQ1 eq1 = new EQ1();
		EQ2 eq2 = new EQ2();
		// int userSize = person.get(userId).cultId.size();
		for (int i = 0; i < person.size(); i++) {
			// �켱�� ��� simPerson�� ����@@@@@@@@@@@@@@@@@@@@@@@
			if (userId != i)
				simPerson[i] = eq1.getSim(userId, i, person);
			else
				simPerson[userId] = -99;
			// System.out.println("���絵 _i : " + i + ", " + simPerson[i]);
		}

		for (int i = 0; i < allContents.size(); i++) {
			// user�� �򰡳������� ���� ���������,
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
