package com.intel.cosbench.driver.generator;

import java.util.Random;

public class BimodalDistribution implements Distribution {

    private double coinFlip;
    private NormalDistribution dis1, dis2;
    private Random rand;

    public BimodalDistribution(double mean1, double stdDeviation1,
                               double mean2, double stdDeviation2,
                               double coinFlip) {
        rand = new Random();
        if(stdDeviation1 <= 0 || stdDeviation2 <= 0)
            throw new IllegalArgumentException("stdDeviation1 and "+
              "stdDeviation2 must be > 0.");
        if(coinFlip < 0 || coinFlip > 1)
            throw new IllegalArgumentException("coinFlip must be >= 0 and "+
              "<= 1.");
        dis1 = new NormalDistribution(mean1, stdDeviation1);
        dis2 = new NormalDistribution(mean2, stdDeviation2);
        this.coinFlip = coinFlip;
    }

    public double generate() {
        double d = rand.nextDouble();
        if(d <= coinFlip)
            return dis1.generate();
        else
            return dis2.generate();
    }

    public double generate(Random random) {
        double d = random.nextDouble();
        if(d <= coinFlip)
            return dis1.generate(random);
        else
            return dis2.generate(random);
    }
}