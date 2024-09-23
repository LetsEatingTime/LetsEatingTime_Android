package com.alt.letseatingtime_android.ui.fragment.login

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentLoginBinding
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.ui.activity.HomeActivity
import com.alt.letseatingtime_android.ui.activity.SignupActivity
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    /**
     * 버튼 누를때 모션
     *
     * @param boolean up/down
     */
    private fun loginBtnAnim(boolean: Boolean) {
        val startColor =
            ContextCompat.getColor(
                requireContext(),
                if (!boolean) R.color.button else R.color.button_push
            )
        val endColor = ContextCompat.getColor(
            requireContext(), if (!boolean) R.color.button else R.color.button_push
        )
        // ValueAnimator 생성
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        animator.duration = 60 // 애니메이션 지속 시간 (밀리초)

        animator.addUpdateListener {
            val animatedValue = animator.animatedValue as Int
            binding.btnLoginSubmit.background.setTint(animatedValue)
        }

        animator.start()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            exit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        binding.btnLoginSubmit.setOnClickListener(OnSingleClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            Log.d("test", "id : $id \n pw : $pw")
            if (id != "" && pw != "") {
                loginBtnAnim(true)
                binding.loginErrorMessage.text = ""
                Log.d("test", "id : $id \n pw : $pw")
                login(id = id, pw = pw)
                binding.loginErrorMessage.visibility = View.GONE
            } else {
                binding.loginErrorMessage.visibility = View.VISIBLE
                binding.loginErrorMessage.text = "비밀번호와 아이디를 입력해주세요"
            }
        })

        binding.tvSignup.setOnClickListener(OnSingleClickListener {
            Intent(requireContext(), SignupActivity::class.java).also {
                startActivity(it)
            }
        })
        binding.tvFindPw.setOnClickListener(OnSingleClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_changePasswordFragment)
        })
        return binding.root
    }


    private fun login(id: String, pw: String) {
        RetrofitClient.api.login(LoginRequest(id = id, password = pw))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val result = response.body()
                    if (response.isSuccessful) {
                        binding.loginErrorMessage.visibility = View.GONE
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        if (binding.cbLogin.isChecked) {
                            prefs.autoLogin = true
                            prefs.refreshToken = result?.data?.refreshToken.toString()
                            prefs.accessToken = result?.data?.accessToken.toString()
                        } else {
                            prefs.autoLogin = false
                            prefs.accessToken = result?.data?.accessToken.toString()
                        }
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        binding.loginErrorMessage.visibility = View.VISIBLE
                        binding.loginErrorMessage.text = "비밀번호나 아이디가 틀렸습니다"
                        loginBtnAnim(false)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    context?.shortToast("서버 애러")
                    Log.e("LoginFragment log", t.stackTraceToString())
//                    Toast.makeText(requireActivity(), "서버 애러", Toast.LENGTH_SHORT).show()
                    loginBtnAnim(false)
                }
            })
    }

    fun exit() {
        ActivityCompat.finishAffinity(requireActivity())
    }
}