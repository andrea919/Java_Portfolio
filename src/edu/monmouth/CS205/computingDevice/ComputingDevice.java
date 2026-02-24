package edu.monmouth.CS205.computingDevice;

public interface ComputingDevice {
	
	public String getDeviceName();
	public void setDeviceName(String deviceName);
	public void acquireInput();
	public void communicate();
	
}
