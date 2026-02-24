package edu.monmouth.CS205.computingDevice;

public class Laptop implements ComputingDevice{
	protected String deviceName;
	protected int weight;
	
	public Laptop(String deviceName, int weight) {
		setDeviceName(deviceName);
		setWeight(weight);
	}
	
	@Override
	public String getDeviceName() {
		return deviceName;
	}

	@Override
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public void communicate() {
		System.out.println("Trackpad active");
	}
	
	@Override
	public void acquireInput() {
		System.out.println("Wifi connecting");
	}

	@Override
	public String toString() {
		return "Laptop [deviceName=" + deviceName + ", weight=" + weight + "]";
	}
	
	
}
