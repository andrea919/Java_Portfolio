package edu.monmouth.CS205.computingDevice;

import java.util.Arrays;

public class Assignment1 {

	public static void main(String[] args) {
		// create ComputingDevice objects and populate an array
		ComputingDevice allDevices[] = createDevices();
		String[] secondDevices = new String[constants.COMPUTING_DEVICE_SIZE];
		
		// using 3 looping constructs, iterate thru the array
		// invoking each object's accessor methods
		// first loop is given here
		System.out.println("Here are the devices using enhanced for loop:");
		for(ComputingDevice cd : allDevices) {
			System.out.println(cd);
			cd.toString();
			cd.acquireInput();
			cd.communicate();
			System.out.println();
		}
		
		// second loop
		System.out.println("Here are the devices using enhanced for second loop:");
		int counter = constants.START_ITERATION_CYCLE;
		boolean isElement = constants.IS_ELEMENT;
		while(isElement) {
			System.out.println(allDevices[counter]);
			allDevices[counter].toString();
			allDevices[counter].acquireInput();
			allDevices[counter].communicate();
			System.out.println();
			
			if (counter < allDevices.length-1) {
				counter++;
			} else {
				isElement = false;
			}
		}
		
		// third loop
		System.out.println("Here are the devices using enhanced for third loop:");
		counter = constants.START_ITERATION_CYCLE;
		do {
			System.out.println(allDevices[counter]);
			allDevices[counter].toString();
			allDevices[counter].acquireInput();
			allDevices[counter].communicate();
			System.out.println();
			
			counter++;
			
		} while (counter < allDevices.length);
			
		
		
		// invoke method to swap 2 elements
		swap(2, 8, allDevices);
		System.out.println("After swap"); 
		
		// start a new iteration cycle to prove the swap
		// and populate the secondDevice Array 
		counter = constants.START_ITERATION_CYCLE;
		isElement = constants.IS_ELEMENT;
		
		while(isElement) {
			secondDevices[counter] = allDevices[counter].toString();
			System.out.println(secondDevices[counter]);
			
			if (counter < secondDevices.length-1) {
				counter++;
			} else {
				isElement = false;
			}
		}
		
		// invoke method to populate an array of Laptop objects only
		ComputingDevice laptopsOnly[] = findLaptops(allDevices);
		
		// print each instance in the laptopsOnly array
		// invoke each object's toString() method
		System.out.println("Here are the Laptop objects:");
		for (ComputingDevice cd: laptopsOnly) {
			System.out.println(cd.toString());
		}
		
		// use Arrays.copyOf to copy the first 3 objects from the allDevices array to a new array
		ComputingDevice[] threeDevices = Arrays.copyOf(allDevices, constants.MAX_DEVICES_COPY);
		
		//  print each element in the new array
		// using the toString() method from the Arrays class
		System.out.println("Here are the first 3 objects:");
		System.out.println(Arrays.toString(threeDevices));
		
	}
	
	public static ComputingDevice[] createDevices() {
		// declare and populate an an array of references to
		//  ComputingDevices (15 or more)
		ComputingDevice[] computingDeviceArr = new ComputingDevice[constants.COMPUTING_DEVICE_SIZE];
		// here are 10 objects you can use to populate your array
		// also add objects from the classes you create for a total of 15 or more
		computingDeviceArr[0] = new Laptop("Lenovo ThinkPad",  3);
		computingDeviceArr[1] = new Laptop("MacBook", 2);
		computingDeviceArr[2] = new Laptop("Lenovo Yoga",  4);
		computingDeviceArr[3] = new Laptop("MacBook", 3);
		computingDeviceArr[4] = new Laptop("Lenovo Yoga",  3);
		computingDeviceArr[5] = new Laptop("Lenovo Legion", 4);
		computingDeviceArr[6] = new Desktop("Dell", false);
		computingDeviceArr[7] = new Laptop("Dell XPS", 3);
		computingDeviceArr[8] = new Desktop("HP Pavillion", true);
		computingDeviceArr[9] = new Desktop("Asus ROG", true);
		computingDeviceArr[10] = new AirpodsMax("Andrea's Airpods max", true);
		computingDeviceArr[11] = new AppleWatch("Andrea's Apple watch", 89);
		computingDeviceArr[12] = new AppleWatchUltra("Andrea's new Apple Watch Ultra", 68, true);
		computingDeviceArr[13] = new AirpodsMax("Broken Airpods max", false);
		computingDeviceArr[14] = new AppleWatch("Old Apple watch", 149);
		computingDeviceArr[15] = new AppleWatchUltra("Broken Apple Watch Ultra", 65, false);
		
		
		// change to return the array created in this method
		return computingDeviceArr;
		
	}
	
	public static void swap(int firstOffset, int secondOffset, ComputingDevice[] sourceArray) {
		// swap element from firstOffset to secondOffset 
		ComputingDevice temp = sourceArray[firstOffset];
		sourceArray[firstOffset] = sourceArray[secondOffset];
		sourceArray[secondOffset] = temp;
		
		System.out.println("Successfully swapped elements in positions: "+firstOffset+" & "+secondOffset);
		
	}
	
	public static ComputingDevice[] findLaptops(ComputingDevice[] sourceArray) {
		
		int counter = constants.START_ITERATION_CYCLE;
		int laptopsCount = constants.START_ITERATION_CYCLE;
		
		for(ComputingDevice cd : sourceArray) {
			if(cd instanceof Laptop) {
				laptopsCount++;
			}
		} 
		
		ComputingDevice laptopsOnly[] = new ComputingDevice[laptopsCount];
		
		// iterate thru the array passed in-use the instanceof operator to find 
		//  Laptop objects-place the Laptop objects in an array allocated and
		// created in this method. This is the array to be returned
		int index = constants.START_ITERATION_CYCLE;
		for(ComputingDevice cd : sourceArray) {
			if(cd instanceof Laptop) {
				laptopsOnly[index] = cd;
				index++;
			}
		} 
		
		// change to return the array created in this method
		return laptopsOnly;
	}

}
