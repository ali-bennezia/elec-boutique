package fr.alib.elec_boutique.utils;

public class Pair<KeyType, DataType>
{
	private KeyType key;
	private DataType data;
	public KeyType getKey() {
		return key;
	}
	public void setKey(KeyType key) {
		this.key = key;
	}
	public DataType getData() {
		return data;
	}
	public void setData(DataType data) {
		this.data = data;
	}
	
	public Pair(KeyType key, DataType data) {
		super();
		this.key = key;
		this.data = data;
	}
}