
package edu.berkeley.nlp.assignments.assign3.student;

import java.util.List;
import java.util.Map;

import edu.berkeley.nlp.mt.Alignment;
import edu.berkeley.nlp.mt.SentencePair;
import edu.berkeley.nlp.util.Counter;
import edu.berkeley.nlp.util.CounterMap;

/**
 * HMM-based word aligner, as proposed by Vogel 1996. This is done using a soft
 * EM algorithm.
 * 
 * @author rxin
 */
public class HmmAligner extends Model1HardEmAligner {
   
   /**
    * Number of EM iterations to run.
    */
   protected static final int NUM_EM_ITERATIONS = 5;
   
   /**
    * The distortion likelihood for aligning a word to NULL.
    */
   protected double nullDistortionLikelihood = 0.2;
   
   /* (non-Javadoc)
    * @see edu.berkeley.nlp.mt.WordAligner#alignSentencePair(edu.berkeley.nlp.mt.SentencePair)
    */
   @Override
   public Alignment alignSentencePair(SentencePair sentencePair) {
      Alignment alignment = new Alignment();
      double[][] gamma = trainAlignmentForSentence(