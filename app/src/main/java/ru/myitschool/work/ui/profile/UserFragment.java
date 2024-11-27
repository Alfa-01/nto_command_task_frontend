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

import java.text.MessageFormat;

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

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            UserEntity entity = state.getItem();
            if (entity == null) {
                return;
            } else if (state.getErrorMessage() != null) {
                binding.error.setVisibility(View.VISIBLE);
                binding.error.setText(state.getErrorMessage());

                binding.logout.setVisibility(View.GONE);
                binding.scan.setVisibility(View.GONE);
            } else {
                binding.photo.setVisibility(Utils.visibleOrGone(entity.getPhotoUrl() != null));
                binding.position.setVisibility(Utils.visibleOrGone(entity.getPosition() != null));
                binding.lastEntry.setVisibility(Utils.visibleOrGone(entity.getLast_visit() != null));

                binding.fullname.setText(entity.getName());
                binding.position.setText(entity.getPosition());
                String lastVisit = entity.getLast_visit();
                binding.lastEntry.setText(MessageFormat.format("{0} {1}",
                        lastVisit.substring(0, 10), lastVisit.substring(11, 16)));

                if (entity.getPhotoUrl() != null) {
                    Picasso.get().load(entity.getPhotoUrl()).into(binding.photo);
                }
            }
        });

        if (getContext() != null) {
            SharedPreferences preferences = getContext().getSharedPreferences(
                    "login", Context.MODE_PRIVATE);
            viewModel.update(preferences.getString("login", ""));
        }

        binding.refresh.setOnClickListener(v -> {
            if (getContext() != null) {
                SharedPreferences preferences = getContext().getSharedPreferences(
                        "login", Context.MODE_PRIVATE);
                viewModel.update(preferences.getString("login", ""));
            }
        });

        binding.scan.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(
                R.id.action_userFragment_to_qrScanFragment));

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

        getParentFragmentManager().setFragmentResultListener(QrScanDestination.REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (QrScanDestination.INSTANCE.getDataIfExist(result) != null) {
                    getParentFragmentManager().setFragmentResult(QrScanDestination.REQUEST_KEY, result);
                    Navigation.findNavController(getView()).navigate(
                            R.id.action_userFragment_to_qrResultFragment);
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
