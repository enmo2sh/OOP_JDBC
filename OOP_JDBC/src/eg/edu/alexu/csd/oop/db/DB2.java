
        package eg.edu.alexu.csd.oop.db;

        import org.w3c.dom.*;
        import org.xml.sax.SAXException;
        import javax.xml.XMLConstants;
        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.*;
        import javax.xml.transform.dom.DOMSource;
        import javax.xml.transform.stream.StreamResult;
        import javax.xml.transform.stream.StreamSource;
        import javax.xml.validation.Schema;
        import javax.xml.validation.SchemaFactory;
        import javax.xml.validation.Validator;
        import javax.xml.xpath.XPath;
        import javax.xml.xpath.XPathConstants;
        import javax.xml.xpath.XPathExpressionException;
        import javax.xml.xpath.XPathFactory;
        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.Base64;
        import java.util.LinkedList;

public class DB2 implements Database {
    private static final Exception SQLException = null;
    private ArrayList<String>[] Base;
    private Factory factory  = new Factory();
    private Parser pars = null;
    private String ex=System.getProperty("user.dir");
    private String path;
    private  String defaultDB= "";
    private String Sub;
    private String s,t;
    private String DefaultTable="";
    private final String nsPrefix ="xs:";
    private LinkedList<String> Tables=new LinkedList<String>();

    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        if(dropIfExists) {
            try {
                executeStructureQuery("DROP DATABASE " + databaseName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            executeStructureQuery("CREATE DATABASE " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        try {

            pars = factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (pars.validation(query)) {
            query=remove_extra_spaces(query);
            if(query.contains("(")){
                Sub= query.substring(query.indexOf('('),query.indexOf(')')+1);
                query=query.replace(Sub," ");
            }

            String[] split=query.split(" ");
            if(split[1].equalsIgnoreCase("database")){
           /* if(split[2].contains(File.separator))
                path=split[2];
            else*/
                path=ex+File.separator+split[2];
                if(split[0].equalsIgnoreCase("create")){
                    File DataBase=new File(path);
                    defaultDB=path;
                    Tables.clear();
                    if((DataBase.mkdirs())||(!DataBase.mkdirs()))
                        return true;
                }
                else if(split[0].equalsIgnoreCase("drop")){
                    try {
                        File dir=new File(path);
                        if (dir.isDirectory()) {
                            File[] Children=dir.listFiles();
                            for(int i=0;i<Children.length;i++)
                                Children[i].delete();
                        }
                        Files.deleteIfExists(Paths.get(path));
                        defaultDB="";
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(split[1].equalsIgnoreCase("table")) {
                if (!defaultDB.equals("")) {
                    if (split[0].equalsIgnoreCase("create")) {
                        if(Tables.contains(split[2].toLowerCase()))
                            return false;
                        else {
                            try {
                                if(!DefaultTable.equals(""))
                                    Save(split[2]);

                                DefaultTable=split[2];
                                create(split[2], Sub);
                                Tables.add(split[2].toLowerCase());
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
                    } else if (split[0].equalsIgnoreCase("drop")) {
                        try {
                            Files.delete(Paths.get(defaultDB+File.separator+split[2]+".xml"));
                            Files.delete(Paths.get(defaultDB+File.separator+split[2]+".xsd"));
                            Tables.remove(split[2].toLowerCase());
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    throw new RuntimeException("No Database Found");
                }
            }}
        return false;
    }
    @Override
    public Object[][] executeQuery(String query) throws SQLException {

        try {
            pars = factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (pars.validation(query)) {
            int m=0,n = 0,s=0,condition2=0,c2=0,c1=0;
            Object[][] table = null;
            Object value="";
            m= Base[0].size()-1;
            LinkedList<Integer> list = null;
            String temp=query.toLowerCase(),condition;
            if (temp.contains("where")) {
                condition = temp.substring(temp.indexOf("where")+6,temp.length());
                m= where_condition(condition).size();
                list =where_condition(condition);
            }
            String[] split=temp.split("( )|(\\*)|(\\,)");
            String[] noC = null;
            for (int i=0;i<split.length;i++) {
                if (split[i].contentEquals(""))
                    s++;
                if (split[i].contains("from")) {
                    n= i-1-s;
                    break;
                }
            }
            if (!temp.contains("*")) {
                condition2=1;
                noC= new  String[n];
                for (int i=0;i<n;i++) {
                    int k=i+1;
                    if (!split[k].contentEquals(""))
                        noC[i]=split[k];
                    else
                        noC[i]=split[k+1];
                }
            }else {
                n=Base.length;
            }
            table = new Object[m][n];
            for (int i=1;i<Base[0].size();i++){
                c2=0;
                for (int j=0;j<Base.length;j++){
                    value=Base[j].get(i);
                    if (((String)value).contains("'")){
                        value=((String)value).substring(1,((String)value).length()-1 );
                    }else{
                        value= Integer.parseInt(((String)value));
                    }
                    if ((temp.contains("where")) ){
                        for (int k=0;k<list.size();k++) {
                            if (i==list.get(k)) {
                                if (((condition2==1)&&(find (Base[j].get(0),noC)))|| (condition2==0)){
                                    table[c1][c2]= value;
                                    if (c2==n-1){
                                        c1++;
                                    }
                                    c2++;
                                }
                                break;
                            }
                        }
                    }else if (((condition2==1)&&(find (Base[j].get(0),noC)))||(condition2==0)){
                        table[c1][c2]= value;
                        if (c2==n-1){
                            c1++;
                        }
                        c2++;
                    }
                }
            }
            return table;
        }
        return null;
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
        int result=0;
        try {

            pars = factory.Object(query);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (pars.validation(query)) {
            String h=query;
            query=remove_extra_spaces(query);
            if(h.contains("(")) {
                s = h.substring(query.indexOf('('), query.indexOf(')') + 1);
                h= h.replace(s, " ");
            }
            String []split=h.split(" ");
            if(split[0].equalsIgnoreCase("insert")){
                try {
                    Save(split[2]);
                    read(split[2]);
                    DefaultTable=split[2];
                    add(query);
                    result=1;
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
            }
            else if(split[0].equalsIgnoreCase("delete")){
                if((Tables.contains(split[2].toLowerCase()))&&(Base[0].size()>1)){
                    try {
                        Save(split[2]);
                        read(split[2]);
                        DefaultTable=split[2];
                        result = Delete(query);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(split[0].equalsIgnoreCase("update")){
                if((Tables.contains(split[1].toLowerCase()))&&(Base[0].size()>1)) {
                    try {
                        Save(split[1]);
                        read(split[1]);
                        DefaultTable=split[1];
                        result = Update_Path(query);
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (TransformerException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    private void create (String Table_name, String sub) throws ParserConfigurationException, TransformerException {
        sub=sub.replace("(","");
        sub=sub.replace(")","");
        String arr[]=sub.split(",");
        String[][] type = new String[arr.length][2];
        Base = new ArrayList[arr.length];
        for(int i=0;i<arr.length;i++){
            Base[i]=new ArrayList<>();
            arr[i]=remove_extra_spaces(arr[i]);
            String subarr[]=arr[i].split(" ");
            type[i][0]=subarr[0];
            type[i][1]=subarr[1];
            Base[i].add(type[i][0]);
        }

        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder build=dbf.newDocumentBuilder();
        Document doc = build.newDocument();
        Element root = doc.createElement(Table_name);
        doc.appendChild(root);
        TransformerFactory TF=TransformerFactory.newInstance();
        Transformer t=TF.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource s=new DOMSource(doc);
        Result result =new StreamResult(new File(defaultDB+File.separator+Table_name+".xml"));
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
        t.transform(domSource2, new StreamResult(new File(defaultDB+File.separator+Table_name+".xsd")));
    }

    private void add(String query) throws ParserConfigurationException, TransformerException, SAXException, XPathExpressionException, IOException {
        s= query.substring(query.indexOf('('),query.indexOf(')')+1);
        query=query.replace(s," ");
        if(!query.contains("(")) {
            t=s;
            s="("+Base[0].get(0);
            for(int i=1; i<Base.length; i++)
                s+=","+Base[i].get(0);
            s+=")";
        }
        else
            t= query.substring(query.indexOf('('),query.indexOf(')')+1);
        query=query.replace(t," ");

        String data[][]=splitting(s,t);
        String []split=query.split(" ");
        String Table_name=split[2];
        addReal(Table_name,data);
    }


    public void addReal(String Table_name,String firstIn[][]) {
        String [][] arr = check(Base, firstIn);
        for(int i=0;i<arr.length; i++)
            Base[i].add(arr[i][1]);

    }

    private void validation(String xmlFile, String xsdFile) throws SAXException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdFile));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(xmlFile)));
    }
    //column order
    private String[][] check (ArrayList <String>[] Base, String [][] arr){
        String [][] input = new String[arr.length][2];
        for(int i=0; i<Base.length; i++){
            int flag = 0;
            input[i][0] = Base[i].get(0);
            String first = Base[i].get(0).toLowerCase();
            for (int j=0; j<arr.length;j++){
                String sec = arr[j][0].toLowerCase();
                if(first.equals(sec)){
                    input[i][1]=arr[j][1];
                    flag=1;
                    break;
                }
            }
            if(flag==0)
                input[i][1]=null;
            flag =0;
        }
        return input;
    }

    private String [][] splitting(String s,String t){
        s=s.replace("(","");
        s=s.replace(")","");
        t=t.replace("(","");
        t=t.replace(")","");
        String [] names1=s.split(",");
        String [] names2=t.split(",");
        String[][] result=new String[names1.length][2];
        for(int x=0;x<names1.length;x++){
            names1[x]=remove_extra_spaces(names1[x]);
            names2[x]=remove_extra_spaces(names2[x]);
            result[x][0]=names1[x];
            result[x][1]=names2[x];
        }
        return result;
    }

    private static String remove_extra_spaces (String o){
        String n="";
        if(o.charAt(0)!=' ')
            n=n+o.charAt(0);
        for(int i=1;i<o.length()-1;i++){
            if((o.charAt(i)==' ')&&(o.charAt(i+1)==' '))
                n=n;
            else
                n=n+o.charAt(i);
        }
        if(o.charAt(o.length()-1)!=' ')
            n=n+o.charAt(o.length()-1);

        return n;

    }

    private int Delete(String query) throws ParserConfigurationException, IOException, SAXException, TransformerException, XPathExpressionException {
        String []split=query.split("WHERE ");
        int res;
        if(split.length>1) {
            LinkedList<Integer> accepted=new LinkedList<Integer>();
            accepted= where_condition(split[1]);
            res=accepted.size();
            int z=0;
            for(int x=0;x<accepted.size();x++) {
                for (int j = 0; j < Base.length; j++) {
                    Base[j].remove(Base[j].get(accepted.get(x)-z));
                }
                z++;
            }
        }
        else{
            int l=Base[0].size()-1;
            for(int j=0;j<Base.length;j++){
                for(int f=1;f<Base[j].size();f++){
                    Base[j].remove(Base[j].get(f));
                    f--;
                }
            }
            res= l;
        }
        return res;
    }

    public boolean find (String s, String[] arr) {
        s=s.toLowerCase();
        for (int i=0;i<arr.length;i++)
        {
            if (s.contentEquals(arr[i]))
            {
                return true;
            }
        }
        return false;
    }

    private LinkedList<Integer>  where_condition (String condition) {
        LinkedList<Integer> target1=new LinkedList<Integer>();
        String[] oop;
        int type=0;
        String sara=condition.toLowerCase();
        if(sara.contains("and")) {
            oop=condition.split("((?i)\\band\\b)");
            type=1;
        }
        else if (sara.contains("or"))
        {
            oop=condition.split("((?i)\\bor\\b)");
            type=0;
        }
        else if(sara.contains("not"))
        {
            String oop1=sara.replace("not","");
            // oop.length=oop1.length-1;
            oop=oop1.split(",");
            type=2;
        }
        else
        {
            oop=condition.split(",");
            type=0;
        }
        int counting = 0;
        for(int i=1; i<Base[0].size();i++) {
            counting=0;
            for(int e=0;e<Base.length;e++) {
                for(int z=0;z< oop.length;z++) {
                    oop[z]=remove_extra_spaces(oop[z]);
                    oop[z]=oop[z].replaceAll(" ","");
                    String temp_space=oop[z];
                    String[] y = oop[z].split("(\\>)|(\\<)|(\\=)");
                    temp_space=temp_space.replace(y[0],"");
                    temp_space=temp_space.replace(y[1],"");
                    if(temp_space.equals("=")) {
                        //System.out.println(name.getTagName() +" "+ name.getTextContent());
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&(Base[e].get(i).equalsIgnoreCase(y[1]))) {
                            counting++;
                        }
                    }
                    else if(temp_space.equals(">")) {
                        //System.out.println(name.getTagName() +" "+ (name.getTextContent())+ " "+ Double.valueOf(y[1]));
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&((Double.valueOf(Base[e].get(i)))>(Double.valueOf(y[1])))) {
                            counting++;
                        }
                    }
                    else if(temp_space.equals("<"))
                    {
                        //System.out.println(name.getTagName() +" "+ name.getTextContent());
                        if ((Base[e].get(0).equalsIgnoreCase(y[0]))&&(Double.valueOf(Base[e].get(i))<Double.valueOf(y[1])))
                        {
                            counting++;
                        }
                    }
                }
            }
            if((counting==oop.length)&&(type==1)) {
                target1.add(i);
            }
            else if((counting>=1)&&(type==0)) {
                target1.add(i);
            }
            else if((type==2)&&(counting==0)) {
                target1.add(i);
            }
        }
        return target1;
    }

    private int UpDate_Method (String Table_Name, String condition ,String New_value) {
        String []temp=New_value.split(",");
        String [][]new_date = new String[temp.length][2];

        for(int q=0;q<temp.length;q++) {
            temp[q]=remove_extra_spaces(temp[q]);
            String[] equal=temp[q].split("=");
            new_date[q][0]=equal[0];
            new_date[q][1]=equal[1];

        }
        LinkedList<Integer> target=new LinkedList();
        if (!(condition.equals("")||condition.equals(" "))) {
            condition=remove_extra_spaces(condition);
            target= where_condition(condition);
        }
        else {
            for(int o=1;o<Base[0].size();o++) {
                target.add(o);
            }

        }

        for(int i=0;i<target.size();i++) {
            for(int j=0;j<Base.length;j++){
                for(int z=0;z<new_date.length;z++) {
                    if (Base[j].get(0).equalsIgnoreCase(new_date[z][0])) {
                        int s= Integer.parseInt(String.valueOf(target.get(i)));
                        String eeee=Base[j].remove(s);
                        ///System.out.println(eeee);
                        Base[j].add(target.get(i),new_date[z][1]);
                    }

                }
            }
        }
        return target.size();
    }

    private int Update_Path (String query ) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        String []query1;
        String Condition;
        String[] query2;
        String[] query3;
        String New_value;
        String Table_Name;
        int num=0;
        String temp=query.toLowerCase();
        if(temp.contains("where")) {
            query1 = query.split("((?i)\\bwhere\\b)");
            Condition =query1[1];
            query2=query1[0].split("((?i)\\bset\\b)");
            New_value =query2[1];
            query3=query2[0].split(" ");
            Table_Name=query3[1];
            num= UpDate_Method (Table_Name, Condition , New_value);
        }
        else {
            query2=query.split("((?i)\\bset\\b)");
            New_value =query2[1];
            query3=query2[0].split(" ");
            Table_Name=query3[1];
            num= UpDate_Method (Table_Name, "" , New_value);
        }
        return num;
    }

    public void print_fun () {
        for(int i=0;i<Base.length;i++){
            for (int j=0; j<Base[i].size();j++){
                System.out.println(Base[i].get(j));
            }
            System.out.println();
        }
    }

    private void Save(String currentTable) throws ParserConfigurationException, IOException, SAXException, TransformerException, XPathExpressionException {
        if(!(currentTable.equalsIgnoreCase(DefaultTable))){
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(defaultDB+File.separator+DefaultTable+".xml");
            Node root = doc.getDocumentElement();
            NodeList nl=doc.getDocumentElement().getChildNodes();
            while(nl.getLength()>0)
                root.removeChild(nl.item(nl.getLength()-1));

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
            StreamResult result = new StreamResult(defaultDB+File.separator+DefaultTable+".xml");
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            for(int i=0;i<Base.length;i++)
                Base[i].clear();

        }

    }

    private void read(String currentTable) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        if (!(currentTable.equalsIgnoreCase(DefaultTable))) {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(defaultDB + File.separator + currentTable + ".xml");
            Node root = doc.getDocumentElement();
            NodeList nl = doc.getElementsByTagName("Row");
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl2= (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
            for (int i=0; i < nl2.getLength(); i++) {
                Node node = nl2.item(i);
                node.getParentNode().removeChild(node);
            }
            if (nl.getLength() > 0) {
                Base = new ArrayList[nl.item(0).getChildNodes().getLength()];
                for(int h=0;h<Base.length;h++)
                    Base[h]=new ArrayList<>();
                for (int i = 0; i < nl.getLength(); i++) {
                    NodeList ns = nl.item(i).getChildNodes();
                    for (int j = 0; j < ns.getLength(); j++) {
                        if(i==0)
                            Base[j].add(0,ns.item(j).getNodeName());
                        Base[j].add(ns.item(j).getTextContent());
                    }
                }
            }
        }
    }
}
