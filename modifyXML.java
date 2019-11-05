import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class modifyXML {

    public static final String fXmlFile = "C:\\Users\\xyz\\Desktop\\sample.xml";

    public static void main (String args []) {

        try {
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            printNote(doc.getChildNodes());
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(fXmlFile));
            transformer.transform(domSource, streamResult);
            System.out.println("The XML File was Updated!!!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printNote(NodeList nodeList) {

        for (int count = 0; count < nodeList.getLength(); count++) {

	    Node tempNode = nodeList.item(count);
	    if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
		System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
         if ("medicaidCheck".equals(tempNode.getNodeName())) {
                    tempNode.setTextContent("false");
				    System.out.println("UPdated!!!! " + tempNode.getNodeName());
         }
		 //System.out.println("Node Value =" + tempNode.getTextContent());

		 if (tempNode.hasAttributes()) {
			NamedNodeMap nodeMap = tempNode.getAttributes();
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Node node = nodeMap.item(i);
				System.out.println("attr name : " + node.getNodeName());
			//	System.out.println("attr value : " + node.getNodeValue());
			}
		 }

		 if (tempNode.hasChildNodes()) {
            printNote(tempNode.getChildNodes());
		 }

		    System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
	    }   
        }
    }

}
