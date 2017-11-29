import java.util.ArrayList;

class PersonList {
	public ArrayList<Person> person;
	public ArrayList<Integer> userId;

	PersonList() {
		person = new ArrayList<Person>();
		userId = new ArrayList<Integer>();
	}
/*
	ArrayList<Integer> getContents(String args, StringBuilder userId) {
		// args :
		// {userId:11,allContents:[96235,96234,96232,96201,96177,96077,95947,95710,95593,95592,95296,94300,94061,93931,93930,92219,96163,96012,95931,95908,95862,95806,95706,95703,95463,94313,93596,93384,92974,96200]}
		userId.delete(0, userId.length()); // userId �޾Ƴ���
		String temp;
		temp = args.replaceAll(",", ":");
		temp = temp.replaceAll("\\[", "");
		temp = temp.replaceAll("\\]}", "");
		// {userId:1581059065303931:allContents:96235:96234:96232:96201:96177:96077:95947:95710:95593:95592:95296:94300:94061:93931:93930:92219:96163:96012:95931:95908:95862:95806:95706:95703:95463:94313:93596:93384:92974:96200

		String[] parsing = temp.split(":");
		ArrayList<Integer> allContents = new ArrayList<>();
		userId.append(parsing[1]);
		// System.out.println(userId);
		for (int i = 3; i < parsing.length; i++) {
			allContents.add(Integer.parseInt(parsing[i]));
		}
		return allContents;
	}
*/
	void savePerson(String args, Functions func, ArrayList<Integer> allContents, FamousContents contents) {
		// {mongId:1,rate:4.0:96117:0
		ArrayList<String> rate = new ArrayList();
		int mongId = 1;
		String split[] = args.split("},");
		// �� data������ŭ for��
		for (int i = 0; i < split.length; i++) {
			String result[] = split[i].split(",");
			// {mongId:1
			// rate:4.0:96117:0
			// mongId�� �״�θ� ��� arrayList rate�� ����.
			if (mongId == Integer.parseInt(result[0].substring(8, result[0].length()))) {
				rate.add(result[1].substring(5, result[1].length()));
			} else {
				func.save(mongId - 1, rate, person,allContents, contents);
				mongId = Integer.parseInt(result[0].substring(8, result[0].length()));
				// rate ���� �ʱ�ȭ
				rate = new ArrayList();
				rate.add(result[1].substring(5, result[1].length()));
			}
		} // mongID�� ���� rate���� func.save�� �ѱ��
			// android���� mongId�� 1���� �����ϹǷ� 0���� �������.
		func.save(mongId - 1, rate, person,allContents, contents);
	}

	ArrayList<Person> getPerson() {
		return person;
	}
}

class Person {
	public ArrayList<Integer> cultId;
	public ArrayList<Float> rate;
	public ArrayList<Integer> watchedCultId;

	Person() {
		rate = new ArrayList<Float>();
		cultId = new ArrayList<Integer>();
		watchedCultId = new ArrayList<Integer>();
	}
}