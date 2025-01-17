import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by youngun on 2017-09-12.
 */
public class RealContents extends Thread {
	private int count;
	private ArrayList<String> recommendlist;
	private ArrayList<String> famouslist;
	private String[] codeList;
	private String[] expectScoreList;
	private String[] famousCodeList;
	private String[] famousScoreList;
	public int recommendSize = 0;
	public int famousSize = 0;
	URL url;
	URLConnection connection;
	Document doc;

	RealContents(RecommendContents rc, FamousContents fc, int count, int recommendSize, int famousSize) {

		codeList = rc.getCode();
		expectScoreList = rc.getScore();
		famousCodeList = fc.getCode();
		famousScoreList = fc.getScore();
		this.count = count;
		if(rc.getCode().length<recommendSize)
			this.recommendSize = rc.getCode().length;
		else
			this.recommendSize = recommendSize;
		this.famousSize = famousSize;
		makeList();
	}

	public void makeList() {
		recommendlist = new ArrayList<String>();
		famouslist = new ArrayList<String>();
		boolean recom = true, famo = true;

		int c1 = 0, c2 = 0, c3 = 0;
		int rSize = 0, fSize = 0;
		String nodeName;
		String textName;
		try {
			url = new URL(
					"http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/"
							+ count + "/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("url실패");
		}

		try {
			connection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("connection실패");
		}

		try {
			doc = parseXML(connection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("doc실패1");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("doc실패2");
			e.printStackTrace();
		}
		int famousMin=0;
		//인기순위 TOP10 : 10개 vs 평가된 인기순위개수 : 3개
		if(famousSize<famousCodeList.length)
			famousMin = famousSize;
		else
			famousMin = famousCodeList.length;
		
		while (rSize < recommendSize || fSize < famousMin) {
			NodeList descNodes = doc.getElementsByTagName("row");
			for (int tm = 0; tm < descNodes.getLength(); tm++) {
				for (Node node = descNodes.item(tm).getFirstChild(); node != null; node = node.getNextSibling()) { // 첫번째
					nodeName = node.getNodeName();
					textName = node.getTextContent();
					if (nodeName.equals("CULTCODE")) {
						recom = false;
						famo = false;
						if (rSize < recommendSize) {
							if (textName.compareTo(codeList[rSize]) == 0) {
								recommendlist.add(textName);
								recommendlist.add(expectScoreList[rSize]);
								recom = true;
								rSize++;
							}
						}
						if (fSize < famousMin) {
							// famousCodeList는 원하는 인기순위(ex. 10개)보다 적을 수 있다.
							// System.out.println(k+" : "+famousCodeList[k]);
							// System.out.println(textName);
							if (famousCodeList[fSize] != null && textName.compareTo(famousCodeList[fSize]) == 0) {
								famouslist.add(textName);
								famouslist.add(famousScoreList[fSize]);
								famo = true;
								fSize++;
							}
						}
					}

					if (recom || famo) {
						if (nodeName.equals("CODENAME")) {
							// janre
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("TITLE")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("STRTDATE")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("END_DATE")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("TIME")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("PLACE")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("ORG_LINK")) {
							// homepage
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						} else if (nodeName.equals("MAIN_IMG")) {
							if (recom) {
								recommendlist.add(textName);
							}
							if (famo) {
								famouslist.add(textName);
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<String> getRecommendlist() {
		return recommendlist;
	}

	public ArrayList<String> getFamouslist() {
		return famouslist;
	}
	
	public int getRecommendSize(){
		return recommendSize;
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

}
