package icesi.edu.co.icesiapp241.services

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import icesi.edu.co.icesiapp241.domain.model.Message
import kotlinx.coroutines.tasks.await

class MessagesServices {


    fun observeGeneralRoom(
        listener:(QueryDocumentSnapshot) -> Unit
    ) {
        Firebase.firestore.collection("generalroom")
            .orderBy("date")
            .addSnapshotListener { snapshot, error ->
            snapshot?.documentChanges?.forEach { change ->
                when(change.type){
                    DocumentChange.Type.ADDED -> {
                        listener(change.document)
                    }
                    else -> {}
                }
            }
        }
    }

    suspend fun sendMessage(message: Message) {
        Firebase.firestore.collection("generalroom").add(message).await()
    }


}