
//   Copyright (c) 1999 CERN - European Organization for Nuclear Research.

//   Permission to use, copy, modify, distribute and sell this software
//   and its documentation for any purpose is hereby granted without fee,
//   provided that the above copyright notice appear in all copies and
//   that both that copyright notice and this permission notice appear in
//   supporting documentation. CERN makes no representations about the
//   suitability of this software for any purpose. It is provided "as is"
//   without expressed or implied warranty.

package edu.berkeley.nlp.assignments.assign1.student;

import java.util.Arrays;

/*
 * Modified for Trove to use the java.util.Arrays sort/search
 * algorithms instead of those provided with colt.
 */

/**
 * Used to keep hash table capacities prime numbers.
 * Not of interest for users; only for implementors of hashtables.
 *
 * <p>Choosing prime numbers as hash table capacities is a good idea
 * to keep them working fast, particularly under hash table
 * expansions.
 *
 * <p>However, JDK 1.2, JGL 3.1 and many other toolkits do nothing to
 * keep capacities prime.  This class provides efficient means to
 * choose prime capacities.
 *
 * <p>Choosing a prime is <tt>O(log 300)</tt> (binary search in a list
 * of 300 ints).  Memory requirements: 1 KB static memory.
 *
 * @author wolfgang.hoschek@cern.ch
 * @version 1.0, 09/24/99
 */
public final class PrimeFinder {
    /**
     * The largest prime this class can generate; currently equal to
     * <tt>Integer.MAX_VALUE</tt>.
     */
    public static final int largestPrime = Integer.MAX_VALUE; //yes, it is prime.

    /**
     * The prime number list consists of 11 chunks.
     *
     * Each chunk contains prime numbers.
     *
     * A chunk starts with a prime P1. The next element is a prime
     * P2. P2 is the smallest prime for which holds: P2 >= 2*P1.
     *
     * The next element is P3, for which the same holds with respect
     * to P2, and so on.
     *
     * Chunks are chosen such that for any desired capacity >= 1000
     * the list includes a prime number <= desired capacity * 1.11.
     *
     * Therefore, primes can be retrieved which are quite close to any
     * desired capacity, which in turn avoids wasting memory.
     *
     * For example, the list includes
     * 1039,1117,1201,1277,1361,1439,1523,1597,1759,1907,2081.
     *
     * So if you need a prime >= 1040, you will find a prime <=
     * 1040*1.11=1154.
     *    
     * Chunks are chosen such that they are optimized for a hashtable