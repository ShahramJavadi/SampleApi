package hsj.shahram.sampleapi.di.key

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@MustBeDocumented
annotation class ViewModelKey(val type : KClass<out ViewModel>)
