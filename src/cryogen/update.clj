(ns cryogen.update
  (:require [clojure.string :as str]))

(def basic_tutorial "#### Basic Tutorial\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/basic_tutorial.clj) of basic data manipulation operations performed through Clojask")
(def gb-agg "#### Groupby Aggregation\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/groupby_aggregate.clj) of Group-by, then aggregate v.s. direct aggregate")

(def ordinary_join "#### Ordinary Join\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/ordinary_join.clj) of a basic join operation on Clojask ")
(def in-mem "#### Store Results in Memory\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/in_memory.clj) of storing Clojask output in memory.")
(def techml "#### Connection with `tech.ml.dataset`\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/techmldataset.clj) of converting from and to the popular Clojure DataFrame library `tech.ml.dataset`.")
(def outer_join  "#### Outer Join\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/outer_join.clj) of an outer join operation on Clojask ")

(def rolling_join "#### Rolling Join\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/rolling_join.clj) on performing rolling joins in Clojask ")

;; (def multi_threading "#### Multi-Threading\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/multi_threading.clj) of multi threading various operations on Clojask ")

(def enhanced_reshape "#### Enhanced Reshape\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/enhanced_reshape.clj) on performing melt and cast operations in Clojask ")

(def timezone "#### Timezone\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/timezone.clj) on running timezone operations on clojask")

;; (def ending_message "#### Coming Soon\n - foverlaps")

(defn grab_data
  [input]
  (str/split
   (str/join ""
             (drop-last
              (slurp (str "https://raw.githubusercontent.com/clojure-finance/clojask-examples/main/src/clojask_examples/" input)))) #"(?=[(])"))

;; (defn parse [text remove-num]
;;   (->> text
;;        (drop remove-num)
;;        (str/join)
;;        (spit "test.md")))

(defn parse [text]
  (apply str "\n```clojure\n"
         (->> text
              (str/join))
         "\n```\n\n"))

;; Old parsing setting 
;; (defn option
;;   [text]
;;   (cond (str/includes? text "requires")
;;         (parse text 4)
;;         :else (parse text 3)))

(defn option
  [text]
  (parse (apply str (rest (str/split (str/join "" text) #";; Content Below")))))

(defn -main []
  (spit "content/md/posts/examples.md"
        (str (slurp "doc/template.md")
             "\n"
             basic_tutorial
             (option (grab_data "basic_tutorial.clj"))
             "\n---\n"
             gb-agg
             (option (grab_data "groupby_aggregate.clj"))
             "\n---\n"
             ordinary_join
             (option (grab_data "ordinary_join.clj"))
             "\n---\n"
             in-mem
             (option (grab_data "in_memory.clj"))
             "\n---\n"
             techml
             (option (grab_data "techmldataset.clj"))
             "\n---\n"
             rolling_join
             (option (grab_data "rolling_join.clj"))
             "\n---\n"
             outer_join
             (option (grab_data "outer_join.clj"))
             "\n---\n"
            ;;  multi_threading
            ;;  (option (grab_data "multi_threading.clj"))
            ;;  "\n---\n"
             enhanced_reshape
             (option (grab_data "enhanced_reshape.clj"))
             "\n---\n"
             timezone
             (option (grab_data "timezone.clj"))
             "\n---\n"
            ;;  ending_message
             ))
  (println "All done"))


