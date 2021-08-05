package kr.ac.green;
import java.io.Serializable;

public class PData implements Serializable {
	private static final long serialVersionUID = 1;
	private int pCode;
	private Object pObject;
	private Object[] pObjects;
	
	// PData를 주고받는다. PData를 받았을 때, Object상태로 받게되므로 PData로 형변환해
	// 안에 있는 pCode를 확인하고 쓰레드에서 pCode = "..." 일때 할 행동을 미리 정의해놓는다.
	
	//Protocol에 숫자만 있는 경
	public PData(int pCode) {
		setPCode(pCode);
	}
	//Protocol에 숫자랑 객체(데이터) 1개 있는경우 
	public PData(int pCode, Object pObject) {
		setPCode(pCode);
		setPObject(pObject);
	}
	//Protocol에 숫자랑 객체 여러개 있는 경우
	public PData(int pCode, Object[] pObjects) {
		setPCode(pCode);
		setPObjects(pObjects);
	}
	public int getPCode() {
		return pCode;
	}

	public void setPCode(int pCode) {
		this.pCode = pCode;
	}

	public Object getPObject() {
		return pObject;
	}

	public void setPObject(Object pObject) {
		this.pObject = pObject;
	}
	public Object getPObjects(int i) {
		return pObjects[i];
	}

	public void setPObjects(Object[] pObjects) {
		this.pObjects = pObjects;
	}
	@Override
	public String toString() {
		return "PData [pCode=" + pCode + ", pObject=" + pObject + ", pObjecs=" + pObjects + "]";
	}
	
}
