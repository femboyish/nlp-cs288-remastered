package edu.berkeley.nlp.assignments.assign1.student;


/**
 * A very memory efficient trigram counter using a modified hash map
 * implementation. The hash map has only one long array (64bit each element).
 * Both the keys (trigram) and the values are packed into a single long
 * primitive to be stored.
 * 
 * The most frequent trigram in this corpus appears 468,261 times, costing 19
 * bits to store (the value).
 * 
 * To store the key (i.e. trigram), we need to store one unigram (495,172
 * unigrams or 19 bits) and a bigram index (8,374,230 bigrams or 23 bits).
 * 
 * In total, we need 19 + 19 + 23 = 61 bits to store!
 * 
 * @author rxin
 * 
 */
public class TrigramCounter extends CrazilyPackedHashMap implements
      TrigramCounterInterface {

   public TrigramCounter(int initialCapacity, float 