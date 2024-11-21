package ru.myitschool.work.ui.login;

import android.os.Bundle;
import android.text.Editable;
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

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            binding.login.setClickable(state.isButtonActive());
        });
        viewModel.openProfileLiveData.observe(getViewLifecycleOwner(), (unused) -> {
            View view = getView();
            if (view == null) return;

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
