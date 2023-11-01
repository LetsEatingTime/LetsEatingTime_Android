package com.alt.letseatingtime_android.ui.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.Signup3Binding
import com.alt.letseatingtime_android.util.OnSingleClickListener
import java.util.regex.Pattern

class SignupFragment3 : Fragment() {
    private lateinit var binding: Signup3Binding
    private val signupFragment4 = SignupFragment4()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Signup3Binding.inflate(inflater, container, false)
        val pattern = Pattern.compile(LoginPattern.name)
        val id = arguments?.getString("id").toString()
        val pw = arguments?.getString("pw").toString()
        binding.btnSubmit.setOnClickListener( OnSingleClickListener{
            val name = binding.etName.text.toString()
            if (pattern.matcher(name).find()) {
                if (name != "") {
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    bundle.putString("pw",pw)
                    bundle.putString("name",name)
                    replaceFragment(signupFragment4, bundle)
                } else {
                    Toast.makeText(activity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "한글로 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }
}