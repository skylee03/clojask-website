{:title "API Docs"
:date "2021-09-28"
:layout :post 
:toc :ul
:tags  []} 



### Basic Information

- Most dataframe manipulation operations are performed lazily (except `sort` and `join`). They will be executed all at once when `compute` is called. 
- The dataframe traverses the data in rows, with each row represented by a vector.
- The size of the input dataframe can be larger than the size of available memory in the machine.
- By default, all columns are assigned with the data type `string` when the dataframe is first imported. You are allowed to change its type using our predefined type keywords.


--- 

#### dataframe 
Defines the dataframe and returns `Clojask.DataFrame` 

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `input-directory`        | String  | Directory of DataFrame file                                          |                                                   |

```clojure
;; defines df as a dataframe from dataframe.csv file
(def df (dataframe "resources/dataframe.csv"))
```
---  

#### get-col-names

Get the column names of the dataframe

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe`       | Clojask.DataFrame  | The operated object                                          |                                                   |

<br>

**Return**

`get-col-names` returns a clojure.lang.PersistentVector

```clojure
(get-col-names x)
;; columns: ["Employee" "EmployeeName" "Department" "Salary"]
```

#### reorder-col / rename-col

Reorder the columns / rename the column names in the dataframe

| Argument            | Type               | Function                                                     | Remarks                                           |
| ------------------- | ------------------ | ------------------------------------------------------------ | ------------------------------------------------- |
| `dataframe`       | Clojask.DataFrame  | The operated object                                          |                                                   |
| `columns`         | Clojure.collection | The new set of column names                                  | Should be existing set of column names in dataframe if it is `reorder-col`         |

```clojure
;; columns: ["Employee" "EmployeeName" "Department" "Salary"]
(rename-col x ["Employee" "EmployeeName" "new-Department" "Salary"])
;; columns: ["Employee" "EmployeeName" "new-Department" "Salary"]
(reorder-col x ["Employee" "new-Department" "EmployeeName" "Salary"])
```

---

#### filter

Filter the dataframe by rows.

| Argument    | Type                           | Function                                                    | Remarks                                                      |
| ----------- | ------------------------------ | ----------------------------------------------------------- | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame              | The operated object                                         |                                                              |
| `columns`   | String / collection of strings | The columns that the predicate function would apply to              |                                                              |
| `predicate` | Function                       | The predicate function to determine if a row should be kept | This function should have the same number of arguments with the above columns and in the same order. Only rows that return `true` will be kept. |

**Example**

```clojure
(filter x "Salary" (fn [salary] (<= salary 800)))
;; this statement deletes all the rows that have a salary larger than 800
(filter x ["Salary" "Department"] (fn [salary dept] (and (<= salary 800) (= dept "computer science"))))
;; keeps only people from computer science department with salary not larger than 800
```

--- 

#### set-type

Set the data type of a column. As a result, the value will be parsed as the assigned data type when it is used in any subsequent operations.

| Argument    | Type              | Function            | Remarks                                                      |
| ----------- | ----------------- | ------------------- | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame | The operated object |                                                              |
| `column`    | String            | Target columns      | Should be existing columns within the dataframe.              |
| `type`      | String            | Type of the column  | The natively supported types are: int, double, string, date. Note that by default all the column types are string. If you need a special parsing function, see `add-parser`. |

**Example**

```clojure
;; set data type of the column "Salary" to be double 
(set-type x "Salary" "double")
```

--- 

#### set-parser

A more flexible way to set type.

| Argument    | Type              | Function                                                     | Remarks                                                      |
| ----------- | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `dataframe` | Clojask.DataFrame | The operated object                                          |                                                              |
| `column`    | String            | Target columns                                               | Should be existing columns within the dataframe              |
| `parser `   | function          | The parser function that will parse a string to other types (or even string) | The function should take only one argument which is a string, and the parsed type should be serializable. |

**Example**

```clojure
;; parse all the values in "Salary" with this function
(set-parser x "Salary" #(Double/parseDouble %))
```

---

#### operate (In-place modification)

**In-place modification** on a single column

| Argument      | Type              | Function                      | Remarks                                                      |
| ------------- | ----------------- | ----------------------------- | ------------------------------------------------------------ |
| `dataframe`   | Clojask.DataFrame | The operated object           |                                                              |
| `operation`   | function          | Function to be applied lazily | The function should take only one argument which is the value of the below column. |
| `column name` | Keyword           | Target columns                | Should be existing columns within the dataframe.              |

**Example**

```clojure
;; set data type as double
(set-type x "Salary" "double")
;; take the negative of the column "Salary"
(operate x - "Salary")
```

---

#### operate (Column generating)

Calculate the result and store in a new column

| Argument         | Type                           | Function                      | Remarks                                                      |
| ---------------- | ------------------------------ | ----------------------------- | ------------------------------------------------------------ |
| `dataframe`      | Clojask.DataFrame              | The operated object           |                                                              |
| `operation`      | function                       | Function that is to be applied lazily | Argument number should align with the number of column names below, ie *if operation functions takes two arguments, the length of column names should also be two, and in the same order that is passed to the function*. |
| `column name(s)` | String or collection of Strings | Target columns                | Should be existing columns within the dataframe.              |
| `new column`     | String                         | Resultant column              | Should be new column(s) other than those existing in the dataframe.                |

**Example**

```clojure
(operate x str ["Employee" "EmployeeName"] "new")
;; concats the two columns into the "new" column
```

---

#### group-by

Group the dataframe by some specific columns (always used together with `aggregate`), or group the dataframe by function output(s)

| Argument       | Type                | Function                                | Remarks                                      |
| -------------- | ------------------- | --------------------------------------- | -------------------------------------------- |
| `dataframe`    | Clojask.DataFrame   | The operated object                     |                                              |
| `groupby-keys` | String / Collection | Group by columns (functions of columns) | Find the specification below               . |

**Example**

```clojure
;; group by one or more columns
(group-by x ["Department"])
(group-by x ["Department" "DepartmentName"])
```

**<p id="groupby-keys">Group-by Keys Specification</p>**

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

Aggregate the dataframe(s) by applying some functions. The aggregation function will be applied to every column registered in sequence.

| Argument               | Type                           | Function                              | Remarks                                                      |
| ---------------------- | ------------------------------ | ------------------------------------- | ------------------------------------------------------------ |
| `dataframe`            | Clojask.DataFrame              | The operated object                   |                                                              |
| `aggregation function` | function                       | Function to be applied to each column | Should take a collection as argument. And return one or a collection of predefined type*. |
| `column name(s)`       | String or collection of String | Aggregate columns                     | Should be existing columns within the dataframe              |
| [`new column`]         | String or collection of string | Resultant column                      | Should be new columns not in the dataframe                   |

**Example**

```clojure
;; get the min of the selected column(s)
(aggregate x clojask/max ["Salary"] ["Salary-min"])
(aggregate x clojask/min ["Employee" "EmployeeName"] ["Employee-min" "EmployeeName-min"])
```

Custom functions can be made for aggregation. Please refer to [Aggregation Function](/posts-output/aggregate-function) for additional details  

The keys used in specifying the aggregate operation are identical to the [group-by](#group-by) function 

---  

#### sort

**Immediately** sort the dataframe

| Argument           | Type                    | Function                 | Remarks                                                      |
| ------------------ | ----------------------- | ------------------------ | ------------------------------------------------------------ |
| `dataframe`        | Clojask.DataFrame       | The operated object      |                                                              |
| `trending list`    | Collection (seq vector) | Indicates the sort order | Example: ["Salary" "+" "Employee" "-"] means that sort the Salary in ascending order, if equal sort then by Employee in descending order |
| `output-directory` | String                  | The output path          |                                                              |

**Example**

```clojure
;; sort by "Salary" in ascending order
(sort x ["+" "Salary"] "path/output.csv")
```

---

#### inner-join / left-join / right-join

Inner / left / right join two dataframes on specific columns

*Remarks:*

*The registered operations and filters (like `compute`) will be automatically pipelined. You could think of `join` as as an operation that first computes the two dataframes then joins them together.*

*Only `compute` will be able to be performed after joing functions*


| Argument      | Type                | Function                    | Remarks                                      |
| ------------- | ------------------- | --------------------------- | -------------------------------------------- |
| `dataframe a` | Clojask.DataFrame   | The operated object         |                                              |
| `dataframe b` | Clojask.DataFrame   | The operated object         |                                              |
| `a join keys` | String / Collection | The keys of a to be aligned | Find the specification [here](#groupby-keys) |
| `b join keys` | String / Collection | The keys of b to be aligned | Find the specification [here](#groupby-keys) |  

**Example**

```clojure
(def x (dataframe "path/to/a"))
(def y (dataframe "path/to/b"))

(def z (inner-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"]))
(compute z 8 "path/to/output")
;; inner join x and y

(def z (left-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"]))
(compute z 8 "path/to/output")
;; left join x and y

(def z (right-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"]))
(compute z 8 "path/to/output")
;; right join x and y
```

**Return**

A `Clojask.JoinedDataFrame`

Unlike `Clojask.DataFrame`, it only supports three operations:
  - `print-df`
  - `get-col-names`
  - `compute`
This means you cannot further apply complicated operations to a joined dataframe. An alternative is to first compute the result, then read it in as a new dataframe.
---

#### rolling-join-forward/rolling-join-backward

Rolling join two dataframes on columns

*Remarks:*

*Join functions are immediate actions that will be executed once they are being called.*

*The registered operations and filters (like `compute`) will be automatically pipelined. You could think of `join` as as an operation that first computes the two dataframes then joins them together.*

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

(rolling-join-forward x y ["Employee"] ["Employee"] "Salary" "Salary" 8 "path/output.csv" :exception true)
(rolling-join-forward x y ["Employee"] ["Employee"] "Salary" "Salary" 8 "path/output.csv" :exception true)
```

---  


#### compute

Compute the result. The pre-defined lazy operations will be executed in pipeline, ie the result of the previous operation becomes the argument of the next operation.

| Argument         | Type                           | Function                                                     | Remarks                                                      |
| ---------------- | ------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `dataframe`      | Clojask.DataFrame              | The operated object                                          |                                                              |
| `num of workers` | int (max 8)                    | The number of worker instances (except the input and output nodes) | Uses [onyx](http://www.onyxplatform.org/) as the distributed platform |
| `output path`    | String                         | The path of the output csv file                              | Could exist or not.                                          |
| [`exception`]    | boolean                        | Whether an exception during calculation will cause termination | Is useful for debugging or detecting empty fields            |
| [`select`]       | String / Collection of strings | Chooses columns to select for the operation | Can only specify either of select and exclude                |
| [`exclude`]      | String / Collection of strings | Chooses columns to be excluded for the operation                          | Can only specify either of select and exclude                |

**Return**

`compute` returns a Clojask.DataFrame

**Example**

```clojure
(compute x 8 "../resources/test.csv" :exception true)
;; computes all the pre-registered operations

(compute x 8 "../resources/test.csv" :select "col a")
;; only select column a

(compute x 8 "../resources/test.csv" :select ["col b" "col a"])
;; select two columns, column b and column a in order

(compute x 8 "../resources/test.csv" :exclude ["col b" "col a"])
;; select all columns except column b and column a, other columns are in order
```


