(deftemplate product
  (slot device)
  (slot brand)
  (slot model)
  (slot disc)
  (slot ram)
  (slot price)
)

(deftemplate payment
    (slot method)
    (slot bank)
    (slot promotion)
)

(deftemplate payorder
    (slot method)
    (slot bank)
)

(deftemplate order
    (slot device)
    (slot brand)
    (slot model)
    (slot disc)
    (slot ram)
)

(deftemplate offer
    (slot device)
    (slot brand)
    (slot model)
    (slot disc)
    (slot ram)
    (slot price)
    (slot promotion)
    (slot item)
)