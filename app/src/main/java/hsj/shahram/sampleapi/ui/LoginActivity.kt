package hsj.shahram.sampleapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import hsj.shahram.sampleapi.MyApplication
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.databinding.ActivityLoginBinding
import hsj.shahram.sampleapi.util.Status
import hsj.shahram.sampleapi.viewmodel.LoginViewModel
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.view.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {


    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        (application as MyApplication).appComponent.inject(this)

        setupViewModel()
        init()

    }

    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)


    }


    private fun init() {
        binding.viewModel = viewModel
        clickEventObserver()
        loginResultObserver()


    }

    private fun clickEventObserver() {

        viewModel.getClickEvent().observe(this) {

            when (it) {

                LoginViewModel.LOGIN_BUTTON_CLICKED -> checkEntry()


            }

        }


    }

    private fun checkEntry() {


        val userName = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()

        if (userName.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_user_name_or_email)
                    , Toast.LENGTH_SHORT).show()

            return
        }

        viewModel.login(userName, email)


    }

    private fun loginResultObserver() {

        viewModel.getLoginResultLiveData().observe(this) {

            when (it.status) {
                Status.ERROR -> {
                    binding.inProgress = false
                    Toast.makeText(this, getString(R.string.error_occurred),
                            Toast.LENGTH_SHORT).show()

                }
                Status.LOADING -> {
                    binding.inProgress = true
                }
                Status.SUCCESS -> {
                    binding.inProgress = false
                    if (it.data!!)

                        navigateToDashboard()
                        else
                        Toast.makeText(this, getString(R.string.wrong_username_or_email),
                                Toast.LENGTH_SHORT).show()



                }


            }

        }

    }


    private fun navigateToDashboard()
    {

        startActivity(Intent(this , DashBoardActivity::class.java))

    }

}