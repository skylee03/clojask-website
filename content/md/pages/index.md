{:title "Welcome to the Clojask Library"
 :layout :page
 :page-index 0
 :navbar? true
 :home? true}

Welcome to the Clojask Library! This is an open-source library for parallel computing of larger-than-memory datasets developed at HKU Business School.

## Website Navigation

- [Part 1: About](/pages-output/about)
- [Part 2: Get Started](/posts-output/get-started)
- [Part 3: API Documentation](/posts-output/API)
- [Part 4: Examples](/posts-output/examples)
- [Part 5: Extensions](/posts-output/extensions)
- [Part 6: Archives](/archives)

## Features
- **Unlimited size**  
  It supports datasets larger than memory!
<ul></ul>

- **Various Operations**  
  Although Clojask is designed for larger-than-memory datasets, like NoSQLs, it does not sacrifice common operations on relational dataframes, such as [group by](https://clojure-finance.github.io/clojask-website/posts-output/API/#group-by), [aggregate](https://clojure-finance.github.io/clojask-website/posts-output/API/#aggregate), [join](https://clojure-finance.github.io/clojask-website/posts-output/API/#inner-join--left-join--right-join).  
<ul></ul>

- **Lazy operations**  
  Most operations will not be executed immediately. Dataframe will intelligently pipeline the operations altogether in computation. 
<ul></ul>

- **Fast**  
  Faster than Dask in most operations, and the larger the dataframe is, the bigger the advantage. Please find the benchmarks [here](https://clojure-finance.github.io/clojask-website/pages-output/about/#benchmarks).
<ul></ul>

- **All native types**  
  All the datatypes used to store data is native Clojure (or Java) types!
<ul></ul>

- **From file to file**  
  Integrate IO inside the dataframe. No need to write your own read-in and output functions!
<ul></ul>

- **Parallel**  
  Most operations could be executed into multiple threads or even machines. See the principle in [Onyx](http://www.onyxplatform.org/).

## Demo Video

Here is a demo video for a basic introduction to Clojask and some of its applications, including inner join and group-by aggregation.  

<iframe width="640" height="400" 
src="https://www.youtube.com/embed/Jl-Pbu16Xk8">
</iframe> 

## Report Bugs 

Clojask is currently under active development.  
If you find any bugs or errors, we would appreciate if you could help [report](https://github.com/clojure-finance/clojask/issues) these issues so that we could repair them accordingly.
