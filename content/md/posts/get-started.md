{:title "Get Started"
:date "2021-09-30"
:layout :post
:toc :ul
:tags ["Start"]}

## Getting Started

Please note that the Onyx library is currently being used in Clojask for its distributed platform.

Clojask is currently **not supported on Windows.**  

Currently, Onyx is **supported only on Linux or MacOS** and **currently does not support Windows** for its distributed computing. 

We hope to be able to provide support soon. 

### Requirements
- Clojure ([installation](https://clojure.org/guides/getting_started))
- Leiningen ([installation](https://leiningen.org/))
- Java 


### Running Clojask

Please include in the project.clj file as one of the dependencies
``` clojure
[clojask "0.1.0-SNAPSHOT"]
;; The current snapshot
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



## Beginner Tutorials

If you are new to Clojure, we recommend having a quick read of the following tutorials first:

- [Clojure by Example](http://kimh.github.io/clojure-by-example/#about) - useful for beginners pick up the syntax quickly

- [Clojure Docs](https://clojuredocs.org/) - a more thorough documentation that explains the built-in functions in Clojure

- [Clojure for the Brave and True](https://www.braveclojure.com/clojure-for-the-brave-and-true/) - a book that helps you learn Clojure in an in-depth manner



## Help & Support

In case you would encounter difficulties or have any suggestions for additional examples, please feel free to post it [here](https://github.com/clojure-finance/clojask/issues).



## API Documentation 
[Check out the documentaion post here](/posts-output/API/)
 
If you more interested in the inner workings of Clojask, please [check out our abouts section](/pages-output/about)
