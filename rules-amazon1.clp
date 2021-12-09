(defrule price-smartphone
    (order (device smartphone))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method ?method)(bank ?bank))
    (payment (method ?method)(bank ?bank)(promotion ?promotion))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion ?promotion)(item "funda gratis")))
)

(defrule price-laptop
    (order (device laptop))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method ?method)(bank ?bank))
    (payment (method ?method)(bank ?bank)(promotion ?promotion))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion ?promotion)(item "mochila gratis")))
)

(defrule price-smartphone-contado
    (order (device smartphone))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method contado))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion "cupon de 10 por ciento")(item "funda y audifonos gratis")))
)

(defrule price-laptop-contado
    (order (device laptop))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method contado))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion "cupon de 15 por ciento")(item "mochila y audifonos gratis")))
)