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

class PostListViewModel @Inject constructor(val repository : MainRepo)  :ViewModel() {

    private val postListLiveData  = MutableLiveData<Resources<MutableList<ResponseObj.Post>>>()
    private val editPostLiveData  = MutableLiveData<Resources<ResponseObj.Post>>()
    private val deletePostLiveData  = MutableLiveData<Resources<Boolean>>()
    private val createdPostLiveData  = MutableLiveData<Resources<ResponseObj.Post>>()



    init {
        getPostList()
    }
    @SuppressLint("CheckResult")
    fun deletePost(postId : Int){

        deletePostLiveData.postValue(Resources.loading(null))
        repository.deletePost(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({deletePostLiveData.postValue(Resources.success(true))}
                , { error -> deletePostLiveData.postValue(Resources.error(false , error.message))})



    }

    fun getUserId() :String?
    {
        return repository.getUserInfo()
    }


    @SuppressLint("CheckResult")
    fun createPost(post: ResponseObj.Post)
    {

        createdPostLiveData.postValue(Resources.loading(null))

        repository.createPost(post).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value->  createdPostLiveData.postValue(Resources.success(value))
                }, {error ->  createdPostLiveData.postValue(Resources.error(null , error.message))
                })


    }

    @SuppressLint("CheckResult")
    fun editPost(post: ResponseObj.Post)
    {

        editPostLiveData.postValue(Resources.loading(null))

        repository.editPost(post).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value->  editPostLiveData.postValue(Resources.success(value))
                }, {error ->  editPostLiveData.postValue(Resources.error(null , error.message))
                })
    }


    @SuppressLint("CheckResult")
    private fun getPostList(){


        postListLiveData.postValue(Resources.loading(null))

        repository.getPostList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({value->  postListLiveData.postValue(Resources.success(value))
                }, {error ->  postListLiveData.postValue(Resources.error(null , error.message))
                })

    }




    fun getPostListLiveData() :LiveData<Resources<MutableList<ResponseObj.Post>>> = postListLiveData
    fun getEditedPostLiveData() :LiveData<Resources<ResponseObj.Post>> = editPostLiveData
    fun getDeletedPostLiveData() :LiveData<Resources<Boolean>> = deletePostLiveData
    fun getCreatedPostLiveData() :LiveData<Resources<ResponseObj.Post>> = createdPostLiveData
}