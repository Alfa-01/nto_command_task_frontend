package ru.myitschool.work.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.myitschool.work.data.UserRepositoryImplementation;
import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.domain.user.GetUserByIdUseCase;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<State>();
    public final LiveData<State> stateLiveData = mutableStateLiveData;

    public final GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCase(
            UserRepositoryImplementation.getInstance()
    );



    public UserViewModel(String id) {
        update(id);
    }

    public void update(@NonNull String id) {
        mutableStateLiveData.setValue(new State(null, null, true));
        getUserByIdUseCase.execute(id, status -> {
            mutableStateLiveData.postValue(fromStatus(status));
        });
    }

    private State fromStatus(Status<UserEntity> status) {
        return new State(
                status.getErrors() != null ? status.getErrors().getLocalizedMessage(): null,
                status.getValue(),
                false
        );
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
