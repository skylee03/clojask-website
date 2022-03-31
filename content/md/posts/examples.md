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

  (defn main
    []
    ;; The simple way
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

(defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    (compute (clojask/left-join x y ["Employee"] ["Employee"] 8) "resources/output.csv" :exception false)
    
```


---
#### Multi-Threading
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/multi_threading.clj) of multi threading various operations on Clojask 
```clojure

  (defn main
    []
    (def x (cj/dataframe "resources/employees.csv"))
    (def y (cj/dataframe "resources/employees-workleave.csv"))

    ;; create a thread for each operation
    (async/thread (cj/set-type x "Salary" "double"))
    (async/thread (cj/set-type y "WorkLeave" "double"))

    (compute (cj/left-join x y ["Employee"] ["Employee"]) 8 "resources/output.csv" :exception false)
    
```


---
#### Rolling Join
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/rolling_join.clj) on performing rolling joins in Clojask 
```clojure

(defn main
    []
    (def x (clojask/dataframe "resources/employees.csv"))
    (def y (clojask/dataframe "resources/employees-workleave.csv"))

    (clojask/set-type x "UpdateDate" "datetime")
    (clojask/set-type y "UpdateDate" "datetime"))

    (clojask/compute (clojask/rolling-join-forward x y ["Employee"] ["Employee"] "UpdateDate" "UpdateDate") 8 "resources/output.csv" :exception false)
    
```


---
#### Enhanced Reshape
An [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/enhanced_reshape.clj) on performing melt and cast operations in Clojask 
```clojure

;; find more explicit explanation here
;; https://cran.r-project.org/web/packages/data.table/vignettes/datatable-reshape.html#enhanced-new-functionality
(defn main
  []
  ;; enhanced melt
  (def x (cj/dataframe "resources/melt.csv"))
  ;; some operations to x
  (cj/rename-col x "dob_child1" "child1")
  (cj/rename-col x "dob_child2" "child2")
  (cj/rename-col x "dob_child3" "child3")
  (melt x "outputs/1.csv" ["family_id" "age_mother"] ["child1" "child2" "child3"] :measure-name "child" :value-name "dob")
  ;; x and y are from the same source
  (def y (cj/dataframe "resources/melt.csv"))
  (cj/rename-col x "gender_child1" "child1")
  (cj/rename-col x "gender_child2" "child2")
  (cj/rename-col x "gender_child3" "child3")
  (melt x "outputs/2.csv" ["family_id" "age_mother"] ["child1" "child2" "child3"] :measure-name "child" :value-name "gender")
  (def z (cbind-csv "outputs/1.csv" "outputs/2.csv"))
  ;; you can rename the column names of z here
  ;; skipped
  (cj/compute z 8 "outputs/melt_result.csv" :select ["family_id1" "age_mother1" "child1" "dob" "gender"])

  ;; enhanced dcast
  (def a (cj/dataframe "resources/dcast.csv"))
  (def dob (dcast a "outputs/1.csv" ["family_id" "age_mother"] "child" "dob" ["1" "2" "3"] :vals-name ["child1" "child2" "child3"]))
  (def b (cj/dataframe "resources/dcast.csv"))
  (def gender (dcast b "outputs/2.csv" ["family_id" "age_mother"] "child" "gender" ["1" "2" "3"] :vals-name ["child1" "child2" "child3"]))
  (def res (cj/inner-join dob gender ["family_id" "age_mother"] ["family_id" "age_mother"] :col-prefix ["dob" "gender"]))
  ;; you can rename the column names of res here
  ;; skipped
  (cj/compute res 8 "outputs/dcast_result.csv" :exclude ["dob_family_id" "dob_age_mother"])
```


---
#### Coming Soon
- Timezone