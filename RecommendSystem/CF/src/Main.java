import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {

	public static void main(String args[]) {
		// String arg =
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

		// String temp2 =
		// "{mongId:1,rate:5.0:96262:0},{mongId:1,rate:2.0:96261:0},{mongId:1,rate:2.5:96260:0},{mongId:1,rate:4.0:96258:0},{mongId:1,rate:4.5:96256:0},{mongId:1,rate:5.0:96200:0},{mongId:1,rate:2.0:96001:0},{mongId:2,rate:4.0:96262:0},{mongId:2,rate:2.0:96261:0},{mongId:2,rate:3.0:96260:0},{mongId:2,rate:5.0:96258:0},{mongId:2,rate:4.5:96256:0},{mongId:2,rate:5.0:96200:0},{mongId:2,rate:2.5:96001:0},{mongId:2,rate:4.0:95956:0},{mongId:2,rate:3.0:95678:0},{mongId:2,rate:4.5:95665:0},{mongId:2,rate:5.0:96038:0},{mongId:1,rate:5.0:95884:0},{mongId:1,rate:1.5:95594:0},";
		FamousContents famousContents = new FamousContents();
		// userId 받고
		String userId = args[0];
		personlist.savePerson(args[1], func, allContents, famousContents, Integer.parseInt(userId));
		// contents에 값을 넣는 작업
		// 참고 : userId는 savePerson과 getExpection에서 각각 0부터 시작하도록 정해져야 함.

		// 인기순위 10개, 추천 args[2]개 보여주기 * 10개의 정보들로 구성됨(MONG어플 구조 txt참조)
		int famousSize = 10;
		int recommendSize = Integer.parseInt(args[2]);

		// 인기 contents 찾아냄
		famousContents.getFamousContents(10);
		// 해당하는 추천 contents 찾아냄
		RecommendContents recommendContents = new RecommendContents(recommendSize, func, userId, allContents,
				personlist);

		if (info.getCount() == 0) {
			// 서울시 api 에러...
			System.out.println("서울시 공공데이터 api 에러입니다.\n문의 후 즉각 조치하겠습니다.");
		} else {
			RealContents rc = new RealContents(recommendContents, famousContents, info.getCount(), recommendSize,
					famousSize);
			recommendSize = rc.getRecommendSize();	//30개보다 적을 수 있음.
			ArrayList recom = rc.getRecommendlist();
			ArrayList famous = rc.getFamouslist();

			for (int i = 0; i < recommendSize * 10; i++) {
				System.out.print(recom.get(i) + "==");
			}
			System.out.print("split");

			// 인기 컨텐츠 개수가 famousSize보다 작을 때 대비. (사용 수가 적으면 발생)
			int size = famousSize * 10;
			if (size > famous.size())
				size = famous.size();
			for (int i = 0; i < size; i++) {
				System.out.print(famous.get(i) + "==");
			}
			// user가 평가내리지 않은 결과들만 expection에 속함.
			// System.out.println(personlist.person.get().cultId);
			// 현 알고리즘은 나와 반대대는 사람이 1점을 주면 반대로 자신은 4점을 받게되는 단점이 있음
			// 이를 item기반 추천시스템을 도입하면 더욱 정확한 결과가 나올 듯
		}
	}
}
