package com.gilt.omar;

/**
 * Created by onouhi on 18/06/2016.
 */
public class Paint {

	private Integer color;
	private Finish finish;


	public Paint(Integer color, Finish finish) {
		this.color = color;
		this.finish=finish;
	}
	public Paint() {
		super();
	}
	public Finish getFinish() {
		return finish;
	}

	public void setFinish(Finish finish) {
		this.finish = finish;
	}

	public Integer getColor() {
		return color;
	}
	public void setColor(Integer color) {
		this.color = color;
	}
	public boolean equals(Paint paint1 , Paint paint2) {
		if((paint1.getColor()==paint2.getColor())&&(paint1.getFinish().equals(paint2.getFinish()))){
			return true;
		}else{
			return false;
		}
	}

}





