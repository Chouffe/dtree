(ns dtree.core
  (:require [dtree.id3 :refer [make-dtree]]
            [dtree.tree :refer [predict]]
            [dtree.data :refer [make]]))


; DATA EXAMPLE
; -----------------------------------------
(def fs [:grade :bumpiness :speed-limit])
(def X [[:steep :bumpy :yes]
        [:steep :smooth :yes]
        [:flat :bumpy :no]
        [:steep :smooth :no]])
(def y [:slow :slow :fast :fast])
; -----------------------------------------

(def data (make fs X y))
(def dtree (make-dtree data))

;; How to use it?
(->> [[:steep :bumpy :no]]
     (make fs)
     first
     (predict dtree))
