package hsj.shahram.sampleapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hsj.shahram.sampleapi.MyApplication
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.databinding.ActivityUsersBinding
import hsj.shahram.sampleapi.ui.adapter.UserListAdapter
import hsj.shahram.sampleapi.util.Status
import hsj.shahram.sampleapi.viewmodel.UsersViewModel
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import javax.inject.Inject

class UsersActivity : AppCompatActivity() {

    @Inject lateinit var factory: ViewModelFactory
    private lateinit var viewModel : UsersViewModel
    private lateinit var binding: ActivityUsersBinding
    private lateinit var adapter : UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        (application as MyApplication).appComponent.inject(this)
        init()
    }

    private fun init() {

        setupViewModel()
        setupRecyclerView()
        userListObserver()
    }

    private fun setupViewModel()
    {
        viewModel = ViewModelProvider(this , factory).get(UsersViewModel::class.java)
    }


    private fun userListObserver()
    {

        viewModel.getUserListLiveData().observe(this){

            when(it.status)
            {

                Status.SUCCESS -> {
                    binding.inProgress = false
                    adapter.submitList(it.data!!)}

                Status.LOADING -> binding.inProgress = true

                Status.ERROR -> {
                    binding.inProgress = false

                    Toast.makeText(this , getString(R.string.error_occurred) , Toast.LENGTH_SHORT).show()


                }


            }

        }

    }
    private fun setupRecyclerView()
    {
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = UserListAdapter()
        binding.rvUsers.adapter = adapter


    }
}