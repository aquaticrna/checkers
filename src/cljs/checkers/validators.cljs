(ns checkers.validators
(:require [re-frame.core :as re-frame]
            [goog.dom :as dom]
            [goog.events :as event]))

(defn validJump?
  [sy sx dy dx board turn]
  (if (and (>= dx 0) (< dx 8) (>= dy 0) (< dy 8))
    (let [source ((@board sx) sy) dest ((@board dx) dy) mid ((@board (/ (+ sx dx) 2)) (/ (+ sy dy) 2))]
      (if (and (= "x" dest) ;destination space is empty
               (= (subs source 0 1) @turn);the source space contains the right color piece
               (not= (subs mid 0 1) @turn) ;the piece inbetween source and dest is the opposite color
               (not= (subs mid 0 1) "x") ;the space in between isn't empty
               (if (not= (subs source 1) "k") ;if it's not a king
                 (if (= @turn "w") ;and it's white's turn
                   (and (= -2 (- sx dx)) (= 2 (.abs js/Math (- sy dy))));or jumping 2 up and 2 left/right with
                   (and (= 2 (- sx dx)) (= 2 (.abs js/Math (- sy dy))))) ;or stepping 2 down and 2 left/right
                 (and (= 2 (.abs js/Math (- sx dx)) (= 2 (.abs js/Math (- sy dy))))))) ;if it is a king it must move 2 up/down and 2 left/right
        true
        false))
    false))

(defn validMove?
  [sy sx dy dx board turn]
  (if (and (>= dx 0) (< dx 8) (>= dy 0) (< dy 8))
    (let [source ((@board sx) sy) dest ((@board dx) dy)]
      (if (and (= "x" dest) ;destination space is empty
               (= (subs source 0 1) @turn);the source space contains the right color piece
               (if (not= (subs source 1) "k") ;if it's not a king
                 (if (= @turn "w") ;and it's white's turn
                   (and (= -1 (- sx dx)) (= 1 (.abs js/Math (- sy dy)))) ;step 1 down and 1 left/right
                   (and (= 1 (- sx dx)) (= 1 (.abs js/Math (- sy dy))))) ;red and stepping one up and 2 left/right
                 (and (= 1 (.abs js/Math (- sx dx))) (= 1 (.abs js/Math (- sy dy)))) ;king step 1 up/down and 1 left/right
                 ))
        true
        false))
    false))