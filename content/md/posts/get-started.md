{:title "Get Started"
:date "2021-09-30"
:layout :post
:toc :ul
:tags ["Start"]}

## Clojask Types

### string

The default type for all columns

Class: ` java.lang.String`

**Examples**

```clojure
(set-type dataframe "col-name" "string")
```

### int

Most efficiently stores an integer

Class: `java.lang.Integer`

**Examples**

```clojure
(set-type dataframe "col-name" "int")
```

### double

Accepts floats and integers

Class: `java.lang.Double`

**Examples**

```clojure
(set-type dataframe "col-name" "double")
```

### date

Transform a date string (no time field)

Class: `java.util.Date` (default format string: `yyyy-MM-dd`)

**Examples**

```clojure
;; if the date looks like this 2020/11/12
(set-type dataframe "col-name" "date:yyyy/MM/dd")
```

### datetime

Transform a date string (no time field)

Class: `java.util.Date` (default format string: `yyyy-MM-dd HH:mm:ss`)

**Examples**

```clojure
;; if the date looks like this 2020/11/12 12:12:36
(set-type dataframe "col-name" "datetime:yyyy/MM/dd HH:mm:ss")
```



## Hybrid Column & Empty Fields

Take this dataset as an example.

| Employee | EmployeeName | Department | Salary |
| -------- | ------------ | ---------- | ------ |
| 1        | Alice        | 11         | 300    |
| 2        | Bob          | 11         | 34,000 |
| 3        | Carla        |            | 900    |
| 4        | Daniel       | 12         | 1,000  |
| 5        | Evelyn       | 13         | 800    |
| ...      | ...          | ...        | ...    |

The first thing to do after creating this dataframe is to set the type of column **Salary** to `int`.

```clojure
(ck/set-type "Salary" "int")
```

However, when you take a look at the data type of the latest **Salary** column using function `print-df`. It contains both `int` and `string`. This is because some numbers (contains ",") are not recognized by the integer parser, such as 34,000 and 1,000. Therefore, when you operate function to this column, you should accept both `int` and `string` as the input. One way to solve this issue is to create your own parser for integer that ignores comma.

```clojure
(ck/set-parser "Salary" customized-parser)
```

The column will empty fields is just a special case of hybrid column. The **Department** column has an empty field. The type of it is `string` & `nil`. You can tell that empty fields simply contain value `nil`. It is worth notice that 

```clojure
(ck/compute ... :exception true)
```

will also assign `nil` to fields that throw exceptions during execution. Therefore, your operation functions have to consider `nil` input in these two scenarios.



## System of operations

Clojask is defined by two key types: incremental and replaceable operations. 

### Replaceable operations 

Replaceable operatons include 

- set-type
- set-parser 
- group-by
- add-formatter
- join

Replaceable operations can be called multiple times to **replace** the previous call of the operation 

```clojure 
(def x (dataframe "path/to/a.csv"))
;; defines dataframe from csv file "a.csv" 

(set-type x "int" "employee_no")
;; makes column "employee numbers" integers
 
(set-type x "str" "employee_no")
;; makes column "employee numbers" strings  
```

In the case above, Clojask will ultimately interpret the "employeee_no" column as a **string**, rather than an **integer**. 

### Incremental operations

Incremental operations include

- filter
- operate
- aggregate

Incremental operations are able to build on top of one another and build up **incrementally** in their operation.

```clojure
(filter x "Salary" (fn [salary] (<= salary 800)))
;; this statement deletes all the rows that have a salary larger than 800

(filter x "Department" (fn [dept] (= dept "computer science")))
;; this statements deletes all the rows that contain people not in the computer science department

(filter x ["Salary" "Department"] (fn [salary dept] (and (<= salary 800) (= dept "computer science"))))
;; keeps only people from computer science department with salary not larger than 800
```

This means additional incremental operations are applied on top of existing operations
