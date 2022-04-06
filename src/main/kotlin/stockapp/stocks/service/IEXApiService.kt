package stockapp.stocks.service

import com.fasterxml.jackson.databind.JsonNode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onErrorResume
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull
import org.springframework.web.reactive.function.client.bodyToFlow

@Service
class IEXApiService {

    @Value("\${IEX_PUBLIC_TOKEN}")
    lateinit var iexToken: String

    private val iexBase: String = "https://sandbox.iexapis.com/stable/"
    private val iexBaseTimeSeries: String = "https://sandbox.iexapis.com/stable/time-series/"

    fun GetStockQuote(symbol: String): Flow<JsonNode> = WebClient
        .create(iexBase)
        .get()
        .uri("stock/$symbol/quote?token=$iexToken")
        .retrieve()
        .bodyToFlow()

    fun GetStockStatsBasic(symbol: String): Flow<String> = WebClient
        .create(iexBase)
        .get()
        .uri("stock/$symbol/stats?token=$iexToken")
        .retrieve()
        .bodyToFlow()

    fun GetStockInsiderTrading(symbol: String): Flow<String> = WebClient
        .create(iexBaseTimeSeries)
        .get()
        .uri("insider_transactions/$symbol?last=10?token=$iexToken")
        .retrieve()
        .bodyToFlow()

    fun GetStockPreviousDividends(symbol: String): Flow<String> = WebClient
        .create(iexBaseTimeSeries)
        .get()
        .uri("dividends/$symbol?last=4?token=$iexToken")
        .retrieve()
        .bodyToFlow()

    fun GetStockNextDividends(symbol: String): Flow<String> = WebClient
        .create(iexBase)
        .get()
        .uri("stock/$symbol/dividends/next?token=$iexToken")
        .retrieve()
        .bodyToFlow()

    fun GetStockLargestTrades(symbol: String): Flow<String> = WebClient
        .create(iexBase)
        .get()
        .uri("stock/$symbol/largest-trades?token=$iexToken")
        .retrieve()
        .bodyToFlow()
}