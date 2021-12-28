{:title "Aggregation Functions"
:date "2021-09-28"
:layout :post 
:tags  []} 

#### Aggregation Functions

In Clojask, you can aggregate the whole dataframe or aggregate the grouped by dataframe(s). The former could be known as "simple aggregation", and the latter as "group-by aggregation". Some given functions for simple aggregation are defined in the namespace `clojask.api.aggregate`, and the given functions for group-by aggregation are defined in the namespace `clojask.api.gb-aggregate`. 

Below is the full list of given functions for the two types of aggregation.

`clojask.api.aggregate`:

`max`: Find the max value (use `clojure.core/compare` as the comparator)

`min`: Find the min value (use `clojure.core/compare` as the comparator)
<br>
*Note that the default behaviour for `clojask/min` is that `null` could be returned as a minimal value.

`clojask.api.gb-aggregate`:

`max`: Find the max value (use `clojure.core/compare` as the comparator)

`min`: Find the min value (use `clojure.core/compare` as the comparator) 

In addition to these given functions, you are also welcomed to define your own aggregation function.

##### How to define group-by aggregation functions?

This is the template:

```clojure
(defn gb-aggre-template
  [col]  ;; take only one argument which is the aggregation column in the format of vector
  ;; ... your implementation
  result    ;; return one variable (could be int / double / string / collection of above)
  )
```

Basically, the function should take one argument only, which is the full aggregation column. ***Here we simply assume this column should be smaller than memory!***

You may find many built-in function in Clojure also fulfilling this requirement, for example, `count`, `mean`, and countless function constructed from [`reduce`](https://clojuredocs.org/clojure.core/reduce).

##### How to define simple aggregation functions?

This is the template:

```clojure
(defn aggre-template
  ;; [new-value old-result]
  [old-result new-value]
  ;; old-result: the value of the result for the previous gb-aggre-template
  ;; new-value: the value for the column on the current row
  ;; ... your implementation
  new-result   ;; return the new result, and this will be passed as old-result for the next gb-aggre-template
  )
```

**Notes:**

1. The old-result for the first `aggre-template` is `clojask.api.aggregate/start`. So your function must be able to deal with cases when the first argument is `clojask.api.aggregate/start`.
2. Your function should be self-sustainable, meaning that the result of `aggre-template` should be safe as the input for `aggre-template`.

To better understand the this template, you may refer to the documentation of [`reduce`](https://clojuredocs.org/clojure.core/reduce), the `aggre-func` should be able to use in `reduce`.

---
