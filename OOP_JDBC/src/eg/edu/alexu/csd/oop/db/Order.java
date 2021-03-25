package eg.edu.alexu.csd.oop.db;
 
public class Order {
    public Object[][] Order_Select(Object[][] new_array,int Specific,String order_Type) {
        if((order_Type.equalsIgnoreCase("ASC"))||(order_Type.equals(""))) {
            for (int j = 0; j < new_array.length; j++) {
                for (int z = j + 1; z < new_array.length; z++) {
                    Object temp;
                    if (new_array[j][Specific] instanceof String) {
                        if (new_array[j][Specific].toString().compareTo(new_array[z][Specific].toString()) > 0) {
                            for (int u = 0; u < new_array[0].length; u++) {
                                temp = new_array[j][u];
                                new_array[j][u] = new_array[z][u];
                                new_array[z][u] = temp;
                            }
                        }
                    } else {
 
                        if (Integer.parseInt(new_array[j][Specific].toString()) > Integer.parseInt(new_array[z][Specific].toString())) {
                            for (int u = 0; u < new_array[0].length; u++) {
                                temp = new_array[j][u];
                                new_array[j][u] = new_array[z][u];
                                new_array[z][u] = temp;
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int j = 0; j < new_array.length; j++) {
                for(int z=j+1;z<new_array.length;z++) {
                    Object temp;
                    if(new_array[j][Specific] instanceof String){
                        if (new_array[j][Specific].toString().compareTo(new_array[z][Specific].toString())<0) {
                            for(int u=0;u<new_array[0].length;u++) {
                                temp =  new_array[j][u];
                                new_array[j][u] = new_array[z][u];
                                new_array[z][u] = temp;
                            }
                        }
                    }
                    else{
 
                        if (Integer.parseInt(new_array[j][Specific].toString())<Integer.parseInt(new_array[z][Specific].toString())) {
                            for(int u=0;u<new_array[0].length;u++) {
                                temp =  new_array[j][u];
                                new_array[j][u] = new_array[z][u];
                                new_array[z][u] = temp;
                            }
                        }
                    }
                }
            }
        }
        return new_array;
    }
}