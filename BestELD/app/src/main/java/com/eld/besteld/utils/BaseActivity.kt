
package com.eld.besteld.utils
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


abstract class BaseActivity(activityMain: Int) : AppCompatActivity(), View.OnClickListener {

    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (connectivityManager != null) {

                val activeNetwork = connectivityManager.activeNetworkInfo
                activeNetwork != null && activeNetwork.isConnectedOrConnecting
            } else {
                false
            }
        }

        fun showToast(message: String) {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            val view = toast.view
            val text = view?.findViewById(android.R.id.message) as TextView
            text.textSize = 18f
            toast.show()
        }


        fun addFragment(layoutResId: Int, fragment: Fragment, tag: String) {
            if (supportFragmentManager.findFragmentByTag(tag) == null)
                supportFragmentManager.beginTransaction()
                    .add(layoutResId, fragment, tag)
                    .commit()
        }

        fun addFragmentWithBackstack(layoutResId: Int, fragment: Fragment, tag: String) {
            supportFragmentManager.beginTransaction()
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit()
        }


        fun replaceFragment(layoutResId: Int, fragment: Fragment, tag: String) {
            if (supportFragmentManager.findFragmentByTag(tag) == null)
                supportFragmentManager.beginTransaction()
                    .addToBackStack("")
                    .replace(layoutResId, fragment, tag)
                    .commit()
        }

        fun replaceFragmentWithBackstack(mTitle: String?, layoutResId: Int, fragment: Fragment) {
            val fragmentManager = supportFragmentManager
//        fragmentManager.popBackStack(mTitle, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(layoutResId, fragment, mTitle)
                .addToBackStack(mTitle)
                .commit()
        }

        fun replaceFragmentWithBackstackWithStateLoss(
            layoutResId: Int,
            fragment: Fragment,
            tag: String
        ) {
            supportFragmentManager.beginTransaction()
                .replace(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss()
        }
    }



}