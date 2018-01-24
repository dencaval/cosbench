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

import com.intel.cosbench.config.ConfigException;

class BimodalIntGenerator implements IntGenerator {

    BimodalDistribution bimodalDistribution = null;

    public BimodalIntGenerator(double mean1, double stdDeviation1,
                               double mean2, double stdDeviation2,
                               double coinFlip) {
        bimodalDistribution = new BimodalDistribution(mean1,
          stdDeviation1, mean2, stdDeviation2, coinFlip);
    }

    @Override
    public int next(Random random) {
        return next(random, 1, 1);
    }

    @Override
    public int next(Random random, int idx, int all) {
        return (int)bimodalDistribution.generate(random);
    }

    public static BimodalIntGenerator parse(String pattern) {
        if (!StringUtils.startsWith(pattern, "b("))
            return null;
        try {
            return tryParse(pattern);
        } catch (Exception e) {
        }
        String msg = "illegal uniform distribution pattern: " + pattern;
        throw new ConfigException(msg);
    }

    private static BimodalIntGenerator tryParse(String pattern) {
        pattern = StringUtils.substringBetween(pattern, "(", ")");
        String[] args = StringUtils.split(pattern, ',');
        double mean1 = Double.parseDouble(args[0]);
        double stdDeviation1 = Double.parseDouble(args[1]);
        double mean2 = Double.parseDouble(args[2]);
        double stdDeviation2 = Double.parseDouble(args[3]);
        double coinFlip = Double.parseDouble(args[4]);
        return new BimodalIntGenerator(mean1, stdDeviation1,
          mean2, stdDeviation2, coinFlip);
    }

}
