package ru.myitschool.work.ui.qr.result;

import static ru.myitschool.work.core.Constants.RESPONSE_KEY;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.myitschool.work.R;
import ru.myitschool.work.databinding.FragmentQrResultBinding;
import ru.myitschool.work.ui.qr.scan.QrScanDestination;
import ru.myitschool.work.utils.Utils;

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

        getParentFragmentManager().setFragmentResultListener(RESPONSE_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                resultQr = QrScanDestination.INSTANCE.getDataIfExist(result);

                if (getContext() != null && Utils.getLogin(getContext()) != null) {
                    viewModel.update(Utils.getLogin(getContext()), resultQr);
                }
            }
        });

        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            if (state.getErrorMessage() == null && state.isOpened()) {
                binding.result.setText(R.string.door_opened);
                binding.close.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.main_button, getContext().getTheme()));
            } else if (state.getErrorMessage() != null) {
                binding.result.setText(R.string.error);
            } else if (resultQr == null) {
                binding.result.setText(R.string.door_closed);
                binding.close.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.warn_button, getContext().getTheme()));
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
