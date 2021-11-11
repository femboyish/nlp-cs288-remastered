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
   StringIn