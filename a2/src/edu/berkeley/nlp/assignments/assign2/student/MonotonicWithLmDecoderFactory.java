
package edu.berkeley.nlp.assignments.assign2.student;

import edu.berkeley.nlp.langmodel.NgramLanguageModel;
import edu.berkeley.nlp.mt.decoder.Decoder;
import edu.berkeley.nlp.mt.decoder.DecoderFactory;
import edu.berkeley.nlp.mt.decoder.DistortionModel;
import edu.berkeley.nlp.mt.phrasetable.PhraseTable;

public class MonotonicWithLmDecoderFactory implements DecoderFactory
{

	public Decoder newDecoder(PhraseTable tm, NgramLanguageModel lm, DistortionModel dm) {

	   return new MonotonicWithLmDecoder(tm, lm, dm);

	}

}