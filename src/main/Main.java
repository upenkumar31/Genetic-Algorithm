package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dna.DNA;

public class Main {

    private static final int POPULATION_SIZE = 400;
    private static final int LOOPS = 10000;
    private static final int GENES_NUMBER = 31;
    private static List<DNA> population = new ArrayList<DNA>();
    private static List<DNA> DNAPool = new ArrayList<DNA>();

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        init();
        //printPopulation();

        for (int i = 0; i < LOOPS; ++i) {
            nextDNAPool();
            nextGen();
            //printPopulation();
            //printPopulationFitness();
        }

        printPopulationFitness();

        long endTime = System.currentTimeMillis();

        System.out.println("Total run time : " + (endTime - startTime) / 1000.0 + " s");
        System.out.println("Time per generation : " + (endTime - startTime) / LOOPS / 1000.0 + " s");

    }

    private static void init() {

        File text = new File("test.txt");
        try {
            Scanner scnr = new Scanner(text);

            double[] arr = new double[GENES_NUMBER];
            for (int i = 0; i < POPULATION_SIZE; ++i) {
                for (int j = 0; j < GENES_NUMBER; j++)
                    arr[j] = Float.floatToIntBits((float) scnr.nextDouble());
                population.add(new DNA(GENES_NUMBER, arr));
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    private static void mutate() {
        for (DNA dna : population) {
            dna.mutate();
        }
    }

    private static void nextDNAPool() {
        DNAPool = new ArrayList<DNA>();
        for (DNA dna : population) {
            int fitness = (int) (dna.fitness() * 100);
            for (int i = 0; i < fitness; ++i) {
                DNAPool.add(dna);
            }
        }
    }

    private static void nextGen() {
        population = new ArrayList<DNA>();
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            DNA parent1 = DNAPool.get((int) (Math.random() * DNAPool.size()));
            DNA parent2 = DNAPool.get((int) (Math.random() * DNAPool.size()));
            population.add(parent1.crossover(parent2));
        }
        mutate();
    }

    private static void printPopulation() {
        for (DNA dna : population) {
            System.out.println(dna.toString());
        }
    }

    private static void printPopulationFitness() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        double sum = 0;
        double bestFit = population.get(0).fitness();
        DNA best = population.get(0);
        for (DNA dna : population) {
            double fit = dna.fitness();
            sum += fit;
            if (fit > bestFit) {
                best = dna;
                bestFit = fit;
            }
        }
        try {
            fw = new FileWriter("fit.txt");
            bw = new BufferedWriter(fw);
            for (DNA dna : population) {
                double fit = dna.fitness();
                sum += fit;
                if (fit -bestFit<=0.000000001) {
                    for(int i=0;i<GENES_NUMBER;i++)
                    bw.write(Double.toString(dna.getGene(i))+" ");
                    bw.write("\n");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try
            {
                bw.close();
                fw.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        System.out.println("Population average fitness : " + sum / population.size());
        System.out.println("Best DNA : " + best.toString() + "- Fitness : " + bestFit);

    }
}
