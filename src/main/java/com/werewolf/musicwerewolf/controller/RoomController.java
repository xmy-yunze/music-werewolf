package com.werewolf.musicwerewolf.controller;

import com.werewolf.musicwerewolf.model.Music;
import com.werewolf.musicwerewolf.model.Room;
import com.werewolf.musicwerewolf.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 创建房间
    @PostMapping("/create")
    public Map<String, Object> createRoom(@RequestParam(value = "hostName", required = true) String hostName) {
        Map<String, Object> result = new HashMap<>();
        if (hostName == null || hostName.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "房主名字不能为空");
            return result;
        }
        Room room = roomService.createRoom(hostName);
        result.put("success", true);
        result.put("roomId", room.getRoomId());
        result.put("hostName", room.getHostName());
        result.put("message", "房间创建成功！");
        return result;
    }

    // 加入房间
    @PostMapping("/join")
    public Map<String, Object> joinRoom(@RequestParam(value = "roomId", required = true) String roomId,
                                        @RequestParam(value = "playerName", required = true) String playerName) {
        Map<String, Object> result = new HashMap<>();
        if (roomId == null || roomId.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "房间号不能为空");
            return result;
        }
        if (playerName == null || playerName.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "玩家名字不能为空");
            return result;
        }
        boolean success = roomService.joinRoom(roomId, playerName);
        if (success) {
            result.put("success", true);
            result.put("message", "加入房间成功！");
            result.put("roomId", roomId);
            result.put("playerName", playerName);
        } else {
            result.put("success", false);
            result.put("message", "加入失败，房间不存在或已满或名字重复");
        }
        return result;
    }

    // 查看房间信息
    @GetMapping("/info/{roomId}")
    public Map<String, Object> getRoomInfo(@PathVariable(value = "roomId") String roomId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> info = roomService.getRoomInfo(roomId);
        if (info == null) {
            result.put("success", false);
            result.put("message", "房间不存在");
            return result;
        }
        result.put("success", true);
        result.put("data", info);
        return result;
    }
    // 开始游戏
    @PostMapping("/start/{roomId}")
    public Map<String, Object> startGame(@PathVariable(value = "roomId") String roomId) {
        Map<String, Object> result = new HashMap<>();
        boolean success = roomService.startGame(roomId);
        if (success) {
            result.put("success", true);
            result.put("message", "游戏已开始！");
            result.put("state", roomService.getGameState(roomId));
        } else {
            result.put("success", false);
            result.put("message", "无法开始游戏，请检查房间是否存在、人数是否≥3、状态是否正确");
        }
        return result;
    }
    // 获取B站音乐链接
    @GetMapping("/bilibili/{roomId}/{playerName}")
    public Map<String, Object> getBilibiliMusic(
            @PathVariable(value = "roomId") String roomId,
            @PathVariable(value = "playerName") String playerName) {

        Map<String, Object> result = new HashMap<>();

        // 获取房间
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            result.put("success", false);
            result.put("message", "房间不存在");
            return result;
        }

        // 获取玩家的歌曲
        Music music = roomService.getPlayerMusic(roomId, playerName);
        if (music == null) {
            result.put("success", false);
            result.put("message", "未找到歌曲信息");
            return result;
        }

        result.put("success", true);
        result.put("musicTitle", music.getTitle());
        result.put("musicArtist", music.getArtist());
        result.put("bilibiliUrl", music.getBilibiliUrl());
        result.put("roomId", roomId);
        result.put("playerName", playerName);
        return result;
    }
    // 投票
    @PostMapping("/vote")
    public Map<String, Object> vote(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        String roomId = request.get("roomId");
        String voterName = request.get("voterName");
        String targetName = request.get("targetName");

        boolean success = roomService.vote(roomId, voterName, targetName);
        if (success) {
            result.put("success", true);
            result.put("message", "投票成功");
        } else {
            result.put("success", false);
            result.put("message", "投票失败");
        }
        return result;
    }

    // 获取结果
    @GetMapping("/result/{roomId}")
    public Map<String, Object> getResult(@PathVariable(value = "roomId") String roomId) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = roomService.getResult(roomId);
        if (data == null) {
            result.put("success", false);
            result.put("message", "未找到结果");
            return result;
        }
        result.put("success", true);
        result.put("data", data);
        return result;
    }

}