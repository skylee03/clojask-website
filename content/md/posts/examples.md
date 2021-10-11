{:title "Examples" 
:date "2021-09-29"
:layout :post
:toc true}

#### Multi-threading with core.async

```clojure
(def x (dataframe "resources/Employees-large.csv"))
(def y (dataframe "resources/Employees.csv"))

;; create a thread for each operation
(async/thread (set-type x "double" "Department"))
(async/thread (set-type y "double" "Department"))

(time (left-join x y ["Employee"] ["Employee"] 4 "output/test.csv" :exception false))
```