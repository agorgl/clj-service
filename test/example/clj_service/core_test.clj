(ns example.clj-service.core-test
  (:require
   [clojure.test :refer :all]
   [example.clj-service.core :refer :all]))

(deftest a-test
  (testing "A passing test."
    (is (= 4 (+ 2 2)))))
