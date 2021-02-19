package hsj.shahram.sampleapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import hsj.shahram.sampleapi.MyApplication
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.databinding.ActivityMainBinding
import hsj.shahram.sampleapi.viewmodel.MainViewModel
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()


    }


    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }

    private fun init() {

        (application as MyApplication).appComponent.inject(this)
        setupViewModel()
        userInfoObserver()
        viewModel.getUserInfo()


    }


    private fun userInfoObserver() {

        viewModel.isVerifiedUserLiveData().observe(this) {

            if (it)
                navigateToDashboard()
            else
                navigateToLogin()

        }

    }

    private fun navigateToDashboard() {

        startActivity(Intent(this, DashBoardActivity::class.java))
        finish()


    }

    private fun navigateToLogin() {

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}