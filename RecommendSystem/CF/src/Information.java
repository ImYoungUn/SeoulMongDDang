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
		URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/"+count+"/");

        URLConnection connection = url.openConnection();
 
        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("row");
        for(int i=0; i<descNodes.getLength();i++){
        	 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //ù��° �ڽ��� �������� ���������� ���� ������ ����
 
                if(node.getNodeName().equals("CULTCODE")){
                    cultCode.add(Integer.parseInt(node.getTextContent()));
                }
            }
        }
        return cultCode;
	}
	private int get_total_count() throws Exception{
		URL url = new URL("http://openapi.seoul.go.kr:8088/554a764c56796f7531364e77764250/xml/SearchConcertDetailService/1/2/");
        URLConnection connection = url.openConnection();
 
        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("SearchConcertDetailService");
        for(int i=0; i<descNodes.getLength();i++){
        	 
            for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){ //ù��° �ڽ��� �������� ���������� ���� ������ ����
 
                if(node.getNodeName().equals("list_total_count")){
                	count = Integer.parseInt(node.getTextContent());
                    return count;
                }
 
            }
 
        }
        return 0;
	}
	private Document parseXML(InputStream stream) throws Exception{
		 
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
 
        try{
 
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
 
            doc = objDocumentBuilder.parse(stream);
 
        }catch(Exception ex){
            throw ex;
        }       
 
        return doc;
    }
	public int getCount(){
		return count;
	}
}