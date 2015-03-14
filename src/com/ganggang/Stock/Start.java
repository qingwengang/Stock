package com.ganggang.Stock;

public class Start {

	public static void main(String[] args) {
		SetRate setRate = new SetRate();
		setRate.setRateType(1);
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(setRate);
			t.start();
		}
	}

}
