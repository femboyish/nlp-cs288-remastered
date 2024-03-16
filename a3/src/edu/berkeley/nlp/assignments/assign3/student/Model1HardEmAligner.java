
package edu.berkeley.nlp.assignments.assign3.student;

import edu.berkeley.nlp.mt.Alignment;
import edu.berkeley.nlp.mt.SentencePair;
import edu.berkeley.nlp.util.Counter;
import edu.berkeley.nlp.util.CounterMap;

/**
 * IBM Model 1 Alignment using soft EM.
 * 
 * The caller must call train() to train the model before using it.
 * 
 * @author rxin
 */
public class Model1HardEmAligner extends AlignerBase {
   
   /**
    * Number of EM iterations to run.
    */
   protected static final int NUM_EM_ITERATIONS = 5;