package ru.geekbrains.service;

import java.util.Optional;
import ru.geekbrains.entity.PictureData;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    PictureData createPictureData(byte[] picture);
}
