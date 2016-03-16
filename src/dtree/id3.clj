(ns dtree.id3
  (:require [dtree.tree :refer [make-leaf make-node]]
            [dtree.data :refer [lone-class? select-class split-by entropy empty-features? features]]))

(defn information-gain [data split-fn]
  (- (entropy data)
     (->> data
          (split-by split-fn)
          (map entropy)
          (reduce +))))

(defn select-feature [data]
  (->> data
       features
       (map (fn [feature] [feature (information-gain data feature)]))
       (sort-by second >)
       first
       first))

(defn make-dtree [data]
  (cond
    (lone-class? data)     (make-leaf (select-class data))
    (empty-features? data) (make-leaf (select-class data))
    :else                  (-> data select-feature (make-node data make-dtree))))
