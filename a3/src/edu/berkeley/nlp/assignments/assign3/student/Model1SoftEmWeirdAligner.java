
package edu.berkeley.nlp.assignments.assign3.student;

import edu.berkeley.nlp.mt.SentencePair;
import edu.berkeley.nlp.util.CounterMap;

/**
 * IBM Model 1 Alignment using soft EM with an interesting bug that actually
 * improves the AER score.
 * 
 * The caller must call train() to train the model before using it.
 * 
 * @author rxin
 */
public class Model1SoftEmWeirdAligner extends Model1HardEmAligner {

   /**
    * Number of EM iterations to run.
    */
   protected static final int NUM_EM_ITERATIONS = 30;
   
   protected static final double weirdNullDistortionLikelihood = 0.95;

   /* (non-Javadoc)
    * @see edu.berkeley.nlp.assignments.assign3.student.Model1HardEmAligner#train(java.lang.Iterable)
    */
   public void train(Iterable<SentencePair> trainingData) {
      
      System.out.println(
            "(Soft Weird EM Model 1) Initializing pair counters ...");
      initializePairCounters(trainingData);
      //initializePairCountersBaseline(trainingData);
      
      // alignmentProbability[i] = P( a_j = i | f[j], e[i] )
      double[] alignmentProbability = new double[MAX_SENTENCE_LEN];
      
      for (int emIterNum = 0; emIterNum < NUM_EM_ITERATIONS; emIterNum++) {
         
         System.out.println("EM iteration # " + emIterNum + " / "
               + NUM_EM_ITERATIONS + " ...");