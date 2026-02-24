package edu.monmouth.CS205.computingDevice;

public class Desktop implements ComputingDevice {
	protected String deviceName;
	protected boolean wifiEnabled;
	
	public Desktop(String deviceName, boolean wifiEnabled) {
		setDeviceName(deviceName);
		setWifiEnabled(wifiEnabled);
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
		System.out.println("Keyboard connected");

	}

	@Override
	public void communicate() {
		System.out.println("Ethernet connected");

	}

	@Override
	public String toString() {
		return "Desktop [deviceName=" + deviceName + ", wifiEnabled=" + wifiEnabled + "]";
	}

	public boolean isWifiEnabled() {
		return wifiEnabled;
	}

	public void setWifiEnabled(boolean wifiEnabled) {
		this.wifiEnabled = wifiEnabled;
	}
	
	

}
