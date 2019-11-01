(ns org.christianromney.modes
  (:require [clojure.string :as str]
            [clojure.pprint :refer [cl-format]])
  (:gen-class))

(defn rotate
  "Each successive mode rotates the first step to the end."
  [[head & tail]]
  (conj (vec tail) head))

(def mode-names
  "The names of all 7 modes."
  [:ionian :dorian :phrygian :lydian :mixolydian :aeolian :locrian])

(def ionian-pattern
  "The pattern of the Ionian mode (major scale)."
  [:W :W :H :W :W :W :H])

(def modes
  "A map from mode-name keyword to the step pattern of whole (:W) or half (:H) notes."
  (zipmap
   mode-names
   (take (count mode-names) (iterate rotate ionian-pattern))))

(defn -main
  "Print the pattern of whole and half steps for the given mode."
  [& args]
  (let [mode-name (some-> args first str/trim str/lower-case)]
    (if-let [pattern (some-> mode-name keyword modes seq)]
      (cl-format true "~a mode: ~{~d~^, ~}~%" (str/capitalize mode-name) (mapv name pattern))
      (cl-format true "Please supply the name of a mode (~{~d~^, ~}~%)" (mapv name mode-names)))))
