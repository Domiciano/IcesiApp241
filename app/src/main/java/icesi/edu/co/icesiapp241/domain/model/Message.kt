package icesi.edu.co.icesiapp241.domain.model

import com.google.firebase.Timestamp

data class Message(
    var author: String = "",
    var message: String = "",
    var date: Timestamp = Timestamp.now()
)