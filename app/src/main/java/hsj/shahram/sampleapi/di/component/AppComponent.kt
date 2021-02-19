package hsj.shahram.sampleapi.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import hsj.shahram.film.di.module.NetworkModule
import hsj.shahram.film.di.module.ViewModelModule
import hsj.shahram.sampleapi.ui.*
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class , ViewModelModule::class])

interface AppComponent {



    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(dashBoardActivity: DashBoardActivity)
    fun inject(usersActivity: UsersActivity)
    fun inject(postListActivity: PostListActivity)
    fun inject(postDetailActivity: PostDetailActivity)


    @Component.Factory
    interface Factory
    {


        fun create(@BindsInstance context: Context) : AppComponent



    }

}