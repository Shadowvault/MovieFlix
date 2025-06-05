package com.shadowvault.auth.data.account.model

import com.shadowvault.auth.domain.account.model.AccountDetailsResult
import com.shadowvault.auth.domain.account.model.AvatarResult
import com.shadowvault.auth.domain.account.model.TmdbAvatarResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetailsResponse(
  val id: Int,
  val username: String,
  val name: String,
  val avatar: AvatarResponse,
)

@Serializable
data class AvatarResponse(val tmdb: TmdbAvatarResponse)

@Serializable
data class TmdbAvatarResponse(@SerialName("avatar_path") val avatarPath: String?)

fun AccountDetailsResponse.toAccountDetailsResult(): AccountDetailsResult {
  return AccountDetailsResult(
    id = id,
    username = username,
    name = name,
    avatar = avatar.toAvatarResult()
  )
}

fun AvatarResponse.toAvatarResult(): AvatarResult {
  return AvatarResult(
    tmdb = tmdb.toTmdbAvatarResult()
  )
}

fun TmdbAvatarResponse.toTmdbAvatarResult(): TmdbAvatarResult {
  return TmdbAvatarResult(
    avatarPath = avatarPath
  )
}

fun AccountDetailsResult.toAccountDetailsResponse(): AccountDetailsResponse {
  return AccountDetailsResponse(
    id = id,
    username = username,
    name = name,
    avatar = avatar.toAvatarResponse()
  )
}

fun AvatarResult.toAvatarResponse(): AvatarResponse {
  return AvatarResponse(
    tmdb = tmdb.toTmdbAvatarResponseApi()
  )
}

fun TmdbAvatarResult.toTmdbAvatarResponseApi(): TmdbAvatarResponse {
  return TmdbAvatarResponse(
    avatarPath = avatarPath
  )
}
