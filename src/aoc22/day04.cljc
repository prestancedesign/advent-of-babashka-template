(ns aoc22.day04
  (:require
   [clojure.java.io :as io]))

(def input (slurp (io/resource "aoc22/day04.txt")))

(defn parse [s]
  (->> (re-seq #"\d+" s)
       (map parse-long)
       (partition 4)))

(defn fully-contains?
  [[a1 a2 b1 b2]]
  (or (<= a1 b1 b2 a2)
      (<= b1 a1 a2 b2)))

(defn part-1
  "Run with bb -x aoc22.day04/part-1"
  [_]
  (->> (parse input)
       (filter fully-contains?)
       count))

(defn overlap?
  [[a1 a2 b1 b2]]
  (or (<= a1 b1 a2)
      (<= b1 a1 b2)))

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (->> (parse input)
       (filter overlap?)
       count))
