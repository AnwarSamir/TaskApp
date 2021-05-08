package com.codestomp.task.ui

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.codestomp.task.data.network.Resource
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        // TODO make flges if you want to clear stake and so on
        // it.flags
        startActivity(it)
    }
}


fun View.showView(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun View.enable(isViewEnabled: Boolean) {
    isEnabled = isViewEnabled
}


fun Fragment.handleApiErrors(failure: Resource.Failure, retry: (() -> Unit)?=null){
    when{
         failure.isNetworkError -> {
             requireView().snackBar("check your internet connection ",retry)
         }

        failure.errorCode==401 ->{
            requireView().snackBar("Error in email or password ")
        }
        failure.errorCode==404 ->{
            requireView().snackBar("This End point not Founded 404")
        }

        else ->{
            val error=failure.errorBody?.string()
            requireView().snackBar(error.toString())
        }
    }
}




fun View.snackBar(message:String,action: (()->Unit)?=null){
    val snackBar=Snackbar.make(this,message,LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}





fun EditText.text() = this.text.toString()


fun TextView.countDownTimer(timeInMill:Long, interval:Long)
{
    val timer = object: CountDownTimer(timeInMill, interval) {
        override fun onTick(millisUntilFinished: Long) {

            var diff = millisUntilFinished
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays = diff / daysInMilli
            diff %= daysInMilli

            val elapsedHours = diff / hoursInMilli
            diff %= hoursInMilli

            val elapsedMinutes = diff / minutesInMilli
            diff %= minutesInMilli

            val elapsedSeconds = diff / secondsInMilli

            text="$elapsedMinutes : $elapsedSeconds sec"

        }
        override fun onFinish() {

        }
    }
    timer.start()
}


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}


fun Fragment.circleLoader():CircularProgressDrawable
{
    val circularProgressDrawable = CircularProgressDrawable(this.requireActivity())
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}


inline infix fun <reified T : Any> T.merge(other: T): T {
    val nameToProperty = T::class.memberProperties.associateBy { it.name }
    val primaryConstructor = T::class.primaryConstructor!!
    val args = primaryConstructor.parameters.associateWith { parameter ->
        val property = nameToProperty[parameter.name]!!
        property.get(other) ?: property.get(this)
    }
    return primaryConstructor.callBy(args)
}





