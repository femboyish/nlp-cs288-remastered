
package edu.berkeley.nlp.assignments.assign2.student;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.berkeley.nlp.langmodel.EnglishWordIndexer;
import edu.berkeley.nlp.langmodel.NgramLanguageModel;
import edu.berkeley.nlp.mt.decoder.Decoder;
import edu.berkeley.nlp.mt.decoder.DistortionModel;
import edu.berkeley.nlp.mt.phrasetable.PhraseTable;
import edu.berkeley.nlp.mt.phrasetable.PhraseTableForSentence;
import edu.berkeley.nlp.mt.phrasetable.ScoredPhrasePairForSentence;
import edu.berkeley.nlp.util.FastPriorityQueue;

/**
 * Monotonic beam-search decoder with trigram language model.
 * 
 * @author rxin
 * 
 */
public class MonotonicWithLmDecoder implements Decoder {
  
  /*
   * 1, 3.052s, 25.887, -3933.7543302336053
   * 10, 4.475s, 25.833, -3911.2285924622297
   * 40, 8.328s, 25.838, -3908.7408309736734
   * 50, 10.508s, 25.761, -3913.1241259627673
   * 100, 18.319s, 25.758, -3913.1237282543516
   * 250, 42.626s, 25.798, -3922.883984979305
   * 500, 86.511s, 25.746, -3925.8376456715787
   * 1000, 167.871s, 25.746, -3925.8376456715787
   */

  public static final int priorityQueueSize = 10;