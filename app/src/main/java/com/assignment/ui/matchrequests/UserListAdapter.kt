package com.assignment.ui.matchrequests

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.local.entities.UserListJoinResult
import com.assignment.databinding.ItemUserBinding
import com.assignment.utils.MatchReqStatus
import com.bumptech.glide.Glide


class UserListAdapter(private val listener: UserItemListener) :
    RecyclerView.Adapter<UserViewHolder>() {

    interface UserItemListener {
        fun onAcceptClick(userId: String)
        fun onRejectClick(userId: String)
    }

    private val items = ArrayList<UserListJoinResult>()

    fun setItems(items: ArrayList<UserListJoinResult>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(items[position])
}

class UserViewHolder(
    private val itemBinding: ItemUserBinding,
    private val listener: UserListAdapter.UserItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var user: UserListJoinResult

    init {
        itemBinding.root.setOnClickListener(this)
    }

    private fun hideButtons(status: Int, context: Context) {
        if (status == MatchReqStatus.ACCEPTED.VALUE || status == MatchReqStatus.REJECTED.VALUE) {
            itemBinding.imgReject.visibility = View.INVISIBLE
            itemBinding.imgAccept.visibility = View.INVISIBLE
            itemBinding.txtUserStatus.visibility = View.VISIBLE
            if (status == 1) {
                itemBinding.txtUserStatus.text = "User Request Rejected"
                itemBinding.txtUserStatus.setTextColor(
                    ContextCompat.getColor(context, R.color.rejectedUserColor)
                )
            } else {
                itemBinding.txtUserStatus.text = "User Request Accepted"
                itemBinding.txtUserStatus.setTextColor(
                    ContextCompat.getColor(context, R.color.acceptedUserColor)
                )
            }
        } else {
            itemBinding.imgReject.visibility = View.VISIBLE
            itemBinding.imgAccept.visibility = View.VISIBLE
            itemBinding.txtUserStatus.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: UserListJoinResult) {
        this.user = item
        itemBinding.name.text = item.name
        itemBinding.ageNheight.text = "Age : " + item.age.toString()
        itemBinding.profession.text = "City : " + item.city

        itemBinding.imgReject.setOnClickListener {
            item.uuid?.let { it1 -> listener.onRejectClick(it1) }
        }
        itemBinding.imgAccept.setOnClickListener {
            item.uuid?.let { it1 -> listener.onAcceptClick(it1) }
        }

        Glide.with(itemBinding.root)
            .load(item.picURL)
            .into(itemBinding.userPic)

        hideButtons(item.userApproval, itemBinding.root.context)
    }

    override fun onClick(v: View?) {

    }
}

