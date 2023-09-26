package edu.berkeley.nlp.assignments.assign3.student;

/**
 * An integer (primitive data type) array that dynamically grows. We don't use
 * the Java Vector class because the auto-boxing of primitives cost too much 
 * overhead.
 * 
 * @author rxin
 */
public class DynamicIntArray {
   
   private int[] array = null;
   
   public DynamicIntArray(int init_size) {
      array = new int[init_size];
   }
   
   public int get(int index) {
      return array[index];
   }
   
   public void set