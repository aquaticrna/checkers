(ns checkers.draw
  (:require [re-frame.core :as re-frame]
            [goog.dom :as dom]
            [goog.events :as event]
            [checkers.validators :refer [validMove? validJump?]]))

(defn getPen []
  (.getContext (.getElementById js/document "canv") "2d"))

(defn drawBoard [pen coords]
  (set! (. pen -fillStyle) "#000000")
  (set! (. pen -strokeStyle) "#000000")
  (.strokeRect pen 0 0 800 800)
  (doseq [[x y] coords]
    (.fillRect pen y x 100 100))
  )

(defn drawPiece
  [pen [x y] color]
  (if (and (number? x) (number? y))
    (do (if (= color "w")
          (set! (. pen -fillStyle) "#FFFFFF")
          (set! (. pen -fillStyle) "#FF0000"))
      (.beginPath pen)
      (.arc pen y x 45 0 (* (.-PI js/Math) 2))
      (.fill pen))
    ))

(defn colorMoves
  [x y board turn pen]
  (let [xind (/ x 100) yind (/ y 100) xs [(- xind 1) (+ xind 1)] ys [(- yind 1) (+ yind 1)]]
    (set! (. pen -fillStyle) "#00FFOO")
    (doseq [dx xs]
      (doseq [dy ys]
        (if (validMove? xind yind dx dy board turn)
          (.fillRect pen (* 100 dx) (* 100 dy) 100 100))
        )))
  )

(defn colorJumps [x y board turn pen]
  (let [xind (/ x 100) yind (/ y 100) xs [(- xind 2) (+ xind 2)] ys [(- yind 2) (+ yind 2)]]
    (set! (. pen -fillStyle) "#F000F0")
    (doseq [dx xs]
      (doseq [dy ys]
        (if (validJump? xind yind dx dy board turn)
          (.fillRect pen (* 100 dx) (* 100 dy) 100 100))
        )))
  )

(defn colorSelected [selected pen board turn]
  (let [[[x y] val] @selected]
    (if (and (not= (subs val 0 1) "e") (not= (subs val 0 1) "x"))
      (do (set! (. pen -fillStyle) "#0000FF")
        (.fillRect pen x y 100 100)
        (set! (. pen -fillStyle) "#00FF00")
        (colorMoves x y board turn pen)
        (colorJumps x y board turn pen))
      )))

(defn drawBoardState []
  (let [piecesCoords (re-frame/subscribe [:pieceCoordinants]) coords (re-frame/subscribe [:squareCoords])
        pen (getPen) selected (re-frame/subscribe [:selected]) turn (re-frame/subscribe [:turn]) board (re-frame/subscribe [:boardState])]
    (drawBoard pen coords)
    (colorSelected selected pen board turn)
    (doseq [[piece color] piecesCoords]
      (drawPiece pen piece color))))


