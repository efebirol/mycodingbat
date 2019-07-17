package com.codingchallenge.helper;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Efe
 */
@Getter
@Setter
public class ZweiWerte
{

  private int valueOne = 0;

  private int valueTwo = 0;

  /**
   * @author Efe
   */
  public ZweiWerte(int a, int b)
  {
    this.valueOne = a;
    this.valueTwo = b;
  }
}
