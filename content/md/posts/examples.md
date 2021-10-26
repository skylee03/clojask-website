{:title "Examples" 
:date "2021-09-29"
:layout :post
:toc :ul}

#### Multi-threading with core.async

```clojure
(def x (dataframe "resources/Employees-large.csv"))
(def y (dataframe "resources/Employees.csv"))

;; create a thread for each operation
(async/thread (set-type x "double" "Department"))
(async/thread (set-type y "double" "Department"))

(time (left-join x y ["Employee"] ["Employee"] 4 "output/test.csv" :exception false))
```

#### rolling-join-forward
(Probably need to move this to examples instead)

Perform a rolling join forward on two dataframes by some columns
| Argument            | Type               | Function                                   | Remarks                                           |
| ------------------- | ------------------ | -------------------------------------------| ------------------------------------------------- |
| `dataframe b`       | Clojask.DataFrame  | The operated object                        |                                                   |
| `a columns`         | Clojure.collection | The keys of a to be aligned                | Should be existing headers in dataframe a         |
| `b columns`         | Clojure.collection | The keys of b to be aligned                | Should be existing headers in dataframe b         |
| `number of workers` | int (max 8)        | Number of worker nodes doing the joining   |                                                   |
| `distination file`  | string             | The file path to the distination           | Will be emptied first                             |

**Example**

```clojure
(def x (dataframe "path/to/a"))
(def y (dataframe "path/to/b"))

(inner-join x y ["col a 1" "col a 2"] ["col b 1" "col b 2"] ["a-roll" "b-roll"] 8 "path/to/distination" :exception true)
;; rolling join forward x and y