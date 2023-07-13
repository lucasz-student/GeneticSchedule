package com.solvd.laba.Service;

import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public class EvoRunner {

    public static void main(String[] args) throws InvalidConfigurationException {
        SchedulingService schedulingService = new SchedulingService();
        IChromosome bestSolution = schedulingService.schedule(1, 3);
        System.out.println("best solution fitness value: " + bestSolution.getFitnessValue());
        schedulingService.displaySchedule(bestSolution);
    }
}
