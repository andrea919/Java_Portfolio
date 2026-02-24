package edu.monmouth.CS205.computingDevice;

public class AppleWatchUltra extends AppleWatch {
	
	protected boolean waterResistance;

	public AppleWatchUltra(String deviceName, int beatsPerMinute, boolean waterResistance) {
		super(deviceName, beatsPerMinute);
		setWaterResistance(waterResistance);
	}

	public boolean isWaterResistance() {
		return waterResistance;
	}

	public void setWaterResistance(boolean waterResistance) {
		this.waterResistance = waterResistance;
	}

	@Override
	public String toString() {
		return "AppleWatchUltra [waterResistance=" + waterResistance + ", " + super.toString() + "]";	}
	
}
