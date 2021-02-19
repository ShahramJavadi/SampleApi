package hsj.shahram.sampleapi.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hsj.shahram.sampleapi.data.model.ResponseObj
import hsj.shahram.sampleapi.repository.MainRepo
import hsj.shahram.sampleapi.util.Resources
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersViewModel @Inject constructor(val repository : MainRepo): ViewModel() {


    private val userListLiveData  = MutableLiveData<Resources<List<ResponseObj.UserItem>>>()

    init {
        getUserList()
    }


    @SuppressLint("CheckResult")
    private fun getUserList(){


        userListLiveData.postValue(Resources.loading(null))

        repository.getUserList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value->  userListLiveData.postValue(Resources.success(value))
                }, {error ->  userListLiveData.postValue(Resources.error(null , error.message))
                })

    }



    fun getUserListLiveData() : LiveData<Resources<List<ResponseObj.UserItem>>> = userListLiveData


}