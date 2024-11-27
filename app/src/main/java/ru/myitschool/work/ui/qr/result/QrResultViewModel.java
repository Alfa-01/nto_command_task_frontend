package ru.myitschool.work.ui.qr.result;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.myitschool.work.data.UserRepositoryImplementation;
import ru.myitschool.work.domain.entities.QrEntity;
import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.qr.PushQrUseCase;

public class QrResultViewModel extends ViewModel {
    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<State>();
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    public final PushQrUseCase pushQrUseCase = new PushQrUseCase(
            UserRepositoryImplementation.getInstance()
    );

    public void update(@NonNull String login, @NonNull String qr) {
        pushQrUseCase.execute(new QrEntity(login, qr), status -> mutableStateLiveData.postValue(fromStatus(status)));
    }

    private State fromStatus(Status<Boolean> status) {
        return new State(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage(): null,
                status.getValue() != null ? status.getValue() : false
        );
    }


    public class State {

        @Nullable
        private final String errorMessage;
        private final boolean isOpened;

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean isOpened() {
            return isOpened;
        }

        public State(@Nullable String errorMessage, boolean isOpened) {
            this.errorMessage = errorMessage;
            this.isOpened = isOpened;
        }
    }
}
