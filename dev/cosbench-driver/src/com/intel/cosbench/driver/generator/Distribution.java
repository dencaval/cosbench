package com.intel.cosbench.driver.generator;

public interface Distribution {

    /**
     Generates a random number according to the implemented random
     distribution.
     */
    public abstract double generate();
}