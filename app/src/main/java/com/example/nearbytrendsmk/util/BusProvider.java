package com.example.nearbytrendsmk.util;

import com.squareup.otto.Bus;


public class BusProvider {
	private static Bus mInstance = null;

	private BusProvider() {

	}

	public static Bus getInstance() {
		if (mInstance == null) {
			mInstance = new Bus();
		}
		return mInstance;
	}
}
