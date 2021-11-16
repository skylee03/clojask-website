{:title "API Docs"
:date "2021-09-28"
:layout :post 
:toc :ul
:tags  []} 


### Features 

- **Unlimited size**  
  Theoretically speaking, it supports dataset larger than memory to infinity!  

- **All native types**  
  All the datatypes used to store data is native Clojure (or Java) types!  

- **From file to file**  
  Integrate IO inside the dataframe. No need to write your own read-in and output functions!  

- **Distributed (coming soon)**  
  Most operations could be distributed to different computers in a clusters. See the principle in [Onyx](http://www.onyxplatform.org/)  <br>

- **Lazy operations**  
  Some operations will not be executed immediately. Dataframe will intelligently pipeline the operations altogether in computation.  

---

### Basic Information

- Most operations to the dataframe is performed lazily and all at once with `compute` except `sort ` and `join`. 
- The dataframe process the data in rows, ie one row in one vector.
- The input dataframe can be larger than memory in size.
- By default, all columns have the same type: string. You are allowed to set its type, with our predefined type keywords.


--- 

## API Documentation

---


#### reorder-col / rename-col

Reorder the columns / rename the column names in the dataframe

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `columns`         | Clojure.collection | The new set of column names                                  | Should be existing headers in dataframe a if it is `reorderCol`         |

```clojure
(rename-col y ["Employee" "EmployeeName" "new-Department" "Salary"])
(reorder-col y ["Employee" "new-Department" "EmployeeName" "Salary"])
```

---

#### select-col

Select columns to keep in the dataframe.

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `columns`         | Clojure.collection | The set of columns names to keep                                  | Should be existing columns within the dataframe         |

```clojure
(select-col y ["Employee" "Department"])
```

---

#### delete-col

Eliminate columns in the dataframe.

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `columns`         | Clojure.collection | The set of columns names to eliminate                                | Should be existing columns within the dataframe         |

```clojure
(delete-col y ["EmployeeName" "Salary"])
```

---


#### filter

Filters the data frame by rows

| Argument    | Type                           | Function                                                    | Remarks                                                      |
| ----------- | ------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame              | The operated object                                         |                                                              |
| `columns`   | String / collection of strings | The columns the predicate function to apply to              |                                                              |
| `predicate` | Function                       | The predicate function to determine if a row should be kept | This function should have the same number of arguments with the above columns and in the same order. Only rows that return `true ` will be kept. |

**Example**

```clojure
(filter x "Salary" (fn [salary] (<= salary 800)))
;; this statement deletes all the rows that have a salary larger than 800
(filter x ["Salary" "Department"] (fn [salary dept] (and (<= salary 800) (= dept "computer science"))))
;; keeps only people from computer science department with salary not larger than 800
```

--- 

#### set-type

Set the type of a column. So when using the value of that column, it would be in that type.

| Argument    | Type              | Function            | Remarks                                                      |
| ----------- | ----------------- | ------------------- | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame | The operated object |                                                              |
| `type`      | String            | Type of the column  | The native support types are: int, double, string, date. Note that by default all the column types are string. If you need special parsing function, see `add-parser`. |
| `column`    | String            | Target columns      | Should be existing columns within the dataframe              |

**Example**

```clojure
(set-type x "double" "Salary")
;; makes the column Salary doubles
```

--- 

#### set-parser

A more flexible way to set type.

| Argument    | Type              | Function                                                     | Remarks                                                      |
| ----------- | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame | The operated object                                          |                                                              |
| `parser `   | function          | The parser function that will parse a string to other types (or even string) | The function should take only one argument which is a string, and the parsed type should be serializable. |
| `column`    | String            | Target columns                                               | Should be existing columns within the dataframe              |

**Example**

```clojure
(set-parser x Double/parseDouble "Salary")
;; parse all the values in Salary with this function
```

---

#### operate (In-place modification)

**In-place modification** on a single column

| Argument      | Type              | Function                      | Remarks                                                      |
| ------------- | ----------------- | ----------------------------- | ------------------------------------------------------------ |
| `dataframe`   | Clojask.DataFrame | The operated object           |                                                              |
| `operation`   | function          | Function to be applied lazily | The function should take only one argument which is the value of the below column |
| `column name` | Keyword           | Target columns                | Should be existing columns within the dataframe              |

**Example**

```clojure
(set-type x "double" "Salary")
(operate x - "Salary")
;; takes the negative of column Salary
```

---

#### operate (Column generating)

Calculate the result and store in a new column

| Argument         | Type                           | Function                      | Remarks                                                      |
| ---------------- | ------------------------------ | ----------------------------- | ------------------------------------------------------------ |
| `dataframe`      | Clojask.DataFrame              | The operated object           |                                                              |
| `operation`      | function                       | Function to be applied lazily | Argument number should be complied with the column names below, ie *if operation functions takes two arguments, the length of column names should also be 2, and in the same order to be passed to the function* |
| `column name(s)` | String or collection of String | Target columns                | Should be existing columns within the dataframe              |
| `new column`     | String                         | Resultant column              | Should be new column other than the dataframe                |

**Example**

```clojure
(operate x str ["Employee" "EmployeeName"] "new")
;; concats the two columns into the "new" column
```

---

#### group-by

Group by the dataframe with some columns (always use together with `aggregate`), or the result by applying the function to the column

| Argument       | Type                | Function                                | Remarks                                      |
| -------------- | ------------------- | --------------------------------------- | -------------------------------------------- |
| `dataframe`    | Clojask.DataFrame   | The operated object                     |                                              |
| `groupby-keys` | String / Collection | Group by columns (functions of columns) | Find the specification [here](#groupby-keys) |

**Example**

```clojure
(group-by x ["Department" "DepartmentName"])
;; group by both columns
```



<a name="groupby-keys">**Group-by Keys Specification**</a>

**Group-by functions requirements:**

- Take one argument
- Return type: int / double / string

One general rule is to put the group-by function and its corresponding column name together.

```clojure
(defn rem10
  "Get the reminder of the num by 10"
  [num]
  (rem num 10))

(group-by x [rem10 "Salary"])
;; or
(group-by x [[rem10 "Salary"]])
```

If no group-by function, the column name can be alone.

```clojure
(group-by x "Salary")
;; or
(group-by x ["Salary"])
```

You can also group by the combination of keys. (Use the above two rules together)

```clojure
(group-by x [[rem10 "Salary"] "Department"])
;; or
(group-by x [[rem10 "Salary"] ["Department"]])
```


---

#### aggregate

Aggregate the grouped dataframes with some functions. The aggregation function will be applied to every columns registered in sequence.

| Argument               | Type                           | Function                              | Remarks                                                      |
| ---------------------- | ------------------------------ | ------------------------------------- | ------------------------------------------------------------ |
| `dataframe`            | Clojask.DataFrame              | The operated object                   |                                                              |
| `aggregation function` | function                       | Function to be applied to each column | Should take a collection as argument. And return one or a collection of predefined type*. |
| `column name(s)`       | String or collection of String | Aggregate columns                     | Should be existing columns within the dataframe              |
| [`new column`]         | String or collection of string | Resultant column                      | Should be new columns not in the dataframe                   |

**Example**

```clojure
(aggregate x clojask/min ["Employee" "EmployeeName"] ["new" "new2"])
;; get the min of the two columns grouped by ...
```

#### Aggregation Functions

In Clojask, you can aggregate on the whole dataframe, or on the group-by dataframe. We call the first case "simple aggregation" and the second "group-by aggregation". Some given functions for simple aggregation are defined in namespace `clojask.api.aggregate`, and the given functions for group-by aggregation are defined in namespace `clojask.api.gb-aggregate`. 

Below are full list of given functions for the two types.

`clojask.api.aggregate`:

`max`: Find the max value (use `clojure.core/compare` as the comparator)

`min`: Find the min value (use `clojure.core/compare` as the comparator)

`clojask.api.gb-aggregate`:

`max`: Find the max value (use `clojure.core/compare` as the comparator)

`min`: Find the min value (use `clojure.core/compare` as the comparator)

Besides these given functions, you are also welcomed to define your own.

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
   1. To better understand the this template, you may refer to the documentation of [`reduce`](https://clojuredocs.org/clojure.core/reduce), the `aggre-func` should be able to use in `reduce`.

---

#### sort

**Immediately** sort the dataframe

| Argument           | Type                    | Function                 | Remarks                                                      |
| ------------------ | ----------------------- | ------------------------ | ------------------------------------------------------------ |
| `dataframe`        | Clojask.DataFrame       | The operated object      |                                                              |
| `trending list`    | Collection (seq vector) | Indicates the sort order | Example: ["Salary" "+" "Employee" "-"] means that sort the Salary in ascending order, if equal sort the Employee in descending order |
| `output-directory` | String                  | The output path          |                                                              |

**Example**

```clojure
(sort y ["+" "Salary"] "resources/sort.csv")
;; sort by Salary ascendingly
```

---  

#### compute

Compute the result. The pre-defined lazy operations will be executed in pipeline, i.e. the result of the previous operation becomes the argument of the next operation.

| Argument         | Type              | Function                                                     | Remarks                                                      |
| ---------------- | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `dataframe`      | Clojask.DataFrame | The operated object                                          |                                                              |
| `num of workers` | int (max 8)       | The number of worker instances (except the input and output nodes) | If this argument >= 2, will use [onyx](http://www.onyxplatform.org/) as the distributed platform |
| `output path`    | String            | The path of the output csv file                              | Could exist or not.                                          |
| [`exception`]    | boolean           | Whether an exception during calculation will cause termination | Is useful for debugging or detecting empty fields            |

**Example**

```clojure
(compute x 8 "../resources/test.csv" :exception true)
;; computes all the pre-registered operations
```
  
---

#### inner-join / left-join / right-join

Inner / left / right join two dataframes by some columns

*Remarks:*

*Join functions are immediate actions, which will be executed at once.*

*Will automatically pipeline the registered operations and filters like `compute`. You could think of join as first compute the two dataframes then join.*

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe a`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `dataframe b`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `a columns`         | Clojure.collection | The keys of a to be aligned                                  | Should be existing headers in dataframe a         |
| `b columns`         | Clojure.collection | The keys of b to be aligned                                  | Should be existing headers in dataframe b         |
| `number of workers` | int (max 8)        | Number of worker nodes doing the joining                     |                                                   |
| `distination file`  | string             | The file path to the distination                             | Will be emptied first                             |
| [`exception`]       | boolean            | Whether an exception during calculation will cause termination | Is useful for debugging or detecting empty fields |

**Example**

```clojure
(def x (dataframe "path/to/a"))
(def y (dataframe "path/to/b"))

(inner-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"] 8 "path/to/distination" :exception true)
;; inner join x and y

(left-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"] 8 "path/to/distination" :exception true)
;; left join x and y

(right-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"] 8 "path/to/distination" :exception true)
;; right join x and y
```

---

#### rolling-join-forward/rolling-join-backward

Rolling join two dataframes by columns

*Remarks:*

*Join functions are immediate actions, which will be executed at once.*

*Will automatically pipeline the registered operations and filters like `compute`. You could think of join as first compute the two dataframes then join.*

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe a`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `dataframe b`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `a columns`         | Clojure.collection | The keys of a to be aligned                                  | Should be existing headers in dataframe a         |
| `b columns`         | Clojure.collection | The keys of b to be aligned                                  | Should be existing headers in dataframe b         |
| `number of workers` | int (max 8)        | Number of worker nodes doing the joining                     |                                                   |
| `distination file`  | string             | The file path to the distination                             | Will be emptied first                             |
| [`exception`]       | boolean            | Whether an exception during calculation will cause termination | Is useful for debugging or detecting empty fields |

**Example**

```clojure
(def x (dataframe "path/to/a"))
(def y (dataframe "path/to/b"))

(rolling-join-forward x y ["Employee"] ["Employee"] "Salary" "Salary" 8 "resources/test.csv" :exception true)
(rolling-join-forward x y ["Employee"] ["Employee"] "Salary" "Salary" 8 "resources/test.csv" :exception true)
```


  