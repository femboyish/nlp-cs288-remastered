package edu.berkeley.nlp.assignments.assign1.student;


/**
 * A very memory efficient trigram counter using a modified hash map
 * implementation. The hash map has only one long array (64bit each element).
 * Both the keys (trigram) and the values are packed into a single long
 * primitive to be stored.
 * 
 * The most frequent trigram i