/** 

Copyright 2013 Intel Corporation, All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/ 

package com.intel.cosbench.driver.generator;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.math3.distribution.ZipfDistribution;

import com.intel.cosbench.config.ConfigException;

/**
 * This class is to generate integers, it's for uniform distribution.
 * 
 * @author ywang19, qzheng7
 * 
 */
class ZipfIntGenerator implements IntGenerator {

    private int numberOfElements;
    private double exponent;
    ZipfDistribution zipf;
    private static double MAXexponent = Double.MAX_VALUE;
    public ZipfIntGenerator(int numberOfElements, double exponent) {
        if (numberOfElements <= 0 || exponent <= 0 )
            throw new IllegalArgumentException();
        this.numberOfElements = numberOfElements;
        this.exponent= exponent;
        zipf = new ZipfDistribution(numberOfElements, exponent);
    }

    @Override
    public int next(Random random) {
        return next(random, 1, 1);
    }

    @Override
    public int next(Random random, int idx, int all) {
        int value = zipf.sample();
        return value;
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
        int numberOfElements = Integer.parseInt(args[0]);
        double exponent = (args.length == 2) ? Double.parseDouble(args[1]) : MAXexponent;
        return new ZipfIntGenerator(numberOfElements, exponent);
    }
    public static double getMAXexponent () {
		return MAXexponent;
	}
}