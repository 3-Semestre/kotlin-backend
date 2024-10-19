package com.english4ever.chatbot.controller
import com.english4ever.chatbot.model.request.BotRequest
import com.english4ever.chatbot.model.response.BotResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/messages")
class ChatbotController {

    @PostMapping
    fun receiveMessage(@RequestBody request: BotRequest): BotResponse {
        val responseMessage = processMessage(request.text)
        return BotResponse(responseMessage)
    }

    fun processMessage(message: String): String {
        return "Você disse: $message"
    }
}

