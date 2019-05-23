package shared;

import java.util.HashMap;
import java.util.Set;

public class SynchronizedHashMap<K,V> {
	
	private HashMap<K, V> map;
	
	public SynchronizedHashMap() {
		map = new HashMap<K,V>();
	}
	
	public synchronized void put(K key, V val) {
		map.put(key, val);
	}
	
	public synchronized V get(K key) {
		return map.get(key);
	}
	
	public synchronized boolean containsKey(K key) {
		return map.containsKey(key);
	}
	
	public synchronized boolean containsValue(V val) {
		return map.containsValue(val);
	}
	
	public synchronized Set<K> keySet(){
		return map.keySet();
	}
	
	public synchronized V remove(K key) {
		return map.remove(key);
	}
}
