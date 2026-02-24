package edu.monmouth.CS205.computingDevice;

public class AppleWatch implements ComputingDevice {

	protected String deviceName;
	protected int beatsPerMinute;
	
	public AppleWatch(String deviceName, int beatsPerMinute) {
		setDeviceName(deviceName);
		setBeatsPerMinute(beatsPerMinute);
	}
	
	@Override
	public String getDeviceName() {
		return deviceName;
	}

	@Override
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	public void acquireInput() {
		System.out.println("Recording");

	}

	@Override
	public void communicate() {
		System.out.println("Bluetooth connected");

	}

	public int getBeatsPerMinute() {
		return beatsPerMinute;
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}

	@Override
	public String toString() {
		return "AppleWatch [deviceName=" + deviceName + ", beatsPerMinute=" + beatsPerMinute + "]";
	}
	
	

}
