interface Observer {
   void update();
}
class Observable {
	private List<Observer> observers = new ArrayList<Observer>();
	public void addObserver(Observer o) { 
		observers.add(o);
	} 
	public void removeObserver(Observer o) { 
		observers.remove(o);
	}
	protected void notifyObservers() {
		for(Observer obs : observers) {
        	obs.update();
      	}
	}
}
class Sensor extends Observable {
	private int temp;
	public int getTemperature(){ 
		return temp; 
	} 
	public void setTemperature(int val){
		temp = val;
    	notifyObservers();
    }
}
class SensorObserver implements Observer {
	private Sensor s;
	SensorObserver (Sensor s){
		this.s = s; 
		s.addObserver(this);
	}
	public void update(){
		System.out.println("Sensor has changed, new temperature is " + s.getTemperatore());
	}
}