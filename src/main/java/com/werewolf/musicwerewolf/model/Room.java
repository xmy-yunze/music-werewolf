package com.werewolf.musicwerewolf.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Room {
    private String roomId;           // 房间号（6位）
    private String hostName;         // 房主名字
    private List<Player> players;    // 玩家列表
    private int maxPlayers = 10;      // 最大人数（先固定4人）
    private GameState state = GameState.WAITING;
    private List<Music> assignedMusic;
    private int spyIndex = -1;
    private Long gameStartTime;  // 游戏开始的时间戳（毫秒）
    private int gameDuration = 150;  // 游戏时长（秒），默认2分30秒
    private Map<String, Boolean> voteStatus = new HashMap<>();  // 记录谁已经投票

    public Room() {
        this.players = new ArrayList<>();
    }

    public Room(String roomId, String hostName) {
        this.roomId = roomId;
        this.hostName = hostName;
        this.players = new ArrayList<>();
        // 房主自动加入房间
        this.players.add(new Player(hostName, roomId));
        this.voteStatus = new HashMap<>();
    }

    // 判断房间是否已满
    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    // 添加玩家
    public boolean addPlayer(Player player) {
        if (isFull()) {
            return false;
        }
        // 检查名字是否重复
        for (Player p : players) {
            if (p.getName().equals(player.getName())) {
                return false;
            }
        }
        players.add(player);
        return true;
    }
    // 移除玩家
    public boolean removePlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(playerName)) {
                players.remove(i);
                return true;
            }
        }
        return false;
    }

}