package com.heller.util;

import org.junit.Assert;
import org.junit.Test;

import static com.heller.util.PrimeNumberGenerator.isPrimeNumber;
import static com.heller.util.PrimeNumberGenerator.runALongTimeJob;

public class PrimeNumberGeneratorTest {

    @Test
    public void testIsPrime() {
        Assert.assertTrue(isPrimeNumber(2));
        Assert.assertTrue(isPrimeNumber(3));
        Assert.assertTrue(isPrimeNumber(5));
        Assert.assertFalse(isPrimeNumber(4));
        Assert.assertFalse(isPrimeNumber(6));
    }

    /**
     * 本机，普通机器，求第4000个素数，大概耗时 700 ms
     */
    @Test
    public void testLongTimeJob() {
        runALongTimeJob(4000);
    }

}