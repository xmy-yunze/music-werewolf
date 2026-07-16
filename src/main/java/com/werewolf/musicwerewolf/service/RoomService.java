package com.werewolf.musicwerewolf.service;

import com.werewolf.musicwerewolf.model.GameState;
import com.werewolf.musicwerewolf.model.Music;
import com.werewolf.musicwerewolf.model.Player;
import com.werewolf.musicwerewolf.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import java.util.stream.Collectors;

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

    // 玩家退出房间
    public boolean leaveRoom(String roomId, String playerName) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return false;
        }

        // 先检查是否是房主
        boolean isHost = room.getHostName().equals(playerName);

        // 先移除玩家
        boolean removed = room.removePlayer(playerName);

        // 如果移除的是房主且房间还有玩家，转移房主权限
        if (isHost && removed && !room.getPlayers().isEmpty()) {
            room.setHostName(room.getPlayers().get(0).getName());
        }

        return removed;
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
        info.put("state", room.getState());                    // ← 加上
        info.put("assignedMusic", room.getAssignedMusic());    // ← 加上
        info.put("spyIndex", room.getSpyIndex());              // ← 加上
        return info;
    }

    // 开始游戏（需要至少3人）
    public boolean startGame(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return false;
        if (room.getPlayers().size() < 3) return false;
        if (room.getState() != GameState.WAITING) return false;

        // 随机指定卧底
        Random random = new Random();
        int playerCount = room.getPlayers().size();
        int spyIndex = random.nextInt(playerCount);
        room.setSpyIndex(spyIndex);

        // 选歌
        List<Music> selectedMusic = musicService.selectMusicForGame(playerCount);

        // 重新排列音乐列表，确保spyIndex位置是卧底歌曲
        // MusicService返回的列表格式：前playerCount首是大众歌曲，最后一首是卧底歌曲
        Music commonMusic = selectedMusic.get(0);
        Music spyMusic = selectedMusic.get(selectedMusic.size() - 1);

        List<Music> finalMusicList = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            if (i == spyIndex) {
                finalMusicList.add(spyMusic);
            } else {
                finalMusicList.add(commonMusic);
            }
        }

        room.setAssignedMusic(finalMusicList);
        room.setGameStartTime(System.currentTimeMillis());
        room.getVoteStatus().clear();
        votes.entrySet().removeIf(entry -> entry.getKey().startsWith(roomId + "_"));  // 添加这一行
        for (Player p : room.getPlayers()) {
            room.getVoteStatus().put(p.getName(), false);
        }
        for (Player p : room.getPlayers()) {
            room.getVoteStatus().put(p.getName(), false);
        }
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

    // 投票
    private Map<String, Map<String, String>> votes = new HashMap<>();

    public boolean vote(String roomId, String voterName, String targetName) {
        Room room = rooms.get(roomId);
        if (room == null) return false;
        if (room.getState() != GameState.PLAYING && room.getState() != GameState.VOTING) return false;

        // 不能投自己
        if (voterName.equals(targetName)) return false;

        // 检查玩家是否存在
        boolean voterExists = room.getPlayers().stream().anyMatch(p -> p.getName().equals(voterName));
        boolean targetExists = room.getPlayers().stream().anyMatch(p -> p.getName().equals(targetName));
        if (!voterExists || !targetExists) return false;

        // 记录投票
        String key = roomId + "_" + voterName;
        votes.put(key, Map.of("target", targetName));

        // 标记该玩家已投票
        room.getVoteStatus().put(voterName, true);

        // 检查是否所有玩家都已投票
        boolean allVoted = room.getPlayers().stream()
                .allMatch(p -> room.getVoteStatus().getOrDefault(p.getName(), false));

        if (allVoted) {
            calculateResult(roomId);
        }

        return true;
    }

    // 计算结果
    public Map<String, Object> calculateResult(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;

        Map<String, Integer> voteCount = new HashMap<>();
        for (Player p : room.getPlayers()) {
            String key = roomId + "_" + p.getName();
            if (votes.containsKey(key)) {
                String target = votes.get(key).get("target");
                voteCount.put(target, voteCount.getOrDefault(target, 0) + 1);
            }
        }

        // 找出得票最多的人
        String mostVoted = null;
        int maxVotes = 0;
        for (Map.Entry<String, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                mostVoted = entry.getKey();
            }
        }

        Player spy = getSpy(roomId);
        boolean spyFound = mostVoted != null && mostVoted.equals(spy.getName());

        room.setState(GameState.RESULT);

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("spyName", spy.getName());
        result.put("spyFound", spyFound);
        result.put("voteStats", voteCount.entrySet().stream()
                .map(e -> Map.of("player", e.getKey(), "votes", e.getValue()))
                .collect(Collectors.toList()));

        return result;
    }

    public Map<String, Object> getResult(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;
        if (room.getState() != GameState.RESULT) {
            return null; // 如果还没结算，返回null而不是强制计算
        }
        // 从缓存读取结果
        return calculateResult(roomId);
    }

    // 获取投票进度
    public Map<String, Object> getVoteProgress(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;

        int totalPlayers = room.getPlayers().size();
        int votedCount = (int) room.getVoteStatus().values().stream().filter(v -> v).count();
        boolean allVoted = votedCount == totalPlayers;

        Map<String, Object> progress = new HashMap<>();
        progress.put("totalPlayers", totalPlayers);
        progress.put("votedCount", votedCount);
        progress.put("allVoted", allVoted);
        progress.put("voteStatus", room.getVoteStatus());
        return progress;
    }

    // 重置房间游戏状态（用于开始下一局）
    public boolean resetGame(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) return false;

        room.setState(GameState.WAITING);
        room.setAssignedMusic(null);
        room.setSpyIndex(-1);
        room.setGameStartTime(null);
        room.getVoteStatus().clear();

        return true;
    }
}