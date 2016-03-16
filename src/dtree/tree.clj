(ns dtree.tree
  (:require [dtree.data :refer [remove-feature]]))

; Decision Tree Example
; ----------------------------------------------------
; (def dt {:split-fn :speed-limit
;          :children {:yes {:split-fn :grade
;                           :children
;                           {:steep {:value :slow}
;                            :flat {:value :fast}}}
;                     :no {:split-fn :bumpiness
;                          :chilren
;                          {:bumpy {:value :slow}
;                           :smooth {:value :fast}}}}})
; ----------------------------------------------------

(defn leaf? [dtree]
  (boolean (get dtree :value)))

(defn make-leaf [value]
  {:value value})

(defn make-node [split-fn data tree-algo-fn]
  {:split-fn split-fn
   :children (->> data
                  (group-by split-fn)
                  (map (fn [[k d]] [k (tree-algo-fn (remove-feature k d))]))
                  (into {}))})

(defn predict
  [{:keys [split-fn value] :as dtree} datapoint]
  (if (leaf? dtree)
    value
    (recur (get-in dtree [:children (split-fn datapoint)]) datapoint)))
