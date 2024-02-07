public class Print0And1 {
	public static void main(String[] args) throws Exception {
		Print p = new Print();
		
		Thread t1 = new Thread(new Thread0(p)), t2 = new Thread(new Thread1(p));
		
		t1.setName("printer 0");
		t2.setName("printer 1");
		t1.start();
		t2.start();
	}
}

class Thread1 implements Runnable {
	private Print p;
	
	public Thread1(Print p) {
		this.p = p;
	}
	
	public void run() {
		while(!Thread.currentThread().interrupted()) {
			p.print();
			synchronized(this) {
				try {
					notifyAll();
					wait();
				}
				catch(Exception e) {}
			}
		}
	}
}
class Thread0 implements Runnable {
	private Print p;
	
	public Thread0(Print p) {
		this.p = p;
	}
	
	public void run() {
		while(!Thread.currentThread().interrupted()) {
			p.print();
			synchronized(this) {
				try {
					notifyAll();
					wait();
				}
				catch(Exception e) {}
			}
		}
	}
}

class Print {
	private int s = 0;
	
	public synchronized void print() {
		System.out.println(Thread.currentThread().getName() +  "-->" + s++);
	}
}