{:title "About"
 :layout :page
 :toc :ul
 :page-index 0
 :navbar? true}

## Talk on Clojure data-recur meeting

This talk is about the general information and status of the project as of Oct 2022. (From: 9:09 To: 58:02)

<iframe width="560" height="315" src="https://www.youtube.com/embed/nTyPMxDlw0w?start=549&end=586" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

</a>

---

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

**Source code**

The benchmarking code for Dask and Clojask could be found here respectively:

* [Dask benchmarking code](https://github.com/clojure-finance/clojask/blob/main/benchmark/dask-benchmark.ipynb)
* [Clojask benchmaking code](https://github.com/clojure-finance/clojask/blob/main/benchmark/clojure-benchmark.clj)

</a>

---

## Comparison/Advantages with other larger than memory systems

**Hadoop MapReduce**

| Functions                      | Clojask             | Hadoop MapReduce   |
| ------------------------------ | ------------------- | ------------------ |
| Larger-than-memory source file | ✅                 | ✅                |
| Write intermediate results to tmp files | ✅                 | ✅                |
| MapReduce paradigm | ✅                 | ✅                |
| Join, filter, aggregate, etc. on large files | ✅                | ❌                |


**Spark**

| Functions           | Clojask            | Spark               |
| ------------------- | ------------------- | ------------------ | 
| Construct operations' DAG | ❌ | ✅ |
| Join, filter, aggregate, etc | ✅ | ✅ |
| Cache intermediate results between stages in memory | ❌ | ✅ |
| Minimum memory usage | ✅ | ❌ |

</a>

---

## Clojask Library Ecosystem 

(Click to view a closeup of image)

<!-- ![Clojask operations](/img/clojask_ecosystem.png) -->

<a href="https://raw.githubusercontent.com/clojure-finance/clojask-website/main/content/img/clojask_ecosystem.png" target="_blank" >
<img src="https://raw.githubusercontent.com/clojure-finance/clojask-website/main/content/img/clojask_ecosystem.png" alt="Clojask ecosystem" />
</a>

---

## Clojask Logic Flow Diagram 
(Click to view a closeup of image)
<a href="https://raw.githubusercontent.com/clojure-finance/clojask/main/docs/diagram.jpg" target="_blank" >
<img src="https://raw.githubusercontent.com/clojure-finance/clojask/main/docs/diagram.jpg" alt="Clojask logic" />
</a>
<!-- /img/diagram.png -->

---
