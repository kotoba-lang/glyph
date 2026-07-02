(ns kotoba.glyph.constants
  "EDN-authority constants for the `kotoba.glyph` SDF text-layout contract.
  Every value here has a corresponding hardcoded literal in the original
  kami-text (Rust) source; see resources/kotoba/glyph/constants.edn for the
  Rust symbol each entry mirrors.

  Loading is JVM-only (`slurp`/`io/resource`, guarded behind `#?(:clj ...)`
  so ClojureScript compilation still resolves cleanly); a ClojureScript
  build should bundle/inline `resources/kotoba/glyph/constants.edn` at
  build time (e.g. via a `defmacro` that slurps it at macro-expansion
  time) instead of calling `registry` at runtime."
  #?(:clj (:require [clojure.edn :as edn]
                     [clojure.java.io :as io])))

(def registry-resource "kotoba/glyph/constants.edn")

(defn registry
  "Load the glyph constants registry (character ranges, atlas packing
  ratios, capacity floors, pinned entries, layout fallback ratios). JVM-only."
  []
  #?(:clj (edn/read-string (slurp (io/resource registry-resource)))
     :cljs (throw (ex-info "kotoba.glyph.constants/registry is JVM-only; bundle resources/kotoba/glyph/constants.edn at build time for ClojureScript" {}))))

(def defaults
  "Registry loaded once at namespace load. Prefer this over calling
  `registry` repeatedly in hot paths."
  #?(:clj (registry) :cljs nil))
