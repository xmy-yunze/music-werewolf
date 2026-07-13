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