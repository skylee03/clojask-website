{:title "Extensions" 
:date "2022-02-22"
:layout :post
:toc true}

### clojask.extensions

Like many popular Python libraries, such as numpy and pandas, third-party users can extend the function of Clojask by introducing more codes above the basic source code. Here is an example to support the creating such extension functions under the namespace of `clojask.extensions`. 

---

#### `cbind`

Joins some dataset files into a new dataframe by columns.

*The separator of each file is determined by its file format. The list of default seperator for each file can be find in [clojask-io.input](https://github.com/clojure-finance/clojask-io/blob/main/src/clojask_io/input.clj#L54). If it does not fit your needs, please try to modify the source code of `cbind` and change the `:sep` option of each `read-file` function.*

| Argument   | Type   | Function                            | Remarks                                                   |
| ---------- | ------ | ----------------------------------- | --------------------------------------------------------- |
| path-a     | String | The path of the first dataset file  | Can be absolute or relative path                          |
| path-b     | String | The path of the second dataset file | Can be absolute or relative path                          |
| [path-c's] | String | The path of the rest dataset files  | Can be absolute or relative path; the number is not fixed |

**Example**

```clojure
;; file a
;; date,item,price
;; 2010-01-20,1,18.3
;; 2010-01-20,2,38.3
;; 2010-01-23,1,18.9
;; 2010-01-23,2,48.9
;; 2010-01-26,1,19.1
;; 2010-01-26,2,59.1

;; file b
;; date,cust,Item,sold
;; 2010-01-19,101,2,11
;; 2010-01-22,102,1,7
;; 2010-01-24,102,2,9
;; 2010-01-25,101,2,9
;; 2010-01-26,101,1,10

(def x (cbind "path/to/a" "path/to/b"))

;; x
;; date1,item,price,date2,cust,Item,sold
;; 2010-01-20,1,18.3,2010-01-19,101,2,11
;; 2010-01-20,2,38.3,2010-01-22,102,1,7
;; 2010-01-23,1,18.9,2010-01-24,102,2,9
;; 2010-01-23,2,48.9,2010-01-25,101,2,9
;; 2010-01-26,1,19.1,2010-01-26,101,1,10

;; can further be
(def x (cbind "path/to/a" "path/to/b" "path/to/c" "path/to/d"))
```

---

#### `rbind`

Joins some dataset files into a new dataframe by rows.

*The separator of each file is determined by its file format. The list of default seperator for each file can be find in [clojask-io.input](https://github.com/clojure-finance/clojask-io/blob/main/src/clojask_io/input.clj#L54). If it does not fit your needs, please try to modify the source code of `cbind` and change the `:sep` option of each `read-file` function.*

| Argument   | Type   | Function                            | Remarks                                                   |
| ---------- | ------ | ----------------------------------- | --------------------------------------------------------- |
| path-a     | String | The path of the first dataset file  | Can be absolute or relative path                          |
| path-b     | String | The path of the second dataset file | Can be absolute or relative path                          |
| [path-c's] | String | The path of the rest dataset files  | Can be absolute or relative path; the number is not fixed |

**Example**

```clojure
;; file a
;; date,item,price
;; 2010-01-20,1,18.3
;; 2010-01-20,2,38.3
;; 2010-01-23,1,18.9
;; 2010-01-23,2,48.9
;; 2010-01-26,1,19.1
;; 2010-01-26,2,59.1

;; file b
;; date,cust,Item,sold
;; 2010-01-19,101,2,11
;; 2010-01-22,102,1,7
;; 2010-01-24,102,2,9
;; 2010-01-25,101,2,9
;; 2010-01-26,101,1,10

(def x (rbind "path/to/a" "path/to/b"))
(print-df x)

|             date |             item |            price |
|------------------+------------------+------------------|
| java.lang.String | java.lang.String | java.lang.String |
|       2010-01-20 |                1 |             18.3 |
|       2010-01-20 |                2 |             38.3 |
|       2010-01-23 |                1 |             18.9 |
|       2010-01-23 |                2 |             48.9 |
|       2010-01-26 |                1 |             19.1 |
|       2010-01-26 |                2 |             59.1 |
|       2010-01-19 |              101 |                2 |
|       2010-01-22 |              102 |                1 |
|       2010-01-24 |              102 |                2 |
|       2010-01-25 |              101 |                2 |
```

---

