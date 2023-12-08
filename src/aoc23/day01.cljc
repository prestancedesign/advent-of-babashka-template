(ns aoc23.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> (slurp (io/resource "aoc23/day01.txt"))
                (str/split-lines)))

(defn part-1
  "Run with bb -x aoc23.day01/part-1"
  [input]
  (apply + (map (fn [line]
                  (->> (re-seq #"\d" line)
                       ((juxt first last))
                       (apply str)
                       parse-long))
                input)))

(def literal
  ["one"
   "two"
   "three"
   "four"
   "five"
   "six"
   "seven"
   "eight"
   "nine"])

(def str->num
  (zipmap literal (range 1 (inc (count literal)))))

(defn part-2
  "Run with bb -x aoc23.day02/part-2"
  [input]
  (apply + (map (fn [line]
                  (->> (re-seq #"one|two|three|four|five|six|seven|eight|nine|\d" line)
                       ((juxt first last))
                       (map #(str->num % %))
                       (apply str)
                       parse-long))
                input)))

(comment
  (part-1 input) ; => 54561

  (part-2 input)) ; => 54076
