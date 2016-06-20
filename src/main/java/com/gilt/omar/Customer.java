package com.gilt.omar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onouhi on 18/06/2016.
 */
public class Customer {

	List<Paint> preferedPaints = new ArrayList<Paint>();


	public List<Paint> getPreferedPaints() {
		return preferedPaints;
	}

	public void setPreferedPaints(Paint paint) {
		preferedPaints.add(paint);
	}
	public Integer getnumOfPreferedPaints(){
		return preferedPaints.size();
	}


}

