package hsj.shahram.sampleapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.databinding.UserItemBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserHolder>() {

    private var userList: List<ResponseObj.UserItem> = ArrayList()


    fun submitList(users: List<ResponseObj.UserItem>) {
        userList = users
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {

        val binding: UserItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.user_item, parent, false);


        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {

        holder.bindTo(userList[position])

    }

    override fun getItemCount(): Int = userList.size



    class UserHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: ResponseObj.UserItem) {

            binding.model = user

        }

    }

}