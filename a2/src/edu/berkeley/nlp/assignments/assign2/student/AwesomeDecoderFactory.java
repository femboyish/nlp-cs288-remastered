package edu.berkeley.nlp.assignments.assign2.student;

import edu.berkeley.nlp.langmodel.NgramLanguageModel;
import edu.berkeley.nlp.mt.decoder.Decoder;
import edu.berkeley.nlp.mt.decoder.DecoderFactory;
import edu.berkeley.nlp.mt.decoder.DistortionModel;
import edu.berkeley.nlp.mt.phrasetable.PhraseTable;

// Decoder using a quadratic distortion model (instead of linear).
public class AwesomeDecoderFactory implements DecoderFactory
{

	public Decoder newDecoder(PhraseTable tm, NgramLan