
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
   
   /**
    * The distortion likelihood for aligning a word to NULL.
    */
   protected double nullDistortionLikelihood = 0.2;
   
   protected int[] englishIndexBuffer = new int[MAX_SENTENCE_LEN];
   protected int[] frenchIndexBuffer = new int[MAX_SENTENCE_LEN];
   
   /* (non-Javadoc)
    * @see edu.berkeley.nlp.mt.WordAligner#alignSentencePair(edu.berkeley.nlp.mt.SentencePair)
    */
   @Override
   public Alignment alignSentencePair(SentencePair sentencePair) {
      int[] a = align(sentencePair);
      Alignment alignment = new Alignment();
      for (int i = 0; i < a.length; i++) {
         if (a[i] != -1) {
            alignment.addAlignment(a[i], i, true);
         }
      }
      
      return alignment;
   }
   
   /**
    * Distortion likelihood for IBM Model 1.
    * 
    * @param numEnglishWords
    * @return distortion likelihood, 0.8 / (|E| + 1).
    */
   protected double distortionProbability(int numEnglishWords) {
      return (1.0 - nullDistortionLikelihood) / (numEnglishWords + 1);
   }
   
   /**
    * Aligns a sentence pair. This assumes pairCounters has been initialized.
    * 
    * @param sentencePair
    * @return
    */
   protected int[] align(SentencePair sentencePair) {
      int numEnglishWords = sentencePair.englishWords.size();
      int numFrenchWords = sentencePair.frenchWords.size();
      int[] alignments = new int[numFrenchWords];
      
      // Generate the English word index.
      for (int ei = 0; ei < numEnglishWords; ei++) {
         String englishWord = sentencePair.englishWords.get(ei);
         int eIndex = englishWordIndexer.addAndGetIndex(englishWord);
         englishIndexBuffer[ei] = eIndex;
      }
      
      // For each French word, find the most likely alignment.
      for (int fi = 0; fi < numFrenchWords; fi++) {
         // First align the word to null (-1).
         String fWord = sentencePair.frenchWords.get(fi);
         int fWordIndex = frenchWordIndexer.addAndGetIndex(fWord);
         frenchIndexBuffer[fi] = fWordIndex;

         double bestProbability = nullDistortionLikelihood
               * pairCounters.getCount(-1, fWordIndex);
         alignments[fi] = -1;
         //System.out.println(fi + ",-1: " + bestProbability);
         
         // Find the most likely alignment.
         for (int ei = 0; ei < numEnglishWords; ei++) {
            int eWordIndex = englishIndexBuffer[ei];
            double probability = distortionProbability(numEnglishWords)
                  * pairCounters.getCount(eWordIndex, fWordIndex);
            
            //System.out.println(fi + "," + ei + ": " + probability);
            
            if (probability > bestProbability) {