package ru.myitschool.work.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.myitschool.work.data.UserRepositoryImplementation;
import ru.myitschool.work.domain.login.IsUserExistUseCase;

public class LoginViewModel extends ViewModel {

    private final State INIT_STATE = new State(false);
    private final MutableLiveData<State> mutableStateLiveData = new MutableLiveData<State>(INIT_STATE);
    public final LiveData<State> stateLiveData = mutableStateLiveData;
    private final MutableLiveData<String> mutableErrorLiveData = new MutableLiveData<String>();
    public final LiveData<String> errorLiveData = mutableErrorLiveData;
    private final MutableLiveData<Void> mutableOpenProfileLiveData = new MutableLiveData<Void>();
    public final LiveData<Void> openProfileLiveData = mutableOpenProfileLiveData;

    private final IsUserExistUseCase isUserExistUseCase = new IsUserExistUseCase(
            UserRepositoryImplementation.getInstance()
    );

    @Nullable
    private String login = null;
    private boolean userCheckCompleted = false;
    public void changeLogin(@NonNull String login) {
        this.login = login;
        userCheckCompleted = !login.isBlank() &&
                login.length() >= 3 &&
                !Character.isDigit(login.charAt(0)) &&
                login.matches("^[a-zA-Z0-9]+$");

        mutableStateLiveData.postValue(new State(userCheckCompleted));
    }

    public void confirm() {
        checkUserExist();
    }

    private void checkUserExist() {
        final String currentLogin = login;
        if (currentLogin == null || currentLogin.isEmpty()) {
            mutableErrorLiveData.postValue("Login cannot be null");
            return;
        }
        isUserExistUseCase.execute(currentLogin, status -> {
            if (status.getValue() == null || status.getErrors() != null) {
                mutableErrorLiveData.postValue("Something went wrong. Try again later");
                return;
            }
            if (status.getStatusCode() == 401) {
                mutableErrorLiveData.postValue("There is no such login or incorrect");
            } else if (status.getStatusCode() == 200) {
                mutableOpenProfileLiveData.postValue(null);
            }
        });

    }

    public class State {
        private final boolean isButtonActive;

        public boolean isButtonActive() {
            return isButtonActive;
        }

        public State(boolean isButtonActive) {
            this.isButtonActive = isButtonActive;
        }
    }


}
