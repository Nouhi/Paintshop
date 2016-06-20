package com.gilt.omar;

/**
 * Created by onouhi on 18/06/2016.
 */
public enum Finish{

    MATTE("M"),
    GLOSS("G");

    private final String code;
    
    Finish(String code) {
        this.code = code;
    }
    public static Finish setCode(String code) throws Exception{
		switch (code) {
		case "M":
			return MATTE;
		case "G":			
			return GLOSS;
		default:
			throw new Exception("wrong code: " + code);
		}
    }
    
    public String getCode(){
        return code;
    }
    
}