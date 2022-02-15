package edu.berkeley.nlp.assignments.assign1.student;

import java.util.ArrayList;
import java.util.List;

import edu.berkeley.nlp.langmodel.EnglishWordIndexer;
import edu.berkeley.nlp.langmodel.NgramLanguageModel;
import edu.berkeley.nlp.util.StringIndexer;

/**
 * An trigram language model with Kneser-Ney smoothing.
 * 
 * @author rxin
 */
public class KneserNeyTrigramLm implements NgramLanguageModel {

   /**
    * Discounting factor in Kneser-Ney.
    * 0.70 -> 24.493
    * 0.75 -> 24.502
    * 0.80 -> 24.520
    * 0.90 -> 24.533
    * 0.95 -> 24.493
    */
   public static final float discount = 0.9f;
   
   /**
    * Hash table load factor. The larger this is, the less memory
    * we need to store the language model. However, performance 
    * degrades linearly when the factor goes beyond 0.7.
    * 0.70 -> 297.570s, 997M
    * 0.75 -> 315.191s, 950M
    * 0.80 -> 311.193s, 905M
    * 0.85 -> 331.348s, 881M
    * 0.90 -> 354.125s, 861M
    * 0.95 -> 710.900s, 836M
    */
   public static final float loadFactor = 0.75f;
   
   /**
    * Initial capacities for the counters and hash maps. Note that this
    * parameter should not affect the language model performance. It only helps
    * speed up the model training.
    */
   public static final int initial_unigram_capacity = 495200;
   public static final int initial_bigram_capacity  = 8375000;
   public static final int initial_trigram_capacity = 42000000;
   // Basic stats:
   // ---495,172 unigrams - 19 bits
   // -8,374,230 bigrams  - 23 bits
   // 41,627,672 trigrams - 26 bits
   
   // Unigram Size: 495172
   // Bigram Size: 8374230
   // Trigram Size: 29973319

   
   /**
    * Smaller capacity for the sanity test.
    */
   public static final int initial_unigram_capacity_small = 50000;
   public static final int initial_bigram_capacity_small  = 50000;
   public static final int initial_trigram_capacity_small = 50000;
   
   /**
    * A small value for probability.
    * 1e-6: 24.502
    */
   public static final double very_small_value = 1e-6;

   /**
    * Unigram word indexer and counter.
    */
   StringIndexer wordIndexer;
   int unigramCounter[];

   /**
    * Bigram indexer and bigram counter.
    */
   BigramIndexer bigramIndexer;
   int bigramCounter[];

   /**
    * Trigram counter. There is no need for an indexer here. Note this is the
    * data structure that consumes the most amount of memory.
    */
   TrigramCounterInterface trigramCounter;
   int totalTrigram = 0;

   /**
    * The three N1+(...).
    */
   int n1plus_x_unigram_x[];
   int n1plus_bigram_x[];
   int n1plus_x_bigram[];
   
   /**
    * Basic stats.
    */
   int num_trigrams;
   int num_bigrams;
   int num_unigrams;
   
   double unseenBigramLogProb = 0;
   double unseenTrigramLogProb = 0;

   public KneserNeyTrigramLm(Iterable<List<String>> sentenceCollection,
         boolean approximate) {

      if (Runtime.getRuntime().maxMemory() > 500 * 1024 * 1024) {
         init(initial_unigram_capacity, initial_bigram_capacity,
               initial_trigram_capacity, approximate);
      } else {
         init(initial_unigram_capacity_small, initial_bigram_capacity_small,
               initial_trigram_capacity_small, approximate);
      }
      
      buildModel(sentenceCollection);
   }

   /**
    * Allocate memory for the structures. The structures are allocated here so I
    * can better measure the memory consumption.
    */
   private void init(int unigram_cap, int bigram_cap, int trigram_cap,
         boolean approximate) {
     Utils.reportMemoryUsage();
      wordIndexer = EnglishWordIndexer.getIndexer();
      Utils.reportMemoryUsage();
      unigramCounter = new int[unigram_cap];
      Utils.reportMemoryUsage();
      bigramIndexer = new BigramIndexer(bigram_cap, loadFactor);
      Utils.reportMemoryUsage();
      bigramCounter = new int[bigram_cap];
      Utils.reportMemoryUsage();
      
      if (!approximate) {
         trigramCounter = new TrigramCounter(trigram_cap, loadFactor);
      } else {
         trigramCounter = new TrigramCounterApproximate(trigram_cap, loadFactor);
      }
      Utils.reportMemoryUsage();
      
      n1plus_x_unigram_x = new int[unigram_cap];
      Utils.reportMemoryUsage();
      n1plus_bigram_x = new int[bigram_cap];
      Utils.reportMemoryUsage();
      n1plus_x_bigram = new int[bigram_cap];
      Utils.reportMemoryUsage();
   }

   /* (non-Javadoc)
    * @see edu.berkeley.nlp.langmodel.NgramLanguageModel#getOrder()
    */
   @Override
   public int getOrder() {
      return 3;
   }

   /* (non-Javadoc)
    * @see edu.berkeley.nlp.langmodel.NgramLanguageModel#getNgramLogProbability(int[], int, int)
    */
   @Override
   public double getNgramLogProbability(int[] ngram, int from, int to) {
      if (to - from == 3) {
         // Assertion: Trigram.
         double prob = 0.0;
         int word2 = ngram[from + 1];

         int word1word2 = bigramIndexer.get(ngram[from], word2);
         int word2word3 = bigramIndexer.get(word2, ngram[from + 2]);

         int word1word2count = word1word2 > 0 ? bigramCounter[word1word2] : 1;

         if (word1word2 > 0 && w