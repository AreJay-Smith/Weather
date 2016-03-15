package com.arejaysmith.weather;

import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 * Created by Urge_Smith on 3/14/16.
 */
public class WeatherDbHelperTest extends TestCase {


    public void testAdd() {
        // setting up test
        int one = 1;
        int two = 2;

        AddHelper addHelper = new AddHelper();

        // testing function
        int result = addHelper.add(one, two);

        // check if test passes
        assertEquals(3, result);
    }



}