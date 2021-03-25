package eg.edu.alexu.csd.oop.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SaveRead {
    private Facade facade = new Facade();

    public void Save(String currentTable,int counter) throws ParserConfigurationException, IOException, SAXException, TransformerException, XPathExpressionException {

        if(((!(currentTable.equalsIgnoreCase(facade.Tables.GetDefaultTable())))&& (facade.Tables.GetDefaultTable()!= ""))||(counter==5)){
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(facade.path.getdefaultDB()+File.separator+facade.Tables.GetDefaultTable()+".xml");
            Node root = doc.getDocumentElement();
            NodeList nl=doc.getDocumentElement().getChildNodes();
            while(nl.getLength()>0)
                root.removeChild(nl.item(nl.getLength()-1));
            ArrayList <String>[] Base= facade.base.GetBase();
            for(int i=1; i<Base[0].size(); i++){
                Element element =doc.createElement("Row");
                for(int j=0;j<Base.length;j++){
                    Element column = doc.createElement(Base[j].get(0));
                    column.appendChild(doc.createTextNode(Base[j].get(i)));
                    element.appendChild(column);
                }
                root.appendChild(element);
            }
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl2= (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
            for (int i=0; i < nl2.getLength(); i++) {
                Node node = nl2.item(i);
                node.getParentNode().removeChild(node);
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(facade.path.getdefaultDB()+File.separator+facade.Tables.GetDefaultTable()+".xml");
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            if(counter!=5) {
                for (int i = 0; i < facade.base.GetBase().length; i++)
                    facade.base.clear(i);
            }

        }

    }

    public void read(String currentTable) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        if (!(currentTable.equalsIgnoreCase(facade.Tables.GetDefaultTable()))){
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(facade.path.getdefaultDB() + File.separator + currentTable + ".xml");
            Node root = doc.getDocumentElement();
            NodeList nl = doc.getElementsByTagName("Row");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl2= (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
            for (int i=0; i < nl2.getLength(); i++) {
                Node node = nl2.item(i);
                node.getParentNode().removeChild(node);
            }
            if (nl.getLength() > 0) {
                facade.base.openBase(nl.item(0).getChildNodes().getLength());
                for (int i = 0; i < nl.getLength(); i++) {
                    NodeList ns = nl.item(i).getChildNodes();
                    for (int j = 0; j < ns.getLength(); j++) {
                        if(i==0)
                            facade.base.AddSpecific(j,0, ns.item(j).getNodeName());
                        facade.base.AddToBase(j, ns.item(j).getTextContent());
                    }
                }
            }
            doc = docBuilder.parse(facade.path.getdefaultDB() + File.separator + currentTable + ".xsd");
            NodeList list = doc.getElementsByTagName("xs:element");
            int count=0;
            int b=0;
            LinkedList<String> data = new LinkedList<>();
            for(int i = 0 ; i < list.getLength(); i++) {
                Element first = (Element)list.item(i);
                if(first.hasAttributes()){
                    count++;
                    if(count==1)
                        facade.Tables.SetDefaultTable(first.getAttribute("name"));
                    else if(count>2){
                        data.add(first.getAttribute("name"));
                        data.add(first.getAttribute("type"));
                    }

                }
            }
            String arr[][] = new String[data.size()/2][2];
            facade.base.openBase(data.size()/2);
            int j=0;
            for (int i=0; i<arr.length; i++){
                facade.base.AddToBase(i,data.get(j));
                arr[i][0]=data.get(j++);
                arr[i][1]=data.get(j++).substring(3);
            }
            facade.xsd.SetXSD(arr);
            arr=facade.xsd.GetXSD();
        }
    }

}
