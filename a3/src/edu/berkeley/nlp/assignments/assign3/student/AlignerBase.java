package edu.berkeley.nlp.assignments.assign3.student;

import edu.berkeley.nlp.langmodel.EnglishWordIndexer;
import edu.berkeley.nlp.mt.Alignment;
import edu.berkeley.nlp.mt.SentencePair;
import edu.berkeley.nlp.mt.WordAligner;
import edu.berkeley.nlp.util.CounterMap;
import edu.berkeley.nlp.util.StringIndexer;

/**
 * Base class for aligners.
 * 
 * @author rxin
 */
public abstract class AlignerBase implements WordAligner {

   protected static final int MAX_SENTENCE_LEN = 1024;
   
 