package cn.jm.happy.navfragment.persist

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun findNavControllerX(fm: FragmentManager, @IdRes id: Int): NavController {
    val f = fm.findFragmentById(id) as NavHostFragment
    return f.navController
}