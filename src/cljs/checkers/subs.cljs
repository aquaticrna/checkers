(ns checkers.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :turn
  (fn [db]
    (reaction (:turn @db))))

(re-frame/register-sub
  :boardState
  (fn [db]
    (reaction (:boardState @db))))

(re-frame/register-sub
  :pieceCoordinants
  (fn [db]
    (let [board (re-frame/subscribe [:boardState])]
      (filter vector? (map-indexed (fn [index pos] (if (and (not= pos "e") (not= pos "x"))
                                                     [[(+ (* (.floor js/Math (/ index 8)) 100) 50) ;x position
                                                       (+ (* (mod index 8) 100) 50)] ;y position
                                                      pos])) ;color
                     (flatten @board)))
      )))

(re-frame/register-sub
  :squareCoords
  (fn [db]
    (let [board (re-frame/subscribe [:boardState])]
      (filter vector? (map-indexed (fn [index pos] (if (not= pos "e")
                                     [(* (.floor js/Math (/ index 8)) 100) ;x position
                                      (* (mod index 8) 100)])) ;y position
                     (flatten @board)))
      )))

(re-frame/register-sub
  :selected
  (fn [db]
    (reaction (:sel @db))))

(re-frame/register-sub
  :dest
  (fn [db]
    (reaction (:dest @db))))
