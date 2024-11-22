package ru.myitschool.work.domain.qr;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.QrEntity;
import ru.myitschool.work.domain.entities.Status;

public interface QrRepository {

    void pushQr(@NonNull QrEntity qrEntity, @NonNull Consumer<Status<Void>> callback);
}
