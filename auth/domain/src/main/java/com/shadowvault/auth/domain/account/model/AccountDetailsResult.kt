package com.shadowvault.auth.domain.account.model

data class AccountDetailsResult(
  val id: Int,
  val username: String,
  val name: String,
  val avatar: AvatarResult,
)

data class AvatarResult(val tmdb: TmdbAvatarResult)

data class TmdbAvatarResult(val avatarPath: String?)
