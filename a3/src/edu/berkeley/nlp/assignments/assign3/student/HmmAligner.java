
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
            sentencePair.frenchWords, sentencePair.englishWords);
      
      //print2dArray(gamma);
      
      int numEnglishWords = sentencePair.englishWords.size();
      int numFrenchWords = sentencePair.frenchWords.size();
      
      for (int fi = 0; fi < numFrenchWords; fi++) {
         // Find the highest gamma value.
         int bestEnglishPosition = numEnglishWords;
         double bestGamma = gamma[fi][numEnglishWords];
         for (int ei = 0; ei < numEnglishWords; ei++) {
            if (gamma[fi][ei] > bestGamma) {
               bestEnglishPosition = ei;
               bestGamma = gamma[fi][ei];
            }
         }
         
         // Add the most likely position if it is not NULL.
         if (bestEnglishPosition != numEnglishWords) {
            alignment.addAlignment(bestEnglishPosition, fi, true);
         }
      }
      
      return alignment;
   }

   protected double[][] trainAlignmentForSentence(List<String> frenchWords,
         List<String> englishWords) {
      
      int numEnglishWords = englishWords.size();
      int numFrenchWords = frenchWords.size();
      
      // The +1 is for NULL, i.e. we store NULL as the last English word.
      double[][] alpha = new double[numFrenchWords][numEnglishWords + 1];
      double[][] gamma = new double[numFrenchWords][numEnglishWords + 1];
      
      // Construct transition matrix.
      double[][] trans = new double[numEnglishWords + 1][numEnglishWords + 1];
      for (int i = 0; i < numEnglishWords; i++) {
         trans[i][numEnglishWords] = nullDistortionLikelihood;
         trans[numEnglishWords][i] = (1 - nullDistortionLikelihood)
               / numEnglishWords;
         double norm = 0;
         for (int j = 0; j < numEnglishWords; j++) {
            trans[i][j] = Math.exp(- 2 * Math.abs(j - i - 1.1));
            norm += trans[i][j];
         }
         for (int j = 0; j < numEnglishWords; j++) {
            trans[i][j] = trans[i][j] * (1 - nullDistortionLikelihood) / norm;
         }
      }

      // Get the first French word.
      String firstFWord = frenchWords.get(0);
      int firstFWordIdx = frenchWordIndexer.addAndGetIndex(firstFWord);
      frenchIndexBuffer[0] = firstFWordIdx;
      
      // Generate the English word index. Use the last one as NULL
      // alignment. Also set the initial alpha.
      double normAlphaZero = 0;
      for (int ei = 0; ei < numEnglishWords; ei++) {
         String englishWord = englishWords.get(ei);
         int eIndex = englishWordIndexer.addAndGetIndex(englishWord);
         englishIndexBuffer[ei] = eIndex;
         
         // Initialize alpha.
         alpha[0][ei] = pairCounters.getCount(eIndex, firstFWordIdx)
               * trans[ei][numEnglishWords];
         //alpha[0][ei] = trans[ei][numEnglishWords];
         normAlphaZero += alpha[0][ei];
      }
      
      // Initialize the NULL word (and the last alpha[0] value).
      englishIndexBuffer[numEnglishWords] = -1;
      alpha[0][numEnglishWords] = pairCounters.getCount(-1, firstFWordIdx)