(require '[nextjournal.clerk :as clerk])

(clerk/serve! {:browse true})

(clerk/halt!)