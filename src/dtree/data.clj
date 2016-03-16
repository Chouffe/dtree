(ns dtree.data
  (:require [dtree.utils :as utils]))

; DATA EXAMPLE
; -----------------------------------------
; (def fs [:grade :bumpiness :speed-limit])
; (def X [[:steep :bumpy :yes]
;         [:steep :smooth :yes]
;         [:flat :bumpy :no]
;         [:steep :smooth :no]])
; (def y [:slow :slow :fast :fast])
; -----------------------------------------

(def target-kw :target)

(defn make
  "Creates the data from features, X and optionaly y"
  ([features X] (mapv #(->> %
                            (map-indexed (fn [i xi] [(get features i) xi]))
                            (into {}))
                      X))
  ([features X y] (make (conj features target-kw) (mapv conj X y))))

(defn probabilities [data]
  (let [n (count data)]
    (->> data
         (group-by target-kw)
         (mapv (fn [[k xs]] [k (/ (count xs) n)]))
         (into {}))))

(defn entropy [data]
  (apply utils/entropy (vals (probabilities data))))

(defn split-by [split-fn data]
  (vals (group-by split-fn data)))

(defn lone-class? [data]
  (->> data (map target-kw) (apply =)))

(defn features [data]
  (->> data
       first
       keys
       (remove #{target-kw})
       (into #{})))

(defn feature-values [data feature]
  (->> data
       (map feature data)
       (into #{})))

(defn select-class [data]
  (->> data
       (group-by target-kw)
       (sort-by (comp count second) >)
       first
       key))

(def empty-features? #(-> % features count (= 0)))

(defn remove-feature [feature data]
  (assert (not= feature target-kw))
  (mapv #(dissoc % feature) data))
