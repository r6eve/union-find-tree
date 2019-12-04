(ns union-find-tree.core
  (:refer-clojure :exclude [find]))

(defrecord UnionFind [parent rank])

(defn make
  [n]
  (into-array (map #(->UnionFind % 0) (range n))))

(defn find
  [s x]
  (letfn [(doit [x]
            (let [sx (aget s x)]
              (if (= x (.parent sx))
                x
                (let [p (doit (.parent sx))]
                  (aset s x (->UnionFind p (.rank sx)))
                  p))))]
    (doit x)))

(defn union!
  [s x y]
  (let [px (find s x)
        py (find s y)
        spx (aget s px)
        spy (aget s py)]
    (if (< (.rank spx) (.rank spy))
      (aset s px (->UnionFind py (.rank spx)))
      (do
        (aset s py (->UnionFind px (.rank spy)))
        (when (= (.rank spy) (.rank spx))
          (aset s px (->UnionFind (.parent spx) (inc (.rank spx)))))))))

(defn same-set?
  [s x y]
  (= (find s x) (find s y)))
