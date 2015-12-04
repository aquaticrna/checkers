(ns checkers.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [checkers.handlers]
              [checkers.subs]
              [checkers.views :as views]
              [checkers.draw]
              [checkers.validators]))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (views/clickListener (.getElementById js/document "canv"))
  (checkers.draw/drawBoardState)
  (mount-root))
