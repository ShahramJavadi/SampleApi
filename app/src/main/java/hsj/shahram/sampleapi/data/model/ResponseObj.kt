package hsj.shahram.sampleapi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

object ResponseObj{



    data class UserItem(val id :Int
    , val name :String
    ,@SerializedName("username") val userName : String
    , val email :String )




    @Parcelize
    data class Post(val userId : Int ?  , val id : Int?  ,val title : String?, var body : String?) : Parcelable


    data class Comment(val postId : Int ?  , val id : Int?  ,val name : String?,val email : String?,
                       var body : String?)
}