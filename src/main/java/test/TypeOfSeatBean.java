package test;
import java.io.*;
@SuppressWarnings("serial")
public class TypeOfSeatBean implements Serializable {
	private String typeOfSeat;
	private String title;
	public TypeOfSeatBean() {}
	public String getTypeOfSeat() {
		return typeOfSeat;
	}
	public void setTypeOfSeat(String typeOfSeat) {
		this.typeOfSeat = typeOfSeat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
