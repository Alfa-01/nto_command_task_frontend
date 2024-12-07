package ru.myitschool.work.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
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
            if (Utils.getLogin(getContext()) != null && getView() != null)
                Navigation.findNavController(getView()).navigate(
                        R.id.action_loginFragment_to_userFragment);
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
        viewModel.errorLiveData.observe(getViewLifecycleOwner(), error ->
                binding.error.setVisibility(Utils.visibleOrGone(error != null)));
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            binding.login.setClickable(state.isButtonActive());
            if (state.isButtonActive()) {
                binding.login.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_button, getContext().getTheme()));
                binding.login.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, getContext().getTheme()));
            } else {
                binding.login.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.inactive_button, getContext().getTheme()));
                binding.login.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getContext().getTheme()));
            }
        });
        viewModel.openProfileLiveData.observe(getViewLifecycleOwner(), (unused) -> {

            if (getContext() != null) {
                Utils.saveLogin(binding.username.getText().toString(), getContext());
            }

            if (getView() == null) return;
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
