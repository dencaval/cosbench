package com.intel.cosbench.driver.generator;

import java.util.Random;

public class NormalDistribution implements Distribution {

    private double mean;
    private double stdDeviation;
    private Random rand;

    public NormalDistribution() {
        this(0,1);
    }

    public NormalDistribution(double mean, double stdDeviation) {
        if(stdDeviation <= 0)
            throw new IllegalArgumentException("stdDeviation must be > 0.");
        rand = new Random();
        this.mean = mean;
        this.stdDeviation = stdDeviation;
    }

    public double generate() {
        return rand.nextGaussian() * stdDeviation + mean;
    }

    public double generate(Random random) {
        return random.nextGaussian() * stdDeviation + mean;
    }
}