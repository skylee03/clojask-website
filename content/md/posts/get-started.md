{:title "Get Started"
:date "2021-09-30"
:layout :post
:toc true
:tags ["Start"]}

---
## Getting Started

Please note that the Onyx library is currently being used in Clojask for its distributed platform.

Currently, Onyx is **supported only on Linux or MacOS** and **currently does not support Windows** for its distributed computing. 

As a result, Clojask is currently **not supported on Windows.**  

We hope to be able to provide support soon. 

### Requirements
```
- Clojure
- Leiningen
```

### Dependencies 

Please include in the project.clj file as one of the dependencies
``` clojure
[clojask "0.1.0-SNAPSHOT"]
;; The current most recent snapshot
```

### Development
To start an interactive prompt where you can enter arbitrary code to run in the context of your project:

```
lein repl
```
To run the default `:main` set in `project.clj`:
```
lein run
```
To run all tests written in the `test` namespace:
```
lein test
```

--- 

## Beginner Tutorials

<br>

If you are new to Clojure, we recommend having a quick read of the following tutorials first:

- [Clojure by Example](http://kimh.github.io/clojure-by-example/#about) - useful for beginners pick up the syntax quickly

- [Clojure Docs](https://clojuredocs.org/) - a more thorough documentation that explains the built-in functions in Clojure

- [Clojure for the Brave and True](https://www.braveclojure.com/clojure-for-the-brave-and-true/) - a book that helps you learn Clojure in an in-depth manner

---


## In Built Dependencies

To make use of the functions in the Clojask library, it is important to ensure your dependencies are installed.

While using Clojask, the library will automatically set up the following dependencies. 

```clojure
  :dependencies [[clojure-csv "2.0.2"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 ^{:voom {:repo "git@github.com:onyx-platform/onyx.git" :branch "master"}}
                 [org.onyxplatform/onyx "0.14.5"]
                 [techascent/tech.ml.dataset "5.17" :exclusions [[ch.qos.logback/logback-classic][org.slf4j/slf4j-api]]]
                 [com.google.code.externalsortinginjava/externalsortinginjava "0.6.0"]]
```

Please note that the above Onyx library is currently **only supported in Linux or MacOS.**  

As a result, Clojask is currently **not supported in Windows.**


--- 

## Help & Support

<br>

In case you would encounter difficulties or have any suggestions for additional examples, please feel free to post it [here](https://github.com/clojure-finance/clojask/issues).

---

### API Documentation 
[Check out the documentaion post here](/posts-output/API/)
 
If you more interested in the inner workings of Clojask, please [refer back to the abouts section](/pages-output/about)
