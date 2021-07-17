
package edu.berkeley.nlp.assignments.assign1.student;

/**
 * A very compact hash map with approximation. This is based on
 * CrazilyPackedHashMap but it only uses a single int array (instead of long
 * array). Instead of storing the keys, a 9-bit checksum of the keys are stored.