(ns aoc23.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input (->> (slurp (io/resource "aoc23/day02.txt"))
                str/split-lines))

(def set-to-compare
  {:red 12
   :green 13
   :blue 14})

(defn extract-id [s]
  (parse-long (re-find #"\d+" s)))

(defn parse-cubes [s]
  (->> (str/split s #",")
       (map #(re-seq #"\d+|\w+" %))
       (reduce (fn [acc [n color]] (assoc acc (keyword color) (parse-long n))) {})))

(defn compare-games-sets [m1 m2]
  (let [common-keys (clojure.set/intersection (set (keys m1)) (set (keys m2)))]
    (every? #(>= (get m1 %) (get m2 %)) common-keys)))

(defn part-1
  "Run with (n)bb -x aoc23.day02/part-1"
  [_]
  (->> input
       (map #(str/split % #":"))
       (reduce (fn [acc [k v]] (assoc acc (extract-id k) (->> (str/split v #";")
                                                              (map parse-cubes)
                                                              (map (partial compare-games-sets set-to-compare))))) {})
       (remove #(some false? (val %)))
       keys
       (apply +)
       prn))

(defn parse-line [line]
  (let [[_ colors] (str/split line #":")]
    (->> (str/split colors #";")
         (map parse-cubes)
         (apply merge-with max)
         vals
         (apply *))))

(defn part-2
  "Run with (n)bb -x aoc23.day02/part-2"
  [_]
  (apply + (map parse-line input)))

(comment

  (def sample
    "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

  (->> (str/split-lines sample)
       (map parse-line)
       (apply +)))
