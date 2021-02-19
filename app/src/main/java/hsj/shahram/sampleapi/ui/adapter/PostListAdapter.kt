package hsj.shahram.sampleapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.databinding.PostItemBinding
import hsj.shahram.sampleapi.databinding.UserItemBinding

class PostListAdapter(
        private val onItemClicked: (post: ResponseObj.Post) -> Unit,
        private val onDeleteClicked: (post: ResponseObj.Post, position: Int) -> Unit,
        private val onEditClicked: (post: ResponseObj.Post, position: Int) -> Unit,
)
    : RecyclerView.Adapter<PostListAdapter.PostHolder>() {

    private var postList: MutableList<ResponseObj.Post> = ArrayList()



    fun editItem(position : Int , post : ResponseObj.Post)
    {

        postList.set(position , post)
        notifyItemChanged(position)

    }

    fun deletePost(position : Int)
    {

        postList.removeAt(position)
        notifyItemRemoved(position)

    }

    fun addItem(post: ResponseObj.Post)
    {

        postList.add(0 , post)
        notifyDataSetChanged()

    }
    fun submitList(posts: MutableList<ResponseObj.Post>) {
        postList = posts
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {

        val binding: PostItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.post_item, parent, false);


        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.bindTo(postList[position])


        holder.itemView.setOnClickListener {
            onItemClicked.invoke(postList[holder.adapterPosition])
        }

        holder.binding.imageDelete.setOnClickListener {
            onDeleteClicked.invoke(postList[holder.adapterPosition] , holder.adapterPosition)
        }

        holder.binding.imageEdit.setOnClickListener {
            onEditClicked.invoke(postList[holder.adapterPosition] , holder.adapterPosition)

        }

    }

    override fun getItemCount(): Int = postList.size


    class PostHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(post: ResponseObj.Post) {

            binding.model = post

        }

    }

}