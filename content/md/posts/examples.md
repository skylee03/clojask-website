{:title "Examples" 
:date "2021-09-29"
:layout :post
:toc true}


Examples and their corresponding source files have been moved to a [GitHub Repository](https://github.com/clojure-finance/clojask-examples) for ease of use

All of the following operations can be executed on the files provided on the Clojask Examples [GitHub Repository](https://github.com/clojure-finance/clojask-examples)  

---  


#### Basic Tutorial
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/basic_tutorial.clj) of basic data manipulation operations performed through Clojask
```clojure
(def df (clojask/dataframe "resources/employees.csv"))
(clojask/print-df df)
(clojask/set-type df "Salary" "double")
(clojask/filter df "Salary" (fn [salary] (<= salary 800)))
(clojask/operate df (fn [salary] (* salary 1.2)) "Salary")
(clojask/compute df 8 "resources/output.csv" :exception true)

;; Using the `->` macro:
(-> (clojask/dataframe "resources/employees.csv")
(clojask/set-type "Salary" "double")
(clojask/filter "Salary" (fn [salary] (<= salary 800)))
(clojask/operate (fn [salary] (* salary 1.2)) "Salary")
(clojask/compute 8 "resources/output.csv" :exception true))

;; import csv files with no column name header
(def df (clojask/dataframe "resources/employees.csv" :have-col false))
(clojask/print-df df)

```


---
#### Ordinary Join
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/ordinary_join.clj) of a basic join operation on Clojask 
```clojure
(clojask/dataframe "resources/employees.csv"))
(def y (clojask/dataframe "resources/employees-workleave.csv"))

(compute (clojask/left-join x y ["Employee"] ["Employee"] 8) "resources/output.csv" :exception false)

```


---
#### Multi-Threading
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/multi_threading.clj) of multi threading various operations on the Clojask 
```clojure
(def x (clojask/dataframe "resources/employees.csv"))
(def y (clojask/dataframe "resources/employees-workleave.csv"))

;; create a thread for each operation
(async/thread (clojask/set-type x "Salary" "double"))
(async/thread (clojask/set-type y "WorkLeave" "double"))

(compute (clojask/left-join x y ["Employee"] ["Employee"]) 8 "resources/output.csv" :exception false)

```


---
#### Rolling Join
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/rolling_join.clj) on performing rolling joins in the Clojask 
```clojure
(clojask/dataframe "resources/employees.csv"))
(def y (clojask/dataframe "resources/employees-workleave.csv"))

(clojask/set-type x "UpdateDate" "datetime")
(clojask/set-type y "UpdateDate" "datetime"))

(clojask/compute (clojask/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "resources/output.csv" :exception false)

```


---
#### Coming Soon
- Timezone