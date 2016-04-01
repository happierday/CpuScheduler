
import java.util.LinkedList;

public class Queues<T>{
	
	public LinkedList<T> list;
	
	public int size = 0;
	public Queues(){
		list = new LinkedList<T>();
	}
	
	public void insert(T pcb){
		
		list.add(pcb);
		++size;
	}

	public boolean isEmpty(){
		if(list.isEmpty()) return true;
		else return false;
	}
	
	public T get(int n){
		return list.get(n);
	}
	
	public T getFirst(){
		return list.getFirst();
	}
	
	public T getLast(){
		return list.getLast();
	}
	
	public void remove(int n){
		list.remove(n);
		size--;
	}
	
	public void removeFirst(){
		list.removeFirst();
		size--;
	}
}