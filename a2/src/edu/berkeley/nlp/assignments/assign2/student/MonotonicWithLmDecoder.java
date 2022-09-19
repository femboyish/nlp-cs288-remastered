
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

  public class BeamSearchOption {
    public int[] lmContextBuf;
    double score;
    int lmContextBufLen;
    
    // used for backtrace.
    ScoredPhrasePairForSentence phrasePair;
    BeamSearchOption prev;
  }

  private PhraseTable tm;

  private NgramLanguageModel lm;

  private int lmOrder;

  @SuppressWarnings("unused")
  private DistortionModel dm;

  public MonotonicWithLmDecoder(PhraseTable tm, NgramLanguageModel lm,
      DistortionModel dm) {
    super();
    this.tm = tm;
    this.lm = lm;
    this.dm = dm;
    this.lmOrder = lm.getOrder();
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.berkeley.nlp.mt.decoder.Decoder#decode(java.util.List)
   */
  @Override
  public List<ScoredPhrasePairForSentence> decode(List<String> frenchSentence) {
    int length = frenchSentence.size();
    PhraseTableForSentence tmState = tm.initialize(frenchSentence);
    List<ScoredPhrasePairForSentence> ret = new LinkedList<ScoredPhrasePairForSentence>();

    int maxPhraseLen = tmState.getMaxPhraseLength();
    int lmContextBufSize = lm.getOrder() + tmState.getMaxPhraseLength() + 1;
    int[] lmContextBuf = new int[lmContextBufSize];
    int currentContextBufLen = 0;

    // Initialize the priority queues.
    @SuppressWarnings("unchecked")
    FastPriorityQueue<BeamSearchOption> beams[] = new FastPriorityQueue[length + 1];
    for (int start = 0; start <= length; start++) {
      beams[start] = new FastPriorityQueue<BeamSearchOption>();
    }

    // Add search beam's root node.
    BeamSearchOption optionStart = new BeamSearchOption();
    optionStart.lmContextBufLen = 1;
    optionStart.lmContextBuf = new int[1];
    optionStart.lmContextBuf[0] = EnglishWordIndexer.getIndexer()
        .addAndGetIndex(NgramLanguageModel.START);
    optionStart.score = 0;