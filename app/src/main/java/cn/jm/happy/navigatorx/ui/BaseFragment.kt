package cn.jm.happy.navigatorx.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    companion object {
        private const val TAG = "BaseFragment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: $this")
    }

}