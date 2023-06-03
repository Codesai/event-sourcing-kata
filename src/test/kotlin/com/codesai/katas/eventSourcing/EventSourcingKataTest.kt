package com.codesai.katas.eventSourcing
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.Test

class EventSourcingKataTest {

    private val auctionRepository = AuctionRepositoryEventSourcing()

    @Test
    fun `create an auction`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id).shouldBeEqual(auction)
    }

    @Test
    fun `new bid`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        auction.bid(auction.initialPrice + 100  )

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id).shouldBeEqual(auction)
    }

    @Test
    fun `close auction`() {

    }



}



