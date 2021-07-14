
package edu.berkeley.nlp.assignments.assign1.student;

/**
 * A very compact hash map. It is so compact that it has only one array for
 * data. Both the keys and the values are packed in the same data array.
 * 
 * @author rxin
 * 
 */
public class CrazilyPackedHashMap {
   
   // 0x3FFFFF = 22 bits of 1.
   protected long valueMask = 0x3FFFFF;
   protected long keyMask = ~valueMask;
   protected int numFirstKeyBits = 23;
   protected int numSecondKeyBits = 19;
   protected int numValueBits = 64 - numFirstKeyBits - numSecondKeyBits;
   
   int size;
   long data[];

   /**
    * Initializes the hash table to a prime capacity which is at least
    * <tt>initialCapacity/loadFactor + 1</tt>.
    */
   public CrazilyPackedHashMap(int initialCapacity, float loadFactor,
         long valueMask, int numFirstKeyBits, int numSecondKeyBits) {
      
      // Initialize the data array.
      int length = HashFunctions.fastCeil(initialCapacity / loadFactor);
      length = PrimeFinder.nextPrime(length);
      System.out.println("TrigramCounter length: " + length);
      data = new long[length];
      
      // Set the parameters.