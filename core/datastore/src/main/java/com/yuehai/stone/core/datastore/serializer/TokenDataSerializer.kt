package com.yuehai.stone.core.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.yuehai.stone.core.datastore.TokenProto
import java.io.InputStream
import java.io.OutputStream

internal object TokenDataSerializer : Serializer<TokenProto> {
    override val defaultValue: TokenProto = TokenProto.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): TokenProto {
        try {
            return TokenProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TokenProto, output: OutputStream) {
        t.writeTo(output)
    }
}