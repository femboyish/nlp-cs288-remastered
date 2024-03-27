
package edu.berkeley.nlp.assignments.assign3.student;

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
public class Model1SoftEmAligner extends Model1HardEmAligner {

   /**
    * Number of EM iterations to run.