package ru.myitschool.work.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentUserBinding;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.ui.qr.scan.QrScanDestination;
import ru.myitschool.work.utils.Utils;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private UserViewModel viewModel;

    public UserFragment() {
        super(R.layout.fragment_user);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentUserBinding.bind(view);

        binding.scan.setOnClickListener(v -> {
            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(
                        R.id.action_userFragment_to_qrScanFragment);
            }
        });

        getParentFragmentManager().setFragmentResultListener(QrScanDestination.REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {

                if (getView() != null) {
                    Navigation.findNavController(getView()).navigate(
                            R.id.action_userFragment_to_qrResultFragment);
                }
            }
        });

        binding.logout.setOnClickListener(v -> {
            if (getContext() != null) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", null);
                editor.commit();
            }
            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(
                        R.id.action_userFragment_to_loginFragment);
            }
        });

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            UserEntity entity = state.getItem();
            if (entity == null) {
                return;
            } else {
                binding.photo.setVisibility(Utils.visibleOrGone(entity.getPhotoUrl() != null));
                binding.position.setVisibility(Utils.visibleOrGone(entity.getPosition() != null));
                binding.lastEntry.setVisibility(Utils.visibleOrGone(entity.getLast_visit() != null));

                binding.fullname.setText(entity.getName());
                binding.position.setText(entity.getPosition());
                binding.lastEntry.setText(entity.getLast_visit());

                if (entity.getPhotoUrl() != null) {
                    Picasso.get().load(entity.getPhotoUrl()).into(binding.photo);
                }
            }
        });

        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            String login = sharedPreferences.getString("login", null);
            if (login != null && getView() != null) {
                viewModel.update(login);
            }
        }

    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
