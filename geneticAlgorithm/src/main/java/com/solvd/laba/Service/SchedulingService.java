package com.solvd.laba.Service;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.*;

public class SchedulingService {
    public static final int EVOLUTIONS = 5000;
    public static final int POPULATION_SIZE = 100;
    public static final int NUMBER_OF_PERIODS_PER_DAY = 6;
    public static final int NUMBER_OF_DAYS = 5;
    public static final int NUMBER_OF_GROUPS = 3;
    public static final int NUMBER_OF_TEACHERS = 8;
    public static final int USER_GROUP = 0; 

    public IChromosome schedule(int minDays, int maxDays) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        conf.setPopulationSize(POPULATION_SIZE);

        Gene[] sampleGenes = new Gene[NUMBER_OF_GROUPS * NUMBER_OF_DAYS * NUMBER_OF_PERIODS_PER_DAY];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new IntegerGene(conf, 0, NUMBER_OF_TEACHERS); // teachers are represented as integers starting from 0
        }

        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);

        conf.setFitnessFunction(new ScheduleFitnessFunction(minDays, maxDays));
        Genotype population = Genotype.randomInitialGenotype(conf);
        assignNoClassDaysToGroup(population.getPopulation(), USER_GROUP, minDays, maxDays);


        for (int i = 0; i < EVOLUTIONS; i++) {
            population.evolve();
        }
        IChromosome bestSolution = population.getFittestChromosome();
        return bestSolution;
    }
    
    public void assignNoClassDaysToGroup(Population population, int group, int minDays, int maxDays) {
        Random rng = new Random();
        int noClassDays = minDays + rng.nextInt(maxDays - minDays + 1);
        for (Object obj : population.getChromosomes()) {
            IChromosome chromosome = (IChromosome) obj;
            for (int i = 0; i < noClassDays; i++) {
                int day = rng.nextInt(NUMBER_OF_DAYS);  
                for (int period = 0; period < NUMBER_OF_PERIODS_PER_DAY; period++) {
                    int geneIndex = (group * NUMBER_OF_DAYS * NUMBER_OF_PERIODS_PER_DAY) + (day * NUMBER_OF_PERIODS_PER_DAY) + period;
                    chromosome.getGene(geneIndex).setAllele(0);  // assign 'no class' to this period
                }
            }
        }
    }

    public void displaySchedule(IChromosome chromosome) {
        for (int group = 0; group < NUMBER_OF_GROUPS; group++) {
            System.out.println("Group " + (group + 1) + ":");
            for (int day = 0; day < NUMBER_OF_DAYS; day++) {
                System.out.println("  Day " + (day + 1) + ":");
                for (int period = 0; period < NUMBER_OF_PERIODS_PER_DAY; period++) {
                    int geneIndex = group * NUMBER_OF_DAYS * NUMBER_OF_PERIODS_PER_DAY + day * NUMBER_OF_PERIODS_PER_DAY + period;
                    System.out.println("    Period " + (period + 1) + ": Teacher " + chromosome.getGene(geneIndex).getAllele());
                }
            }
        }
    }
}

class ScheduleFitnessFunction extends FitnessFunction {
    private final int maxDays;
    private final int minDays;

    public ScheduleFitnessFunction(int minDays, int maxDays) {
        this.minDays = minDays;
        this.maxDays = maxDays;
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
        double fitness = 0;
        
        for (int group = 0; group < SchedulingService.NUMBER_OF_GROUPS; group++) {
            int classDays = 0;
            for (int day = 0; day < SchedulingService.NUMBER_OF_DAYS; day++) {
                int classCount = 0;
                for (int period = 0; period < SchedulingService.NUMBER_OF_PERIODS_PER_DAY; period++) {
                    int geneIndex = group * SchedulingService.NUMBER_OF_DAYS * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + day * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + period;
                    Integer teacher = (Integer) chromosome.getGene(geneIndex).getAllele();
                    if (teacher != 0) { 
                        classCount++; 
                    }
                }
                if (classCount == SchedulingService.NUMBER_OF_PERIODS_PER_DAY) {
                    classDays++; 
                }
            }
            if (classDays >= minDays && classDays <= maxDays) {
                fitness += 1; 
            }
        }
        if (noCollisions(chromosome)) {
            fitness += 1;
        }
        int noClassDays = countNoClassDays(chromosome, SchedulingService.USER_GROUP);
        if (noClassDays == (SchedulingService.NUMBER_OF_DAYS - maxDays)) {
        	fitness += 1;
        }
        	
        return fitness;
    }

    private int countNoClassDays(IChromosome chromosome, int group) {
        int noClassDays = 0;
        for (int day = 0; day < SchedulingService.NUMBER_OF_DAYS; day++) {
            boolean isNoClassDay = true;
            for (int period = 0; period < SchedulingService.NUMBER_OF_PERIODS_PER_DAY; period++) {
                int geneIndex = group * SchedulingService.NUMBER_OF_DAYS * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + day * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + period;
                Integer teacher = (Integer) chromosome.getGene(geneIndex).getAllele();
                if (teacher != 0) { 
                    isNoClassDay = false;
                    break;
                }
            }
            if (isNoClassDay) {
                noClassDays++;
            }
        }
        return noClassDays;
    }



    private boolean noCollisions(IChromosome chromosome) {
        for (int day = 0; day < SchedulingService.NUMBER_OF_DAYS; day++) {
            for (int period = 0; period < SchedulingService.NUMBER_OF_PERIODS_PER_DAY; period++) {
                Set<Integer> teachers = new HashSet<>();
                for (int group = 0; group < SchedulingService.NUMBER_OF_GROUPS; group++) {
                    int geneIndex = day * SchedulingService.NUMBER_OF_GROUPS * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + group * SchedulingService.NUMBER_OF_PERIODS_PER_DAY + period;
                    if (group != SchedulingService.USER_GROUP) {
                        if(chromosome.getGene(geneIndex).getAllele() instanceof Integer) {
                            Integer teacher = (Integer) chromosome.getGene(geneIndex).getAllele();
                            if (teachers.contains(teacher)) {
                                return false;
                            }
                            teachers.add(teacher);
                        }
                    }
                }
            }
        }
        return true;
    }
}
