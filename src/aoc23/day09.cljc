(ns aoc23.day09
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> (slurp (io/resource "aoc23/day09.txt"))
                (str/split-lines)))

(defn diffs [numbers]
  (map - (next numbers) numbers))

(defn solve [input]
  (loop [line input
         result 0]
    (if (every? zero? line)
      result
      (recur (diffs line) (+ result (last line))))))

(defn parse [input]
  (map #(->> (re-seq #"-?\d+" %)
             (mapv parse-long)) input))

(defn part-1
  [input]
  (->> (parse input)
       (map solve)
       (apply +)))

(println "Part 1:" (part-1 input))

(defn part-2
  [input]
  (->> (parse input)
       (map reverse)
       (map solve)
       (apply +)))

(println "Part 2:" (part-2 input))

(comment

  (def sample-input
    (str/split-lines "0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"))

  (part-1 sample-input));; => 114
