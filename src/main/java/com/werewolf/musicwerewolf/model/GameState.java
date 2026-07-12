package com.werewolf.musicwerewolf.model;

public enum GameState {
    WAITING,   // 等待玩家加入
    PLAYING,   // 游戏进行中（播放音乐）
    VOTING,    // 投票阶段
    RESULT     // 显示结果
}