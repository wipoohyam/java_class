package kr.ac.green;
import java.io.Serializable;

public class PData implements Serializable {
	private static final long serialVersionUID = 1;
	private int pCode;
	private Object pObject;
	private Object[] pObjects;
	
	// PData�� �ְ�޴´�. PData�� �޾��� ��, Object���·� �ްԵǹǷ� PData�� ����ȯ��
	// �ȿ� �ִ� pCode�� Ȯ���ϰ� �����忡�� pCode = "..." �϶� �� �ൿ�� �̸� �����س��´�.
	
	//Protocol�� ���ڸ� �ִ� ��
	public PData(int pCode) {
		setPCode(pCode);
	}
	//Protocol�� ���ڶ� ��ü(������) 1�� �ִ°�� 
	public PData(int pCode, Object pObject) {
		setPCode(pCode);
		setPObject(pObject);
	}
	//Protocol�� ���ڶ� ��ü ������ �ִ� ���
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
