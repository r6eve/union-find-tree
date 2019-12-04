(ns union-find-tree.core-test
  (:require [clojure.test :refer [deftest is]]
            [union-find-tree.core :as union-find-tree]))

(deftest make-test
  (let [s (union-find-tree/make 5)]
    ;; tree:
    ;;       0 1 2 3 4
    (is (= [(union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 1 0)
            (union-find-tree/->UnionFind 2 0)
            (union-find-tree/->UnionFind 3 0)
            (union-find-tree/->UnionFind 4 0)]
           (vec s)))))

(deftest manipulation-test
  (let [s (union-find-tree/make 5)]
    (union-find-tree/union! s 0 3)
    (union-find-tree/union! s 1 2)
    (is (not (union-find-tree/same-set? s 0 1)))
    (is (not (union-find-tree/same-set? s 2 3)))
    (is (union-find-tree/same-set? s 0 3))
    (is (union-find-tree/same-set? s 2 1))
    ;; tree:
    ;;       0  1  4
    ;;       |  |
    ;;       3  2
    (is (= [(union-find-tree/->UnionFind 0 1)
            (union-find-tree/->UnionFind 1 1)
            (union-find-tree/->UnionFind 1 0)
            (union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 4 0)]
           (vec s)))

    (union-find-tree/union! s 0 2)
    (is (union-find-tree/same-set? s 1 3))
    (is (not (union-find-tree/same-set? s 2 4)))
    ;; tree:
    ;;         0    4         0     4
    ;;        / \           / | \
    ;;       3   1     =>  3  1  2
    ;;           |
    ;;           2
    (is (= [(union-find-tree/->UnionFind 0 2)
            (union-find-tree/->UnionFind 0 1)
            (union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 4 0)]
           (vec s)))

    (union-find-tree/union! s 4 3)
    (is (union-find-tree/same-set? s 4 1))
    (is (union-find-tree/same-set? s 2 4))
    ;; tree:
    ;;          0            0
    ;;        / | \        / | \  \
    ;;       3  1  2  =>  3  1  2  4
    ;;       |
    ;;       4
    (is (= [(union-find-tree/->UnionFind 0 2)
            (union-find-tree/->UnionFind 0 1)
            (union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 0 0)
            (union-find-tree/->UnionFind 0 0)]
           (vec s)))))
