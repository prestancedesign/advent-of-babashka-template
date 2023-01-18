(ns aoc22.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "aoc22/day07.txt")))

(def sample
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

;; - / (dir)
;;   - a (dir)
;;     - e (dir)
;;       - i (file, size=584)
;;     - f (file, size=29116)
;;     - g (file, size=2557)
;;     - h.lst (file, size=62596)
;;   - b.txt (file, size=14848514)
;;   - c.dat (file, size=8504156)
;;   - d (dir)
;;     - j (file, size=4060174)
;;     - d.log (file, size=8033020)
;;     - d.ext (file, size=5626152)
;;     - k (file, size=7214296)

;; {"a"
;;  {"e"
;;   {"i" 584}
;;   "f" 29116
;;   "g" 2557
;;   "h.lst" 62596}
;;  "b.txt" 14848514
;;  "c.dat" 8504156
;;  "d"
;;  {"j" 4060174
;;   "d.log" 8033020
;;   "d.ext" 5626152
;;   "k" 7214296}}

(defn parse-fs
  [input]
  (loop [fs {}
         path []
         [line & lines] (str/split input #"\n *")]
    (if-not line
      fs
      (let [[_ ls dir size file cd] (re-find #"[$] (ls)|dir (.+)|(\d+) (.+)|[$] cd (.+)" line)]
        (cond
          ls (recur fs path lines)
          dir (recur (assoc-in fs (conj path dir) {}) path lines)
          file (recur (assoc-in fs (conj path file) (parse-long size)) path lines)
          cd (case cd
               "/" (recur fs [] lines)
               ".." (recur fs (pop path) lines)
               (recur fs (conj path cd) lines)))))))

(parse-fs sample)

;; (loop [xs (seq [1 2 3])
;;        result []]
;;   (if xs
;;     (let [x (first xs)]
;;       (recur (next xs) (conj result (* x x))))
;;     result))

;; (map #(* % %) [1 2 3])

(defn part-1
  "Run with bb -x aoc22.day06/part-1"
  [_])

(defn part-2
  "Run with bb -x aoc22.day02/part-2"
  [_])

(let [f (fn [state [_ cmd path filesize _]]
          (if (= "cd" cmd)
            (if (= path "..")
              (update state :path pop)
              (update state :path conj path))
            (let [size (parse-long filesize)]
              (loop [state state path (:path state)]
                (if (seq path)
                  (recur (update-in state [:size path] #(+ size (or % 0))) (pop path))
                  state)))))
      m (->> sample
             (re-seq #"\$ (cd) (.+)|(\d+) (.+)")
             (reduce f {:path []})
             :size)
      v (vals m)
      x (+ (m ["/"]) -70000000 30000000)
      q1 (->> v (filter #(>= 100000 %)) (reduce +))
      q2 (->> v (filter #(>= % x)) (reduce min))]
  [q1 q2])
;; => [95437 24933642]
