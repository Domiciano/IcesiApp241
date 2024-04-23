package icesi.edu.co.icesiapp241.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import icesi.edu.co.icesiapp241.R
import icesi.edu.co.icesiapp241.databinding.ItemMessageBinding
import icesi.edu.co.icesiapp241.databinding.ItemOtherMessageBinding
import icesi.edu.co.icesiapp241.domain.model.Message

class ChatAdapter(var messages:ArrayList<Message> = arrayListOf()) : Adapter<ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        if(messages[position].author == Firebase.auth.uid){
            return 0
        }else{
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 0){
            val root = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            return MessageViewHolder(root)
        }else{
            val root = LayoutInflater.from(parent.context).inflate(R.layout.item_other_message, parent, false)
            return OtherMessageViewHolder(root)
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is MessageViewHolder -> {
                holder.messageTV.text = messages[position].message        
            }
            is OtherMessageViewHolder -> {
                holder.messageTV.text = messages[position].message
            }
        }

    }
}

class MessageViewHolder(root: View):ViewHolder(root){
    private val binding = ItemMessageBinding.bind(root)
    val messageTV = binding.messageTV
}

class OtherMessageViewHolder(root: View):ViewHolder(root){
    private val binding = ItemOtherMessageBinding.bind(root)
    val messageTV = binding.messageTV
}