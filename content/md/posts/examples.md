{:title "Examples" 
:date "2021-09-29"
:layout :post
:toc :ul}

#### Basic example

The simple way:
```clojure
(dataframe df "resources/Employees-large.csv" :have-col true)
(set-type df "Salary" "double")
(filter df "Salary" (fn [salary] (<= salary 800)))
(operate df (fn [salary] (* salary 1.2)) "Salary")
(compute df 8 "output.csv" :exception true)
```

Using the `->` macro:
```clojure
(-> (dataframe "resources/Employees-large.csv" :have-col true)
    (set-type "Salary" "double")
    (filter "Salary" (fn [salary] (<= salary 800)))
    (operate (fn [salary] (* salary 1.2)) "Salary")
    (compute 8 "output.csv" :exception true))
```

#### Multi-threading with core.async

```clojure
(def x (dataframe "resources/Employees-large.csv"))
(def y (dataframe "resources/Employees.csv"))

;; create a thread for each operation
(async/thread (set-type x "double" "Department"))
(async/thread (set-type y "double" "Department"))

(time (left-join x y ["Employee"] ["Employee"] 4 "output/test.csv" :exception false))
```
