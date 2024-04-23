package icesi.edu.co.icesiapp241.repository

import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.toObject
import icesi.edu.co.icesiapp241.domain.model.Message
import icesi.edu.co.icesiapp241.services.MessagesServices

interface MessagesRepository {
    fun observe(listener: (Message) -> Unit)
    suspend fun sendMessage(message: Message)
}

class MessagesRepositoryImpl(
    val service:MessagesServices = MessagesServices()
) : MessagesRepository{
    override fun observe(listener: (Message) -> Unit) {
        service.observeGeneralRoom{
            val message = it.toObject(Message::class.java)
            listener(message)
        }
    }
    override suspend fun sendMessage(message: Message) {
        service.sendMessage(message)
    }

}
