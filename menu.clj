(ns menu
  (:require [db]))

(defn menu_fct []
  (do
    (println "*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\nEnter an option?")
    (def choice (read-line))

    (if (= choice "1")
      (do
        (db/read_cust)
        )
      )
    (if (= choice "2")
      (do (db/read_prod))
      )
    (if (= choice "3")
      (do (db/display_sales))
      )
    (if (= choice "4")
      (do
        (println "Please enter a customer name: ")

        (def cust_name (read-line))
        (print cust_name)
        ;Display purchases
        (if (double? (db/search_cust cust_name))
          (do
            (print ": ")
            (print (format "%.2f" (db/search_cust cust_name)))
            (println)
            )
          (do (println ": 0.00$")
              )

          )
        )
      )
    (if (= choice "5")

      (do
        (println "Please enter an item: ")
        (def item (read-line))

        (print item)
        (db/sale_count item)
        (if (> (db/sale_count item) 0)
          (do
            (print ": ")
            (println (db/sale_count item))


            )
          (do
            (println ": 0")

            )
          )
        )
      )
    (if (= choice "6")
      (do
        (println "Good Bye")
        (System/exit 0)
        )
      )
    )
  (when (not= choice "6")
    (recur)
    )
  )

