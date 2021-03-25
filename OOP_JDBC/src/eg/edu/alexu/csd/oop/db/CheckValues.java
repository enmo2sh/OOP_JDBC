package eg.edu.alexu.csd.oop.db;

public class CheckValues {
    Facade facade = new Facade();
    String [][] xsd = facade.xsd.GetXSD();
   public String[][] check (String [][] arr){

       String [][] input = new String[xsd.length][2];
        int count=0;
       for(int i=0; i<xsd.length; i++){
            int flag = 0;
            input[i][0] = xsd[i][0];
            String first = xsd[i][0].toLowerCase();
            for (int j=0; j<arr.length;j++){
                String sec = arr[j][0].toLowerCase();
                if(first.equals(sec)){
                    if (xsd[i][1].equals("int") ) {
                        if(!Character.isDigit(arr[j][1].charAt(0)))
                            break;
                    }
                    else{
                        if(Character.isDigit(arr[j][1].charAt(0)))
                            break;

                    }
                    input[i][1]=arr[j][1];
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                input[i][1]=null;
                count++;
            }

        }
       int no = xsd.length-count;
       if(no!= arr.length)
           input=null;
        return input;
    }
}
