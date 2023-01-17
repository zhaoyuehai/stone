package com.yuehai.stone.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.yuehai.stone.core.datastore.serializer.TokenDataSerializer
import com.yuehai.stone.core.model.TokenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TOKEN_DATA = "token_data.pb"
private val Context.tokenData: DataStore<TokenProto> by dataStore(
    fileName = TOKEN_DATA, serializer = TokenDataSerializer
)

internal class DefaultTokenStore(context: Context) : TokenStore {
    private val dataStore = context.tokenData
    override suspend fun put(token: TokenData?) {
        dataStore.updateData {
            if (token == null) {
                TokenProto.getDefaultInstance()
            } else {
                it.toBuilder().setAccessToken(token.access_token).setTokenType(token.token_type)
                    .setRefreshToken(token.refresh_token).setExpiresIn(token.expires_in)
                    .setScope(token.scope).build()
            }
        }
    }

    override fun getStream(): Flow<TokenData?> =
        dataStore.data.catch { if (it is IOException) emit(TokenProto.getDefaultInstance()) else throw it }
            .map {
                if (it != TokenProto.getDefaultInstance()) {
                    TokenData(
                        token_type = it.accessToken,
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken,
                        expires_in = it.expiresIn,
                        scope = it.scope
                    )
                } else {
                    null
                }
            }
}