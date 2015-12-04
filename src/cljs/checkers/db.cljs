(ns checkers.db)

(def default-db
  {:turn "w"
   :boardState [["e" "w" "e" "w" "e" "w" "e" "w"]
                ["w" "e" "w" "e" "w" "e" "w" "e"]
                ["e" "w" "e" "w" "e" "w" "e" "w"]
                ["x" "e" "x" "e" "x" "e" "x" "e"]
                ["e" "x" "e" "x" "e" "x" "e" "x"]
                ["r" "e" "r" "e" "r" "e" "r" "e"]
                ["e" "r" "e" "r" "e" "r" "e" "r"]
                ["r" "e" "r" "e" "r" "e" "r" "e"]]
   :pieceCount {:w 12 :r 12}
   :sel [[0 0] "e"]
   :dest [[0 0] "e"]
   :toggle 0})
