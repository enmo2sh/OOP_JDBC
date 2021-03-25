package eg.edu.alexu.csd.oop.jdbc;

import java.util.logging.Logger;

public class Time_singleton {
	private static  long startTime;
	
	 public void set(long x)
	 {
	   /* if(startTime==0){
	    		startTime=0;	
	    }*/
		startTime=x;
	   // return startTime;
    }
	 public long get() {
	    return startTime;
    }
	 
}
