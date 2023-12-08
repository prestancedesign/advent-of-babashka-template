(ns aoc23.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> (slurp (io/resource "aoc23/day08.txt"))
                (str/split-lines)))

(defn parse-nodes [nodes]
  (into {}
        (map (fn [node]
               (let [[pos left right] (re-seq #"\w+" node)]
                 [pos {:left left
                       :right right}]))
             nodes)))

(defn parse-instructions [instr]
  (map #({"L" :left
          "R" :right}
         (str %))
       instr))

(defn part-1
  [[instructions _ & nodes]]
  (let [parsed-instructions (parse-instructions instructions)
        parsed-nodes (parse-nodes nodes)]
    (loop [steps 0
           current-node "AAA"
           inst parsed-instructions]
      (if (= current-node "ZZZ")
        steps
        (let [next-node (get-in parsed-nodes [current-node (first inst)])
              next-instruction (or (next inst) parsed-instructions)]
          (recur (inc steps) next-node next-instruction))))))

(println "Part 1 =" (part-1 input))

(defn part-2
  [input])

(comment

  (def sample-data
    "LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)")

  (part-1 (str/split-lines sample-data))) ;; => 6
