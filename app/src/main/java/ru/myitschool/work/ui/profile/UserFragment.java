package ru.myitschool.work.ui.profile;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentUserBinding;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.utils.Utils;

public class UserFragment extends Fragment {

    private static final String KEY_ID = "id";

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
            } else {
                binding.photo.setVisibility(Utils.visibleOrGone(entity.getPhoto() != null));
                binding.position.setVisibility(Utils.visibleOrGone(entity.getPosition() != null));
                binding.lastEntry.setVisibility(Utils.visibleOrGone(entity.getLast_visit() != null));

                binding.fullname.setText(entity.getName());
                binding.position.setText(entity.getPosition());
                binding.lastEntry.setText(entity.getLast_visit());

                if (entity.getPhoto() != null) {
                    Picasso.get().load(entity.getPhoto()).into(binding.photo);
                }
            }
        });

        String id = getArguments() != null ? getArguments().getString(KEY_ID) : null;
        if (id == null) throw new IllegalStateException("ID is null");
        viewModel.update(id);
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public static Bundle getBundle(@NonNull String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, id);
        return bundle;
    }
}
