import java.util.*;

public class ModularMultiplicativeInverseCalculator {
   private List<GCD> steps;
   
   public ModularMultiplicativeInverseCalculator(int a, int b) {
      this.steps = new ArrayList<>();
      // gcd(a, b) = gcd(b, a)
      GCD gcd = new GCD(Math.max(a, b), Math.min(a, b));
      gcd.compute();
   }
   
//    public int answer() {
//       
//    }
   
   public void printSteps() {
      // step 1
      System.out.println("Equations with recursive calls: ");
      GCD curr = steps.get(0);
      System.out.print(curr + " = " + curr.next());
      int n = curr.toString().length();
      int m = curr.next().length();
      for (int i = 1; i < steps.size(); i++) {
         curr = steps.get(i);
         System.out.printf(" = %s\n%" + n + "s", curr, "");
         if (m == curr.next().length()) {
            System.out.print(" = " + curr.next());
         } else {
            System.out.printf(" = %s%" + (m - curr.next().length()) + "s",
                              curr.next(), "");
         }
      }
      System.out.println();
      System.out.println();
      
      // step 2
      System.out.println("Tableau form: ");
      curr = steps.get(0);
      System.out.println(curr.tableau() + "   " + curr.tableauAlternate());
      int x = String.valueOf(curr.a).length();
      int y = curr.tableau().length();
      for (int i = 1; i < steps.size() - 1; i++) {
         curr = steps.get(i);
         if (x == String.valueOf(curr.a).length()) {
            System.out.print(curr.tableau());
         } else {
            System.out.printf("%" + (x - String.valueOf(curr.a).length())
                              + "s%s", "", curr.tableau());
         }
         if (y == curr.tableau().length()) {
            System.out.println("   " + curr.tableauAlternate());
         } else {
            System.out.printf("%" + (y - curr.tableau().length()
                              - (x - String.valueOf(curr.a).length()))
                              + "s   %s\n", "", curr.tableauAlternate());
         }
      }
      System.out.println();
      
      // step 3
      System.out.println("Back substitution: ");
      curr = steps.get(steps.size() - 1);
      int z = String.valueOf(curr.b).length();
      curr = steps.get(steps.size() - 2);
      System.out.println(curr.tableauAlternate());
      for (int i = 2; i < steps.size(); i++) {
         System.out.printf("%" + z + "s = ", " ");
         for (int j = 0; j < i; j++) {
            curr = steps.get(steps.size() - j - 2);
            System.out.print(curr.tableauAlternate("") + "(");
         }
         System.out.print(curr.b);
         for (int j = 0; j < i; j++) {
            System.out.print(")");
         }
         System.out.println();
      }
   }
   
   // gcd(a, b) = gcd(b, a mod b)
   // gcd(a, 0) = a
   private class GCD {
      public int a;
      public int b;
      public boolean coprime;
      
      public GCD(int a, int b) {
         if (a < 0 || b < 0) {
            throw new IllegalArgumentException();
         }
         this.a = a;
         this.b = b;
      }
      
      public int compute() {
         int result = compute(a, b);
         if (result == 1) {
            coprime = true;
         } else {
            coprime = false;
         }
         return result;
      }
      
      private int compute(int a, int b) {
         if (b == 0) {
            return a;
         }
         steps.add(new GCD(a, b));
         return compute(b, a % b);
      }
      
      // returns gcd(a, b)
      public String toString() {
         if (b == 0) {
            return String.valueOf(a);
         }
         return "gcd(" + a + ", " + b + ")";
      }
      
      // returns gcd(b, a mod b)
      public String next() {
         return "gcd(" + b + ", " + a + " mod " + b + ")";
      }
      
      // returns a = q * b + r
      public String tableau() {
         return a + " = " + a / b + " * " + b + " + " + a % b;
      }
      
      // returns r = a - q * b
      public String tableauAlternate() {
         return a % b + " = " + tableauAlternate(String.valueOf(b));
      }
      
      // returns a - q * s
      public String tableauAlternate(String s) {
         return a + " - " + a / b + " * " + s;
      }
   }
}