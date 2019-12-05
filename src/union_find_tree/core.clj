(ns union-find-tree.core
  "Library designed to manipulate union-find tree."
  (:refer-clojure :exclude [find]))

(defrecord UnionFind [^long parent ^long rank])

(defn make
  "Initializes tree with `n` elements (0, 1, ..., `n` - 1)."
  [n]
  (into-array (map #(->UnionFind % 0) (range n))))

(defn find
  "Returns a root of a tree of the given `x`."
  [^"[Lunion_find_tree.core.UnionFind;" s x]
  (letfn [(doit [x]
            (let [^UnionFind sx (aget s x)]
              (if (= x (.parent sx))
                x
                (let [p (doit (.parent sx))]
                  (aset s x (->UnionFind p (.rank sx)))
                  p))))]
    (doit x)))

(defn union!
  "Combines the trees that `x` and `y` belong to."
  [^"[Lunion_find_tree.core.UnionFind;" s x y]
  (let [px (find s x)
        py (find s y)
        ^UnionFind spx (aget s px)
        ^UnionFind spy (aget s py)]
    (if (< (.rank spx) (.rank spy))
      (aset s px (->UnionFind py (.rank spx)))
      (do
        (aset s py (->UnionFind px (.rank spy)))
        (when (= (.rank spy) (.rank spx))
          (aset s px (->UnionFind (.parent spx) (inc (.rank spx)))))))))

(defn same-set?
  "Returns true if `x` and `y` are belong to the same tree."
  [s x y]
  (= (find s x) (find s y)))
