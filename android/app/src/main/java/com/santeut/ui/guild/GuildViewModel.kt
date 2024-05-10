package com.santeut.ui.guild

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santeut.data.model.response.GuildResponse
import com.santeut.domain.usecase.GuildUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuildViewModel @Inject constructor(
    private val guildUseCase: GuildUseCase
) : ViewModel() {

    private val _guilds = MutableLiveData<List<GuildResponse>>(emptyList())
    val guilds: LiveData<List<GuildResponse>> = _guilds

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _guild = MutableLiveData<GuildResponse>()
    val guild: LiveData<GuildResponse> = _guild

    fun getGuilds() {
        viewModelScope.launch {
            try {
                _guilds.postValue(guildUseCase.getGuilds())
            } catch (e: Exception) {
                _error.value = "길드 목록 조회 실패: ${e.message}"
            }
        }
    }

    fun myGuilds() {
        viewModelScope.launch {
            try {
                _guilds.postValue(guildUseCase.myGuilds())
            } catch (e: Exception) {
                _error.value = "내 길드 목록 조회 실패: ${e.message}"
            }
        }
    }

    fun getGuild(guildId: Int) {
        viewModelScope.launch {
            try {
                _guild.value = guildUseCase.getGuild(guildId)
            } catch (e: Exception) {
                _error.value = "길드 정보 조회 실패: ${e.message}"
            }
        }
    }

    fun applyGuild(guildId: Int){
        viewModelScope.launch {
            try {
                guildUseCase.applyGuild(guildId).collect{
                    Log.d("GuildViewModel", "가입 요청 전송")
                }
            }catch (e:Exception){
                _error.value = "가입 요청 전송 실패: ${e.message}"
            }
        }
    }
}