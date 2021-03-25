package eg.edu.alexu.csd.oop.db;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImpTable implements Implement {
    private Facade facade = new Facade();
    private BasicFacade basicfacade = new BasicFacade();
    private String name;
    private String sub;
    private final String nsPrefix ="xs:";

    ImpTable(String name, String sub){
        this.name = name;
        this.sub = sub;
    }
    @Override
    public boolean create() {
        if(facade.Tables.getTables().contains(name.toLowerCase()))
            return false;
        else {
            try {
                if(!(facade.Tables.GetDefaultTable().equals("")))
                    basicfacade.saveORread.Save(name,0);
                facade.Tables.addTable(name.toLowerCase());
                creat(name, sub);
                return true;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    @Override public boolean drop() {
        try {
            Files.deleteIfExists(Paths.get(facade.path.getdefaultDB()+File.separator+name+".xml"));
            Files.deleteIfExists(Paths.get(facade.path.getdefaultDB()+File.separator+name+".xsd"));
            facade.Tables.RemoveFromTable(name.toLowerCase());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void creat (String Table_name, String sub) throws ParserConfigurationException, TransformerException {
        sub=sub.replace("(","");
        sub=sub.replace(")","");
        String arr[]=sub.split(",");
        String[][] type = new String[arr.length][2];
        facade.base.openBase(arr.length);
        for(int i=0;i<arr.length;i++){
            arr[i]=facade.queryOperations.remove_extra_spaces(arr[i]);
            String subarr[]=arr[i].split(" ");
            type[i][0]=subarr[0];
            type[i][1]=subarr[1];
            facade.base.AddToBase(i, type[i][0]);
        }
        facade.xsd.SetXSD(type);
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder build=dbf.newDocumentBuilder();
        Document doc = build.newDocument();
        Element root = doc.createElement(Table_name);
        doc.appendChild(root);
        TransformerFactory TF=TransformerFactory.newInstance();
        Transformer t=TF.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource s=new DOMSource(doc);
        Result result =new StreamResult(new File(facade.path.getdefaultDB()+File.separator+Table_name+".xml"));
        t.transform(s,result);

        Document doc2 = build.newDocument();
        Element schemaRoot = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI,  nsPrefix+"schema");
        doc2.appendChild(schemaRoot);
        Element subroot = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"element");
        subroot.setAttribute("name", Table_name);
        schemaRoot.appendChild(subroot);
        Element a = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"complexType");
        subroot.appendChild(a);
        Element b = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"sequence");
        a.appendChild(b);
        Element element2 = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"element");
        element2.setAttribute("name", "Row");
        b.appendChild(element2);
        Element s1 = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"complexType");
        element2.appendChild(s1);
        Element s2 = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"sequence");
        s1.appendChild(s2);
        Element id = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"element");
        for(int i=0; i<arr.length; i++){
            Element column = doc2.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, nsPrefix+"element");
            column.setAttribute("name", type[i][0]);
            column.setAttribute("type", nsPrefix+type[i][1]);
            s2.appendChild(column);
        }
        DOMSource domSource2 = new DOMSource(doc2);
        t.transform(domSource2, new StreamResult(new File(facade.path.getdefaultDB()+File.separator+Table_name+".xsd")));
    }

}
