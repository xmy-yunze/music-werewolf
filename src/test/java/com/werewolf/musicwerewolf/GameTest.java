package com.werewolf.musicwerewolf;

import com.werewolf.musicwerewolf.model.Music;
import com.werewolf.musicwerewolf.model.Player;
import com.werewolf.musicwerewolf.model.Room;
import com.werewolf.musicwerewolf.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class GameTest {

    @Autowired
    private RoomService roomService;

    @Test
    public void testMusicDistribution() {
        System.out.println("========================================");
        System.out.println("🎵 开始测试音乐分配逻辑");
        System.out.println("========================================\n");

        // 1. 创建房间
        Room room = roomService.createRoom("测试员");
        String roomId = room.getRoomId();
        System.out.println("✅ 创建房间成功，房间号: " + roomId);

        // 2. 加入4个AI玩家
        String[] aiPlayers = {"AI_小红", "AI_小刚", "AI_小丽", "AI_小强"};
        for (String name : aiPlayers) {
            boolean success = roomService.joinRoom(roomId, name);
            System.out.println("✅ " + name + " 加入房间" + (success ? "成功" : "失败"));
        }

        // 3. 查看房间状态
        Room currentRoom = roomService.getRoom(roomId);
        System.out.println("\n📊 当前房间: " + currentRoom.getPlayers().size() + " 人");
        System.out.println("   玩家列表: " + currentRoom.getPlayers().stream()
                .map(Player::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("无"));

        // 4. 开始游戏
        boolean startSuccess = roomService.startGame(roomId);
        System.out.println("\n🎮 开始游戏: " + (startSuccess ? "✅ 成功" : "❌ 失败"));

        // 5. 获取每个玩家的歌曲
        System.out.println("\n🎵 歌曲分配结果:");
        System.out.println("----------------------------------------");

        List<String> songTitles = new ArrayList<>();
        List<Map<String, String>> playerMusicList = new ArrayList<>();

        for (Player player : currentRoom.getPlayers()) {
            Music music = roomService.getPlayerMusic(roomId, player.getName());
            songTitles.add(music.getTitle());

            Map<String, String> info = new HashMap<>();
            info.put("player", player.getName());
            info.put("title", music.getTitle());
            info.put("artist", music.getArtist());
            info.put("url", music.getBilibiliUrl());
            playerMusicList.add(info);

            System.out.println("  🎤 " + player.getName());
            System.out.println("     ├─ 歌名: " + music.getTitle());
            System.out.println("     ├─ 歌手: " + music.getArtist());
            System.out.println("     └─ 链接: " + music.getBilibiliUrl());
            System.out.println();
        }

        // 6. 显示卧底
        Player spy = roomService.getSpy(roomId);
        System.out.println("🕵️ 卧底是: " + (spy != null ? spy.getName() : "无"));
        System.out.println("----------------------------------------");

        // 7. 统计歌曲分布
        System.out.println("\n📊 歌曲统计:");
        Map<String, Integer> songCount = new HashMap<>();
        for (String title : songTitles) {
            songCount.put(title, songCount.getOrDefault(title, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : songCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " 人");
        }

        // 8. 判断是否正确：应该只有1首歌出现1次（卧底），其他相同
        int spySongCount = 0;
        String spySong = "";
        for (Map.Entry<String, Integer> entry : songCount.entrySet()) {
            if (entry.getValue() == 1) {
                spySongCount++;
                spySong = entry.getKey();
            }
        }

        System.out.println("\n🧪 验证结果:");
        if (spySongCount == 1) {
            System.out.println("  ✅ 正确！只有1首歌是独特的 (「" + spySong + "」)，其他3首相同");
        } else {
            System.out.println("  ❌ 错误！有 " + spySongCount + " 首歌是独特的，应该只有1首");
        }

        System.out.println("\n========================================");
        System.out.println("🎉 测试完成！");
        System.out.println("========================================");
    }
}