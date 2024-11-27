package ru.myitschool.work.domain.qr;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.QrEntity;
import ru.myitschool.work.domain.entities.Status;

public class PushQrUseCase {
    private final QrRepository repository;

    public PushQrUseCase(QrRepository repository) {
        this.repository = repository;
    }
    public void execute(@NonNull QrEntity qrEntity, Consumer<Status<Boolean>> callback) {
        repository.pushQr(qrEntity, status -> {
            boolean isOpened = status.getStatusCode() == 200 || status.getStatusCode() == 401;
            callback.accept(
                    new Status<>(
                            status.getStatusCode(),
                            isOpened ? status.getStatusCode() == 200 : null,
                            status.getErrors()
                    )
            );
        });
    }
}
