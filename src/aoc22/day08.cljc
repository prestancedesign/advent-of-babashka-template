(ns aoc22.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "aoc22/day08.txt")))

(defn parse [input]
  (->> input
       (str/split-lines)
       (mapv #(mapv (comp parse-long str) %))))

(defn visible? [forest x y]
  (let [w (count (first forest))
        h (count forest)
        tree (fn [x y] (get-in forest [x y]))
        height (tree x y)]
    (or
     (every? #(< (tree % y) height) (range 0 x))
     (every? #(< (tree % y) height) (range (inc x) w))
     (every? #(< (tree x %) height) (range 0 y))
     (every? #(< (tree x %) height) (range (inc y) h)))))

(defn part-1
  "Run with bb -x aoc22.day08/part-1"
  [_]
  (count
   (filter true?
           (let [forest (parse input)
                 w (count (first forest))
                 h (count forest)]
             (for [x (range 0 w)
                   y (range 0 h)]
               (visible? forest x y))))))

(defn take-trees
  [pred xs]
  (loop [res []
         xs xs]
    (let [x (first xs)]
      (cond
        (empty? xs) res
        (not (pred x)) (conj res x)
        :else (recur (conj res x) (next xs))))))

(defn distance [forest x y]
  (let [w (count (first forest))
        h (count forest)
        tree (fn [x y] (get-in forest [x y]))
        height (tree x y)]
    (*
     (count (take-trees #(< (tree % y) height) (range (dec x) -1 -1)))
     (count (take-trees #(< (tree % y) height) (range (inc x) w)))
     (count (take-trees #(< (tree x %) height) (range (dec y) -1 -1)))
     (count (take-trees #(< (tree x %) height) (range (inc y) h))))))

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_]
  (let [forest (parse input)
        w (count (first forest))
        h (count forest)]
    (apply max
           (for [x (range 0 w)
                 y (range 0 h)]
             (distance forest x y)))))
