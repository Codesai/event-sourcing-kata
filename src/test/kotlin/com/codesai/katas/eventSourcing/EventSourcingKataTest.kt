package com.codesai.katas.eventSourcing
import io.kotest.matchers.equality.FieldsEqualityCheckConfig
import io.kotest.matchers.equality.beEqualComparingFields
import io.kotest.matchers.shouldHave
import org.junit.jupiter.api.Test
import java.util.Arrays
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

        auctionRepository.getById(auction.id) shouldHave beEqualComparingFields(auction, ignoringFields(Auction::changes))
    }

    @Test
    fun `make a new bid`() {

    }

    private fun ignoringFields(vararg propertiesToExclude: KProperty1<Auction, Any>) =
        FieldsEqualityCheckConfig(propertiesToExclude = listOf(*propertiesToExclude))
}


