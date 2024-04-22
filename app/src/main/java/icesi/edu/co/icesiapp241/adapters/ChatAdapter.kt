package icesi.edu.co.icesiapp241.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import icesi.edu.co.icesiapp241.R
import icesi.edu.co.icesiapp241.databinding.ItemMessageBinding
import icesi.edu.co.icesiapp241.domain.model.Message

class ChatAdapter(var messages:ArrayList<Message> = arrayListOf()) : Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(root)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageTV.text = messages[position].message
    }
}

class MessageViewHolder(root: View):ViewHolder(root){
    private val binding = ItemMessageBinding.bind(root)
    val messageTV = binding.messageTV
}