
package edu.berkeley.nlp.assignments.assign1.student;

/**
 * A very compact hash map with approximation. This is based on
 * CrazilyPackedHashMap but it only uses a single int array (instead of long
 * array). Instead of storing the keys, a 9-bit checksum of the keys are stored.
 * 
 * @author rxin
 * 
 */
public class CrazilyPackedHashMapApproximate {
   
   // 0x3FFFFF = 22 bits of 1.
   protected int valueMask = 0x7FFFFF;
   protected int checksumMask = ~valueMask;
   protected int numKeyBits = 9;
   protected int numValueBits = 23;
   
   int size;
   int data[];

   /**
    * Initializes the hash table to a prime capacity which is at least
    * <tt>initialCapacity/loadFactor + 1</tt>.
    */
   public CrazilyPackedHashMapApproximate(int initialCapacity,
         float loadFactor, int valueMask, int numValueBits) {
      
      // Initialize the data array.
      int length = HashFunctions.fastCeil(initialCapacity / loadFactor);
      length = PrimeFinder.nextPrime(length);
      data = new int[length];
      
      // Set the parameters.
      this.valueMask = valueMask;
      this.checksumMask = ~valueMask;
      this.numValueBits = numValueBits;
      this.numKeyBits = 32 - numValueBits;
   }

   /**
    * Return the number of elements in the hash map.
    */
   public int size() {
      return size;
   }
   
   /**
    * Return the value of (key1, key2).
    */
   public int get(int key1, int key2) {
      int location = locatePosition(key1, key2);
      return data[location] & valueMask;
   }
   
   /**
    * Adjust or put the value and return the value.
    */
   public int adjustOrPutValue(int key1, int key2, int adjustAmount) {
      int location = locatePosition(key1, key2);
      int value = data[location] & valueMask;
      
      if (value == 0) {
         size++;
      }
      value += adjustAmount;
      
      long keyPacked = Utils.pack(key1, key2);
      int checksum = HashFunctions.checksum(keyPacked) << numValueBits;
      data[location] = checksum | value;