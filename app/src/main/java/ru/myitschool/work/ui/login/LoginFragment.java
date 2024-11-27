package ru.myitschool.work.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentLoginBinding;
import ru.myitschool.work.utils.OnChangeText;
import ru.myitschool.work.utils.Utils;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            if (sharedPreferences.getString("login", null) != null && getView() != null) {

                Navigation.findNavController(getView()).navigate(
                        R.id.action_loginFragment_to_userFragment);
            }
        }

        binding = FragmentLoginBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.username.addTextChangedListener(new OnChangeText() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                viewModel.changeLogin(s.toString());
            }
        });
        binding.login.setOnClickListener(v -> viewModel.confirm());
        subscribe(viewModel);
    }


    private void subscribe(LoginViewModel viewModel) {
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            binding.error.setVisibility(Utils.visibleOrGone(error != null));
            binding.error.setText(error);
        });
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            binding.login.setClickable(state.isButtonActive());
        });
        viewModel.openProfileLiveData.observe(getViewLifecycleOwner(), (unused) -> {

            if (getContext() != null) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", binding.username.getText().toString());
                editor.commit();
            }

            if (getView() == null) return;
            Log.d("process", "navigated");
            Navigation.findNavController(getView()).navigate(
                    R.id.action_loginFragment_to_userFragment);
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
