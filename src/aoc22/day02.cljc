(ns aoc22.day02
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "aoc22/day02.txt")))

(def shape
  {"A" :rock "B" :paper "C" :scissors
   "X" :rock "Y" :paper "Z" :scissors})

(def shape-score
  {:rock 1
   :paper 2
   :scissors 3})

(defn score [[l r]]
  (+ (shape-score r)
     (cond
       (= l r) 3
       (= [:rock :paper] [l r]) 6
       (= [:paper :scissors] [l r]) 6
       (= [:scissors :rock] [l r]) 6
       :else 0)))

(defn part-1
  "Run with bb -x aoc22.day02/part-1"
  [_]
  (->> input
       (re-seq #"\w")
       (map shape)
       (partition 2)
       (map score)
       (apply +)))

(part-1 {})
;; => 12679

(defn score2 [[l r]]
  (case r
    :win (score [l ({:rock :paper
                     :paper :scissors
                     :scissors :rock} l)])
    :draw (score [l l])
    :lose (score [l ({:paper :rock
                      :scissors :paper
                      :rock :scissors} l)])))

(def shape2
  {"A" :rock "B" :paper "C" :scissors
   "X" :lose "Y" :draw "Z" :win})

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (->> input
       (re-seq #"\w")
       (map shape2)
       (partition 2)
       (map score2)
       (apply +)))

(part-2 {})
;; => 14470
