package ru.myitschool.work.ui.qr.result;

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

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentQrResultBinding;
import ru.myitschool.work.ui.qr.scan.QrScanDestination;

public class QrResultFragment extends Fragment {

    private FragmentQrResultBinding binding;
    private String resultQr;
    private QrResultViewModel viewModel;

    public QrResultFragment() {
        super(R.layout.fragment_qr_result);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQrResultBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(QrResultViewModel.class);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                resultQr = QrScanDestination.INSTANCE.getDataIfExist(result);

                if (resultQr == null) {
                    binding.result.setText(R.string.door_closed);
                }

                viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
                    if (state.getErrorMessage() == null && state.isOpened()) {
                        binding.result.setText(R.string.door_opened);
                    } else if (state.getErrorMessage() != null) {
                        binding.result.setText(R.string.error);
                    }
                });

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                String login = sharedPreferences.getString("login", null);

                if (login != null) {
                    viewModel.update(login, resultQr);
                }
            }
        });

        binding.close.setOnClickListener(v -> {
            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(
                        R.id.action_qrResultFragment_to_userFragment);
            }
        });
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
