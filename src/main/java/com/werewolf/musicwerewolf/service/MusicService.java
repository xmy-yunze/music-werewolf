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

    // 为游戏选歌（3首相同 + 1首不同）
    // 为游戏选歌（3首相同 + 1首不同）
    public List<Music> selectMusicForGame() {
        List<Music> result = new ArrayList<>();

        // 1. 随机选一首作为大众歌曲（3个人听一样的）
        Music commonMusic = getRandomMusic();

        // 2. 选一首不同的作为卧底歌曲
        Music spyMusic = getRandomMusic();
        // 确保不一样
        while (spyMusic.getId().equals(commonMusic.getId())) {
            spyMusic = getRandomMusic();
        }

        // 3. 构建结果列表：3首大众 + 1首卧底
        for (int i = 0; i < 3; i++) {
            result.add(commonMusic);
        }
        result.add(spyMusic);

        return result;
    }
}