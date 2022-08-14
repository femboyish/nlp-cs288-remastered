package edu.berkeley.nlp.assignments.assign1.student;

public class Utils {
   
   public static final long mask = -1L >>> 32;

   public static long pack(int left, int right) {
     return ((long)left << 32) | ((long)right & mask);
   }

   public static int left(long v) {
     return (int)(v >>> 32);
   }

   public static int right(long v) {
     return (int)(v & mask);
   }

   
   public static void reportMemoryUsage() {
      System.gc(); System.gc(); System.gc(); System.gc();
      long totalMem = Runtime.getRuntime().totalMemory();
      long freeMem = Runtime.getRuntime().freeMemory();
      System.out.println("Memory usage is " + bytesToString(totalMem - freeMem));
   }
   
   private static