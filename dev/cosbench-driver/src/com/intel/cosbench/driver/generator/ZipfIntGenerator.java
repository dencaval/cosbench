package com.intel.cosbench.driver.generator;

import java.util.Random;
import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.intel.cosbench.config.ConfigException;

class ZipfIntGenerator implements IntGenerator {

    ZipfDistribution zipfDistribution = null;

    public ZipfIntGenerator(int size, double skew) {
        zipfDistribution = new ZipfDistribution(size, skew);
    }

    @Override
    public int next(Random random) {
        return next(random, 1, 1);
    }

    @Override
    public int next(Random random, int idx, int all) {
        return (int)zipfDistribution.probability(random.nextInt());
    }

    public static ZipfIntGenerator parse(String pattern) {
        if (!StringUtils.startsWith(pattern, "z("))
            return null;
        try {
            return tryParse(pattern);
        } catch (Exception e) {
        }
        String msg = "illegal uniform distribution pattern: " + pattern;
        throw new ConfigException(msg);
    }

    private static ZipfIntGenerator tryParse(String pattern) {
        pattern = StringUtils.substringBetween(pattern, "(", ")");
        String[] args = StringUtils.split(pattern, ',');
        int size = Integer.parseInt(args[0]);
        double skew = Double.parseDouble(args[1]);
        return new ZipfIntGenerator(size, skew);
    }
}
