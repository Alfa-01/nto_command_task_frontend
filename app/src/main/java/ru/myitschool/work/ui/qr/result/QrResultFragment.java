package ru.myitschool.work.ui.qr.result;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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

        NavController navController = NavHostFragment.findNavController(this);
        MutableLiveData<Bundle> liveData = navController.getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData(QrScanDestination.REQUEST_KEY);
        liveData.observe(getViewLifecycleOwner(), new Observer<Bundle>() {
            @Override
            public void onChanged(Bundle s) {
                resultQr = QrScanDestination.INSTANCE.getDataIfExist(s);
            }
        });

        if (getView() != null && resultQr == null) {
            Navigation.findNavController(getView()).navigate(R.id.action_qrResultFragment_to_qrScanFragment);
        }

        binding = FragmentQrResultBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(QrResultViewModel.class);
        viewModel.stateLiveData.observe(getViewLifecycleOwner(), state -> {
            if (state.getErrorMessage() == null && state.isOpened()) {
                binding.result.setText(R.string.door_opened);
            } else if (state.getErrorMessage() != null) {
                binding.result.setText(R.string.error);
                Toast.makeText(getContext(), state.getErrorMessage(), Toast.LENGTH_SHORT).show();
            } else {
                binding.result.setText(R.string.error);
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        if (getContext() != null) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            String login = sharedPreferences.getString("login", null);
            if (login != null && resultQr != null) {
                viewModel.update(login, resultQr);
            }
        }

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
