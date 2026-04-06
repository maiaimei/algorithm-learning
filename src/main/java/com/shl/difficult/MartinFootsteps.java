package com.shl.difficult;

import java.util.HashSet;
import java.util.Scanner;

public class MartinFootsteps {

  public static class Solution {

    public static void main(String[] args) {

      try (Scanner scanner = new Scanner(System.in)) {

        long X1 = scanner.nextLong();
        long X2 = scanner.nextLong();
        long V1 = scanner.nextLong();
        long N = scanner.nextLong();

        // D is the distance from Martin to father's current position
        long D = X1 - X2;

        // Phase 1: collect unique candidate V2 values by enumerating divisors of
        // positions = D + k*V1 (for k = 0..N) but only when position > 0 and
        // t = position / v2 >= k (same filtering as original implementation).
        // We store candidates into a set to avoid duplicate work in phase 2.
        HashSet<Long> candidateV2Set = new HashSet<>();

        for (long k = 0; k <= N; k++) {
          long position = D + Math.multiplyExact(k, V1);
          if (position <= 0) {
            continue; // preserve original behavior: skip non-positive positions
          }

          long limit = (long) Math.sqrt(position);
          for (long d = 1; d <= limit; d++) {
            if (position % d == 0) {
              long v2b = position / d;

              // Check t >= k condition (t = position / v2)
              long tForA = position / d;
              if (tForA >= k) {
                candidateV2Set.add(d);
              }

              if (v2b != d) {
                long tForB = position / v2b;
                if (tForB >= k) {
                  candidateV2Set.add(v2b);
                }
              }
            }
          }
        }

        // Phase 2: for each unique candidate V2 compute the exact number of
        // valid k in [0..N] that satisfy both divisibility and t >= k using
        // number theory (linear congruence + arithmetic progression counting).
        long bestF = 0;
        long bestV2 = 0;

        for (long candidateV2 : candidateV2Set) {
          long count = countValidStepsForV2(D, V1, N, candidateV2);
          if (count > bestF || (count == bestF && candidateV2 > bestV2)) {
            bestF = count;
            bestV2 = candidateV2;
          }
        }

        System.out.println(bestF + " " + bestV2);
      }
    }

    /**
     * Count number of k in [0..N] such that:
     * position = D + k*V1 > 0,
     * position % v2 == 0,
     * t = position / v2 >= k.
     * <p>
     * Uses the linear congruence k*V1 ≡ -D (mod v2). Let g = gcd(V1, v2).
     * Solutions exist iff g | D. Reduce and compute particular solution k0 in
     * range [0, m-1] where m = v2/g is the period. Then valid k are k0 + j*m.
     * We intersect that arithmetic progression with [kMin..kMax] where
     * kMin = smallest k with position > 0 (preserves original skip of position<=0),
     * kMax = N (or min(N, floor(D/(v2-V1))) when v2>V1 to ensure t>=k).
     */
    private static long countValidStepsForV2(long D, long V1, long N, long v2) {
      if (v2 <= 0) {
        return 0;
      }

      // Compute minimal k that makes position > 0 (original code skipped position<=0)
      long kMin;
      if (D > 0) {
        kMin = 0;
      } else if (D == 0) {
        // position = 0 when k == 0, original code skipped position<=0, so start from 1
        kMin = 1;
      } else {
        // D < 0: need smallest k such that D + k*V1 > 0
        kMin = (-D) / V1 + 1; // floor(-D/V1) + 1
      }

      // Upper bound kMax from t >= k constraint
      long kMax = N;
      if (v2 > V1) {
        long denom = v2 - V1; // positive
        // If D < 0 then D/denom is negative -> kMax becomes negative -> no solution
        long bound = D / denom; // integer division is fine since D>=0 typically; handled when negative
        if (bound < kMax) {
          kMax = bound;
        }
      }

      if (kMin > kMax) {
        return 0L;
      }

      long g = gcd(V1, v2);
      if (D % g != 0) {
        return 0L; // linear congruence has no solution
      }

      long m = v2 / g; // period of k solutions

      // reduce congruence: k * (V1/g) ≡ (-D/g) (mod m)
      long V1p = V1 / g;
      long Dp = D / g;

      long k0;
      if (m == 1) {
        // modulus 1: every integer k satisfies congruence (k ≡ 0 mod 1)
        k0 = 0;
      } else {
        long inv = modInverse(V1p % m + m, m);
        // particular solution k0 = (-Dp * inv) mod m
        long tmp = ((-Dp % m) + m) % m;
        k0 = (mulMod(tmp, inv, m)) % m;
      }

      // Find first solution >= kMin: k = k0 + s*m >= kMin
      long firstK;
      if (k0 >= kMin) {
        firstK = k0;
      } else {
        long diff = kMin - k0;
        long steps = (diff + m - 1) / m; // ceil(diff / m)
        firstK = k0 + steps * m;
      }

      if (firstK > kMax) {
        return 0L;
      }
      return ((kMax - firstK) / m) + 1;
    }

    private static long mulMod(long a, long b, long mod) {
      return (a * b) % mod;
    }

    // Extended gcd
    private static long gcd(long a, long b) {
      a = Math.abs(a);
      b = Math.abs(b);
      while (b != 0) {
        long t = a % b;
        a = b;
        b = t;
      }
      return a;
    }

    // Modular inverse using extended Euclidean algorithm. Assumes a and mod are coprime.
    private static long modInverse(long a, long mod) {
      long m0 = mod;
      long x0 = 0, x1 = 1;
      if (mod == 1) {
        return 0;
      }
      while (a > 1) {
        long q = a / mod;
        long t = mod;
        mod = a % mod;
        a = t;
        t = x0;
        x0 = x1 - q * x0;
        x1 = t;
        if (mod == 0) {
          break;
        }
      }
      long res = x1;
      res %= m0;
      if (res < 0) {
        res += m0;
      }
      return res;
    }

  }
}