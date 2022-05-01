package cn.jm.happy.navfragment.persist

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("fragment")
class PersistFragmentNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(
    context, manager,
    containerId
) {
    companion object {
        private const val TAG = "PersistFragmentNavigator"
        private const val KEY_ROOT_ID = "key_root_id"
    }

    override fun popBackStack(): Boolean {
        return false
    }

    private var root: Int? = null
    override fun onSaveState(): Bundle? {
        return root?.let {
            Bundle().apply {
                putInt(KEY_ROOT_ID, it)
            }
        }
    }

    override fun onRestoreState(savedState: Bundle?) {
        if (savedState != null) {
            root = savedState.getInt(KEY_ROOT_ID)
        }
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (manager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state")
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }
        val ft = manager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        @IdRes val destId = destination.id
        val tag = generateFragmentTag(className, destId)
        val currentFrag = manager.primaryNavigationFragment
        currentFrag?.let {
            ft.setMaxLifecycle(currentFrag, Lifecycle.State.STARTED)
            ft.hide(currentFrag)
        }
        val destFrag = constructFragment(context, manager, className, args, tag)
        if (!destFrag.isAdded) {
            ft.add(containerId, destFrag, tag)
            ft.setMaxLifecycle(destFrag, Lifecycle.State.RESUMED)
        } else {
            ft.show(destFrag)
            ft.setMaxLifecycle(destFrag, Lifecycle.State.RESUMED)
        }
        Log.i("jiang", "dest:$destFrag $className")
        Log.i("jiang", "aa : $root")

        ft.setPrimaryNavigationFragment(destFrag)

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        if (root == null) {
            root = destId
            return destination
        }
        return null
    }

    private fun generateFragmentTag(className: String, destId: Int): String {
        return "$className-$destId"
    }

    private fun constructFragment(
        context: Context,
        fragmentManager: FragmentManager,
        className: String,
        args: Bundle?, tag: String
    ): Fragment {
        return fragmentManager.findFragmentByTag(tag) ?: super.instantiateFragment(
            context,
            fragmentManager,
            className,
            args
        )
    }
}