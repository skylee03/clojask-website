{:title "Examples" 
:date "2021-09-29"
:layout :post
:toc :ul}

#### Basic example

First, import the Clojask library
```clojure
(:require [clojask.dataframe :as clojask])
```


The simple way:
```clojure
(def df (clojask/dataframe "resources/employees.csv"))
(clojask/print-df df)
(clojask/set-type df "Salary" "double")
(clojask/filter df "Salary" (fn [salary] (<= salary 800)))
(clojask/operate df (fn [salary] (* salary 1.2)) "Salary")
(clojask/compute df 8 "resources/output.csv" :exception true)
```

Using the `->` macro:
```clojure
(-> (clojask/dataframe "resources/employees.csv" :have-col true)
    (clojask/set-type "Salary" "double")
    (clojask/filter "Salary" (fn [salary] (<= salary 800)))
    (clojask/operate (fn [salary] (* salary 1.2)) "Salary")
    (clojask/compute 8 "resources/output.csv" :exception true))
```

#### Import csv file with no column names header

```clojure
(def df (clojask/dataframe "resources/employees.csv" :have-col false))
(clojask/print-df df)

;; output
|            Col_1 |            Col_2 |            Col_3 |            Col_4 |            Col_5 |
|------------------+------------------+------------------+------------------+------------------|
| java.lang.String | java.lang.String | java.lang.String | java.lang.String | java.lang.String |
|         Employee |     EmployeeName |       Department |           Salary |       UpdateDate |
|                1 |            Alice |               11 |              300 |       2020/12/12 |
|                2 |              Bob |               11 |              600 |       2020/12/01 |
|                3 |            Carla |               12 |              900 |       2020/12/03 |
|                4 |           Daniel |               12 |             1000 |       2020/12/05 |
|                5 |           Evelyn |               13 |              800 |       2020/12/03 |
|                6 |        Ferdinand |               21 |              700 |       2020/12/05 |
|                7 |              Amy |               11 |            50000 |       2020/11/26 |
```

#### Multi-threading with core.async

```clojure
(:require [clojure.core.async :as async])

(def x (dataframe "resources/Employees-large.csv"))
(def y (dataframe "resources/Employees.csv"))

;; create a thread for each operation
(async/thread (set-type x "double" "Department"))
(async/thread (set-type y "double" "Department"))

(time (left-join x y ["Employee"] ["Employee"] 4 "output/test.csv" :exception false))
```
