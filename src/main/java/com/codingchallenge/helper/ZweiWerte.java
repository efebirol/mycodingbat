package com.codingchallenge.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZweiWerte {
	private int valueOne = 0;
	private int valueTwo = 0;

	public ZweiWerte(int a, int b) {
		this.valueOne = a;
		this.valueTwo = b;
	}
}