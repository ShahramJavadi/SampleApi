package hsj.shahram.sampleapi.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.repository.MainRepo
import hsj.shahram.sampleapi.util.Resources
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(val repository: MainRepo) : ViewModel() {

    private val loginResultLiveData = MutableLiveData<Resources<Boolean>>()
    private val clickEvent  = LiveEvent<Int>()
    companion object{
        const val LOGIN_BUTTON_CLICKED = 1
    }


    fun setClickEvent(event : Int)
    {
        clickEvent.postValue(event)
    }
    @SuppressLint("CheckResult")
    fun login(userName: String, email: String) {


        loginResultLiveData.postValue(Resources.loading(null))

        repository.getUserList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {

                    Observable.fromIterable(it).filter {

                        it.userName == userName && it.email == email

                    }.toList()

                }
                .subscribe({

                    if (it == null || it.size == 0)
                        loginResultLiveData.postValue(Resources.success(false))
                    else{

                        saveUserData(it[0])
                        loginResultLiveData.postValue(Resources.success(true))

                    }


                }, { error ->
                    loginResultLiveData.postValue(Resources.error(null, error.message))
                })


    }



    fun saveUserData(user : ResponseObj.UserItem)
    {


        repository.saveUserData(user)

    }

    fun getLoginResultLiveData() : LiveData<Resources<Boolean>>
    {
        return loginResultLiveData

    }

     fun getClickEvent() : LiveEvent<Int>
    {
        return clickEvent

    }


}