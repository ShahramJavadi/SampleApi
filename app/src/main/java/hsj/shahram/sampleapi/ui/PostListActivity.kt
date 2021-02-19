package hsj.shahram.sampleapi.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hsj.shahram.sampleapi.MyApplication
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.databinding.ActivityPostListBinding
import hsj.shahram.sampleapi.databinding.ActivityUsersBinding
import hsj.shahram.sampleapi.databinding.PostDialogLayoutBinding
import hsj.shahram.sampleapi.ui.adapter.PostListAdapter
import hsj.shahram.sampleapi.ui.adapter.UserListAdapter
import hsj.shahram.sampleapi.util.Status
import hsj.shahram.sampleapi.viewmodel.PostListViewModel
import hsj.shahram.sampleapi.viewmodel.UsersViewModel
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_post_list.*
import javax.inject.Inject

class PostListActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: PostListViewModel
    private lateinit var binding: ActivityPostListBinding
    private lateinit var adapter: PostListAdapter
    private var editedPostPosition = -1
    private var deletedPostPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)
        (application as MyApplication).appComponent.inject(this)
        init()
    }

    private fun init() {

        clickListener()
        setupViewModel()
        setupRecyclerView()
        editedPostObserver()
        createdPostObserver()
        deletedPostObserver()
        userListObserver()
    }


    private fun clickListener() {
        fab_create_post.setOnClickListener {

                showCreateDialog()

        }

    }


    private fun createdPostObserver()
    {
        viewModel.getCreatedPostLiveData().observe(this) {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.inProgress = false
                    adapter.addItem(it.data!!)
                }

                Status.LOADING -> binding.inProgress = true

                Status.ERROR -> {
                    binding.inProgress = false

                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()


                }


            }

        }

    }
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PostListViewModel::class.java)
    }


    private fun userListObserver() {

        viewModel.getPostListLiveData().observe(this) {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.inProgress = false
                    adapter.submitList(it.data!!)
                }

                Status.LOADING -> binding.inProgress = true

                Status.ERROR -> {
                    binding.inProgress = false

                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()


                }


            }

        }

    }


    private fun editedPostObserver() {

        viewModel.getEditedPostLiveData().observe(this) {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.inProgress = false
                    adapter.editItem(editedPostPosition, it.data!!)
                }

                Status.LOADING -> binding.inProgress = true

                Status.ERROR -> {
                    binding.inProgress = false

                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()


                }


            }

        }

    }

    private fun deletedPostObserver() {

        viewModel.getDeletedPostLiveData().observe(this) {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.inProgress = false
                    adapter.deletePost(deletedPostPosition)
                }

                Status.LOADING -> binding.inProgress = true

                Status.ERROR -> {
                    binding.inProgress = false

                    Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()


                }


            }

        }

    }


    private fun setupRecyclerView() {
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        adapter = PostListAdapter(onItemClicked = {
            navigateToPostDetail(it)
        },
                onDeleteClicked = { post, position -> deletePost(position, post) },
                onEditClicked = { post, position -> showEditDialog(position, post) })

        binding.rvPosts.adapter = adapter


    }

    private fun navigateToPostDetail(post: ResponseObj.Post) {

        intent  = Intent(this , PostDetailActivity::class.java)
        intent.putExtra("Post" , post)
        startActivity(intent)

    }

    private fun deletePost(position: Int, post: ResponseObj.Post) {
        deletedPostPosition = position
        viewModel.deletePost(post.id!!)

    }

    private fun showEditDialog(position: Int, post: ResponseObj.Post) {
        val dialogBinding: PostDialogLayoutBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.post_dialog_layout, null, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)

        dialogBinding.buttonText = "Edit"
        dialogBinding.model = post
        val dialog = builder.create()
        dialog.show()


        dialogBinding.btnAction.setOnClickListener {
            val editedPost = ResponseObj.Post(post.userId, post.id,
                    dialogBinding.etTitle.text.toString(),
                    dialogBinding.etBody.text.toString())

            editPost(position, editedPost)
            dialog.dismiss()

        }


    }


    private fun showCreateDialog() {
        val dialogBinding: PostDialogLayoutBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.post_dialog_layout, null, false)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)

        dialogBinding.buttonText = "Create"
        val dialog = builder.create()
        dialog.show()


        dialogBinding.btnAction.setOnClickListener {
            val newPost = ResponseObj.Post(viewModel.getUserId()?.toInt(), null,
                    dialogBinding.etTitle.text.toString(),
                    dialogBinding.etBody.text.toString())

            viewModel.createPost(newPost)
            dialog.dismiss()

        }


    }


    private fun editPost(position: Int, post: ResponseObj.Post) {


        editedPostPosition = position
        viewModel.editPost(post)


    }
}