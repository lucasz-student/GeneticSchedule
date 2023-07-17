package com.solvd.laba.Service;

import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;

public class EvoRunner {
	
	
    public static void main(String[] args) throws InvalidConfigurationException {
    	int[] numbers = new int[args.length];
    	System.out.println("Please enter 'MinimumDays, MaximumDays, MinimumPeriods, MaximumPeriods' as parameters for your best school schedule");
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                try {
                    numbers[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input: Command line argument at index " + i + " must be an integer.");
                    return;
                }
            }
        }
       
        /*SchedulingService schedulingService = new SchedulingService();
        IChromosome bestSolution = schedulingService.schedule(numbers[0], numbers[1], numbers[2], numbers[3]);
        System.out.println("best solution fitness value: " + bestSolution.getFitnessValue());
        schedulingService.displaySchedule(bestSolution);*/
    }
}