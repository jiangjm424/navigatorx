package cn.jm.happy.navfragment.persist

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun AppCompatActivity.findNavControllerX(@IdRes id: Int): NavController {
    return (supportFragmentManager.findFragmentById(id) as? NavHostFragment)?.navController ?: throw IllegalStateException("no nav host fragment")
}