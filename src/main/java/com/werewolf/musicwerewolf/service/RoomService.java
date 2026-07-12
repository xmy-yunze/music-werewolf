package com.werewolf.musicwerewolf.service;

import com.werewolf.musicwerewolf.model.GameState;
import com.werewolf.musicwerewolf.model.Music;
import com.werewolf.musicwerewolf.model.Player;
import com.werewolf.musicwerewolf.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class RoomService {
    @Autowired
    private MusicService musicService;
    // 用Map存储所有房间，key是房间号
    private Map<String, Room> rooms = new HashMap<>();

    // 生成6位随机房间号
    private String generateRoomId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    // 创建房间
    public Room createRoom(String hostName) {
        String roomId = generateRoomId();
        // 确保房间号不重复
        while (rooms.containsKey(roomId)) {
            roomId = generateRoomId();
        }
        Room room = new Room(roomId, hostName);
        rooms.put(roomId, room);
        return room;
    }

    // 根据房间号查找房间
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    // 玩家加入房间
    public boolean joinRoom(String roomId, String playerName) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return false; // 房间不存在
        }
        Player player = new Player(playerName, roomId);
        return room.addPlayer(player);
    }

    // 获取房间所有玩家
    public Map<String, Object> getRoomInfo(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return null;
        }
        Map<String, Object> info = new HashMap<>();
        info.put("roomId", room.getRoomId());
        info.put("hostName", room.getHostName());
        info.put("players", room.getPlayers());
        info.put("maxPlayers", room.getMaxPlayers());
        info.put("currentPlayers", room.getPlayers().size());
        info.put("isFull", room.isFull());
        info.put("state", room.getState());
        return info;
    }

    // 开始游戏（需要至少3人）
    public boolean startGame(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return false;
        if (room.getPlayers().size() < 3) return false;
        if (room.getState() != GameState.WAITING) return false;

        // 选歌
        List<Music> selectedMusic = musicService.selectMusicForGame();
        room.setAssignedMusic(selectedMusic);

        // 随机指定卧底
        Random random = new Random();
        room.setSpyIndex(random.nextInt(room.getPlayers().size()));

        room.setState(GameState.PLAYING);
        return true;
    }

    // 获取游戏状态
    public GameState getGameState(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return null;
        }
        return room.getState();
    }
    public Music getPlayerMusic(String roomId, String playerName) {
        Room room = rooms.get(roomId);
        if (room == null || room.getAssignedMusic() == null) return null;

        List<Player> players = room.getPlayers();
        List<Music> musicList = room.getAssignedMusic();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(playerName)) {
                return musicList.get(i % musicList.size());
            }
        }
        return null;
    }

    public Player getSpy(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;
        List<Player> players = room.getPlayers();
        int spyIndex = room.getSpyIndex();
        if (spyIndex >= 0 && spyIndex < players.size()) {
            return players.get(spyIndex);
        }
        return null;
    }
}