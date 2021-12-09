(defrule price-smartphone
    (order (device smartphone))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method ?method)(bank ?bank))
    (payment (method ?method)(bank ?bank)(promotion ?promotion))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion ?promotion)(item "popSocket incluido")))
)

(defrule price-laptop
    (order (device laptop))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method ?method)(bank ?bank))
    (payment (method ?method)(bank ?bank)(promotion ?promotion))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion ?promotion)(item "mouse y teclado gratis")))
)

(defrule price-smartphone-contado
    (order (device smartphone))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method contado))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion "envio gratis en tu siguiente compra")(item "popSocket incluido")))
)

(defrule price-laptop-contado
    (order (device laptop))
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    (payorder (method contado))
    => (assert(offer (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price)(promotion "envio gratis en tu siguiente compra")(item "mouse inalambrico gratis")))
)