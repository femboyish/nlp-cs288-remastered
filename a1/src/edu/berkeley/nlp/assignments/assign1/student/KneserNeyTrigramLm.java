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
    * w