{:title "About"
 :layout :page
 :toc :ul
 :page-index 0
 :navbar? true}

## <br>
This is the documentation library for the data frame with parallel computing for larger-than-memory dataset library written in Clojure. The source code can be found on [GitHub](https://github.com/clojure-finance/clojask).


## Report Bugs 

Clojask is currently under active development.  
If you find any bugs or errors, we would appreciate if you could help [report](https://github.com/clojure-finance/clojask/issues) these issues so that we could repair them accordingly.


## Overview 
Clojask is an library for parallel computing datasets on Clojure

Our emphasis is on providing the following attributes
- Ease of installation and use 
- Efficient operations with low overhead 
- Provide ability to quickly process larger-than-memory datasets
- Utilise features of Clojure in the form of lazy sequences, macros, etc [link to the about page]
- Provide flexibility on single/cluster machine operations (Currently in the works)

## Benchmarks

Number of workers = 4

| Operation | Dask (N=1.8M) | Dask (N=3.6M) | Dask (N=80M)* | Clojask (N=1.8M) | Clojask (N=3.6M) | Clojask (N=80M) |
| :---:   | :-: | :-: | :-: | :-: | :-: | :-: |
| Element-wise operation | 119.3 | 261.3 | N/A | 72.3 | 133.3 | 1836.6 |
| Row-wise selection | 115.0 | 232.0 | N/A | 67.9 | 145.6 | 1757.5 |
| Aggregation | 116.0 | 226.7 | N/A | - | - | - |
| Groupby-aggregate | 116.7 | 229.3 | N/A | 459.4 | 803.1 | 25860.0 |
| Left join | 114.7 | 248.7 | N/A | 1174.4| 2310.2 | 14007.9 |
| Inner join | 116.7 | 242.0| N/A | 1138.8 | 2768.5 | |
| Rolling join | - | - | - | 2812.1 | 3943.1 | |

<br>
<br>

**Remarks:**
- N = Number of lines in csv file
- All benchmarks are in the unit of second (s)
- All benchmarks are inclusive of the time used for importing necssary libraries, loading the dataframe from csv file and ouputting the processed dataframe to one single csv file.
- *In the case of Dask (N=80M) the program could not manage to complete the operation in 7 hours


### System info
```
'platform': 'Darwin',
'platform-release': '20.4.0',
'platform-version': 'Darwin Kernel Version 20.4.0: Thu Apr 22 21:46:47 PDT 2021; root:xnu-7195.101.2~1/RELEASE_X86_64',
'architecture': 'x86_64',
'processor': 'i386',
'ram': '8 GB'
```

## System of Operations
Clojask is defined by two key types: incremental and replaceable operations. 

### Replaceable operations 

Replaceable operatons include 
- set-type
- add-parser 
- group-by
- add-formatter

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

This means additional incremental operations are appllied on top of exisiting operations

## Clojask Logic Flow Diagram 
(Click to view a closeup of image)
<a href="https://raw.githubusercontent.com/clojure-finance/clojask/main/doc/diagram.jpg" target="_blank" >
<img src="https://raw.githubusercontent.com/clojure-finance/clojask/main/doc/diagram.jpg" alt="some image" />
</a>
<!-- /img/diagram.png -->

--- 
