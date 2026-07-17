package com.werewolf.musicwerewolf.service;

import com.werewolf.musicwerewolf.model.Music;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MusicService {

    private List<Music> musicLibrary = new ArrayList<>();

    public MusicService() {
        // 初始化音乐库
        musicLibrary.add(new Music("1", "起风了", "买辣椒也用券", "pop", "https://www.bilibili.com/video/BV1ft4y1G7so?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("2", "光年之外", "邓紫棋", "pop", "https://www.bilibili.com/video/BV1Zv4y1B71T?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("3", "Lemon", "米津玄師", "pop", "https://www.bilibili.com/video/BV1tY411B7T9?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("4", "稻香", "周杰伦", "pop", "https://www.bilibili.com/video/BV1Qyt6zQEG9?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("5", "阴天快乐", "陈奕迅", "pop", "https://www.bilibili.com/video/BV1Au411L7rw?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("6", "夜曲", "周杰伦", "old", "https://www.bilibili.com/video/BV1Ek4y1r7Rg?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("7", "晴天", "周杰伦", "old", "https://www.bilibili.com/video/BV1BZbSzZEGT?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("8", "十年", "陈奕迅", "old", "https://www.bilibili.com/video/BV1iR4y127wd?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("9", "The way I still love you", "The Beatles", "English", "https://www.bilibili.com/video/BV15F411t7br?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("10", "growl", "EXO", "Korean", "https://www.bilibili.com/video/BV1Jy411Y7LW?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("11", "孤勇者", "陈奕迅", "pop", "https://www.bilibili.com/video/BV1p24y1E73T?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("12", "演员", "薛之谦", "pop", "https://www.bilibili.com/video/BV1i14y117hX?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("13", "告白气球", "周杰伦", "pop", "https://www.bilibili.com/video/BV1mL411E7Fb?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("14", "小幸运", "田馥甄", "pop", "https://www.bilibili.com/video/BV1y82uB2ET4?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("15", "Mojito", "周杰伦", "pop", "https://www.bilibili.com/video/BV1PK4y1b7dt?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("16", "倒数", "邓紫棋", "pop", "https://www.bilibili.com/video/BV1Lu4m1g7DW?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("17", "说好不哭", "周杰伦", "pop", "https://www.bilibili.com/video/BV1gB8NzoETz?vd_source=20b31ed778ab04496489e76cd91a3734"));
        // 经典老歌 (old)
        musicLibrary.add(new Music("18", "七里香", "周杰伦", "old", "https://www.bilibili.com/video/BV1qD4y1U7fs?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("19", "搁浅", "周杰伦", "old", "https://www.bilibili.com/video/BV1uNEg6xEwA?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("20", "K歌之王", "陈奕迅", "old", "https://www.bilibili.com/video/BV1iN4y1J77s?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("21", "一路向北", "周杰伦", "old", "https://www.bilibili.com/video/BV11p4y1b7ej?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("22", "明明就", "周杰伦", "old", "https://www.bilibili.com/video/BV1h64y1q7az?vd_source=20b31ed778ab04496489e76cd91a3734"));

        // 英文歌曲 (english)
        musicLibrary.add(new Music("23", "Love Story", "Taylor Swift", "english", "https://www.bilibili.com/video/BV1VV4y167ER?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("24", "Blank Space", "Taylor Swift", "english", "https://www.bilibili.com/video/BV1ks411P7PT?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("25", "Shape of You", "Ed Sheeran", "english", "https://www.bilibili.com/video/BV1n24y1Y7KZ?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("26", "Perfect", "Ed Sheeran", "english", "https://www.bilibili.com/video/BV1ax4y137Bu?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("27", "See You Again", "Wiz Khalifa", "english", "https://www.bilibili.com/video/BV1qU4y1F73A?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("28", "Faded", "Alan Walker", "english", "https://www.bilibili.com/video/BV1rx411U7ux?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("29", "Monsters", "Katie Sky", "english", "https://www.bilibili.com/video/BV1Ze4y1F7CV?vd_source=20b31ed778ab04496489e76cd91a3734"));
        musicLibrary.add(new Music("30", "Wake", "Hillsong", "english", "https://www.bilibili.com/video/BV11P4y1K7Qp?vd_source=20b31ed778ab04496489e76cd91a3734"));
    }

    // 获取所有音乐
    public List<Music> getAllMusic() {
        return musicLibrary;
    }

    // 随机获取一首歌
    public Music getRandomMusic() {
        Random random = new Random();
        return musicLibrary.get(random.nextInt(musicLibrary.size()));
    }

    // 根据ID获取歌曲
    public Music getMusicById(String id) {
        for (Music music : musicLibrary) {
            if (music.getId().equals(id)) {
                return music;
            }
        }
        return null;
    }

    // 为游戏选歌（根据玩家数量生成音乐列表）
    public List<Music> selectMusicForGame(int playerCount) {
        List<Music> result = new ArrayList<>();

        // 1. 随机选一首作为大众歌曲
        Music commonMusic = getRandomMusic();

        // 2. 选一首不同的作为卧底歌曲
        Music spyMusic = getRandomMusic();
        // 确保不一样
        while (spyMusic.getId().equals(commonMusic.getId())) {
            spyMusic = getRandomMusic();
        }

        // 3. 构建结果列表：所有位置都是大众歌曲
        for (int i = 0; i < playerCount; i++) {
            result.add(commonMusic);
        }

        // 4. 返回大众歌曲和卧底歌曲，调用方需要根据spyIndex替换对应位置
        // 为了简化，我们返回一个包含两首歌曲的Map
        // 但为了保持接口简单，我们直接返回列表，让RoomService处理
        // 这里我们返回一个特殊格式的列表：最后一首是卧底歌曲
        result.add(spyMusic); // 暂时放在最后，RoomService会重新排列

        return result;
    }
}