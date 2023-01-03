(ns aoc22.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "aoc22/day05.txt")))

(defn parse-stacks
  [input]
  (let [columns (->> (str/split input #"\n *")
                     (take-while seq)
                     (apply mapv str))]
    (into [()]
          (for [column columns
                :let [letters (re-find #"[A-Z]+" column)]
                :when letters]
            (apply list letters)))))

(defn parse-moves
  [input]
  (for [[_ & n-from-to] (re-seq #"move (\d+) from (\d+) to (\d+)" input)]
    (map parse-long n-from-to)))

(defn move1
  [stacks [n from to]]
  (loop [source (stacks from)
         target (stacks to)
         n n]
    (if (pos? n)
      (recur
       (pop source)
       (conj target (peek source))
       (dec n))
      (assoc stacks
             from source
             to target))))

(defn part-1
  "Run with bb -x aoc22.day05/part-1"
  [_]
  (->> (reduce move1 (parse-stacks input) (parse-moves input))
       (map peek)
       (apply str)))

(defn move2
  [stacks [n from to]]
  (loop [source (stacks from)
         temp ()
         n n]
    (if (pos? n)
      (recur
       (pop source)
       (conj temp (peek source))
       (dec n))
      (assoc stacks
             from source
             to (into (stacks to) temp)))))

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (->> (reduce move2 (parse-stacks input) (parse-moves input))
       (map peek)
       (apply str)))
