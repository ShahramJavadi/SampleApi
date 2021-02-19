package hsj.shahram.sampleapi.data.local

import android.content.Context
import android.content.SharedPreferences
import hsj.shahram.sampleapi.util.Const
import javax.inject.Inject

class Pref @Inject constructor(context: Context) {

    val pref : SharedPreferences = context.getSharedPreferences(Const.PREF_NAME
            , Context.MODE_PRIVATE);

    companion object{

        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_ID = "user_Id"




    }


    fun putString(key : String , value : String?) =
            pref.edit().putString(key , value).apply()






    fun getString(key : String)
            = pref.getString(key , "")


}