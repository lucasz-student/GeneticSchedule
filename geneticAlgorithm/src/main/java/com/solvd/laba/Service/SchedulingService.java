package com.solvd.laba.Service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jgap.*;
import org.jgap.impl.*;

import com.solvd.laba.DAO.TeacherMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SchedulingService {
    public static final int EVOLUTIONS = 1500;
    public static final int POPULATION_SIZE = 300;
    public static final int NUMBER_OF_PERIODS_PER_DAY = 6;
    public static final int NUMBER_OF_DAYS = 5;
    public static final int NUMBER_OF_GROUPS = 3;
    public static final int NUMBER_OF_TEACHERS = 8;
    public static final int USER_GROUP = 0; 

    public IChromosome schedule(int minDays, int maxDays, int minPeriodsPerDay, int maxPeriodsPerDay) throws InvalidConfigurationException {
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        conf.setPopulationSize(POPULATION_SIZE);

        Gene[] sampleGenes = new Gene[NUMBER_OF_GROUPS * NUMBER_OF_DAYS * NUMBER_OF_PERIODS_PER_DAY];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new IntegerGene(conf, 0, NUMBER_OF_TEACHERS);
        }

        Chromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);

        conf.setFitnessFunction(new ScheduleFitnessFunction(minDays, maxDays, minPeriodsPerDay, maxPeriodsPerDay));
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
                    chromosome.getGene(geneIndex).setAllele(0);
                }
            }
        }
    }

    public void displaySchedule(IChromosome chromosome) throws IOException {
        for (int group = 0; group < NUMBER_OF_GROUPS; group++) {
            System.out.println("Group " + (group + 1) + ":");
            for (int day = 0; day < NUMBER_OF_DAYS; day++) {
                System.out.println("  Day " + (day + 1) + ":");
                for (int period = 0; period < NUMBER_OF_PERIODS_PER_DAY; period++) {
                    int geneIndex = group * NUMBER_OF_DAYS * NUMBER_OF_PERIODS_PER_DAY + day * NUMBER_OF_PERIODS_PER_DAY + period;
                    System.out.println("    Period " + (period + 1) + ": Teacher " + teacherIDToName(((int) chromosome.getGene(geneIndex).getAllele())));
                }
            }
        }
    }
    
    public String teacherIDToName(int id) throws IOException {
    	
    	if (id==0) {
    		return "No Class";
    	}
    	
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        InputStream dbPropertiesStream = Resources.getResourceAsStream("db.properties");
        Properties dbProperties = new Properties();
        dbProperties.load(dbPropertiesStream);
		
        Properties properties = new Properties();
        properties.setProperty("driver", dbProperties.getProperty("db.driver"));
        properties.setProperty("url", dbProperties.getProperty("db.url"));
        properties.setProperty("username", dbProperties.getProperty("db.user"));
        properties.setProperty("password", dbProperties.getProperty("db.password"));
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
		
		try (SqlSession session = sqlSessionFactory.openSession()) {
			TeacherMapper TeacherMapper = session.getMapper(TeacherMapper.class);
			return "Professor " + TeacherMapper.selectById(id).getName();
		}
    }
}

@SuppressWarnings("serial")
class ScheduleFitnessFunction extends FitnessFunction {
    private final int maxDays;
    private final int minDays;
    private final int minPeriodsPerDay;
    private final int maxPeriodsPerDay;

    public ScheduleFitnessFunction(int minDays, int maxDays, int minPeriodsPerDay, int maxPeriodsPerDay) {
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.minPeriodsPerDay = minPeriodsPerDay;
        this.maxPeriodsPerDay = maxPeriodsPerDay;
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
                if (classCount >= minPeriodsPerDay && classCount <= maxPeriodsPerDay) {
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

        int group1Periods = calculateGroupPeriods(chromosome, 1);

        if (group1Periods >= (minPeriodsPerDay * SchedulingService.NUMBER_OF_DAYS) &&
                group1Periods <= (maxPeriodsPerDay * SchedulingService.NUMBER_OF_DAYS)) {
            fitness += 1;
        }

        return fitness;
    }

    private int calculateGroupPeriods(IChromosome chromosome, int group) {
        int totalDays = SchedulingService.NUMBER_OF_DAYS;
        int periodsPerDay = SchedulingService.NUMBER_OF_PERIODS_PER_DAY;

        int groupPeriods = 0;
        for (int day = 0; day < totalDays; day++) {
            for (int period = 0; period < periodsPerDay; period++) {
                int geneIndex = (group * totalDays * periodsPerDay) + (day * periodsPerDay) + period;
                Integer teacher = (Integer) chromosome.getGene(geneIndex).getAllele();
                if (teacher != 0) {
                    groupPeriods++;
                }
            }
        }
        return groupPeriods;
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
                        Integer teacher = (Integer) chromosome.getGene(geneIndex).getAllele();
                        if (teacher != 0) {
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