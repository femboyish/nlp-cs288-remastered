
package edu.berkeley.nlp.assignments.assign2.student;

import java.util.ArrayList;
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
 * A beam search which permits limited distortion.
 * 
 * @author rxin
 * 
 */
public class DistortingWithLmDecoder implements Decoder {
  
  /**
   * 50, 429.148s, 24.212, -3371.0790130316364
   *
   */
  
  public static final int priorityQueueSize = 50;

  public class BeamSearchOption {
    public int[] lmContextBuf;
    double score;
    int lmContextBufLen;
    
    // used for backtrace.
    ArrayList<ScoredPhrasePairForSentence> phrasePairs;
  }

  private PhraseTable tm;

  private NgramLanguageModel lm;

  private int lmOrder;
  
  private DistortionModel dm;

  public DistortingWithLmDecoder(PhraseTable tm, NgramLanguageModel lm,
      DistortionModel dm) {
    super();
    this.tm = tm;