(ns aoc22.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (->> (slurp (io/resource "aoc22/day03.txt"))
                (str/split-lines)))

(def alphabet "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(def priorities (zipmap alphabet (range 1 53)))

(defn get-common-item [s]
  (let [c (count s)
        c1 (set (subs s 0 (/ c 2)))
        c2 (set (subs s (/ c 2) c))]
    (set/intersection c1 c2)))

(defn part-1
  "Run with bb -x aoc22.day03/part-1"
  [_]
  (->> input
       (map get-common-item)
       (map first)
       (map priorities)
       (apply +)))

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (->> (map set input)
       (partition 3)
       (map (fn [[a b c]]
              (set/intersection a b c)))
       (map first)
       (map priorities)
       (apply +)))
