import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Information {
	private int count;

	public ArrayList<Integer> codeParsing() throws Exception {
		ArrayList<Integer> cultCode = new ArrayList<>();
		int count = get_total_count();
		URL url = new URL(
				"http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/"
						+ count + "/");

		URLConnection connection = url.openConnection();

		Document doc = parseXML(connection.getInputStream());
		NodeList descNodes = doc.getElementsByTagName("row");
		int temp = 0;
		boolean exceptCode = false;
		boolean exceptTitle = false;
		for (int i = 0; i < descNodes.getLength(); i++) {

			for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) { // 첫번째
																												// 자식을
																												// 시작으로
																												// 마지막까지
																												// 다음
																												// 형제를
																												// 실행

				if (node.getNodeName().equals("CULTCODE")) {
					temp = Integer.parseInt(node.getTextContent());
					// cultCode.add(Integer.parseInt(node.getTextContent()));
				} else if (node.getNodeName().equals("CODENAME")) {
					String text = node.getTextContent();
					if (text.equals("문화교양/강좌") || text.equals("기타")||text.equals("영화")) {
						exceptCode = true;
					} else {
						exceptCode = false;
					}
				} else if (node.getNodeName().equals("TITLE")) {
					String text = node.getTextContent();
					if (text.contains("독주")||text.contains("소극장")) {
						exceptTitle = true;
					} else {
						exceptTitle = false;
					}
					if (exceptCode==true || exceptTitle==true) {
						// pass
					} else {
						cultCode.add(temp);
					}
				}
			}
		}
		return cultCode;
	}

	private int get_total_count() throws Exception {
		URL url = new URL(
				"http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/2/");
		URLConnection connection = url.openConnection();

		Document doc = parseXML(connection.getInputStream());
		NodeList descNodes = doc.getElementsByTagName("SearchConcertDetailService");
		for (int i = 0; i < descNodes.getLength(); i++) {

			for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) { // 첫번째
																												// 자식을
																												// 시작으로
																												// 마지막까지
																												// 다음
																												// 형제를
																												// 실행

				if (node.getNodeName().equals("list_total_count")) {
					count = Integer.parseInt(node.getTextContent());
					return count;
				}

			}

		}
		return 0;
	}

	private Document parseXML(InputStream stream) throws Exception {

		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;

		try {

			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);

		} catch (Exception ex) {
			throw ex;
		}

		return doc;
	}

	public int getCount() {
		return count;
	}
}
