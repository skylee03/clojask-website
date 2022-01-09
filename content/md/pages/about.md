{:title "About"
 :layout :page
 :toc :ul
 :page-index 0
 :navbar? true}

This is the documentation library for the data frame with parallel computing for larger-than-memory dataset library written in Clojure.  
The [source code](https://github.com/clojure-finance/clojask) can be found on GitHub.

### Features 

- **Unlimited size**  
  Theoretically speaking, it supports dataset larger than memory to infinity!  

- **All native types**  
  All the datatypes used to store data are native Clojure (or Java) types!  

- **From file to file**  
  IO mechanisms are integrated into the dataframe. No need to write your own read-in and output functions!  

- **Distributed (coming soon)**  
  Most operations could be distributed to different computers in a clusters. Read about the principle in [Onyx](http://www.onyxplatform.org/).  <br>

- **Lazy operations**  
  Some operations will not be executed immediately. They are stacked in the pipeline which will be optimised for performance when it comes to the final computation step.  



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

<table id = "box" class = "table1">
    <thead class = "thead1">
    <tr>
        <th>Operation</th>
        <th>Dask (N=1.8M)</th>
        <th>Dask (N=3.6M)</th>
        <th>Dask (N=80M)*</th>
        <th>Clojask (N=1.8M)</th>
        <th>Clojask (N=3.6M)</th>
        <th>Clojask (N=80M)</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Element-wise operation</td>
        <td>119.3</td>
        <td>261.3</td>
        <td>N/A</td>
        <td>72.3</td>
        <td>133.3</td>
        <td>1836.6</td>
    </tr>
    <tr>
        <td>Row-wise selection</td>
        <td>115.0</td>
        <td>232.0</td>
        <td>N/A</td>
        <td>67.9</td>
        <td>145.6</td>
        <td>1757.5</td>
    </tr>
    <tr>
        <td>Aggregation</td>
        <td>116.0</td>
        <td>226.7</td>
        <td>N/A</td>
        <td>58.6</td>
        <td>112.1</td>
        <td>1236.9</td>
    </tr>
    <tr>
        <td>Groupby-aggregate</td>
        <td>116.7</td>
        <td>229.3</td>
        <td>N/A</td>
        <td>459.4</td>
        <td>803.1</td>
        <td>25860.0</td>
    </tr>
    <tr>
        <td>Left join</td>
        <td>114.7</td>
        <td>248.7</td>
        <td>N/A</td>
        <td>1174.4</td>
        <td>2310.2</td>
        <td>14007.9</td>
    </tr>
    <tr>
        <td>Inner join</td>
        <td>116.7</td>
        <td>242.0</td>
        <td>N/A</td>
        <td>1138.8</td>
        <td>2768.5</td>
        <td>21609.3</td>
    </tr>
    <tr>
        <td>Rolling join</td>
        <td>-</td>
        <td>-</td>
        <td>-</td>
        <td>2812.1</td>
        <td>3943.1</td>
        <td>&gt; 28800</td>
    </tr>
    </tbody>
</table>

<br>
<br>

**Remarks:**
- N = Number of lines in csv file
- All benchmarks are in the unit of second (s)
- All benchmarks are inclusive of the time used for importing necssary libraries, loading the dataframe from csv file and ouputting the processed dataframe to one single csv file.
- *In the case of Dask (N=80M) the program could not manage to complete the operation in 7 hours

<br>

**System info**
```
'platform': 'Darwin',
'platform-release': '20.4.0',
'platform-version': 'Darwin Kernel Version 20.4.0: Thu Apr 22 21:46:47 PDT 2021; root:xnu-7195.101.2~1/RELEASE_X86_64',
'architecture': 'x86_64',
'processor': 'i386',
'ram': '8 GB'
```

<br>

**Source code**

The benchmarking code for Dask and Clojask could be found here respectively:

* [Dask benchmarking code](https://github.com/clojure-finance/clojask/blob/main/benchmark/dask-benchmark.ipynb)
* [Clojask benchmaking code](https://github.com/clojure-finance/clojask/blob/main/benchmark/clojure-benchmark.clj)

<br>

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
