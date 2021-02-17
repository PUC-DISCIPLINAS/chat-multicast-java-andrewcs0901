package util;

import java.io.Serializable;
import java.util.List;

public class DataCollection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1452853551237064550L;
	private List<String> data;
	private String type;
	
	public DataCollection(List<String> data, String type){
		this.data = data;
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public List<String> getData(){
		return this.data;
	}
}
