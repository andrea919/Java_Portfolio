package edu.monmouth.CS205.computingDevice;

public class AirpodsMax implements ComputingDevice {
	
	// ASK IF IT IS A COMPUTING DEVICE
	protected String deviceName;
	protected boolean bluetoothEnabled;
	
	public AirpodsMax(String deviceName, boolean bluetoothEnabled) {
		setDeviceName(deviceName);
		setBluetoothEnabled(bluetoothEnabled);
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

	public boolean isBluetoothEnabled() {
		return bluetoothEnabled;
	}

	public void setBluetoothEnabled(boolean bluetoothEnabled) {
		this.bluetoothEnabled = bluetoothEnabled;
	}

	@Override
	public String toString() {
		return "AirpodsMax [deviceName=" + deviceName + ", bluetoothEnabled=" + bluetoothEnabled + "]";
	}
	
	

}
