(ns checkers.handlers
    (:require [re-frame.core :as re-frame]
              [checkers.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
  :changeTurn
  (fn [db [_ currentTurn]]
    (if (= currentTurn "w")
      (assoc db :turn "r")
      (assoc db :turn "w"))))

(re-frame/register-handler
  :select
  (fn [db [_ selection]]
    (let [x (.floor js/Math (/(first selection) 100)) y (.floor js/Math (/ (second selection) 100))]
      (if (= (:toggle db) 0)
        (assoc (assoc db :sel [[(* x 100) (* y 100)] (((:boardState db) y) x)]) :toggle 1)
        (assoc (assoc db :dest [[(* x 100) (* y 100)] (((:boardState db) y) x)]) :toggle 0)
        ))))

(re-frame/register-handler
  :move
  (fn [db [_ [color sx sy dx dy]]]
    (assoc-in (assoc-in db [:boardState sy sx] "x") [:boardState dy dx] color)))

(re-frame/register-handler
  :setToggle
  (fn [db [_ state]]
    (assoc db :toggle state)))

(re-frame/register-handler
  :jump
  (fn [db [_ [color sx sy dx dy mx my]]]
    (assoc-in (assoc-in (assoc-in db [:boardState sy sx] "x") [:boardState dy dx] color) [:boardState my mx] "x")))
