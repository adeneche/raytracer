package haktracer;


public class DynIntArray {

	private int nData;
	private int[] data;
	
	public DynIntArray() {
		this(4);
	}
	
	public DynIntArray(int size) {
		nData = 0;
		data = new int[size];
	}
	
	/**
	 * always added at the end
	 */
	public void append(int item) {
		if (nData == data.length) {
			int[] temp = data;
			data = new int[data.length*2];
			System.arraycopy(temp, 0, data, 0, nData);
		}
		
		data[nData++] = item;
	}
	
	/** make array size == nData */
	public void truncate() {
		if (nData != data.length) {
			Object temp = data;
			data = new int[nData];
			System.arraycopy(temp, 0, data, 0, nData);
		}
	}
	
	int size() { return data.length; }
	
	public void clear() { nData = 0; }
	
	public int length() { return nData; }
	
	public boolean empty() { return nData == 0; }
	
	public int get(int i) { return data[i]; }
	
	
}
