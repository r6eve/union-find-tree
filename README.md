union-find-tree
===============
[![Build Status][]][Build Results]
[![Codecov Status][]][Codecov Results]

A Clojure library designed to manipulate [union-find tree][].

Union-find tree is useful to quickly group sets and check whether the given
elements belong to same sets.

## Usage

Make a union-find tree.

```clojure
(require '[union-find-tree.core :as union-find-tree])

(def s (union-find-tree/make 5))
```

First, all elements are disjoint.

```
tree:
      0 1 2 3 4
```

Union 0 with 3, and 1 with 2.

```clojure
(union-find-tree/union! s 0 3)
(union-find-tree/union! s 1 2)
```

```
tree:
      0  1  4
      |  |
      3  2
```

Check if the same set.

```clojure
(union-find-tree/same-set? s 0 1) ; false
(union-find-tree/same-set? s 2 3) ; false
(union-find-tree/same-set? s 0 3) ; true
(union-find-tree/same-set? s 2 1) ; true
```

Union 0 with 2, and compress tree automatically.

```clojure
(union-find-tree/union! s 0 2)
```

```
tree:
        0    4         0     4
       / \           / | \
      3   1     =>  3  1  2
          |
          2
```

Check.

```clojure
(union-find-tree/same-set? s 1 3) ; true
(union-find-tree/same-set? s 2 4) ; false
```

Union.

```clojure
(union-find-tree/union! s 4 3)
```

```
tree:
         0            0
       / | \        / | \  \
      3  1  2  =>  3  1  2  4
      |
      4
```

Check.

```clojure
(union-find-tree/same-set? s 4 1) ; true
(union-find-tree/same-set? s 2 4) ; true
```

## License

Copyright © 2019 r6eve

[Build Status]: https://github.com/r6eve/union-find-tree/workflows/main/badge.svg
[Build Results]: https://github.com/r6eve/union-find-tree/actions
[Codecov Status]: https://codecov.io/github/r6eve/union-find-tree/coverage.svg?branch=master
[Codecov Results]: https://codecov.io/github/r6eve/union-find-tree?branch=master
[union-find tree]: https://www.slideshare.net/iwiwi/ss-3578491/3
