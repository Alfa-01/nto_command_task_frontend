package ru.myitschool.work.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.myitschool.work.data.UserRepositoryImplementation;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.domain.user.GetUserByLoginUseCase;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<>();
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    public final GetUserByLoginUseCase getUserByLoginUseCase = new GetUserByLoginUseCase(
            UserRepositoryImplementation.getInstance()
    );

    public void update(@NonNull String login) {
        mutableStateLiveData.setValue(new State(null, null, true));
        getUserByLoginUseCase.execute(login, status -> {
            mutableStateLiveData.postValue(new State(
                    status.getErrors() != null ? status.getErrors().getLocalizedMessage() : null,
                    status.getValue(),
                    false
            ));
        });
    }

    public class State {
        @Nullable
        private final String errorMessage;

        @Nullable
        private final UserEntity item;

        private final boolean isLoading;

        @Nullable
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public UserEntity getItem() {
            return item;
        }

        public boolean isLoading() {
            return isLoading;
        }

        public State(@Nullable String errorMessage, @Nullable UserEntity item, boolean isLoading) {
            this.errorMessage = errorMessage;
            this.item = item;
            this.isLoading = isLoading;
        }
    }
}
