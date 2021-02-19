package hsj.shahram.sampleapi.ui

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import hsj.shahram.sampleapi.R
import hsj.shahram.sampleapi.databinding.ActivityDashBoardBinding
import hsj.shahram.sampleapi.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_dash_board.*
import javax.inject.Inject

class DashBoardActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {


    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var drawer : DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)
        init()

    }

    private fun init() {




        initToolbar()
        nav_view.setNavigationItemSelectedListener(this)

        binding.userIcon.setOnClickListener {
            navigateToUserList() }

        binding.postIcon.setOnClickListener {   navigateToPostList() }

    }

    private fun initToolbar()
    {


        val toolbar: Toolbar = toolbar_main
        setSupportActionBar(toolbar)

        drawer = binding.drawer
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open , R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.setDrawerIndicatorEnabled(true);

        supportActionBar?.setHomeButtonEnabled(true)

    }

    private fun navigateToUserList()= startActivity(Intent(this,UsersActivity::class.java ))
    private fun navigateToPostList()= startActivity(Intent(this,PostListActivity::class.java ))


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.post_list ->  navigateToPostList()
            R.id.user_list ->  navigateToUserList()

        }
        return true
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }


}