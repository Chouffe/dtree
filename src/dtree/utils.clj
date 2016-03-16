(ns dtree.utils)

(defn log2 [n]
  (/ (Math/log n) (Math/log 2)))

(assert (= 0.0 (log2 1)))
(assert (= 1.0 (log2 2)))

(defn entropy
  ([p] (if (zero? p) 0.0 (- (* p (log2 p)))))
  ([p & ps] (+ (entropy p) (apply entropy ps))))

(assert (= 0.0 (entropy 0)))
(assert (= 0.0 (entropy 1)))
(assert (= 0.5 (entropy 1/2)))
(assert (= 1.0 (entropy 1/2 1/2)))
