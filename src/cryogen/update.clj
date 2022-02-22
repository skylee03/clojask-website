(ns cryogen.update
  (:require [clojure.string :as str]))

(def basic_tutorial "#### Basic Tutorial\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/basic_tutorial.clj) of basic data manipulation operations performed through Clojask")

(def ordinary_join "#### Ordinary Join\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/ordinary_join.clj) of a basic join operation on Clojask ")

(def multi_threading "#### Multi-Threading\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/multi_threading.clj) of multi threading various operations on the Clojask ")

(def rolling_join "#### Rolling Join\nAn [example](https://github.com/clojure-finance/clojask-examples/blob/main/src/clojask_examples/rolling_join.clj) on performing rolling joins in the Clojask ")

(def ending_message "#### Coming Soon\n- Timezone")

(defn grab_data [input]
  (str/split
   (str/join "\n" (map str/trim
                       (str/split
                        (str/join ""
                                  (drop-last
                                   (slurp (str "https://raw.githubusercontent.com/clojure-finance/clojask-examples/main/src/clojask_examples/" input))))
                        #"\n")))
   #"(?=[(])"))

;; (defn parse [text remove-num]
;;   (->> text
;;        (drop remove-num)
;;        (str/join)
;;        (spit "test.md")))

(defn parse [text remove-num]
  (str "\n```clojure\n"
       (->> text
            (drop remove-num)
            (str/join))
       "\n```\n\n"))

(defn option [text]
  (cond (str/includes? text "requires")
        (parse text 4)
        :else (parse text 3)))

(defn -main []
  (spit "content/md/posts/examples.md"
        (str (slurp "doc/template.md")
             "\n"
             basic_tutorial
             (option (grab_data "basic_tutorial.clj"))
             "\n---\n"
             ordinary_join
             (option (grab_data "ordinary_join.clj"))
             "\n---\n"
             multi_threading
             (option (grab_data "multi_threading.clj"))
             "\n---\n"
             rolling_join
             (option (grab_data "rolling_join.clj"))
             "\n---\n"
             ending_message)))

