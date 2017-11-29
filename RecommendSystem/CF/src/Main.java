import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) {
		 //String arg =
		// "{userId:1,allContents:[96262,96261,96260,96259,96258,96256,96250,96200,96001,95956,95879,95790,95709,95705,95694,95678,95665,95649,95602,95594,95589,95468,96202,96166,96131,96038,95954,95907,95884,95838]}";
		PersonList personlist = new PersonList();
		Information info = new Information();
		ArrayList<Integer> allContents = null;
		try {
			allContents = info.codeParsing();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated catch block
		Functions func = new Functions();

		 //String temp2 =
		// "{mongId:1,rate:5.0:96262:0},{mongId:1,rate:2.0:96261:0},{mongId:1,rate:2.5:96260:0},{mongId:1,rate:4.0:96258:0},{mongId:1,rate:4.5:96256:0},{mongId:1,rate:5.0:96200:0},{mongId:1,rate:2.0:96001:0},{mongId:2,rate:4.0:96262:0},{mongId:2,rate:2.0:96261:0},{mongId:2,rate:3.0:96260:0},{mongId:2,rate:5.0:96258:0},{mongId:2,rate:4.5:96256:0},{mongId:2,rate:5.0:96200:0},{mongId:2,rate:2.5:96001:0},{mongId:2,rate:4.0:95956:0},{mongId:2,rate:3.0:95678:0},{mongId:2,rate:4.5:95665:0},{mongId:2,rate:5.0:96038:0},{mongId:1,rate:5.0:95884:0},{mongId:1,rate:1.5:95594:0},";
		FamousContents famousContents = new FamousContents();
		personlist.savePerson(args[1], func, allContents, famousContents);
		// contents�� ���� �ִ� �۾�
		// ���� : userId�� savePerson�� getExpection���� ���� 0���� �����ϵ��� �������� ��.

		//�α���� 10��, ��õ args[2]�� �����ֱ� * 10���� ������� ������(MONG���� ���� txt����)
		int famousSize = 10;
		int recommendSize = Integer.parseInt(args[2]);
		
		// �α� contents ã�Ƴ�
		famousContents.getFamousContents(10);
		// userId �ް�
		String userId = args[0];
		// �ش��ϴ� ��õ contents ã�Ƴ� 
		RecommendContents recommendContents = new RecommendContents(recommendSize,func,userId, allContents, personlist);

		RealContents rc = new RealContents(recommendContents, famousContents, info.getCount(),recommendSize, famousSize);
		ArrayList recom = rc.getRecommendlist();
		ArrayList famous = rc.getFamouslist();
		
		for (int i = 0; i < recommendSize*10; i++) {
			System.out.print(recom.get(i) + "==");
		}
		System.out.print("split");
		
		// �α� ������ ������ famousSize���� ���� �� ���. (��� ���� ������ �߻�)
		int size = famousSize*10;
		if(size>famous.size())
			size = famous.size(); 
		for (int i = 0; i < size; i++) {
			System.out.print(famous.get(i) + "==");
		}
		// user�� �򰡳����� ���� ����鸸 expection�� ����.
		// System.out.println(personlist.person.get().cultId);
		// �� �˰������� ���� �ݴ��� ����� 1���� �ָ� �ݴ�� �ڽ��� 4���� �ްԵǴ� ������ ����
		// �̸� item��� ��õ�ý����� �����ϸ� ���� ��Ȯ�� ����� ���� ��
	}
}