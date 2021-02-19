package hsj.shahram.sampleapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.databinding.CommentItemBinding
import hsj.shahram.sampleapi.databinding.PostItemBinding
import hsj.shahram.sampleapi.databinding.UserItemBinding

class CommentListAdapter()
    : RecyclerView.Adapter<CommentListAdapter.CommentHolder>() {

    private var commentList: List<ResponseObj.Comment> = ArrayList()




    fun submitList(comments: List<ResponseObj.Comment>) {
        commentList = comments
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {

        val binding: CommentItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.comment_item, parent, false);


        return CommentHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {

        holder.bindTo(commentList[position])



    }

    override fun getItemCount(): Int = commentList.size


    class CommentHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(comment: ResponseObj.Comment) {

            binding.model = comment

        }

    }

}