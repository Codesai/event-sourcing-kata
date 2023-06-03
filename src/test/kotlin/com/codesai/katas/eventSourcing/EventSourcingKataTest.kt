package com.codesai.katas.eventSourcing
import io.kotest.matchers.equality.FieldsEqualityCheckConfig
import io.kotest.matchers.equality.beEqualComparingFields
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.reflect.KProperty1

class EventSourcingKataTest {

    private val auctionRepository = AuctionRepositoryEventSourcing()

    @Test
    fun `create an auction`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id) should
                beEqualComparingFields(auction, ignoringFields(Auction::changes))
    }

    @Test
    fun `new bid`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        auction.bid(auction.initialPrice + 100, "bidder_name")

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id) should
                beEqualComparingFields(auction, ignoringFields(Auction::changes))
    }

    @Test
    fun `close auction`() {
        val auction = Auction(
            itemDescription = "Mario Bros NES",
            initialPrice = 10000
        )
        auction.bid(auction.initialPrice + 100, "bidder_name")
        auction.close()

        auctionRepository.save(auction)

        auctionRepository.getById(auction.id) should
                beEqualComparingFields(auction, ignoringFields(Auction::changes))
    }

    @Test
    fun `include a new bid event that include the name of the bidder`() {
        val auctionRepository = object:AuctionRepositoryEventSourcing()  {
            fun save(events : List<BaseEvent>) {
                inMemoryEvents.addAll(events)
            }
        }

        val id = UUID.randomUUID().toString()
        auctionRepository.save(listOf(
            AuctionCreated(id, "description", 100),
            AuctionNewBid(id, 101),
            AuctionNewBidV2(id, 102, "bidder_name"),
            AuctionClosed(id, true)
        ))

        val auction = auctionRepository.getById(id)

        auction.currentBid shouldBe 102
        auction.bidder shouldBe "bidder_name"
    }

    private fun ignoringFields(vararg propertiesToExclude: KProperty1<Auction, Any>) =
        FieldsEqualityCheckConfig(propertiesToExclude = listOf(*propertiesToExclude))

}



