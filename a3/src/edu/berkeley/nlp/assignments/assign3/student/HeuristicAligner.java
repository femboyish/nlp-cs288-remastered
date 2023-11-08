
package edu.berkeley.nlp.assignments.assign3.student;

import edu.berkeley.nlp.mt.Alignment;
import edu.berkeley.nlp.mt.SentencePair;

/**
 * Heuristic-based word alignment.
 * 
 * max train = 1,000,000
 * Precision: 0.5230769230769231
 * Recall: 0.5564635958395245
 * AER: 0.46473141616928926
 * 
 * max train = 400,000
 * Precision: 0.4891737891737892
 * Recall: 0.5141158989598811
 * AER: 0.5017182130584192
 * 
 * max train = 100,000
 * Precision: 0.4346153846153846
 * Recall: 0.45641406636948983
 * AER: 0.5574244890576958
 *
 * max train = 10,000
 * Precision: 0.31153846153846154
 * Recall: 0.30039623576027735
 * AER: 0.6925302948091879
 *
 * max train = 1,000
 * Precision: 0.22108262108262108
 * Recall: 0.1607231302625062
 * AER: 0.8009585820220655
 * 
 * max train = 100
 * Precision: 0.20754985754985755
 * Recall: 0.13620604259534422
 * AER: 0.8185024416711882
 *
 * max train = 10
 * Precision: 0.24985754985754985
 * Recall: 0.20975730559683012
 * AER: 0.7647856755290288
 *
 * max train = 1
 * Precision: 0.24757834757834757
 * Recall: 0.2055473006438831
 * AER: 0.7677699403147042
 * 
 * max train = 0
 * Precision: 0.24757834757834757
 * Recall: 0.2055473006438831
 * AER: 0.7677699403147042
 *
 * @author rxin
 *
 */
public class HeuristicAligner extends AlignerBase {
   
   /*