(defrule price
    (order (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram))
    (product (device ?device)(brand ?brand)(model ?model)(disc ?disc)(ram ?ram)(price ?price))
    => (printout t ?price crlf)
)