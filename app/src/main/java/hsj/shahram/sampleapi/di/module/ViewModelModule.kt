package hsj.shahram.film.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hsj.shahram.sampleapi.di.key.ViewModelKey
import hsj.shahram.sampleapi.viewmodel.*

@Module
abstract class ViewModelModule {

    @Binds
   abstract fun provideFactory(v : ViewModelFactory) : ViewModelProvider.Factory

   @Binds
   @IntoMap
   @ViewModelKey(MainViewModel::class)
   abstract fun provideMainViewModel(mainViewModel : MainViewModel) : ViewModel

   @Binds
   @IntoMap
   @ViewModelKey(LoginViewModel::class)
   abstract fun provideLoginViewModel(loginViewModel : LoginViewModel) : ViewModel


   @Binds
   @IntoMap
   @ViewModelKey(UsersViewModel::class)
   abstract fun provideUsersViewModel(usersViewModel: UsersViewModel) : ViewModel

   @Binds
   @IntoMap
   @ViewModelKey(PostListViewModel::class)
   abstract fun providePostListViewModel(postListViewModel: PostListViewModel) : ViewModel

   @Binds
   @IntoMap
   @ViewModelKey(PostDetailViewModel::class)
   abstract fun providePostDetailViewModel(postDetailViewModel: PostDetailViewModel) : ViewModel
}