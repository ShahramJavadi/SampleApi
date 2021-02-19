package hsj.shahram.sampleapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hsj.shahram.sampleapi.repository.MainRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository : MainRepo) : ViewModel() {

    private val isVerifiedUserLiveData = MutableLiveData<Boolean>()


    fun getUserInfo(){


        val userInfo = repository.getUserInfo()

        if(userInfo == null  || userInfo.equals(""))

            isVerifiedUserLiveData.postValue(false)


        else

            isVerifiedUserLiveData.postValue(true)



    }





    fun isVerifiedUserLiveData():LiveData<Boolean>
    {
        return isVerifiedUserLiveData
    }

}