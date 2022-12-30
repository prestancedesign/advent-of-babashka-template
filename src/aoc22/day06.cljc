(ns aoc22.day06
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "aoc22/day06.txt")))

(defn start-of-marker
  [size data]
  (->> data
       (partition size 1)
       (take-while #(> size (count (distinct %))))
       count
       (+ size)))

(defn part-1
  "Run with bb -x aoc22.day06/part-1"
  [_]
  (start-of-marker 4 input))

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (start-of-marker 14 input))
