package com.zkt.zktspringjpa.sdk.Enum;

public enum OnOffenum {
	ON(1),
	OFF(0);

	private final int State;

	OnOffenum(int State) {
		this.State = State;
	}

	public int getOnOffState() {
		return State;
	}
}
