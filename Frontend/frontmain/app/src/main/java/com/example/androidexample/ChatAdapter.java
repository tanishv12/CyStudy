package com.example.androidexample;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidexample.databinding.ItemContainerRecievedmsgBinding;
import com.example.androidexample.databinding.ItemContainerSentmsgBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final List<ChatMessage> chatMessages;
    private final String user_id;

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECIEVED = 2;



    public ChatAdapter(List<ChatMessage> chatMessages, String userId)
    {
        this.chatMessages = chatMessages;
        user_id = userId;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == VIEW_TYPE_SENT)
        {
            return new SentMessageViewHolder(
                    ItemContainerSentmsgBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
        else
        {
            return new RecievedMessageViewHolder(
                    ItemContainerRecievedmsgBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(getItemViewType(position) == VIEW_TYPE_SENT)
        {
            ((SentMessageViewHolder)holder).setData(chatMessages.get(position));
        }
        else
        {
            ((RecievedMessageViewHolder)holder).setData(chatMessages.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(chatMessages.get(position).userid.equals(user_id))
        {
            return VIEW_TYPE_SENT;
        }
        else
        {
            return VIEW_TYPE_RECIEVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder
    {
        private final ItemContainerSentmsgBinding binding;

        SentMessageViewHolder(ItemContainerSentmsgBinding itemContainerSentmsgBinding)
        {
            super(itemContainerSentmsgBinding.getRoot());
            binding = itemContainerSentmsgBinding;
        }

        void setData(ChatMessage chatMessage)
        {
            binding.textMessage.setText(chatMessage.message);
        }
    }

    static class RecievedMessageViewHolder extends RecyclerView.ViewHolder
    {
        private final ItemContainerRecievedmsgBinding binding;

        RecievedMessageViewHolder(ItemContainerRecievedmsgBinding itemContainerRecievedmsgBinding)
        {
            super(itemContainerRecievedmsgBinding.getRoot());
            binding = itemContainerRecievedmsgBinding;
        }

        void setData(ChatMessage chatMessage)
        {
            binding.textMessage.setText(chatMessage.message);
        }

    }

}
