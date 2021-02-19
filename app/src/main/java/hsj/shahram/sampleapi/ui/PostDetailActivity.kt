package hsj.shahram.sampleapi.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hsj.shahram.sampleapi.MyApplication
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.databinding.ActivityPostDetailBinding
import hsj.shahram.sampleapi.databinding.ActivityUsersBinding
import hsj.shahram.sampleapi.databinding.CommentsDialogLayoutBinding
import hsj.shahram.sampleapi.databinding.PostDialogLayoutBinding
import hsj.shahram.sampleapi.ui.adapter.CommentListAdapter
import hsj.shahram.sampleapi.ui.adapter.PostListAdapter
import hsj.shahram.sampleapi.ui.adapter.UserListAdapter
import hsj.shahram.sampleapi.util.Status
import hsj.shahram.sampleapi.viewmodel.PostDetailViewModel
import hsj.shahram.sampleapi.viewmodel.PostListViewModel
import hsj.shahram.sampleapi.viewmodel.UsersViewModel
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_post_detail.*
import javax.inject.Inject

class PostDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel : PostDetailViewModel
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var post : ResponseObj.Post
    private lateinit var adapter : CommentListAdapter
    private lateinit var  dialogBinding: CommentsDialogLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        (application as MyApplication).appComponent.inject(this)
        init()
    }

    private fun init() {
        setupViewModel()

        post = intent.getParcelableExtra<ResponseObj.Post>("Post") as ResponseObj.Post

        binding.model = post

        commentListObserver()


        btn_comments.setOnClickListener {

            showCommentDialog()
            viewModel.getCommentList(post.id!!)

        }



    }



   private fun showCommentDialog()
    {
         dialogBinding=
                DataBindingUtil.inflate(layoutInflater, R.layout.comments_dialog_layout, null, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)


        val dialog = builder.create()
        dialog.show()

        setupCommentRecyclerView()



    }

    private fun setupCommentRecyclerView()
    {

        dialogBinding.rvComments.layoutManager = LinearLayoutManager(this)
        adapter = CommentListAdapter()

        dialogBinding.rvComments.adapter = adapter
    }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PostDetailViewModel::class.java)
    }

    private fun commentListObserver()
    {

        viewModel.getCommentLiveData().observe(this){

            when (it.status) {

                Status.SUCCESS -> {
                    dialogBinding.inProgress = false
                    adapter.submitList(it.data!!)
                }

                Status.LOADING -> dialogBinding.inProgress = true

                Status.ERROR -> {
                    dialogBinding.inProgress = false

                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()


                }


            }

        }
    }
}