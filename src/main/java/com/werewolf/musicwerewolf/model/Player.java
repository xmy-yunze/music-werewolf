package com.werewolf.musicwerewolf.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
    private String name;      // 玩家名字
    private String roomId;    // 所在房间号

    public Player() {}

    public Player(String name, String roomId) {
        this.name = name;
        this.roomId = roomId;
    }

}