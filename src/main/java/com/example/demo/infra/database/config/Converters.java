package com.example.demo.infra.database.config;

import com.mongodb.lang.NonNull;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Converters {

    public static class BinaryToUuidConverter implements Converter<Binary, UUID> {
        @Override
        public UUID convert(@NonNull Binary binary) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(binary.getData());
            long high = byteBuffer.getLong();
            long low = byteBuffer.getLong();
            return new UUID(high, low);
        }
    }

    public static class UuidToBinaryConverter implements Converter<UUID, Binary> {
        @Override
        public Binary convert(@NonNull UUID uuid) {
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(uuid.getMostSignificantBits());
            bb.putLong(uuid.getLeastSignificantBits());
            return new Binary(bb.array());
        }
    }
}
