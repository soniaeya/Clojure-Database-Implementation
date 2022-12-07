(require '[clojure.string :as s])

(require '[clojure.string :as s])

;1|John Smith|123 Here Street|456-4567
;2|Sue Jones|43 Rose Court Street|345-7867
;3|Fan Yuhong|165 Happy Lane|345-4533

;Make list to store each vector


; Question 1: Methods and variables to create databases

(def cust_db (vector))
(def sales_db (vector))
(def prod_db (vector))
(def display_sales_db (vector))

(def num_of_customers
  (with-open [rdr (clojure.java.io/reader "cust.txt")]
    (def cust_count (count (line-seq rdr)))
    )

  )
(def num_of_products
  (with-open [rdr (clojure.java.io/reader "prod.txt")]
    (def prod_count (count (line-seq rdr)))
    )
  )

(def num_of_sales
  (with-open [rdr (clojure.java.io/reader "sales.txt")]
    (def sales_count (count (line-seq rdr)))
    )
  )

(def create_cust_db
  (with-open [rdr (clojure.java.io/reader "cust.txt")]
    (doseq [line (line-seq rdr)]


      (def block
        (s/split line #"\|")
        )

      ;0
      (def cust_ID
        (get block 0)
        )
      ;1
      (def cust_name
        (get block 1)
        )
      ;2
      (def street
        (get block 2)
        )
      ;3
      (def phone_num
        (get block 3)
        )
      (def my_vector [cust_ID, cust_name, street, phone_num])
      (def cust_db (conj cust_db my_vector))
      ; Make map with the values
      )
    )
  )
(def create_prod_db
  (with-open [rdr (clojure.java.io/reader "prod.txt")]
    (doseq [line (line-seq rdr)]
      (def block
        (s/split line #"\|")
        )

      ;0
      (def prod_ID
        (get block 0)
        )
      ;1
      (def item_descr
        (get block 1)
        )
      ;2
      (def unit_cost
        (get block 2))
      (def products [prod_ID, item_descr, unit_cost])
      (def prod_db (conj prod_db products))
      ; Make map with the values
      )
    )
  )
(def create_sales_db
  (with-open [rdr (clojure.java.io/reader "sales.txt")]
    (doseq [line (line-seq rdr)]
      (def block
        (s/split line #"\|"))
      ;0
      (def sales_ID
        (get block 0))
      ;1
      (def cust_ID
        (get block 1))
      ;2
      (def prod_ID
        (get block 2))
      ;3
      (def item_count
        (get block 3))

      (def sales [sales_ID, cust_ID, prod_ID, item_count])
      (def sales_db (conj sales_db sales))
      (def display_sales_db (conj display_sales_db sales))
      ; Make map with the values
      )
    )
  )


;Question 2
(defn read_sales []
  (def sales_table (
                     slurp "prod.txt")
    )
  ;(println string1)
  (println sales_table)
  )

; For question 3
(defn display_sales []
  (loop [x 0]
    (when (< x sales_count)
      ; customer id in sales table (sales table's index 1)
      (def cust_id_num (- (Integer/parseInt (get (get sales_db x) 1)) 1)

        )
      ; prod id in sales table (sales table's index 2)
      (def prod_id_num (- (Integer/parseInt (get (get sales_db x) 2)) 1)
        )
      (def cust_id_string
        (get (get cust_db cust_id_num) 1)
        )

      (def prod_id_string
        (get (get prod_db prod_id_num) 1)
        )
      ;replace index 1 of sales table with customer name (assoc (get display_sales_db x) 1 cust_id_string)
      ;replace index 2 of sales table with product name (assoc (get sales_db_with_names x) 2 prod_id_string)
      (def sales_db_with_name
        (assoc (get sales_db x) 1 cust_id_string)
        )
      (println (assoc sales_db_with_name 2 prod_id_string))
      (recur (+ x 1))
      )
    )
  )


; Question 4
(defn read_cust []
  (def string1 (
                 slurp "cust.txt")
    )

  (def block
    (s/split string1 #"\n")
    )

  ;(println block)
(println string1)
  )
;Question 1
(defn read_prod []
  (def string1 (
                 slurp "prod.txt")
    )
  ;(println string1)
  (println string1)

  )



;Question 4

; Search for customer existence in customer table
(defn search_cust [name_input]
  (def cust_exist false)
  (def total 0)
  ;sonia
  (loop [x 0]
    (when (< x cust_count)
      ;if the inputed name is in the cust_db (idx 1)
      (def cust_name
        (get (get cust_db x) 1))


      (if (= cust_name name_input)
        (do
          (def cust_ID (+ 1 x))

          (def total 0)

          (loop [y 0] (when (< y sales_count)
                        ;custID_in_sales is a long
                        (def custID_in_sales (get (get sales_db y) 1))

                        (def custID_in_sales_long
                          (Long/valueOf custID_in_sales)
                          )
                        ;cust_ID is a string
                        (def cust_ID_long
                          (Long/valueOf cust_ID)
                          )

                        (if (= custID_in_sales_long cust_ID_long)
                          (do (def prod_id_in_sales_txt
                                (get (get sales_db y) 2))
                              (loop [z 0] (when (< z prod_count)
                                            (def prod_id_in_prod_txt
                                              (get (get sales_db z) 0))

                                            (if (= prod_id_in_prod_txt prod_id_in_sales_txt)

                                              (do
                                                (def item_price_str (get (get prod_db z) 2))

                                                (def item_count_str (get (get sales_db y) 3)) ;;here

                                                (def item_price_double (Double. item_price_str))
                                                (def item_count_double (Double. item_count_str))

                                                (def price (* item_count_double item_price_double))

                                                (def total (+ total price))

                                                )



                                              )
                                            ;loops over the items in the prod file


                                            (recur (+ z 1))
                                            )

                                          )


                              )
                          ;to do
                          ; match prod name in prod.txt with prodID from sales.txt (need to use the display_sales db (idx 1) instead


                          )
                        ;loop over prod.txt



                        (recur (+ y 1))))
          (def cust_exist true)


          )
        )

      (recur (+ x 1))
      )
    )

  (if (= cust_exist true)
    (do true))
  )

;Question 5
(defn sale_count [item]

  ;loop in prod_db idx 1
  (loop [a 0]
    (when (< a prod_count)
      (def items_in_prod_db (get (get prod_db a) 1))

      (if (= item items_in_prod_db)
        (do
          (def prod_id
            (get (get prod_db a) 0)
            )

          ;loop over sales.txt idx 2
          (def prod_bought 0)
          (loop [y 0]
            (when (< y sales_count)

              (def prod_id_in_sales_db
                (get (get sales_db y) 2)
                )

              (if (= prod_id_in_sales_db prod_id)
                (do
                  (def item_count (Integer/parseInt (get (get sales_db y) 3)))

                  (def prod_bought (+ prod_bought item_count))
                  )
                )
              (recur (+ 1 y))
              )
            ;sonia
            )

          )
        )



      (recur (+ a 1))
      )
    )


  )

(defn optionss []
    (do
      (println "*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\nEnter an option?")
      (def choice (read-line))
      (if (= choice "1")
        (do
          (read_cust)
            )
        )
      (if (= choice "2")
        (do (read_prod))
        )
      (if (= choice "3")
        (do (display_sales))
        )
      (if (= choice "4")
        (do
          (println "Please enter a customer name: ")
          (def cust_name (read-line))
          ;Display purchases
          (if (search_cust cust_name)
            (do
              (print cust_name)
              (print ": ")
              (print (format "%.2f" total))
              (println)
              ))
          )
        )
      (if (= choice "5")
        (do
          (println "Please enter an item: ")
          (def item (read-line))
          (if (sale_count item)
            (do
              (sale_count item)
              (println item)
              (print ": ")
              (println prod_bought)

              ))
          (sale_count item)
          (println prod_bought)
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

(optionss)
