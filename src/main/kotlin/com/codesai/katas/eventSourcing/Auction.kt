package com.codesai.katas.eventSourcing

class Auction {

    val id: String
    val itemDescription: String
    val initialPrice: Int
    var currentBid: Int = 0

    val changes : MutableList<BaseEvent> = mutableListOf()

    constructor(id: String, itemDescription: String, initialPrice: Int) {
        this.id = id
        this.itemDescription = itemDescription
        this.initialPrice = initialPrice
        changes.add(AuctionCreated(id, itemDescription, initialPrice))
    }

    constructor(created : AuctionCreated, events : List<BaseEvent>) {
        this.id = created.id
        this.itemDescription = created.itemDescription
        this.initialPrice = created.initialPrice

        events.forEach { applyEvent(it) }
    }

    fun bid(newBid: Int) {
        if (newBid < currentBid) throw RuntimeException("bid must be greater than $currentBid")

        val event = AuctionNewBid(this.id, newBid)

        applyEvent(event)
    }

    private fun applyEvent(event: BaseEvent) {
        when(event) {
            is AuctionNewBid -> applyEvent(event)
            else -> throw RuntimeException("unknow event $event")
        }
    }

    private fun applyEvent(event: AuctionNewBid) {
        this.currentBid = event.newBid
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Auction

        if (id != other.id) return false
        if (itemDescription != other.itemDescription) return false
        if (initialPrice != other.initialPrice) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + itemDescription.hashCode()
        result = 31 * result + initialPrice
        return result
    }
}
